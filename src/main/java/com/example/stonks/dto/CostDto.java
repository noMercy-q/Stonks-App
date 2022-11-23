package com.example.stonks.dto;

import java.util.Date;

import com.example.stonks.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CostDto {
    private Long amount;
    private Date date;
}

