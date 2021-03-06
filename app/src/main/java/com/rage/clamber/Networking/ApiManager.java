package com.rage.clamber.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Singleton class to initialize Retrofit.
 */
public class ApiManager {


    //emulator works with 10.0.2.2. 71.56.152.102 for VM
    private static final String API_URL = "http://71.56.152.102:8080/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private static final ClamberService clamberService = retrofit.create(ClamberService.class);

    public static ClamberService getClamberService(){
        return clamberService;
    }
    public static String getImageUrl(String imageUrlSuffix) {
        return API_URL + imageUrlSuffix;
    }


}
