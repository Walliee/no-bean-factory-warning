package com.example.nobeanfactorywarning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;

@SpringBootApplication
@EnableBinding(NoBeanFactoryWarningApplication.PolledProcessor.class)
public class NoBeanFactoryWarningApplication {
    private static final Logger logger = LoggerFactory.getLogger(NoBeanFactoryWarningApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NoBeanFactoryWarningApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(PollableMessageSource source) {
        return args -> {
            while (true) {
                boolean result = source.poll(m -> {
                    String payload = (String) m.getPayload();
                    logger.info("Received: " + payload);
                }, new ParameterizedTypeReference<String>() {
                });
                if (result) {
                    logger.info("Processed a message");
                } else {
                    logger.info("Nothing to do");
                }
                Thread.sleep(5_000);
            }
        };
    }

    public interface PolledProcessor {

        @Input
        PollableMessageSource source();

    }
}
