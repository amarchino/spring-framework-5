package guru.springframework.jms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import guru.springframework.jms.config.JmsConfig;
import guru.springframework.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloMessageSender {
	
	private final JmsTemplate jmsTemplate;
	private final MessageConverter messageConverter;

	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		log.info("I'm sending a message");
		HelloWorldMessage message = HelloWorldMessage
			.builder()
			.id(UUID.randomUUID())
			.message("Hello World!")
			.build();
		jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
		log.info("Message sent");
	}
	
	@Scheduled(fixedRate = 2000)
	public void sendReceiveMessage() throws JMSException {
		log.info("I'm sending a message");
		HelloWorldMessage message = HelloWorldMessage
			.builder()
			.id(UUID.randomUUID())
			.message("Hello World!")
			.build();
		Message response = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, session -> messageConverter.toMessage(message, session));
		HelloWorldMessage helloWorldResponse = (HelloWorldMessage) messageConverter.fromMessage(response);
		log.info("Message sent. Response: " + helloWorldResponse);
	}
}
