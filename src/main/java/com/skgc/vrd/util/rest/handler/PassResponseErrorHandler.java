package com.skgc.vrd.util.rest.handler;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class PassResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// do nothing
	}

}
