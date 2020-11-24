package com.twintails.server.dao;

import com.twintails.server.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    public List<User> findAllByUserAccount(String userAccount);
    public List<User> findAllByUserName(String userName);
    public List<User> findAllByUserId(Integer userId);
    public void deleteAllByUserId(Integer userId);
}
