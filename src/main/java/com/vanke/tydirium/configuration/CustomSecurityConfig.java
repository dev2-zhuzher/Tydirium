package com.vanke.tydirium.configuration;

import org.springframework.security.access.SecurityConfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SecurityConfig可反序列化版本
 * 
 * @author Joey
 *
 */
public class CustomSecurityConfig extends SecurityConfig {

	private static final long serialVersionUID = 4823163038438081205L;

	@JsonCreator
	public CustomSecurityConfig(@JsonProperty("attribute") String attribute) {
		super(attribute);
	}

}
