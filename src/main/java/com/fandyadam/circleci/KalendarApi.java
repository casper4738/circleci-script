package com.fandyadam.circleci;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class KalendarApi {

    private final String CODE_API = "APIGnkzE3esNM";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient httpClient;


    private void setUpClient() {
        httpClient = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();
    }

    private void kalendarApi(int year, int month) throws IOException {
        Request request = new Request.Builder().url("https://kalenderindonesia.com/api/" + CODE_API + "/libur/masehi/" + year + "/" + month)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        System.out.println("FANN : "+responseString);
        JSONObject jsonObject = new JSONObject(responseString);
        System.out.println("FANN : "+jsonObject);

    }
}
