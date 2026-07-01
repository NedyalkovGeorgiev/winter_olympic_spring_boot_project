package com.example.winter.olympic.spring.boot.project.dto;

import com.example.winter.olympic.spring.boot.project.model.Athlete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingEntry {
    private Athlete athlete;
    private BigDecimal totalTime;
    private int rank;
    private boolean dnf;
}
