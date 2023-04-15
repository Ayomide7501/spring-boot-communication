package net.elevatedlifestyle.communication.controller;

import net.elevatedlifestyle.communication.dto.UserDto;
import net.elevatedlifestyle.communication.model.User;
import net.elevatedlifestyle.communication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @PostMapping("/register")
    @ResponseBody
    public String reg(@RequestParam("name") String fullName,
                      @RequestParam("type") String type,
                      @RequestParam("role") String role,
                      @RequestParam("email") String email){
        UserDto user = new UserDto();
        user.setName(fullName);
        user.setEmail(email);
        String dbRole = null;
        switch (role) {
            case "chef":
                dbRole = "ROLE_CHEF";
                break;
            case "staff":
                dbRole = "ROLE_STAFF";
                break;
            case "organizer":
                dbRole = "ROLE_STAFF";
                break;
            case "cleaner":
                dbRole = "ROLE_STAFF";
                break;
            case "":
                dbRole = "ROLE_USER";
                break;
        }
        user.setRole(dbRole);
        userService.saveUser(user);
        return "Added Successfully";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin/admin";
    }

    @GetMapping("/forgotPassword")
    public String resetPass(){
        return "auth/forgot-password";
    }

}
