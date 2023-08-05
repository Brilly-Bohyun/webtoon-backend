package com.erp.webtoon.service;

import com.erp.webtoon.domain.Pay;
import com.erp.webtoon.domain.User;
import com.erp.webtoon.dto.pay.*;
import com.erp.webtoon.repository.PayRepository;
import com.erp.webtoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayService {

    private final PayRepository payRepository;
    private final UserRepository userRepository;


    /**
     * 월 급여 등록
     */
    public void save(PayRequestDto dto) {

        User findUser = userRepository.findByEmployeeId(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사번입니다."));

        Pay newPay = dto.toEntity();

        newPay.setUserPay(findUser);

        payRepository.save(newPay);
    }

    /**
     * 급여 조회 -> 이때 직원 정보도 같이 조회해야함 + 급여 list
     */
    public PayResponseDto search(String employeeId) {
        User findUser = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사번입니다."));

        //유저 정보
        PayUserResponseDto userInfoDto = new PayUserResponseDto(findUser);

        //이번달 급여 정보
        if (findUser.getPays().isEmpty()) {
            return PayResponseDto.builder()
                    .userInfo(userInfoDto)
                    .payList(null)
                    .payList(null)
                    .qualificationList(null)
                    .build();
        }

        PayMonthDto payMonthDto = new PayMonthDto(findUser.getPays().get(-1));

        List<PayListResponseDto> payList = findUser.getPays().stream()
                .map(PayListResponseDto::new)
                .collect(Collectors.toList());

        //자격 수당 리스트
        List<PayQualificationDto> qualificationDtos = findUser.getQualifications().stream()
                .map(PayQualificationDto::new)
                .collect(Collectors.toList());

        return PayResponseDto.builder()
                .userInfo(userInfoDto)
                .monthPay(payMonthDto)
                .payList(payList)
                .qualificationList(qualificationDtos)
                .build();
    }

    /**
     * 월 급여 수정
     */
    public void update(String employeeId, PayUpdateDto dto) {
        User findUser = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 직원입니다."));

        findUser.getPays().get(-1).updatePay(dto.getYearSalary(), dto.getAddSalary(), dto.getBankAccount(), dto.getPayDate());
    }


    /**
     * 급여 지급여부 수정
     */
    public boolean update(Long payId) {
        Pay findPay = payRepository.findById(payId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 급여입니다."));

        findPay.updatePayYN();
        return true;
    }

}
