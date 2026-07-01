package com.example.winter.olympic.spring.boot.project.config;

import com.example.winter.olympic.spring.boot.project.model.*;
import com.example.winter.olympic.spring.boot.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AthleteRepository athleteRepository;
    private final CompetitionRepository competitionRepository;
    private final SlalomResultRepository slalomResultRepository;
    private final BiathlonResultRepository biathlonResultRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            seedSecurity();
            seedOlympicData();
        }
    }

    private void seedSecurity() {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role athleteRole = new Role();
        athleteRole.setName("ROLE_ATHLETE");
        roleRepository.save(athleteRole);

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(Set.of(adminRole))
                .build();
        userRepository.save(admin);
    }

    private void seedOlympicData() {
        // Seed Athletes
        Athlete a1 = Athlete.builder().name("Marcel Hirscher").country("Austria").gender(Gender.MALE).birthDate(LocalDate.of(1989, 3, 2)).build();
        Athlete a2 = Athlete.builder().name("Henrik Kristoffersen").country("Norway").gender(Gender.MALE).birthDate(LocalDate.of(1994, 7, 2)).build();
        Athlete a3 = Athlete.builder().name("Johannes Thingnes Bø").country("Norway").gender(Gender.MALE).birthDate(LocalDate.of(1993, 5, 16)).build();
        Athlete a4 = Athlete.builder().name("Martin Fourcade").country("France").gender(Gender.MALE).birthDate(LocalDate.of(1988, 9, 14)).build();
        
        athleteRepository.saveAll(List.of(a1, a2, a3, a4));

        // Seed Slalom Competition
        Competition slalom = Competition.builder()
                .name("Olympic Slalom Men")
                .type(CompetitionType.SLALOM)
                .gender(Gender.MALE)
                .minAge(16)
                .build();
        competitionRepository.save(slalom);

        // Seed Slalom Results
        slalomResultRepository.save(SlalomResult.builder().athlete(a1).competition(slalom).run1Time(new BigDecimal("48.500")).run2Time(new BigDecimal("47.200")).dnf(false).build());
        slalomResultRepository.save(SlalomResult.builder().athlete(a2).competition(slalom).run1Time(new BigDecimal("49.100")).run2Time(new BigDecimal("46.900")).dnf(false).build());

        // Seed Biathlon Competition
        Competition biathlon = Competition.builder()
                .name("Olympic Biathlon 10km Sprint")
                .type(CompetitionType.BIATHLON)
                .gender(Gender.MALE)
                .minAge(18)
                .build();
        competitionRepository.save(biathlon);

        // Seed Biathlon Results
        biathlonResultRepository.save(BiathlonResult.builder().athlete(a3).competition(biathlon).skiTime(new BigDecimal("1440.000")).misses(1).dnf(false).build()); // 24m + 1m = 25m
        biathlonResultRepository.save(BiathlonResult.builder().athlete(a4).competition(biathlon).skiTime(new BigDecimal("1510.000")).misses(0).dnf(false).build()); // 25m 10s
    }
}
