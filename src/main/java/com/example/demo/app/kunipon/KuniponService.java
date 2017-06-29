package com.example.demo.app.kunipon;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.demo.domain.apiai.IApiaiDoWebhook;
import com.google.gson.Gson;

import ai.api.GsonFactory;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KuniponService {
	private final Gson gson = GsonFactory.getDefaultFactory().getGson();
	private Map<String, IApiaiDoWebhook> apiaiDoWebhookMap;
	
	/**
	 * コンストラクタ
	 * @param apiaiDoWebhookMap
	 */
	public KuniponService(Map<String, IApiaiDoWebhook> apiaiDoWebhookMap) {
		this.apiaiDoWebhookMap = apiaiDoWebhookMap;
	}
	
	/**
	 * 
	 * @param fulfillment
	 * @return
	 */
	public Fulfillment createFulfillment(HttpServletRequest request) {
		Fulfillment fulfillment = new Fulfillment();
		try {
			AIResponse aiResponse = gson.fromJson(request.getReader(), AIResponse.class);
			IApiaiDoWebhook apiaiDoWebhook = apiaiDoWebhookMap.get(aiResponse.getResult().getAction());
			apiaiDoWebhook.processing(aiResponse.getResult().getResolvedQuery(), aiResponse.getResult().getParameters(), fulfillment);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			fulfillment.setSpeech("ごめんなさい。今、調子が悪いの。。");
		}
		return fulfillment;
	}
}
