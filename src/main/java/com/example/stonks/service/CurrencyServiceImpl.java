package com.example.stonks.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String CURRENCY_URL = "https://currate.ru/api/";
    private static final String RATES = "rates";
    private static final String SECRET_KEY = "35c324233b1cf3a2bb13341e625f6dca";

    Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private String buildUrl(String currency) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CURRENCY_URL).newBuilder();
        urlBuilder.addQueryParameter("key", SECRET_KEY);
        urlBuilder.addQueryParameter("get", RATES);
        urlBuilder.addQueryParameter("pairs", currency);
  
        return urlBuilder.build().toString();
    }

    private Map<String, Object> makeRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String body = response.body().string(); 
            return new Gson()
                .fromJson(body, new TypeToken<HashMap<String, Object>>() {}.getType());
        } catch (IOException exception) {
            logger.error("Currency service error. " + exception.getMessage());
            return null;
        }
    }

    private String extractRate(Map<String, Object> response_map) {
        String data = response_map.get("data").toString();
        return data.substring(8, 13);
    }
    
    @Override
    public String getCurrencyRate(String currency) {
        String dollar_url = buildUrl(currency);
        Map<String, Object> response_map = makeRequest(dollar_url);

        if (!response_map.get("status").toString().equals("200.0")) {
            logger.error("Currency service bad request: " +
                          response_map.get("status").toString() + " " +
                          response_map.get("message").toString());
            return null;
        }
        return extractRate(response_map);
    }
}
