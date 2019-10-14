package com.blackbaze.common;

public  class Constants {

    public  static final String FILES="files";
    public  static final String FILE_NAME="fileName";
    public static final String API_URL = "apiUrl";
    public static final String AUTHORIZATION_TOKEN = "authorizationToken";
    public static final String DOWNLOAD_URL = "downloadUrl";
    public static final String GET = "GET";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic ";
    public static final String POST = "POST";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CHARSET = "Charset";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String UTF_8 = "UTF-8";
    public static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    // we can read these urls from application.properties file also
    public static final String AUTHORIZE_ACCOUNT = "https://api.backblazeb2.com/b2api/v2/b2_authorize_account";
    public static final String LIST_FILE_NAMES = "/b2api/v2/b2_list_file_names";
}
