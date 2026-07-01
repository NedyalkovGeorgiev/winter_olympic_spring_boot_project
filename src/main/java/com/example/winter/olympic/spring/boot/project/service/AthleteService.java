package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.model.Athlete;
import com.example.winter.olympic.spring.boot.project.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AthleteService {
    private final AthleteRepository athleteRepository;

    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }

    public Optional<Athlete> findById(Long id) {
        return athleteRepository.findById(id);
    }

    @Transactional
    public Athlete save(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    @Transactional
    public void deleteById(Long id) {
        athleteRepository.deleteById(id);
    }
}
