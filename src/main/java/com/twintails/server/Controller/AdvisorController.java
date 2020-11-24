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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/postATweet")
    public void postATweet(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, @RequestParam(value = "holderId") Integer holderId){
        tweetService.addTweet(title,content,holderId);
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

    @RequestMapping("/postResponse")
    public void postResponse(@RequestParam(value = "tweetId") Integer tweetId, @RequestParam(value = "holderId") Integer holderId, @RequestParam(value = "content") String content){
        responseService.addResponse(tweetId, holderId, content);
    }

    @RequestMapping("/changeResponse")
    public void changeResponse(@RequestParam(value = "responseId") Integer responseId, @RequestParam(value = "content") String content){
        responseService.changeResponse(responseId, content);
    }

}