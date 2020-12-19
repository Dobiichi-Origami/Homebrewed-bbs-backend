package com.twintails.server.Controller;

import com.twintails.server.Entity.Response;
import com.twintails.server.Entity.Tweet;
import com.twintails.server.Entity.User;
import com.twintails.server.Service.ResponseService;
import com.twintails.server.Service.TweetService;
import com.twintails.server.Service.UserService;
import com.twintails.server.dao.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdvisorController {

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private ResponseService responseService;

    //用户注册登录及增删改查

    @RequestMapping("/registerUser")
    public Boolean registerUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "userPassword") String userPassword, @RequestParam(value = "userAccount") String userAccount){
        return userService.addUser(userName,userPassword,userAccount);
    }

    public Boolean isRegister(String userAccount){
        return !userService.findUserByUserAccount(userAccount).isEmpty();
    }

    @RequestMapping("/login")
    public Boolean login(@RequestParam(value = "userAccount") String userAccount, @RequestParam(value = "userPassword") String userPassword){
        if(!isRegister(userAccount))
            return false;
        String password = userService.findUserByUserAccount(userAccount).get(0).getUserPassword();
        return password.equals(userPassword);
    }

    @RequestMapping("/findUserByUserId")
    public User findUser(@RequestParam(value = "userId") Integer userId){
        return userService.findUserByUserId(userId).get(0);
    }

    @RequestMapping("/findUserByUserAccount")
    public User findUserByAccount(@RequestParam(value = "userAccount") String userAccount){
        return userService.findUserByUserAccount(userAccount).get(0);
    }

    @RequestMapping("/findUserByUserName")
    public List<User> findUser(@RequestParam(value = "userName") String userName){
        return userService.findUserByUserName(userName);
    }

    @RequestMapping("/findAllUser")
    public List<User> findAllUser(){
        return userService.findAll();
    }

    @RequestMapping("/deleteUser")
    public Boolean deleteUser(@RequestParam(value = "userId") Integer userId){
        return userService.deleteByUserId(userId);
    }

    @RequestMapping("/changeNameOrPassword")
    public Boolean changeNameOrPassword(@RequestParam(value = "updateUserName") String updateUserName, @RequestParam(value = "updateUserPassword") String updateUserPassword, @RequestParam(value = "userAccount") String userAccount){
        return userService.ChangeUserInfo(updateUserName,updateUserPassword,userAccount);
    }

    //帖子的增删改(后面再实现)查

    @RequestMapping("/findAllTweets")
    public List<Tweet> findAllTweets(){
        return tweetService.findAllTweets();
    }

    @RequestMapping("/findTweetsByTitle")
    public List<Tweet> findTweetsByTitle(@RequestParam(value = "title") String title){
        return tweetService.findTweetByTitle(title);
    }

    @RequestMapping("/findTweetsByHolderId")
    public List<Tweet> findTweetsByHolderId(@RequestParam(value = "holderId") Integer holderId){
        return tweetService.findTweetByHolderId(holderId);
    }

    @RequestMapping("/findATweetByTweetId")
    public Tweet findATweetByTweetId(@RequestParam(value = "tweetId") Integer tweetId){
        return tweetService.findTweetByTweetId(tweetId).get(0);
    }

    @RequestMapping(value = "/postATweet", method = RequestMethod.POST)
    public void postATweet(@RequestBody Tweet tweet){
        tweetService.addTweet(tweet.getTitle(),tweet.getContent(),tweet.getHolderId());
    }

    @RequestMapping("/deleteATweet")
    public Boolean deleteATweet(@RequestParam(value = "tweetId") Integer tweetId){
        return tweetService.deleteTweetByTweetId(tweetId);
    }

    //回复的增删改查

    @RequestMapping("/findResponse")
    public List<Response> findResponse(@RequestParam(value = "tweetId") Integer tweetId){
        return responseService.findResponseByTweetId(tweetId);
    }

    @RequestMapping("/deleteResponse")
    public Boolean deleteResponse(@RequestParam(value = "responseId") Integer responseId){
        return responseService.deleteResponseByResponseId(responseId);
    }

    @RequestMapping(value = "/postResponse", method = RequestMethod.POST)
    public void postResponse(@RequestBody Response response){
        responseService.addResponse(response.getTweetId(), response.getHolderId(), response.getContent());
    }

    @RequestMapping(value = "/changeResponse", method = RequestMethod.POST)
    public void changeResponse(@RequestBody Response response){
        responseService.changeResponse(response.getResponseId(), response.getContent());
    }

}
