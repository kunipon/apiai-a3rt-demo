package com.example.demo.domain.a3rt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class A3rtService {
	
	// テストなので~/.spring-boot-devtools.propertiesに用意
	@Value("${a3rt.apikey}")
	private String API_KEY;
	private static final String TALK_API_URL = "https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk";
	
	private final RestTemplate restTemplate;
	
	/**
	 * コンストラクタ
	 * @param restTemplate
	 */
	public A3rtService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	/**
	 * レスポンスの取得
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public A3rtApiResponse fetchResponse(String question) throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("apikey", API_KEY);
		map.add("query", question);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		return restTemplate.postForObject(TALK_API_URL, request, A3rtApiResponse.class);
	}
	
	/**
	 * レスポンスから返答内容を取得
	 * @param a3rtApiResponse
	 * @return
	 */
	public String getReply(A3rtApiResponse a3rtApiResponse) {
		return a3rtApiResponse.getResults()[0].getReply();
	}
	
	/**
	 * レスポンスのstatusが "0"（正常応答）かどうかを判別します
	 * @see <a href="https://a3rt.recruit-tech.co.jp/product/talkAPI/">ステータスコード / メッセージ</a>
	 * @param a3rtApiResponse
	 * @return
	 */
	public boolean isStatusSuccess(A3rtApiResponse a3rtApiResponse) {
		boolean isSuccess = a3rtApiResponse.getStatus()==0;
		
		if(!isSuccess) {
			log.warn("status  :" + a3rtApiResponse.getStatus());
			log.warn("message :" + a3rtApiResponse.getMessage());
		}
		
		return isSuccess;
	}
}
