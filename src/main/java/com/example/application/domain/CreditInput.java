package com.example.application.domain;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Component
@NoArgsConstructor

public class CreditInput {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @Size(min = 4, max = 7)
    @NotBlank(message = "Postcode is mandatory")
    private String postCode;
    @Size(min = 10, max = 15)
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
    @Size(min =1)
    @NotBlank(message = "Credit Limit is mandatory")
    private String creditLimit;
    @Size(min = 8, max = 10)
    @NotBlank(message = "Birth date is mandatory")
    private String birthDate;
}
