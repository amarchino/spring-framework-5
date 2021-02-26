package guru.springframework.jms.listener;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.jms.config.JmsConfig;
import guru.springframework.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloMessageListener {
	
	private final JmsTemplate jsmTemplate;

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
		log.info("I got a message!!" + helloWorldMessage);
//		throw new RuntimeException("foo");
	}
	
	@JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
	public void listenAndRespond(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) throws JmsException, JMSException {
		log.info("I got a message to respond to!!" + helloWorldMessage);
		HelloWorldMessage responseMessage = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("Hello back to you!")
				.build();
		
		jsmTemplate.convertAndSend(message.getJMSReplyTo(), responseMessage);
	}
}
