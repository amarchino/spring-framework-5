package guru.springframework.jms.listener;

import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.jms.config.JmsConfig;
import guru.springframework.jms.model.HelloWorldMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloMessageListener {

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
		log.info("I got a message!!" + helloWorldMessage);
//		throw new RuntimeException("foo");
	}
}
