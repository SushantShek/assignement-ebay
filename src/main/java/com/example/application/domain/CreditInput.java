package com.example.application.domain;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@NoArgsConstructor
@Component
@Data
public class CreditInput {

    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String postCode;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String creditLimit;
    @NotNull
    private String birthDate;
}
