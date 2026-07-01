package com.example.winter.olympic.spring.boot.project.controller;

import com.example.winter.olympic.spring.boot.project.model.BiathlonResult;
import com.example.winter.olympic.spring.boot.project.model.SlalomResult;
import com.example.winter.olympic.spring.boot.project.service.ResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @PostMapping("/slalom")
    public SlalomResult saveSlalom(@Valid @RequestBody SlalomResult result) {
        return resultService.saveSlalomResult(result);
    }

    @PostMapping("/biathlon")
    public BiathlonResult saveBiathlon(@Valid @RequestBody BiathlonResult result) {
        return resultService.saveBiathlonResult(result);
    }

    @GetMapping("/slalom/{competitionId}/qualified")
    public List<SlalomResult> getQualified(@PathVariable Long competitionId, @RequestParam(defaultValue = "30") int limit) {
        return resultService.getQualifiedForRun2(competitionId, limit);
    }

    @GetMapping("/slalom/{competitionId}/run2-order")
    public List<SlalomResult> getRun2Order(@PathVariable Long competitionId, @RequestParam(defaultValue = "30") int limit) {
        return resultService.getRun2StartOrder(competitionId, limit);
    }
}
