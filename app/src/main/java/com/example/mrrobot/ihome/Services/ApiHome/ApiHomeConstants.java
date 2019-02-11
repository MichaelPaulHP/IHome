package com.example.mrrobot.ihome.Services.ApiHome;

import com.example.mrrobot.ihome.Config.ApiHome;
import com.example.mrrobot.ihome.Config.ServerSocketIO;

public class ApiHomeConstants {

    public static final String CLIENT_ID = "c76dffb02fff404fa92a59bc143a4b3e"; // Your client id
    public static final String CLIENT_SECRET = "b82b3249cb824f4cbc28ab1cc1427140"; // Your secret
    public static final String REDIRECT_URI = "";  // Your redirect uri
    public static  String TOKEN="";
    /*public static final String AUTHORIZATION ="Authorization: Basic "+ Base64.encode((CLIENT_ID+":"+CLIENT_SECRET).getBytes());*/
    public static final String GETAUTHORIZATION ="Authorization: Basic Yzc2ZGZmYjAyZmZmNDA0ZmE5MmE1OWJjMTQzYTRiM2U6YjgyYjMyNDljYjgyNGY0Y2JjMjhhYjFjYzE0MjcxNDA=";

    public static final String BASE_URL = ApiHome.URL_BASE; // example:URL_BASE="http://192.168.1.13/";

    public static final String URL_TOKEN = "https://accounts.spotify.com/api/token";
    public static final String URL_ALBUMS = "albums";
    public static final String URL_ALBUM = "albums/{id}";

    private static final String VERSION_PATH = "/v1";
    private static final String SEARCH_PATH = "/search";

    private static final String TYPE_QUERY = "type";
    private static final String QUERY_TO_SEARCH = "q";

    private static final String ARTIST = "artist";
    private static final String LOCATION = "location/";

    public  static final String URL_SAVE_LOCATION = LOCATION+"save/";

    public static final String ARTIST_SEARCH_URL = VERSION_PATH + SEARCH_PATH + "?"+ TYPE_QUERY + "=" + ARTIST;


}
