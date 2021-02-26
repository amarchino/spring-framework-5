package guru.springframework.jms.sender;

import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
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
}
