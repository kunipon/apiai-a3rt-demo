package com.example.demo.app.kunipon;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.domain.a3rt.A3rtApiResponse;
import com.example.demo.domain.a3rt.A3rtService;
import com.example.demo.domain.apiai.IApiaiDoWebhook;
import com.google.gson.JsonElement;

import ai.api.model.Fulfillment;

@Component(value="unknown")
public class KuniponWebhookUnknownImpl implements IApiaiDoWebhook {
	private final A3rtService a3rtService;
	
	public KuniponWebhookUnknownImpl(A3rtService a3rtService) {
		this.a3rtService = a3rtService;
	}
	
	@Override
	public void processing(String question, Map<String, JsonElement> parameters, Fulfillment output) throws Exception {
		A3rtApiResponse a3rtResponse = a3rtService.fetchResponse(question);
		if(a3rtService.isStatusSuccess(a3rtResponse)) {
			output.setSpeech(a3rtService.getReply(a3rtResponse));
		} else {
			output.setSpeech("今日はもうやめにしましょう。。");
		}
	}

}
