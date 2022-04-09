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
public class CalendarVercelApi {

    private final OkHttpClient httpClient;
    private final String calendarUrl;

    public CalendarVercelApi(@Value("${calendar.url.vercel}") String calendarUrl) {
        this.calendarUrl = calendarUrl;

        this.httpClient = new HttpClient().getClient();
    }

    public boolean isHoliday(int year, int month, int day) throws IOException {
        Request request = new Request.Builder()
            .url(calendarUrl + "?month=" + month + "&year=" + year)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        JSONArray objectDataHolidays = new JSONArray(responseString);
        for (int i = 0; i < objectDataHolidays.length(); i++) {
            JSONObject holiday = objectDataHolidays.getJSONObject(i);
            if (holiday.getString("holiday_date").equals(year + "-" + month + "-" + day) ||
                holiday.getString("holiday_date").equals(year + "-" + month + "-" + String.format("%02d", day)) ||
                holiday.getString("holiday_date").equals(year + "-" + String.format("%02d", month) + "-" + day) ||
                holiday.getString("holiday_date").equals(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day))
            ) {
                return true;
            }
        }

        return false;
    }
}
