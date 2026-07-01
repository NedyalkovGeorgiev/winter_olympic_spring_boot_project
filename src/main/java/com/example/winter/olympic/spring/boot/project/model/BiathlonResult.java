package com.example.winter.olympic.spring.boot.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "biathlon_results")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BiathlonResult extends Result {
    @Column(precision = 10, scale = 3)
    private BigDecimal skiTime;

    @Column(nullable = false)
    private int misses;

    public BigDecimal getTotalTime() {
        if (isDnf() || skiTime == null) {
            return null;
        }
        // Each miss adds 60 seconds
        return skiTime.add(BigDecimal.valueOf(misses * 60L));
    }
}
