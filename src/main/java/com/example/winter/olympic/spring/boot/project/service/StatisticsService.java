package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.dto.CountryMedals;
import com.example.winter.olympic.spring.boot.project.dto.RankingEntry;
import com.example.winter.olympic.spring.boot.project.model.Athlete;
import com.example.winter.olympic.spring.boot.project.model.Competition;
import com.example.winter.olympic.spring.boot.project.repository.AthleteRepository;
import com.example.winter.olympic.spring.boot.project.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final RankingService rankingService;
    private final CompetitionRepository competitionRepository;
    private final AthleteRepository athleteRepository;

    public List<CountryMedals> getMedalTable() {
        Map<String, CountryMedals> medalTable = new HashMap<>();
        List<Competition> competitions = competitionRepository.findAll();

        for (Competition competition : competitions) {
            List<RankingEntry> ranking = rankingService.getRanking(competition.getId());
            for (RankingEntry entry : ranking) {
                if (entry.getRank() >= 1 && entry.getRank() <= 3) {
                    CountryMedals cm = medalTable.computeIfAbsent(entry.getAthlete().getCountry(),
                            c -> new CountryMedals(c, 0, 0, 0));
                    if (entry.getRank() == 1) cm.setGold(cm.getGold() + 1);
                    else if (entry.getRank() == 2) cm.setSilver(cm.getSilver() + 1);
                    else if (entry.getRank() == 3) cm.setBronze(cm.getBronze() + 1);
                }
            }
        }

        List<CountryMedals> sortedTable = new ArrayList<>(medalTable.values());
        sortedTable.sort(Comparator.comparing(CountryMedals::getGold)
                .thenComparing(CountryMedals::getSilver)
                .thenComparing(CountryMedals::getBronze).reversed());
        return sortedTable;
    }

    public double getAverageAge() {
        List<Athlete> athletes = athleteRepository.findAll();
        if (athletes.isEmpty()) return 0;
        return athletes.stream()
                .mapToInt(a -> Period.between(a.getBirthDate(), LocalDate.now()).getYears())
                .average()
                .orElse(0);
    }

    public Athlete getYoungestMedalist() {
        return getMedalists().stream()
                .max(Comparator.comparing(Athlete::getBirthDate))
                .orElse(null);
    }

    public Athlete getOldestMedalist() {
        return getMedalists().stream()
                .min(Comparator.comparing(Athlete::getBirthDate))
                .orElse(null);
    }

    private List<Athlete> getMedalists() {
        Set<Athlete> medalists = new HashSet<>();
        List<Competition> competitions = competitionRepository.findAll();
        for (Competition competition : competitions) {
            List<RankingEntry> ranking = rankingService.getRanking(competition.getId());
            ranking.stream()
                    .filter(e -> e.getRank() >= 1 && e.getRank() <= 3)
                    .map(RankingEntry::getAthlete)
                    .forEach(medalists::add);
        }
        return new ArrayList<>(medalists);
    }
}
