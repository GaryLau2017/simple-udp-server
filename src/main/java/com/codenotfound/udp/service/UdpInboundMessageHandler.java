package com.codenotfound.udp.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;

import com.codenotfound.udp.metrics.GenericMetrics;


@MessageEndpoint
public class UdpInboundMessageHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UdpInboundMessageHandler.class);
	
	@ServiceActivator(inputChannel = "inboundChannel")
	public void handeMessage(Message message, @Headers Map<String, Object> headerMap) {
		String status = "0";
		GenericMetrics genericMetrics = new GenericMetrics(GenericMetrics.UDP_TRANSACTION_TYPE, GenericMetrics.TRANSACTION_TYPE, 
	            GenericMetrics.INCOMING_TRANSACTION_DIRECTION, GenericMetrics.TRANSACTION_COMMAND); 
		LOGGER.info("Received UDP message: {}", new String((byte[]) message.getPayload()));
		genericMetrics.close(status.toString());
	}
}
