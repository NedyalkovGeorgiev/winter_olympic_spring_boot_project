package com.example.winter.olympic.spring.boot.project.controller;

import com.example.winter.olympic.spring.boot.project.model.Competition;
import com.example.winter.olympic.spring.boot.project.service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping
    public List<Competition> getAll() {
        return competitionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competition> getById(@PathVariable Long id) {
        return competitionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Competition create(@Valid @RequestBody Competition competition) {
        return competitionService.save(competition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competition> update(@PathVariable Long id, @Valid @RequestBody Competition competition) {
        return competitionService.findById(id)
                .map(existing -> {
                    competition.setId(id);
                    return ResponseEntity.ok(competitionService.save(competition));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        competitionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
