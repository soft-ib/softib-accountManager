package com.softib.accountmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.softib.accountmanager.util.IUserService;

@RestController
public class RestControllerAccount {

	@Autowired
	IUserService userService;

	@Autowired
	private EurekaClient eurekaClient;

	private String coreServiceId ="core-service";

	@GetMapping(value = "accounts")
	@ResponseBody
	String showTestQuestion() {
		return "Hello, accountManager is working !!!!" + " User and roles : " + userService.getCurrentUserName() + " "
				+ userService.getCurrentUserRole();
	}

	@GetMapping(value = "/from-core/users")
	@ResponseBody
	Object fromCore(@RequestHeader("Authorization") String token) {
		Application application = eurekaClient.getApplication(coreServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/users";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
	}
}
