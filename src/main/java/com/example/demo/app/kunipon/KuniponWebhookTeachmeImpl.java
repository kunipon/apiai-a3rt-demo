package com.example.demo.app.kunipon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.domain.apiai.IApiaiDoWebhook;
import com.google.gson.JsonElement;

import ai.api.model.Fulfillment;

@Component(value="teach_me")
public class KuniponWebhookTeachmeImpl implements IApiaiDoWebhook {

	@Override
	public void processing(String question, Map<String, JsonElement> input, Fulfillment output) {
		String date = getDate(input);
		String city = getCity(input);
		
		switch(input.get("teach_me").getAsString()) {
			case "weather": output.setSpeech(getWeatherInfo(date, city)); break;
			case "news": output.setSpeech(getNews(date, city)); break;
			default: output.setSpeech("天気かニュースしかお調べできません。");
		}
	}
	
	private String getDate(Map<String, JsonElement> input) {
		String date = input.get("date").getAsString();
		return StringUtils.isEmpty(date) ? LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) : date;
	}
	
	private String getCity(Map<String, JsonElement> input) {
		String city = input.get("geo-city").getAsString();
		return StringUtils.isEmpty(city) ? "東京" : city;
	}
	
	private String getWeatherInfo(String date, String city) {
		String template = "%sの%sの天気は%sです。";
		return String.format(template, date, city, "晴れ");
	}
	
	private String getNews(String date, String city) {
		String template = "%sの%sで面白いニュースはありませんでした。";
		return String.format(template, date, city);
	}
}
