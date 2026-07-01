package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.model.BiathlonResult;
import com.example.winter.olympic.spring.boot.project.model.SlalomResult;
import com.example.winter.olympic.spring.boot.project.repository.BiathlonResultRepository;
import com.example.winter.olympic.spring.boot.project.repository.SlalomResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final SlalomResultRepository slalomResultRepository;
    private final BiathlonResultRepository biathlonResultRepository;

    @Transactional
    public SlalomResult saveSlalomResult(SlalomResult result) {
        return slalomResultRepository.save(result);
    }

    @Transactional
    public BiathlonResult saveBiathlonResult(BiathlonResult result) {
        return biathlonResultRepository.save(result);
    }

    public List<SlalomResult> getQualifiedForRun2(Long competitionId, int limit) {
        return slalomResultRepository.findByCompetitionId(competitionId).stream()
                .filter(r -> !r.isDnf() && r.getRun1Time() != null)
                .sorted(Comparator.comparing(SlalomResult::getRun1Time))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<SlalomResult> getRun2StartOrder(Long competitionId, int limit) {
        // Reverse order of Run 1 times
        return getQualifiedForRun2(competitionId, limit).stream()
                .sorted(Comparator.comparing(SlalomResult::getRun1Time).reversed())
                .collect(Collectors.toList());
    }
}
