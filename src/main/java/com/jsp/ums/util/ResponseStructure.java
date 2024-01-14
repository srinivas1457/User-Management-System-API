package com.jsp.ums.util;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class ResponseStructure<T> {
	private int statusCode;
	private String message;
	private T data;
}
