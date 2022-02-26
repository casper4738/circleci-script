package com.fandyadam.dailyupdate;

import com.fandyadam.util.HttpClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KalendarApi {

    private final OkHttpClient httpClient;
    private final String kalendarUrl;
    private final String kalendarCodeApi;

    public KalendarApi(
        @Value("${kalendar.url}") String kalendarUrl,
        @Value("${kalendar.codeapi}") String codeApi
    ) {
        this.kalendarUrl = kalendarUrl;
        this.kalendarCodeApi = codeApi;

        this.httpClient = new HttpClient().getClient();
    }

    public boolean isHoliday(int year, int month, int day) throws IOException {
        Request request = new Request.Builder()
            .url(kalendarUrl + "/" + kalendarCodeApi + "/libur/masehi/" + year + "/" + month)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = new JSONObject(responseString);
        JSONObject objectData = jsonObject.getJSONObject("data");
        if (month != objectData.getInt("month")) {
            return false;
        }

        JSONArray objectDataHolidays = objectData.getJSONObject("holiday").getJSONArray("data");
        for (int i = 0; i < objectDataHolidays.length(); i++) {
            JSONObject holiday = objectDataHolidays.getJSONObject(i);
            if (day == holiday.getInt("day")) {
                return true;
            }
        }

        return false;
    }
}
