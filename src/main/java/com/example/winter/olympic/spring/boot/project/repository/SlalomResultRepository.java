package com.example.winter.olympic.spring.boot.project.repository;

import com.example.winter.olympic.spring.boot.project.model.SlalomResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlalomResultRepository extends JpaRepository<SlalomResult, Long> {
    List<SlalomResult> findByCompetitionId(Long competitionId);
}
