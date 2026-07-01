package com.example.winter.olympic.spring.boot.project.controller;

import com.example.winter.olympic.spring.boot.project.dto.CountryMedals;
import com.example.winter.olympic.spring.boot.project.dto.RankingEntry;
import com.example.winter.olympic.spring.boot.project.model.Athlete;
import com.example.winter.olympic.spring.boot.project.service.RankingService;
import com.example.winter.olympic.spring.boot.project.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final RankingService rankingService;
    private final StatisticsService statisticsService;

    @GetMapping("/rankings/{competitionId}")
    public List<RankingEntry> getRanking(@PathVariable Long competitionId) {
        return rankingService.getRanking(competitionId);
    }

    @GetMapping("/medals")
    public List<CountryMedals> getMedalTable() {
        return statisticsService.getMedalTable();
    }

    @GetMapping("/stats/average-age")
    public double getAverageAge() {
        return statisticsService.getAverageAge();
    }

    @GetMapping("/stats/youngest-medalist")
    public Athlete getYoungestMedalist() {
        return statisticsService.getYoungestMedalist();
    }

    @GetMapping("/stats/oldest-medalist")
    public Athlete getOldestMedalist() {
        return statisticsService.getOldestMedalist();
    }
}
