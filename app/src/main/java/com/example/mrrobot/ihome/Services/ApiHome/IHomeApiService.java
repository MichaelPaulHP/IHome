package com.example.mrrobot.ihome.Services.ApiHome;

import com.example.mrrobot.ihome.models.Localization;
import com.example.mrrobot.ihome.models.Log;
import com.example.mrrobot.ihome.models.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IHomeApiService {
    String authorization="";

    // get Token
//    @FormUrlEncoded
//    @Headers(SpotifyApiConstants.GETAUTHORIZATION)
//    @POST(SpotifyApiConstants.URL_TOKEN)
//    Call<Credential> getToken(@Field("grant_type") String client_credentials);

    // save localization
    @POST(ApiHomeConstants.URL_SAVE_LOCATION)
    Call<String> saveMyLocalization (@Body Localization localization);

    @POST(ApiHomeConstants.URL_MESSAGE_LOCATION)
    Call<String> sendMessage(@Body Log message);
    //Albums
    //@Headers("Authorization: Bearer {Token}")
    //GET(SpotifyApiConstants.URL_ALBUMS)
    //Observable<Album> getAlbums (@Header("Authorization") String Token, @Query("ids") String ids);
    //Call<List<Album>> getAlbums (@Header("Authorization") String Token, @Query("ids") String ids);

    //@GET(SpotifyApiConstants.URL_ALBUM)
    //Observable<Album> getAlbum (@Header("Authorization") String Token, @Path("id") String id);

    //@GET(SpotifyApiConstants.URL_ALBUM)
    //Call<Album> callgetAlbum (@Header("Authorization") String Token, @Path("id") String id);

    /*@GET(SpotifyApiConstants.ARTIST_SEARCH_URL)
    Observable<ArtistSearchResponse> searchArtist(@Query(SpotifyApiConstants.QUERY_TO_SEARCH) String query);*/
}
