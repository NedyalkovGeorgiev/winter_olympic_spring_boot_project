package com.example.winter.olympic.spring.boot.project.repository;

import com.example.winter.olympic.spring.boot.project.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
