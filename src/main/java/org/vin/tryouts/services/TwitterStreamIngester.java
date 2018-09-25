package org.vin.tryouts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class TwitterStreamIngester implements StreamListener {

	@Inject
	private Twitter twitter;
	@Inject
	private GraphService graphService;
	@Inject
	private ThreadPoolTaskExecutor taskExecutor;
	@Value("${twitterProcessing.enabled}")
	private boolean processingEnabled;

	private BlockingQueue<Tweet> queue = new ArrayBlockingQueue<>(20);

	public void run() {
		List<StreamListener> listeners = new ArrayList<>();
		listeners.add(this);
		twitter.streamingOperations().sample(listeners);
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		if (processingEnabled) {
			for (int i = 0; i < taskExecutor.getMaxPoolSize(); i++) {
				taskExecutor.execute(new TweetProcessor(graphService, queue));
			}

			run();
		}
	}

	@Override
	public void onTweet(Tweet tweet) {
		queue.offer(tweet);
	}

	@Override
	public void onDelete(StreamDeleteEvent deleteEvent) {
	}

	@Override
	public void onLimit(int numberOfLimitedTweets) {
	}

	@Override
	public void onWarning(StreamWarningEvent warningEvent) {
	}
}
