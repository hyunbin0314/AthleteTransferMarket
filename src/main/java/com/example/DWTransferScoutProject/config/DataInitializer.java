package com.example.DWTransferScoutProject.config;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auction.service.AuctionService;
import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import com.example.DWTransferScoutProject.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionService auctionService;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setAccountId("user1");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setAccountType(ApplicationRoleEnum.USER);
        user1.setUsername("김민균");
        userRepository.save(user1);

        Auction auction1 = new Auction();
        auction1.setUser(userRepository.findById(1L).get());
        auction1.setTitle("첫번째 제목 글");
        auction1.setContents("첫번째 내용 글");
        auction1.setCreateTime(LocalDateTime.now());
        auction1.setDeadline(LocalDateTime.now().plusDays(7));
        auction1.setTransferFee(10000);

        auctionRepository.save(auction1);




    }
}
