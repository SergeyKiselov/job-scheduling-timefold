package com.example.scheduler.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleData {

    /**
     * Example 1: Small demo (8 jobs, 2 machines, 3 timeslots)
     * Pamata piemērs demonstrācijai
     */
    private static Schedule createSmallExample() {
        // Mašīnas ar darba laiku
        Machine m1 = new Machine("M1", "Mašīna 1", 9, 17);
        Machine m2 = new Machine("M2", "Mašīna 2", 9, 17);
        List<Machine> machines = Arrays.asList(m1, m2);

        // Laika sloti (katra stunda)
        List<Timeslot> timeslots = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            timeslots.add(new Timeslot(hour, hour + 1));
        }

        // Uzdevumi ar dueDate (soft) un deadline (hard)
        List<Job> jobs = new ArrayList<>();

        // Job 1-2: vienkārši uzdevumi
        Job j1 = new Job("J1", "Uzdevums A1", 1, 12, 14, 1);
        j1.setPreferredMachine(m1);

        Job j2 = new Job("J2", "Uzdevums A2", 1, 11, 13, 1);
        j2.setPreferredMachine(m1);

        // Job 3-4: ar vēlmēm
        Job j3 = new Job("J3", "Uzdevums B1", 1, 10, 12, 1);
        j3.setPreferredMachine(m2);

        Job j4 = new Job("J4", "Uzdevums B2", 1, 13, 15, 1);
        j4.setPreferredMachine(m2);

        // Job 5-6: gari uzdevumi
        Job j5 = new Job("J5", "Uzdevums C1", 2, 14, 16, 1);
        j5.setPreferredMachine(m1);

        Job j6 = new Job("J6", "Uzdevums C2", 2, 15, 17, 1);
        j6.setPreferredMachine(m2);

        // Job 7-8: ar stingriem termiņiem
        Job j7 = new Job("J7", "Uzdevums D1", 1, 10, 11, 2);
        j7.setPreferredMachine(m1);

        Job j8 = new Job("J8", "Uzdevums D2", 1, 11, 12, 2);
        j8.setPreferredMachine(m2);

        // Sākotnējais izvietojums (nejaušs)
        j1.setMachine(m1); j1.setTimeslot(timeslots.get(0));
        j2.setMachine(m1); j2.setTimeslot(timeslots.get(0)); // pārklājas!
        j3.setMachine(m2); j3.setTimeslot(timeslots.get(1));
        j4.setMachine(m2); j4.setTimeslot(timeslots.get(2));
        j5.setMachine(m1); j5.setTimeslot(timeslots.get(3));
        j6.setMachine(m2); j6.setTimeslot(timeslots.get(4));
        j7.setMachine(m1); j7.setTimeslot(timeslots.get(5));
        j8.setMachine(m2); j8.setTimeslot(timeslots.get(6));

        jobs.addAll(Arrays.asList(j1, j2, j3, j4, j5, j6, j7, j8));

        return new Schedule(machines, timeslots, jobs);
    }

    /**
     * Example 2: Medium overload (15 jobs, 2 machines, 8 timeslots)
     * Vairāk uzdevumu nekā vietu - neizbēgami soft sodi
     */
    private static Schedule createMediumExample() {
        // Mašīnas ar darba laiku
        Machine m1 = new Machine("M1", "Mašīna 1", 9, 17);
        Machine m2 = new Machine("M2", "Mašīna 2", 9, 17);
        List<Machine> machines = Arrays.asList(m1, m2);

        // Laika sloti (katra stunda)
        List<Timeslot> timeslots = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            timeslots.add(new Timeslot(hour, hour + 1));
        }

        // Izveidojam 15 uzdevumus (vairāk nekā slotu: 2 mašīnas * 8 sloti = 16 vietas)
        List<Job> jobs = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            // Dažādi dueDate un deadline dažādībai
            int dueDate = 10 + (i % 5);
            int deadline = dueDate + 2;
            int duration = (i % 3 == 0) ? 2 : 1; // Katrs trešais uzdevums ilgst 2 stundas

            Job job = new Job("J" + i, "Uzdevums " + i, duration, dueDate, deadline, 1);

            // Mainām vēlmes starp mašīnām
            if (i % 2 == 0) {
                job.setPreferredMachine(m1);
            } else {
                job.setPreferredMachine(m2);
            }

            // Sākotnējais izvietojums
            job.setMachine(machines.get(i % 2));
            job.setTimeslot(timeslots.get(i % timeslots.size()));

            jobs.add(job);
        }

        return new Schedule(machines, timeslots, jobs);
    }

    /**
     * Example 3: Machine constraints (12 jobs, 3 machines with different working hours)
     * Dažāds darba laiks mašīnām - būs hard sodi
     */
    private static Schedule createMachineConstraintExample() {
        // Mašīnas ar dažādu darba laiku
        Machine m1 = new Machine("M1", "Mašīna 1 (rīts)", 9, 13);      // Strādā no rīta
        Machine m2 = new Machine("M2", "Mašīna 2 (pēcpusdiena)", 13, 17);     // Strādā pēcpusdienā
        Machine m3 = new Machine("M3", "Mašīna 3 (pilna diena)", 9, 17); // Strādā visu dienu
        List<Machine> machines = Arrays.asList(m1, m2, m3);

        // Laika sloti (katra stunda)
        List<Timeslot> timeslots = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            timeslots.add(new Timeslot(hour, hour + 1));
        }

        List<Job> jobs = new ArrayList<>();

        // Uzdevumi, kam jāiet uz noteiktām mašīnām
        for (int i = 1; i <= 4; i++) {
            Job j = new Job("J" + i, "Rīta uzdevums " + i, 1, 12, 14, 1);
            j.setPreferredMachine(m1);
            j.setMachine(m1);
            j.setTimeslot(timeslots.get(0));
            jobs.add(j);
        }

        for (int i = 5; i <= 8; i++) {
            Job j = new Job("J" + i, "Pēcpusdienas uzdevums " + i, 1, 15, 17, 1);
            j.setPreferredMachine(m2);
            j.setMachine(m2);
            j.setTimeslot(timeslots.get(4));
            jobs.add(j);
        }

        for (int i = 9; i <= 12; i++) {
            Job j = new Job("J" + i, "Elastīgs uzdevums " + i, 2, 14, 16, 1);
            j.setPreferredMachine(m3);
            j.setMachine(m3);
            j.setTimeslot(timeslots.get(2));
            jobs.add(j);
        }

        return new Schedule(machines, timeslots, jobs);
    }

    /**
     * Example 4: Tight deadlines (12 jobs, deadlines almost equal to duration)
     * Ļoti stingri termiņi - grūti izvairīties no pārkāpumiem
     */
    private static Schedule createTightDeadlineExample() {
        Machine m1 = new Machine("M1", "Mašīna 1", 9, 17);
        Machine m2 = new Machine("M2", "Mašīna 2", 9, 17);
        List<Machine> machines = Arrays.asList(m1, m2);

        List<Timeslot> timeslots = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            timeslots.add(new Timeslot(hour, hour + 1));
        }

        List<Job> jobs = new ArrayList<>();

        // Uzdevumi ar ļoti stingriem termiņiem
        for (int i = 1; i <= 12; i++) {
            int duration = (i % 3 == 0) ? 2 : 1;
            int startHour = 9 + (i % 4);
            int deadline = startHour + duration; // Termiņš tūlīt pēc beigām!
            int dueDate = deadline - 1; // dueDate vēl stingrāks

            Job job = new Job("J" + i, "Steidzams uzdevums " + i, duration, dueDate, deadline, 2);

            if (i % 2 == 0) {
                job.setPreferredMachine(m1);
                job.setMachine(m1);
            } else {
                job.setPreferredMachine(m2);
                job.setMachine(m2);
            }

            job.setTimeslot(timeslots.get(startHour - 9));

            jobs.add(job);
        }

        return new Schedule(machines, timeslots, jobs);
    }

    /**
     * Example 5: Large scale (40 jobs, 4 machines, 8 timeslots)
     * Liels piemērs stresa testēšanai
     */
    private static Schedule createLargeExample() {
        // 4 mašīnas, visas strādā pilnu dienu
        List<Machine> machines = Arrays.asList(
                new Machine("M1", "Mašīna 1", 9, 17),
                new Machine("M2", "Mašīna 2", 9, 17),
                new Machine("M3", "Mašīna 3", 9, 17),
                new Machine("M4", "Mašīna 4", 9, 17)
        );

        // 8 stundu sloti (9:00-17:00)
        List<Timeslot> timeslots = new ArrayList<>();
        for (int hour = 9; hour < 17; hour++) {
            timeslots.add(new Timeslot(hour, hour + 1));
        }

        // 40 uzdevumi
        List<Job> jobs = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            // Dažāds ilgums (1-3 stundas)
            int duration = 1 + (i % 3);

            // Dažādi dueDate un deadline
            int dueDate = 10 + (i % 6);
            int deadline = dueDate + (i % 4);

            Job job = new Job("J" + i, "Uzdevums " + i, duration, dueDate, deadline, 1);

            // Vēlmes sadalītas vienmērīgi
            Machine preferred = machines.get(i % machines.size());
            job.setPreferredMachine(preferred);

            // Sākotnējais izvietojums (nejaušs)
            job.setMachine(preferred);
            job.setTimeslot(timeslots.get(i % timeslots.size()));

            jobs.add(job);
        }

        return new Schedule(machines, timeslots, jobs);
    }

    // ===== Public API =====
    public static Schedule getExampleByName(String name) {
        switch (name) {
            case "Example 1":
                return createSmallExample();
            case "Example 2":
                return createMediumExample();
            case "Example 3":
                return createMachineConstraintExample();
            case "Example 4":
                return createTightDeadlineExample();
            case "Example 5":
                return createLargeExample();
            default:
                return createSmallExample();
        }
    }

    // Lai nodrošinātu atpakaļsaderību
    public static Schedule createSampleSchedule() {
        return createSmallExample();
    }
}