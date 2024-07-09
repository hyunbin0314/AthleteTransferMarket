package com.example.DWTransferScoutProject;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.GenderEnum;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DwShopProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = UserDto.builder()
                .accountId("testUser")
                .password("password123")
                .confirmPassword("password123")
                .email("test@example.com")
                .accountRole(ApplicationRoleEnum.USER)
                .username("Test User")
                .birthdate("1990-01-01")
                .gender(GenderEnum.MALE)
                .contact("123-456-7890")
                .address(new AddressDto("123 Main St", "City", "12345", "Country"))
                .build();
    }

    @Test
    public void testSignUpSuccess() throws Exception {
        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        // 데이터베이스에서 사용자 확인
        Optional<User> createdUser = userRepository.findByAccountId(userDto.getAccountId());
        assertThat(createdUser).isPresent();
        assertThat(createdUser.get().getEmail()).isEqualTo(userDto.getEmail());
        assertThat(createdUser.get().getId()).isNotNull();
    }
}
