package org.vin.tryouts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vin.tryouts.domain.Keyword;
import org.vin.tryouts.domain.Profile;
import org.vin.tryouts.domain.Tweet;
import org.vin.tryouts.repositories.KeywordRepository;
import org.vin.tryouts.repositories.ProfileRepository;
import org.vin.tryouts.repositories.TweetRepository;

@Service
public class GraphService {
	@Autowired
	private KeywordRepository keywordRepository;
	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private ProfileRepository profileRepository;

	private Keyword createOrGetKeyword(String str) {
		str = str.toLowerCase();
		Keyword word = keywordRepository.findByWord(str);

		if (word == null) {
			word = new Keyword();
			word.setWord(str);
		}
		word = keywordRepository.save(word);
		return word;
	}

	public Tweet createTweet(Tweet tweet) {
		return tweetRepository.save(tweet);
	}

	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	public Profile createProfile(String screenName) {
		Profile createdProfile = new Profile();
		createdProfile.setScreenName(screenName);
		return profileRepository.save(createdProfile);
	}

	public List<Map> findTopKeywords() {
		return keywordRepository.findTopKeywords();
	}

	public List<Keyword> findRelevantKeywords(String word) {
		Keyword keyword = keywordRepository.findByWord(word);
		return keyword == null ? new ArrayList<Keyword>() : keywordRepository.findRelevantKeywords(keyword.getId());
	}
}
