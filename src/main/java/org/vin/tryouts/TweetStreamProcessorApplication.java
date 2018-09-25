package org.vin.tryouts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@Configuration
@ComponentScan

@SpringBootApplication


@EnableAutoConfiguration
@EnableNeo4jRepositories("org.vin.tryouts.repositories")
public class TweetStreamProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetStreamProcessorApplication.class, args);
    }
}