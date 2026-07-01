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
@Table(name = "slalom_results")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SlalomResult extends Result {
    @Column(precision = 10, scale = 3)
    private BigDecimal run1Time;

    @Column(precision = 10, scale = 3)
    private BigDecimal run2Time;

    public BigDecimal getTotalTime() {
        if (isDnf() || run1Time == null) {
            return null;
        }
        if (run2Time == null) {
            return run1Time;
        }
        return run1Time.add(run2Time);
    }
}
