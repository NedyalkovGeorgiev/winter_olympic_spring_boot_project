package com.example.winter.olympic.spring.boot.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryMedals {
    private String country;
    private int gold;
    private int silver;
    private int bronze;

    public int getTotal() {
        return gold + silver + bronze;
    }
}
