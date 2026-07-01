package com.example.winter.olympic.spring.boot.project.repository;

import com.example.winter.olympic.spring.boot.project.model.BiathlonResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiathlonResultRepository extends JpaRepository<BiathlonResult, Long> {
    List<BiathlonResult> findByCompetitionId(Long competitionId);
}
