package com.fandyadam.dailyupdate.katabijak;

import com.fandyadam.util.HttpClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class KataBijakApi {

    private final OkHttpClient httpClient;
    private final String githubRawUrl;
    private final String githubKatabijak;

    public KataBijakApi(
        @Value("${github.raw.url}") String githubRawUrl,
        @Value("${github.katabijak}") String githubKatabijak
    ) {
        this.githubRawUrl = githubRawUrl;
        this.githubKatabijak = githubKatabijak;

        this.httpClient = new HttpClient().getClient();
    }

    public List<Object> getListKataBijak() throws IOException {
        Request request = new Request.Builder()
            .url(githubRawUrl + "/" + githubKatabijak)
            .build();

        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        return new JSONArray(responseString).toList();
    }

    public String getRandomKataBijak() throws IOException {
        List<Object> list = getListKataBijak();
        Random r = new Random();
        int result = r.nextInt(list.size());
        return list.get(result).toString();
    }
}
