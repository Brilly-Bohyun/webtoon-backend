package com.erp.webtoon.service;

import com.erp.webtoon.domain.Pay;
import com.erp.webtoon.domain.Qualification;
import com.erp.webtoon.domain.User;
import com.erp.webtoon.dto.pay.PayQualificationDto;
import com.erp.webtoon.dto.pay.PayRequestDto;
import com.erp.webtoon.dto.pay.PayResponseDto;
import com.erp.webtoon.dto.user.QualificationRequestDto;
import com.erp.webtoon.repository.PayRepository;
import com.erp.webtoon.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayServiceTest {
    @Autowired
    private PayRepository payRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        payRepository.deleteAll();
    }

    @Test
    @DisplayName("월 급여 등록")
    void save1() {
        //given
        User user = User.builder()
                .employeeId("2000")
                .build();

        userRepository.save(user);

        PayRequestDto dto = new PayRequestDto();
        dto.setEmployeeId("2000");
        dto.setYearSalary(100000);
        dto.setAddSalary(20000);
        dto.setBankAccount("000-000-000-000");
        dto.setPayDate(LocalDate.now());

        //when
        payService.save(dto);

        //then
        assertEquals(1L, payRepository.count());
        Pay pay = payRepository.findAll().get(0);
        assertEquals(20000, pay.getAddPay());
        assertEquals(100000, pay.getSalary());
        assertEquals("000-000-000-000", pay.getBankAccount());
        assertEquals(LocalDate.now(), pay.getPayDate());
    }

    @Test
    @DisplayName("개인 급여 상세 조회")
    void test2() {
        //given
        User user = User.builder()
                .employeeId("2000")
                .name("규규")
                .email("kkk@naver.com")
                .deptName("인사과")
                .build();

        List<Qualification> qualList = new ArrayList<>();
        Qualification qualification = Qualification.builder()
                .qlfcType("정처기")
                .content(null)
                .qlfcDate(LocalDate.now())
                .qlfcPay(50000)
                .user(user)
                .build();
        qualList.add(qualification);
        user.registerQualification(qualList);

        userRepository.save(user);

        PayRequestDto dto = new PayRequestDto();
        dto.setEmployeeId("2000");
        dto.setYearSalary(100000);
        dto.setAddSalary(20000);
        dto.setBankAccount("000-000-000-000");
        dto.setPayDate(LocalDate.now());

        payService.save(dto);

        //when
        PayResponseDto searchDto = payService.search("2000");

        //then
        assertEquals("규규", searchDto.getUserInfo().getName());
        assertEquals("kkk@naver.com", searchDto.getUserInfo().getEmail());
        assertEquals("인사과", searchDto.getUserInfo().getDeptName());
        assertEquals("000-000-000-000", searchDto.getMonthPay().getBankAccount());
        assertEquals(100000, searchDto.getMonthPay().getYearSalary());
        assertEquals(8333, searchDto.getMonthPay().getMonthSalary());
        assertEquals(50000, searchDto.getMonthPay().getQualSalary());
        PayQualificationDto payQualificationDto = searchDto.getQualificationList().get(0);
        assertEquals("정처기", payQualificationDto.getName());
        assertEquals(50000, payQualificationDto.getMoney());
    }
}