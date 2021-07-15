package com.aws.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQSController {

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@SqsListener("demo-queue")
	public void loadMessageFromSQS(String msg) {
		System.out.println("message from SQS Queue :" + msg);
	}

	@GetMapping("/send/{message}")
	public void sendMessage(@PathVariable String message) {
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
	}
}
