package com.example.nobeanfactorywarning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = {"input"}, ports = 9092)
@TestPropertySource(properties = {
		"spring.cloud.stream.binder.kafka.brokers=localhost:9092",
		"logging.level.org.apache=warn",
		"logging.level.kafka=warn"
})
public class NoBeanFactoryWarningApplicationTests {

	@Test
	public void contextLoads() {
		await().untilTrue(new AtomicBoolean(false));
	}

}
