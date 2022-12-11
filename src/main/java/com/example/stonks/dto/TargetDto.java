package com.example.stonks.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetDto {
    private String name;
    private Long current_sum;
    private Long final_sum;
    private Long progress;
}
