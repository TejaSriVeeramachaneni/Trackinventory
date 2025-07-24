package com.ims.service;

import com.ims.model.User;
import com.ims.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HttpSession session;

    @Override
    public User register(User user) {
        return userRepo.save(user);
    }
    @Override
    public User findByName(String username) {
        return userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
    }
    @Override
    public User getCurrentUser() {
        return (User) session.getAttribute("loggedInUser");
    }
}
