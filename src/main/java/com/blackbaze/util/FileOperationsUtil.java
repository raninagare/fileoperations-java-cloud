package com.blackbaze.util;

import com.blackbaze.common.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * Utility class for common operations
 */
public class FileOperationsUtil {

    /**
     * List all the files available in the bucket
     * @param apiUrl : URL of the location from which you want to get files
     * @param accountAuthorizationToken
     * @param bucketId
     * @return List of File names
     * @throws IOException
     */
    public static List<String> getFileNames(String apiUrl, String accountAuthorizationToken, String bucketId) throws IOException {

        String postParams = "{\"bucketId\":\"" + bucketId + "\"}";
        String jsonResponse = null;
        List<String> files = new ArrayList<>();
        byte postData[] = postParams.getBytes( Constants.UTF_8 );

        HttpURLConnection connection = null;
        try {
            URL url = new URL( apiUrl + Constants.LIST_FILE_NAMES );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod( Constants.GET );
            connection.setRequestProperty( Constants.AUTHORIZATION, accountAuthorizationToken );
            connection.setRequestProperty( Constants.CONTENT_TYPE, Constants.X_WWW_FORM_URLENCODED );
            connection.setRequestProperty( Constants.CHARSET, Constants.UTF_8 );
            connection.setRequestProperty( Constants.CONTENT_LENGTH, Integer.toString( postData.length ) );
            connection.setDoOutput( true );

            DataOutputStream writer = new DataOutputStream( connection.getOutputStream() );
            writer.write( postData );
            jsonResponse = myInputStreamReader( connection.getInputStream() );
            JSONObject obj = new JSONObject( jsonResponse );
            JSONArray filesArray = obj.getJSONArray( Constants.FILES );

            for (int i = 0; i < filesArray.length(); i++) {
                String fileName = filesArray.getJSONObject( i ).getString( Constants.FILE_NAME );
                files.add( fileName );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return files;
    }

    /**
     * Helper method to read input stream
     * @param in InputStream
     * @return String
     * @throws IOException
     */
    private static  String myInputStreamReader(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader( in );
        StringBuilder sb = new StringBuilder();
        int c = reader.read();
        while (c != -1) {
            sb.append( (char) c );
            c = reader.read();
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Method to authorize account
     * @param applicationKeyId of the blackbaze account
     * @param applicationKey of the blackbaze account
     * @return Json Response
     */
    public static String accountAuthorization(String applicationKeyId, String applicationKey) {
        HttpURLConnection connection = null;
        String headerForAuthorizeAccount = Constants.BASIC + Base64.getEncoder().encodeToString( (applicationKeyId + ":" + applicationKey).getBytes() );
        String jsonResponse = null;
        try {
            URL url = new URL( Constants.AUTHORIZE_ACCOUNT );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod( Constants.GET );
            connection.setRequestProperty( Constants.AUTHORIZATION, headerForAuthorizeAccount );
            InputStream in = new BufferedInputStream( connection.getInputStream() );
            jsonResponse = myInputStreamReader( in );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return jsonResponse;
    }

    /**
     * This method will download single file by file name
     * @param downloadUrl URL from which you want to download the file
     * @param bucketName
     * @param fileName
     * @param accountAuthorizationToken
     * @param destinationDirectory folder name where you want to download the file
     */
    public static void downLodFileByName(String downloadUrl, String bucketName, String fileName, String accountAuthorizationToken, String destinationDirectory) {
        HttpURLConnection connection = null;
        byte downloadedData[] = null;
        try {
            URL url = new URL( downloadUrl + "/file/" + bucketName + "/" + fileName );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty( Constants.AUTHORIZATION, accountAuthorizationToken );
            connection.setRequestMethod( Constants.GET );
            connection.setDoOutput( true );
             InputStream is= connection.getInputStream();
             Files.copy(is, Paths.get(destinationDirectory+fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}
