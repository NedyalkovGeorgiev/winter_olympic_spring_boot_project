package com.example.winter.olympic.spring.boot.project;

import com.example.winter.olympic.spring.boot.project.model.*;
import com.example.winter.olympic.spring.boot.project.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFullFlow() throws Exception {
        // 1. Create an Athlete
        Athlete athlete = Athlete.builder()
                .name("Ivan Ivanov")
                .country("Bulgaria")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1995, 1, 1))
                .build();
        athlete = athleteRepository.save(athlete);

        // 2. Create a Slalom Competition
        Competition competition = Competition.builder()
                .name("Men's Slalom")
                .type(CompetitionType.SLALOM)
                .gender(Gender.MALE)
                .minAge(18)
                .build();
        competition = competitionRepository.save(competition);

        // 3. Post a Result (Slalom)
        String slalomResultJson = String.format("""
                {
                    "athlete": {"id": %d},
                    "competition": {"id": %d},
                    "run1Time": 45.500,
                    "run2Time": 44.200,
                    "dnf": false
                }
                """, athlete.getId(), competition.getId());

        mockMvc.perform(post("/api/results/slalom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(slalomResultJson))
                .andDo(result -> System.out.println("Result Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk());

        // 4. Check Public Ranking
        mockMvc.perform(get("/api/public/rankings/" + competition.getId()))
                .andDo(result -> System.out.println("Ranking Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rank", is(1)));

        // 5. Check Medal Table
        mockMvc.perform(get("/api/public/medals"))
                .andDo(result -> System.out.println("Medals Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gold", is(1)));
    }
}
