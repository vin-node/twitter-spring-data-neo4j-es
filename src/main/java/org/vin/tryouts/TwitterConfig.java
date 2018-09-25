package org.vin.tryouts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;


@Configuration
public class TwitterConfig {

    @Bean
    // Params injected from application.properties file:
    public Twitter twitter(final @Value("${social.twitter.appId}") String appId,
                           final @Value("${social.twitter.appSecret}") String appSecret,
                           final @Value("${social.twitter.accessToken}") String accessToken,
                           final @Value("${social.twitter.accessTokenSecret}") String accessTokenSecret) {
        return new TwitterTemplate(appId, appSecret, accessToken, accessTokenSecret);
    }
}
