package com.example.demo.domain.apiai;

import java.util.Map;

import com.google.gson.JsonElement;

import ai.api.model.Fulfillment;

public interface IApiaiDoWebhook {
	/**
	 * Web-hook processing method.
	 * @param question users say
	 * @param parameters Received request parameters
	 * @param output Response object. Should be filled in the method.
	 * @throws Exception 
	 */
	public void processing(String question, Map<String, JsonElement> parameters, Fulfillment output) throws Exception;
}
