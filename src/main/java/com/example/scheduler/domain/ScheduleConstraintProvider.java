package com.example.scheduler.domain;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class ScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{
                jobsCannotOverlapOnSameMachine(factory),
                machineAvailability(factory),
                finishBeforeDeadline(factory),         // Hard ierobežojums deadline
                minimizeLateness(factory),              // Soft ierobežojums dueDate
                preferredMachine(factory)
        };
    }

    // Hard ierobežojums: uzdevumi nevar pārklāties uz vienas mašīnas
    private Constraint jobsCannotOverlapOnSameMachine(ConstraintFactory factory) {
        return factory.from(Job.class)
                .join(Job.class,
                        Joiners.equal(Job::getMachine),
                        Joiners.lessThan(Job::getId))
                .filter((job1, job2) ->
                        job1.getTimeslot() != null &&
                                job2.getTimeslot() != null &&
                                job1.getTimeslot().getStartHour() < job2.getTimeslot().getEndHour() &&
                                job2.getTimeslot().getStartHour() < job1.getTimeslot().getEndHour()
                )
                .penalize("Uzdevumi nevar pārklāties uz vienas mašīnas",
                        HardSoftScore.ONE_HARD);
    }

    // Hard ierobežojums: mašīnas pieejamība
    private Constraint machineAvailability(ConstraintFactory factory) {
        return factory.from(Job.class)
                .filter(job ->
                        job.getMachine() != null &&
                                job.getTimeslot() != null &&
                                (job.getTimeslot().getStartHour() < job.getMachine().getAvailableFrom()
                                        || job.getTimeslot().getEndHour() > job.getMachine().getAvailableTo())
                )
                .penalize("Mašīnas darba laika pārkāpums",
                        HardSoftScore.ONE_HARD);
    }

    // Hard ierobežojums: pabeigt pirms hard termiņa
    // Izmanto deadline (stingrais termiņš)
    private Constraint finishBeforeDeadline(ConstraintFactory factory) {
        return factory.from(Job.class)
                .filter(job ->
                        job.getTimeslot() != null &&
                                job.getFinishTime() > job.getDeadline()
                )
                .penalize("Pabeigts pēc stingrā termiņa",
                        HardSoftScore.ONE_HARD);
    }

    // Soft ierobežojums: nokavējuma minimizēšana attiecībā pret dueDate
    // Izmanto dueDate (mīkstais termiņš)
    private Constraint minimizeLateness(ConstraintFactory factory) {
        return factory.from(Job.class)
                .filter(job -> job.getTimeslot() != null)
                .penalize("Uzdevuma nokavējums",
                        HardSoftScore.ONE_SOFT,
                        job -> {
                            int finish = job.getTimeslot().getStartHour() + job.getDuration();
                            int lateness = Math.max(0, finish - job.getDueDate());
                            return lateness;
                        });
    }

    // Soft ierobežojums: vēlamā mašīna
    private Constraint preferredMachine(ConstraintFactory factory) {
        return factory.from(Job.class)
                .filter(job ->
                        job.getPreferredMachine() != null &&
                                job.getMachine() != null &&
                                !job.getMachine().equals(job.getPreferredMachine())
                )
                .penalize("Nav vēlamā mašīna",
                        HardSoftScore.ONE_SOFT);
    }
}