package com.softib.accountmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.softib.accountmanager.util.StringToAccountConverter;

@Configuration
@DependsOn({ "StringToAccountConverter" })
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	StringToAccountConverter converter;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(converter);
	}
}
