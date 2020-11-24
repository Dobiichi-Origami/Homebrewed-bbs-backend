package com.twintails.server.Service;

import com.twintails.server.Entity.Response;
import com.twintails.server.Entity.Tweet;
import com.twintails.server.Entity.User;
import com.twintails.server.dao.ResponseRepository;
import com.twintails.server.dao.TweetRepository;
import com.twintails.server.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseService responseService;

    public List<Tweet> findTweetByTweetId(Integer tweetId){
        return tweetRepository.findAllByTweetId(tweetId);
    }

    public List<Tweet> findTweetByTitle(String title){
        return tweetRepository.findAllByTitle(title);
    }

    public List<Tweet> findTweetByHolderId(Integer holderId){
        return tweetRepository.findAllByHolderId(holderId);
    }

    @Transactional
    public Boolean deleteTweetByTweetId(Integer tweetId){
        Tweet tweet = tweetRepository.findAllByTweetId(tweetId).get(0);
        User user = tweet.getUsers().get(0);

        user.getTweets().remove(tweet);
        userRepository.saveAndFlush(user);

        List<Response> responses = tweet.getResponses();
        for (Response response: responses){
            responseService.deleteResponseByResponseId(response.getResponseId());
        }

        tweetRepository.deleteAllByTweetId(tweetId);
        return tweetRepository.findAllByTweetId(tweetId).isEmpty();
    }

    public void addTweet(String title, String content, Integer holderId){
        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setTitle(title);
        tweet.setHolderId(holderId);
        tweet.setResponses(new ArrayList<>());
        tweetRepository.saveAndFlush(tweet);
    }

}