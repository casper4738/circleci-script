package com.fandyadam.dailyupdate;

import com.fandyadam.util.HttpClient;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.fandyadam.util.HttpClient.JSON;

@Service
public class Office365Api {

    private static final Logger logger = LoggerFactory.getLogger(Office365Api.class);

    private final OkHttpClient httpClient;
    private final String incomingwebhookUrl;
    private final String incomingWebhookGroupExplorer;
    private final String incomingWebhookGroupDailyUpdate;

    public Office365Api(
        @Value("${incomingwebhook.url}") String incomingwebhookUrl,
        @Value("${incomingwebhook.group.explorer}") String incomingWebhookGroupExplorer,
        @Value("${incomingwebhook.group.dailyupdate}") String incomingWebhookGroupDailyUpdate
    ) {
        this.incomingwebhookUrl = incomingwebhookUrl;
        this.incomingWebhookGroupExplorer = incomingWebhookGroupExplorer;
        this.incomingWebhookGroupDailyUpdate = incomingWebhookGroupDailyUpdate;

        this.httpClient = new HttpClient().getClient();
    }

    public boolean webhookDaily(int year, int month, int day) throws IOException {
        return webhookDaily(year, month, day, false);
    }

    public boolean webhookDaily(int year, int month, int day, boolean groupTest) throws IOException {
        JSONObject objectSections = new JSONObject();
        objectSections.put("activityTitle", "DAILY UPDATE - " + day + "-" + month + "-" + year);
        objectSections.put("activitySubtitle", "Finish What You Start");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("themeColor", "0076D7");
        jsonObject.put("summary", "DAILY UPDATE - " + day + "-" + month + "-" + year);
        jsonObject.put("sections", new JSONObject[]{objectSections});

        Request request = new Request.Builder()
            .url(incomingwebhookUrl + "/" + (groupTest ? incomingWebhookGroupExplorer : incomingWebhookGroupDailyUpdate))
            .post(RequestBody.create(JSON, jsonObject.toString()))
            .build();

        Response response = httpClient.newCall(request).execute();
        return response.isSuccessful();
    }
}
