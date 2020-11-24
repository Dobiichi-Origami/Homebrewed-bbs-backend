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
import java.util.Date;
import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseRepository responseRepository;


    public List<Response> findResponseByTweetId(Integer tweetId){
        return responseRepository.findAllByTweetId(tweetId);
    }

    @Transactional
    public Boolean deleteResponseByResponseId(Integer responseId){
        Response response = responseRepository.findAllByResponseId(responseId).get(0);
        User user = response.getUsers().get(0);
        Tweet tweet = response.getTweets().get(0);
        user.getResponses().remove(response);
        userRepository.saveAndFlush(user);
        tweet.getResponses().remove(response);
        tweetRepository.saveAndFlush(tweet);
        responseRepository.deleteAllByResponseId(responseId);
        return responseRepository.findAllByResponseId(responseId).isEmpty();

    }

    public void addResponse(Integer tweetId, Integer holderId, String content){
        Response response = new Response();
        response.setTweetId(tweetId);
        response.setHolderId(holderId);
        response.setContent(content);
        response.setPostTime(new Date());
        responseRepository.saveAndFlush(response);
    }

    public void changeResponse(Integer responseId, String content){
        Response response = responseRepository.findAllByResponseId(responseId).get(0);
        response.setContent(content);
        responseRepository.saveAndFlush(response);
    }

}
