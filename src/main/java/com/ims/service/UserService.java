package com.ims.service;

import com.ims.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User register(User user);

    User findByName(String username);

    User getCurrentUser();
}
