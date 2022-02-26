package com.fandyadam.dailyupdate;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class KalendarApi {

    private final OkHttpClient httpClient;
    private final String codeApi;

    public KalendarApi(OkHttpClient httpClient,
                       @Value("${dailyupdate.kalendar.codeapi}") String codeApi) {
        this.codeApi = codeApi;
        this.httpClient = setUpClient();
    }

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient setUpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();
    }

    public void getHoliday(int year, int month) throws IOException {
        Request request = new Request.Builder().url("https://kalenderindonesia.com/api/" + codeApi + "/libur/masehi/" + year + "/" + month)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        System.out.println("FANN : " + responseString);
        JSONObject jsonObject = new JSONObject(responseString);
        System.out.println("FANN : " + jsonObject);

    }
}
