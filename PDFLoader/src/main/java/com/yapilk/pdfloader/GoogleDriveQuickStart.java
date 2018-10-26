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
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.File;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yapilk
 */
public class GoogleDriveQuickStart {
    private static final String APPLICATION_NAME = "PDF Loader";
    
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    //Global instance of the scopes
   private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    
   //Directory to store credentials for this application   
   private static final String CLIENT_SECRET_PATH = "/client_secret.json";
   
   public static Drive driveService;
   
   
   
   private static Credential getCredentials(final NetHttpTransport HTTP_Transport) throws FileNotFoundException, IOException, GeneralSecurityException
   {
       
       InputStream input = GoogleDriveQuickStart.class.getResourceAsStream(CLIENT_SECRET_PATH);
       System.out.println("input : " + input);
       GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(input));
       System.out.println("clientSecrets : " + clientSecrets);
       
       GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(HTTP_Transport, JSON_FACTORY, clientSecrets, SCOPES)
               .setDataStoreFactory(new MemoryDataStoreFactory())
               .setApprovalPrompt("force")
               .setAccessType("offline")
               .build();
       
       LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
       
       return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize("user");

   }
   
   public void driveQuickStart() throws GeneralSecurityException, IOException 
   {
       System.out.println("Client_secret : " + CLIENT_SECRET_PATH);
       
       //Build a new authorized API client service
       final NetHttpTransport HttpTransport = GoogleNetHttpTransport.newTrustedTransport();
       
       //Create Google Drive Service
       driveService = new Drive.Builder(HttpTransport, JSON_FACTORY, getCredentials(HttpTransport))
               .setApplicationName(APPLICATION_NAME)
               .build();
  }
   
   public List<File> getGoogleFilesByName(String extension) throws IOException, GeneralSecurityException {
 
        //Drive driveService = GoogleDriveUtils.getDriveService();
 
        String pageToken = null;
        List<File> list = new ArrayList<File>();
 
        String query = " fileExtension contains '" + extension + "' " //
                + " and mimeType != 'application/vnd.google-apps.folder' ";
 
        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    .setFields("nextPageToken, files(id, name, createdTime, mimeType)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return list;
    }
   
   public void downloadPDF(String fileId) throws IOException
   {
       OutputStream outputStream = new ByteArrayOutputStream();
       System.out.println("fileId : " + fileId);
       System.out.println("driveService : " + driveService);
       driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
   }
}
