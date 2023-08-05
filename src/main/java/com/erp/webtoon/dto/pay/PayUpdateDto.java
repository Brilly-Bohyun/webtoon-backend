package com.erp.webtoon.dto.pay;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PayUpdateDto {

    @NotNull
    private int yearSalary;   //연봉

    @NotNull
    private int addSalary; // 추가수당

    @NotBlank
    private String bankAccount; // 지급계좌

    @NotNull
    private LocalDate payDate; // 지급일

}
