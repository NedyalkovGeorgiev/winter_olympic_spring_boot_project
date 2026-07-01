package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.model.Competition;
import com.example.winter.olympic.spring.boot.project.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> findById(Long id) {
        return competitionRepository.findById(id);
    }

    @Transactional
    public Competition save(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Transactional
    public void deleteById(Long id) {
        competitionRepository.deleteById(id);
    }
}
