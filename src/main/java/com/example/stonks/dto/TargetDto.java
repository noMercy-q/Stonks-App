package com.example.stonks.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetDto {
    private Long id;
    private String name;
    private Long current_sum;
    private Long final_sum;
    private Long progress;

    public TargetDto(String name, Long final_sum) {
        this.name = name;
        this.final_sum = final_sum;
        current_sum = 0L;
        progress = 0L;
    }

    public void updateCurrentSum(Long sum) {
        current_sum += sum;
        if (current_sum > final_sum) 
            current_sum = final_sum;
    }
}
