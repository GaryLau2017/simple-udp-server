package com.codenotfound.udp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;

@SpringBootApplication
@EnableIntegration
@EnablePrometheusEndpoint
public class UdpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdpServerApplication.class, args);
	}
}
