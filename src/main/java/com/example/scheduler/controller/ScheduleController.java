package com.example.scheduler.controller;

import com.example.scheduler.domain.Schedule;
import com.example.scheduler.domain.SampleData;
import com.example.scheduler.dto.BenchmarkResult;
import com.example.scheduler.dto.SolveResponse;
import com.example.scheduler.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Tavs esošais POST /solve
    @PostMapping("/solve")
    public Schedule solve(@RequestBody Schedule schedule) {
        return scheduleService.solve(schedule);
    }

    // Jauna metode testēšanai ar pārkāpumu izvadi pirms un pēc risinājuma
    @GetMapping("/solve-test")
    public Schedule solveTest() {
        Schedule sample = SampleData.createSampleSchedule();

        // BEFORE score - aprēķinām caur ScoreManager
        sample.setScore(scheduleService.calculateScore(sample));

        System.out.println("=== PIRMS RISINĀŠANAS ===");
        printSchedule(sample);

        Schedule result = scheduleService.solve(sample);

        System.out.println("\n=== PĒC RISINĀŠANAS ===");
        printSchedule(result);

        // Parādām precīzu Timefold skaidrojumu
        System.out.println("\n=== VĒRTĒJUMA SKAIDROJUMS ===");
        System.out.println(scheduleService.buildScoreExplanation(result));

        return result;
    }

    // ===== Examples list =====
    @GetMapping("/examples")
    public List<String> getExampleNames() {
        return List.of("Example 1", "Example 2", "Example 3", "Example 4", "Example 5");
    }

    // ===== Skatīt example bez risināšanas (NEW) =====
    @GetMapping("/view-example/{name}")
    public Schedule viewExample(@PathVariable String name) {
        Schedule example = SampleData.getExampleByName(name);

        // Aprēķināt score, bet NEpalaižot solver
        example.setScore(scheduleService.calculateScore(example));

        return example;
    }

    // ===== Atrisināt izvēlēto example ar pilnu atbildi (BEFORE, AFTER, violations, explanation) =====
    @GetMapping("/solve-example/{name}")
    public SolveResponse solveExample(@PathVariable String name) {
        Schedule example = SampleData.getExampleByName(name);
        return scheduleService.solveWithExplanation(example);
    }

    // ===== Benchmark izvēlēto example =====
    @GetMapping("/benchmark/{name}")
    public List<BenchmarkResult> benchmarkExample(@PathVariable String name) {
        Schedule example = SampleData.getExampleByName(name);
        return scheduleService.runBenchmark(example);
    }

    // Universāla metode grafika izdrukāšanai ar pareizu pārkāpumu loģiku
    private void printSchedule(Schedule schedule) {
        System.out.println("Grafiks:");

        schedule.getJobList().stream()
                .sorted((j1, j2) -> Integer.compare(j1.getStartTime(), j2.getStartTime()))
                .forEach(job -> {

                    StringBuilder violations = new StringBuilder();

                    // --- HARD 1: Pārklāšanās uz vienas mašīnas ---
                    boolean hasOverlap = schedule.getJobList().stream()
                            .anyMatch(other ->
                                    other != job &&
                                            job.getMachine() != null &&
                                            other.getMachine() != null &&
                                            job.getMachine().equals(other.getMachine()) &&
                                            job.getId() != null && other.getId() != null &&
                                            job.getId().compareTo(other.getId()) < 0 &&
                                            job.getTimeslot() != null &&
                                            other.getTimeslot() != null &&
                                            job.getTimeslot().getStartHour() < other.getTimeslot().getEndHour() &&
                                            other.getTimeslot().getStartHour() < job.getTimeslot().getEndHour()
                            );

                    if (hasOverlap) {
                        violations.append("[HARD: pārklāšanās] ");
                    }

                    // --- HARD 2: Mašīnas pieejamība (darba laiks) ---
                    if (job.getMachine() != null && job.getTimeslot() != null &&
                            (job.getTimeslot().getStartHour() < job.getMachine().getAvailableFrom()
                                    || job.getTimeslot().getEndHour() > job.getMachine().getAvailableTo())) {

                        violations.append("[HARD: mašīna nav pieejama] ");
                    }

                    // --- HARD 3: Termiņš (stingrais termiņš) ---
                    if (job.getFinishTime() > job.getDeadline()) {
                        violations.append("[HARD: termiņš] ");
                    }

                    // --- SOFT 1: Nokavējums (attiecībā pret dueDate) ---
                    int lateness = job.getLateness();
                    if (lateness > 0) {
                        violations.append("[SOFT: nokavējums=").append(lateness).append("] ");
                    }

                    // --- SOFT 2: Vēlamā mašīna ---
                    if (job.getPreferredMachine() != null &&
                            job.getMachine() != null &&
                            !job.getPreferredMachine().equals(job.getMachine())) {

                        violations.append("[SOFT: nav vēlamā mašīna] ");
                    }

                    // Formatēta uzdevuma izvade
                    String machineInfo = job.getMachine() != null ? job.getMachine().getId() : "null";
                    String timeslotInfo = job.getTimeslot() != null ?
                            job.getTimeslot().getStartHour() + ":00-" + job.getTimeslot().getEndHour() + ":00" : "null";

                    System.out.println(
                            job.getName() + " -> " +
                                    machineInfo + " @ " +
                                    timeslotInfo +
                                    " | beigas=" + job.getFinishTime() +
                                    " | due=" + job.getDueDate() +
                                    " | termiņš=" + job.getDeadline() +
                                    " | " + violations
                    );
                });

        System.out.println("Kopējais vērtējums: " + schedule.getScore());
    }
}