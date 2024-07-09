package com.example.DWTransferScoutProject.email.controller;

import com.example.DWTransferScoutProject.email.service.EmailService;
import com.example.DWTransferScoutProject.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EmailController {
    private final UserService userService;
    private final EmailService emailService;

    // 생성자 주입
    @Autowired
    public EmailController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }
}
