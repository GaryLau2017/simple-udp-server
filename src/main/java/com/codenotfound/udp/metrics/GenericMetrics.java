package com.codenotfound.udp.metrics;

import io.prometheus.client.Histogram;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class GenericMetrics {
	public static final String UDP_TRANSACTION_TYPE = "microservice_api";
	public static final String INCOMING_TRANSACTION_DIRECTION = "incoming";
	public static final String OUTGOING_TRANSACTION_DIRECTION = "outgoing";

	public static final String TRANSACTION_TYPE = "udpproxy_service";
	public static final String TRANSACTION_COMMAND = "udp_method";
	private static final String RESULT_GROUP_SUCCESS = "SUCCESS";
	private static final String RESULT_GROUP_OTHER_FAILURE = "OTHER FAILURE";

	public static final String OK_STATUS = "OK";
	public static final String UNAVAILABLE_STATUS = "UNAVAILABLE";
	private Histogram.Timer stopwatch;
	private String type;
	private String command;
	private String category;
	private String direction;

	/**
	 * Start generic latency timer
	 * 
	 * @param category
	 * @param type      pure server, same value as command
	 * @param direction outgoing and incoming
	 * @param command   API name
	 */
	public GenericMetrics(String category, String type, String direction, String command) {
		this.category = category;
		this.type = type;
		this.direction = direction;
		this.command = command;
		this.stopwatch = genericLatency.labels(category, type, direction, command).startTimer();
	}

	/**
	 * Stop generic latency timer and inc generic transaction total
	 * 
	 * @param result success case or fail case
	 */
	public void close(String result) {
		if (result.equals("0"))
			genericTransactionCount.labels(category, type, direction, command, RESULT_GROUP_SUCCESS, result).inc();
		else
			genericTransactionCount.labels(category, type, direction, command, RESULT_GROUP_OTHER_FAILURE, result)
					.inc();

		this.stopwatch.observeDuration();
	}

	/**
	 * Generic transaction count, with counter type
	 */
	private static final Counter genericTransactionCount = Counter.build().name("generic_transaction_count")
			.labelNames("category", "type", "direction", "command", "result", "result_group")
			.help("Generic Transaction Details").register();

	/**
	 * Generic transaction latency, with histogram type
	 */
	private static final Histogram genericLatency = Histogram.build().name("generic_latency")
			.labelNames("category", "type", "direction", "command").help("Generic Transaction Latency").register();

	/**
	 * Generic transaction status, with gauge type
	 */
	private static final Gauge genericStatus = Gauge.build().name("generic_status").labelNames("type")
			.help("Generic Status").register();

}
