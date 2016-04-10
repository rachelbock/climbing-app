package com.rage.clamber.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by rage on 4/10/16.
 */
public class ApiManager {


    //emulator works with 10.0.2.2 will need to change when on phone
    private static final String API_URL = "http://10.0.2.2:8080/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private static final ClamberService clamberService = retrofit.create(ClamberService.class);

    public static ClamberService getClamberService(){
        return clamberService;
    }



}
