package com.example.winter.olympic.spring.boot.project.service;

import com.example.winter.olympic.spring.boot.project.dto.RankingEntry;
import com.example.winter.olympic.spring.boot.project.model.*;
import com.example.winter.olympic.spring.boot.project.repository.BiathlonResultRepository;
import com.example.winter.olympic.spring.boot.project.repository.CompetitionRepository;
import com.example.winter.olympic.spring.boot.project.repository.SlalomResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RankingServiceTest {

    @Mock
    private SlalomResultRepository slalomResultRepository;
    @Mock
    private BiathlonResultRepository biathlonResultRepository;
    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSlalomRanking() {
        Long compId = 1L;
        Competition comp = Competition.builder().id(compId).type(CompetitionType.SLALOM).build();
        when(competitionRepository.findById(compId)).thenReturn(Optional.of(comp));

        Athlete a1 = Athlete.builder().name("Athlete 1").build();
        Athlete a2 = Athlete.builder().name("Athlete 2").build();

        SlalomResult r1 = SlalomResult.builder()
                .athlete(a1)
                .run1Time(new BigDecimal("50.123"))
                .run2Time(new BigDecimal("49.456"))
                .build();
        SlalomResult r2 = SlalomResult.builder()
                .athlete(a2)
                .run1Time(new BigDecimal("51.000"))
                .run2Time(new BigDecimal("50.000"))
                .build();

        when(slalomResultRepository.findByCompetitionId(compId)).thenReturn(Arrays.asList(r1, r2));

        List<RankingEntry> ranking = rankingService.getRanking(compId);

        assertEquals(2, ranking.size());
        assertEquals("Athlete 1", ranking.get(0).getAthlete().getName());
        assertEquals(1, ranking.get(0).getRank());
        assertEquals(new BigDecimal("99.579"), ranking.get(0).getTotalTime());
    }

    @Test
    void testBiathlonRanking() {
        Long compId = 2L;
        Competition comp = Competition.builder().id(compId).type(CompetitionType.BIATHLON).build();
        when(competitionRepository.findById(compId)).thenReturn(Optional.of(comp));

        Athlete a1 = Athlete.builder().name("Athlete 1").build();
        Athlete a2 = Athlete.builder().name("Athlete 2").build();

        BiathlonResult r1 = BiathlonResult.builder()
                .athlete(a1)
                .skiTime(new BigDecimal("1200.000"))
                .misses(1)
                .build(); // Total: 1260
        BiathlonResult r2 = BiathlonResult.builder()
                .athlete(a2)
                .skiTime(new BigDecimal("1210.000"))
                .misses(0)
                .build(); // Total: 1210

        when(biathlonResultRepository.findByCompetitionId(compId)).thenReturn(Arrays.asList(r1, r2));

        List<RankingEntry> ranking = rankingService.getRanking(compId);

        assertEquals(2, ranking.size());
        assertEquals("Athlete 2", ranking.get(0).getAthlete().getName());
        assertEquals(1, ranking.get(0).getRank());
        assertEquals(new BigDecimal("1210.000"), ranking.get(0).getTotalTime());
        assertEquals(new BigDecimal("1260.000"), ranking.get(1).getTotalTime());
    }
}
