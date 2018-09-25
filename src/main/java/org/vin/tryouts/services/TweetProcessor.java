package org.vin.tryouts.services;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.vin.tryouts.domain.Keyword;
import org.vin.tryouts.domain.Profile;


public class TweetProcessor implements Runnable {
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");

    private GraphService graphService;
    private final BlockingQueue<Tweet> queue;

    public TweetProcessor(GraphService graphService, BlockingQueue<Tweet> queue) {
        this.graphService = graphService;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Tweet tweet = queue.take();
                processTweet(tweet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processTweet(Tweet tweetEntity) {
        String lang = tweetEntity.getLanguageCode();
        String text = tweetEntity.getText();
        // filter non-English tweets:
        if (!"en".equals(lang)) {
            return;
        }

        Set<String> hashtags = hashtagsFromTweet(text);

        // filter tweets without hashtags:
        if (hashtags.isEmpty()) {
            return;
        }

        org.vin.tryouts.domain.Tweet tweet = new org.vin.tryouts.domain.Tweet();
        tweet.setText(tweetEntity.getText());
        tweet.setCreatedAt(tweetEntity.getCreatedAt());
        tweet.setLanguageCode(tweetEntity.getLanguageCode());
     
        // Neo4J will automatically find existing record based on screenname due to unique index on field:
        Profile author = graphService.createProfile(tweetEntity.getFromUser());
        tweet.setAuthor(author);
        tweet = graphService.createTweet(tweet);
       
       
        int mentions = connectTweetWithMentions(tweetEntity, tweet);

        String[] words = connectTweetWithTags(tweet, hashtags);
        System.out.printf("%d - %d - %s%n", mentions, words.length, text);
    }

    private String[] connectTweetWithTags(org.vin.tryouts.domain.Tweet tweet, Set<String> hashtags) {
        String[] words = hashtags.toArray(new String[hashtags.size()]);
        for (String word : words) {
        	Keyword keyword = new Keyword();
        	keyword.setWord(word);
            tweet.getTags().add(keyword);
        }
        tweet = graphService.createTweet(tweet);
        return words;
    }

    private int connectTweetWithMentions(Tweet tweetEntity, org.vin.tryouts.domain.Tweet tweet) {
        int mentions = 0;
        if (tweetEntity.getEntities() != null) {
            mentions = tweetEntity.getEntities().getMentions().size();
            for (MentionEntity mentionEntity : tweetEntity.getEntities().getMentions()) {
                Profile profile = graphService.createProfile(mentionEntity.getScreenName());
                tweet.getMentionedInProfile().add(profile);
                graphService.createTweet(tweet);
            }
        }
        return mentions;
    }

    private static Set<String> hashtagsFromTweet(String text) {
        Set<String> hashtags = new HashSet<>();
        Matcher matcher = HASHTAG_PATTERN.matcher(text);
        while (matcher.find()) {
            String handle = matcher.group();
            // removing '#' prefix
            hashtags.add(handle.substring(1));
        }
        return hashtags;
    }
}
