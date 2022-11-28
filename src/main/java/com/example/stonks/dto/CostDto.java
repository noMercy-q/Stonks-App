package com.example.stonks.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String category;

    public String toString() {
        String sum = String.valueOf(amount);
        String pattern = "MM/dd/yyyy";
        DateFormat date_format = new SimpleDateFormat(pattern);
        String date_str;
        if (date == null) 
            date_str = "null";
        else
            date_str = date_format.format(date);
        return sum + " " + date_str + " " + category;
    }
}
