package com.twintails.server.dao;

import com.twintails.server.Entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet,Integer> {
    public List<Tweet> findAllByTweetId(Integer tweetId);
    public List<Tweet> findAllByTitle(String title);
    public List<Tweet> findAllByHolderId(Integer holderId);
    public void deleteAllByTweetId(Integer tweetId);
}
