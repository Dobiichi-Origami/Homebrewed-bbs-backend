package com.twintails.server.Service;


import com.twintails.server.Entity.Response;
import com.twintails.server.Entity.Tweet;
import com.twintails.server.Entity.User;
import com.twintails.server.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findUserByUserId(Integer userId){
        return userRepository.findAllByUserId(userId);
    }

    public List<User> findUserByUserName(String userName){
        return userRepository.findAllByUserName(userName);
    }

    public List<User> findUserByUserAccount(String userAccount){
        return userRepository.findAllByUserAccount(userAccount);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public Boolean deleteByUserId(Integer userId){
        User user = userRepository.findAllByUserId(userId).get(0);
        user.getTweets().clear();
        user.getResponses().clear();
        userRepository.saveAndFlush(user);
        userRepository.deleteAllByUserId(userId);
        return userRepository.findAllByUserId(userId).isEmpty();
    }

    public Boolean addUser(String userName, String password, String userAccount){
        User user = new User();
        if(!userRepository.findAllByUserAccount(userAccount).isEmpty())
            return false;
        if(!userRepository.findAllByUserName(userName).isEmpty())
            return false;
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setUserAccount(userAccount);
        user.setTweets(new ArrayList<Tweet>());
        user.setResponses(new ArrayList<Response>());
        user.setUserRegisterTime(new Date());
        userRepository.saveAndFlush(user);
        return !userRepository.findAllByUserAccount(userAccount).isEmpty();
    }

    public Boolean ChangeUserInfo(String updateUserName, String updateUserPassword, String userAccount){
        if(updateUserName != null && !userRepository.findAllByUserName(updateUserName).isEmpty())
            return false;
        User user = userRepository.findAllByUserAccount(userAccount).get(0);
        if(updateUserName != null)
            user.setUserName(updateUserName);
        if(updateUserPassword != null)
            user.setUserPassword(updateUserPassword);
        userRepository.saveAndFlush(user);
        return true;
    }

}
