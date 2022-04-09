package com.fandyadam.dailyupdate.calendar;

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
public class CalendarIndonesiaApi {

    private final OkHttpClient httpClient;
    private final String calendarUrl;
    private final String calendarCodeApi;

    public CalendarIndonesiaApi(
        @Value("${calendar.url.kalenderindonesia}") String calendarUrl,
        @Value("${calendar.codeapi.kalenderindonesia}") String codeApi
    ) {
        this.calendarUrl = calendarUrl;
        this.calendarCodeApi = codeApi;

        this.httpClient = new HttpClient().getClient();
    }

    public boolean isHoliday(int year, int month, int day) throws IOException {
        Request request = new Request.Builder()
            .url(calendarUrl + "/" + calendarCodeApi + "/libur/masehi/" + year + "/" + month)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = new JSONObject(responseString);
        JSONObject objectData = jsonObject.getJSONObject("data");
        if (month != objectData.getInt("month")) {
            return false;
        }

        return isHoliday(day, objectData.getJSONObject("holiday").getJSONArray("data"))
            || isHoliday(day, objectData.getJSONObject("leave").getJSONArray("data"));
    }

    private boolean isHoliday(int day, JSONArray objectDataHolidays) {
        for (int i = 0; i < objectDataHolidays.length(); i++) {
            JSONObject holiday = objectDataHolidays.getJSONObject(i);
            if (day == holiday.getInt("day")) {
                return true;
            }
        }
        return false;
    }
}
