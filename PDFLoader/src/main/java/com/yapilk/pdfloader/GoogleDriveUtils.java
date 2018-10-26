/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yapilk.pdfloader;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.auth.Credentials;

/**
 *
 * @author yapilk
 */
public class GoogleDriveUtils {
    
    private static final String APPLICATION_NAME = "PDF Loader";
    
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    //Global instance of the scopes
   private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    
   //Directory to store credentials for this application   
   private static final String CLIENT_SECRET_PATH = "/client_secret.json";
   
   private static Drive driveService;
   
   int datastoreCount = 0;
   
   
   public static Credential getCredentials(final NetHttpTransport HTTP_Transport) throws FileNotFoundException, IOException{
       InputStream input = GoogleDriveQuickStart.class.getResourceAsStream(CLIENT_SECRET_PATH);
       System.out.println("input : " + input);
       GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(input));
       System.out.println("clientSecrets : " + clientSecrets);
       
       GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_Transport, JSON_FACTORY, clientSecrets, SCOPES)
               .setDataStoreFactory(new MemoryDataStoreFactory())
               .setAccessType("offline")
               .build();
       
       LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
       
       return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize("user");
   }
   
   public static Drive getDriveService() throws IOException, GeneralSecurityException
   {
       final NetHttpTransport HttpTransport = GoogleNetHttpTransport.newTrustedTransport();
       
       if(driveService != null)
       {
           return driveService;
       }
       
       Credential credential = getCredentials(HttpTransport);
       
       driveService = new Drive.Builder(HttpTransport, JSON_FACTORY, credential)
               .setApplicationName(APPLICATION_NAME)
               .build();
       
       return driveService;
   }
   
   public void downloadPDF(String fileId, String filename) throws IOException
   {
       File file = new File(filename);
       
       try(FileOutputStream fileOutput = new FileOutputStream(file))
       {
           if(!file.exists())
           {
               file.createNewFile();
           }
           
           GoogleDriveQuickStart.driveService.files().get(fileId).executeMediaAndDownloadTo(fileOutput);
           
       }
       
   }
    
}
