package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.dto.RankingEntry;
import com.example.winter.olympic.spring.boot.project.model.*;
import com.example.winter.olympic.spring.boot.project.repository.BiathlonResultRepository;
import com.example.winter.olympic.spring.boot.project.repository.CompetitionRepository;
import com.example.winter.olympic.spring.boot.project.repository.SlalomResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final SlalomResultRepository slalomResultRepository;
    private final BiathlonResultRepository biathlonResultRepository;
    private final CompetitionRepository competitionRepository;

    public List<RankingEntry> getRanking(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        if (competition.getType() == CompetitionType.SLALOM) {
            return getSlalomRanking(competitionId);
        } else {
            return getBiathlonRanking(competitionId);
        }
    }

    private List<RankingEntry> getSlalomRanking(Long competitionId) {
        List<SlalomResult> results = slalomResultRepository.findByCompetitionId(competitionId);
        
        List<RankingEntry> ranking = results.stream()
                .filter(r -> !r.isDnf() && r.getRun1Time() != null && r.getRun2Time() != null)
                .map(r -> new RankingEntry(r.getAthlete(), r.getTotalTime(), 0, false))
                .sorted(Comparator.comparing(RankingEntry::getTotalTime))
                .collect(Collectors.toList());

        // Add DNFs at the end
        List<RankingEntry> dnfs = results.stream()
                .filter(r -> r.isDnf() || r.getRun1Time() == null || r.getRun2Time() == null)
                .map(r -> new RankingEntry(r.getAthlete(), null, 0, true))
                .toList();

        ranking.addAll(dnfs);
        
        return IntStream.range(0, ranking.size())
                .mapToObj(i -> {
                    RankingEntry entry = ranking.get(i);
                    if (!entry.isDnf()) {
                        entry.setRank(i + 1);
                    }
                    return entry;
                })
                .collect(Collectors.toList());
    }

    private List<RankingEntry> getBiathlonRanking(Long competitionId) {
        List<BiathlonResult> results = biathlonResultRepository.findByCompetitionId(competitionId);

        List<RankingEntry> ranking = results.stream()
                .filter(r -> !r.isDnf() && r.getSkiTime() != null)
                .map(r -> new RankingEntry(r.getAthlete(), r.getTotalTime(), 0, false))
                .sorted(Comparator.comparing(RankingEntry::getTotalTime))
                .collect(Collectors.toList());

        List<RankingEntry> dnfs = results.stream()
                .filter(r -> r.isDnf() || r.getSkiTime() == null)
                .map(r -> new RankingEntry(r.getAthlete(), null, 0, true))
                .toList();

        ranking.addAll(dnfs);

        return IntStream.range(0, ranking.size())
                .mapToObj(i -> {
                    RankingEntry entry = ranking.get(i);
                    if (!entry.isDnf()) {
                        entry.setRank(i + 1);
                    }
                    return entry;
                })
                .collect(Collectors.toList());
    }
}
