package com.blackbaze;

import com.blackbaze.common.Constants;
import com.blackbaze.util.FileOperationsUtil;
import org.json.JSONObject;
import java.util.List;

/**
 * Main Entry point for this application
 */
public class FileOperationsApplication {
    static {
        System.out.println("Enter applicationKeyId,applicationKey,bucketId destinationDirectory and bucketName:");

    }

    public static void main(String[] args) {
        try {
            //Take applicationKeyId,applicationKey,bucketId from user
            String applicationKeyId=args[0];
            String applicationKey=args[1];
            String bucketId=args[2];
            String destinationDirectory=args[3];
            String bucketName=args[4];

            String jsonResponse=FileOperationsUtil.accountAuthorization(applicationKeyId,applicationKey);
            JSONObject obj = new JSONObject(jsonResponse);
            String apiUrl = (String)obj.get( Constants.API_URL );
            String authorizationToken = (String)obj.get( Constants.AUTHORIZATION_TOKEN );
            String downloadUrl = (String)obj.get( Constants.DOWNLOAD_URL );

            //List all the files available in the bucket
            List<String> files=FileOperationsUtil.getFileNames(apiUrl,authorizationToken,bucketId);

               //Download all the files available in the bucket in destinationDirectory
            for(String fileName:files){
                FileOperationsUtil.downLodFileByName( downloadUrl,bucketName, fileName,authorizationToken,destinationDirectory);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
