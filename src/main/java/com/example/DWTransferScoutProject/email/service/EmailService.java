package com.example.DWTransferScoutProject.email.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    // 이메일 하나당 만들 수 있는 계정의 수
    private static final int MAX_EMAIL_ASSOCIATED_ACCOUNTS = 1;

    // 이메일 인증 코드를 생성하는 메서드
    // 테스트
    public int generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(888888) + 111111;
    }
}
