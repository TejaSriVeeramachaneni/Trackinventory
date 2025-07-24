package com.ims.controller;

import com.ims.model.User;
import com.ims.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "sessionExpired", required = false) String sessionExpired,
                                Model model) {

        log.info("Login Form accessed");
        if ("true".equals(sessionExpired)) {
            model.addAttribute("error", "Your session has expired. Please login again.");
            log.warn("Session expired");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.findByName(username);
        if (user != null && password.equals(user.getPassword())) {
            log.info("User Login Successful");
            session.setAttribute("loggedInUser", user);
            return user.getRole().equals("ADMIN") ? "redirect:/admin/dashboard" : "redirect:/staff/dashboard";
        }
        log.info("User Login Failed");
        model.addAttribute("error", "Invalid Credentials. Please try again.");
        return "login";
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        log.info("Register Form accessed");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        log.info("Register staff accessed");
        user.setPassword(user.getPassword());
        user.setRole("STAFF");
        userService.register(user);
        return "admin-dashboard";
    }
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session) {
        log.info("Admin Dashboard accessed");
        if(!userService.getCurrentUser().getRole().equals("ADMIN")){
            return "login";
        }
        return "admin-dashboard";
    }

    @GetMapping("/staff/dashboard")
    public String showStaffDashboard(HttpSession session) {
        log.info("Staff Dashboard accessed");
        if(!userService.getCurrentUser().getRole().equals("STAFF")){
            return "login";
        }
        return "staff-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout accessed");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
