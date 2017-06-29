package com.example.demo.app.kunipon;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.api.model.Fulfillment;

@Controller
public class KuniponController {
	
	private final KuniponService kuniponService;
	
	/**
	 * コンストラクタ
	 * @param kuniponService
	 */
	public KuniponController(KuniponService kuniponService) {
		this.kuniponService = kuniponService;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/webhook")
	public Fulfillment webhook(HttpServletRequest request) {
		return kuniponService.createFulfillment(request);
	}
}
