package com.example.winter.olympic.spring.boot.project.controller;

import com.example.winter.olympic.spring.boot.project.model.Athlete;
import com.example.winter.olympic.spring.boot.project.service.AthleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping
    public List<Athlete> getAll() {
        return athleteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getById(@PathVariable Long id) {
        return athleteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Athlete create(@Valid @RequestBody Athlete athlete) {
        return athleteService.save(athlete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Athlete> update(@PathVariable Long id, @Valid @RequestBody Athlete athlete) {
        return athleteService.findById(id)
                .map(existing -> {
                    athlete.setId(id);
                    return ResponseEntity.ok(athleteService.save(athlete));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        athleteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
