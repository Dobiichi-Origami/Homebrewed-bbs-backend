package com.twintails.server.dao;

import com.twintails.server.Entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Integer> {
    List<Response> findAllByTweetId(Integer tweetId);
    List<Response> findAllByResponseId(Integer responseId);
    List<Response> findAllByHolderId(Integer holderId);
    void deleteAllByResponseId(Integer responseId);
}
