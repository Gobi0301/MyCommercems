package com.techietact.mycommercems.dtos;

import lombok.Data;

@Data
public class CardDto {
    private String number;
    private String expDate;
    private String cvc;
    private String zip;
}
