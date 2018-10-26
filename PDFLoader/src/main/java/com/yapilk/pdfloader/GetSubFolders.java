/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yapilk.pdfloader;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yapilk
 */
public class GetSubFolders {
    
    public static final List<File> getSubFolders(String parentFolderID) throws IOException, GeneralSecurityException
    {
        String pageToken = null;
        String query = null;
        List<File> fileList = new ArrayList<File>();
        
        Drive driveService = GoogleDriveUtils.getDriveService();
        
        
        if(parentFolderID == null)
        {
            query = " mimeType = 'application/vnd.google-apps.folder' "
                    + " and 'root' in parents";
        } else {
            query = " mimeType = 'application/vnd.google-apps.folder' "
                    + " and '" + parentFolderID + "' in parents";
        }
        
        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive")
                    .setFields("nextPageToken, files(id, name, createdTime)")
                    .setPageToken(pageToken)
                    .execute();
            
            for (File file : result.getFiles()) {
                fileList.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        
        return fileList;
        
    }
    
    public static final List<File> getGoogleRootFolders() throws IOException, GeneralSecurityException {
        return getSubFolders(null);
    }
    
}
