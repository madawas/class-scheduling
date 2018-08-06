package org.iit.genetics.main;

import org.iit.genetics.algorithm.Algorithm;
import org.iit.genetics.algorithm.Population;
import org.iit.genetics.bean.Class;
import org.iit.genetics.bean.Timetable;

import java.util.Arrays;
import java.util.Collections;

public class TimetableGA {
    public static void main(String[] args) {

        Timetable timetable = initializeTimetable();
        // Initialize GA
        Algorithm ga = new Algorithm(100, 0.01, 0.9, 2, 5);

        Population population = ga.initPopulation(timetable);

        ga.evalPopulation(population, timetable);

        // Keep track of current generation
        int generation = 1;

        // Start evolution loop
        while (!ga.isTerminationConditionMet(generation, 1000) && !ga.isTerminationConditionMet(population)) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        // Print fitness
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).
                getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes
        System.out.println();
        Class[] classes = timetable.getClasses();
        int classIndex = 1;

        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + bestClass.getModule().getModuleName());
            System.out.println("Batch: " + bestClass.getBatch().getId());
            System.out.println("Room: " + bestClass.getRoom().getNumber());
            System.out.println("Professor: " + bestClass.getProfessor().getName());
            System.out.println("Time: " + bestClass.getTimeSlot().getTimeSlot());
            System.out.println("-----");
            classIndex++;
        }
    }

    private static Timetable initializeTimetable() {
        // Create timetable
        Timetable timetable = new Timetable();

        // Set up rooms
        timetable.addRoom(1, "A1", 15);
        timetable.addRoom(2, "B1", 30);
        timetable.addRoom(4, "D1", 20);
        timetable.addRoom(5, "F1", 25);

        // Set up timeslots
        timetable.addTimeSlot(1, "Mon 9:00 - 11:00");
        timetable.addTimeSlot(2, "Mon 11:00 - 13:00");
        timetable.addTimeSlot(3, "Mon 13:00 - 15:00");
        timetable.addTimeSlot(4, "Tue 9:00 - 11:00");
        timetable.addTimeSlot(5, "Tue 11:00 - 13:00");
        timetable.addTimeSlot(6, "Tue 13:00 - 15:00");
        timetable.addTimeSlot(7, "Wed 9:00 - 11:00");
        timetable.addTimeSlot(8, "Wed 11:00 - 13:00");
        timetable.addTimeSlot(9, "Wed 13:00 - 15:00");
        timetable.addTimeSlot(10, "Thu 9:00 - 11:00");
        timetable.addTimeSlot(11, "Thu 11:00 - 13:00");
        timetable.addTimeSlot(12, "Thu 13:00 - 15:00");
        timetable.addTimeSlot(13, "Fri 9:00 - 11:00");
        timetable.addTimeSlot(14, "Fri 11:00 - 13:00");
        timetable.addTimeSlot(15, "Fri 13:00 - 15:00");
        // Set up professors
        timetable.addProfessor(1, "Dr P Smith");
        timetable.addProfessor(2, "Mrs E Mitchell");
        timetable.addProfessor(3, "Dr R Williams");
        timetable.addProfessor(4, "Mr A Thompson");
        // Set up modules and define the professors that teach them
        timetable.addModule(1, "Computer Science", Arrays.asList(timetable.getProfessor(1), timetable.getProfessor(2)));
        timetable.addModule(2, "English", Arrays.asList(timetable.getProfessor(1), timetable.getProfessor(3)));
        timetable.addModule(3, "Maths", Arrays.asList(timetable.getProfessor(1), timetable.getProfessor(2)));
        timetable.addModule(4, "Physics", Arrays.asList(timetable.getProfessor(3), timetable.getProfessor(4)));
        timetable.addModule(5, "History", Collections.singletonList(timetable.getProfessor(4)));
        timetable.addModule(6, "Drama", Arrays.asList(timetable.getProfessor(1), timetable.getProfessor(4)));
        // Set up student groups and the modules they take.
        timetable
                .addGroup(1, 10, Arrays.asList(timetable.getModule(1), timetable.getModule(3), timetable.getModule(4)));
        timetable.addGroup(2, 30, Arrays.asList(timetable.getModule(2), timetable.getModule(3), timetable.getModule(5),
                timetable.getModule(6)));
        timetable
                .addGroup(3, 18, Arrays.asList(timetable.getModule(3), timetable.getModule(4), timetable.getModule(5)));
        timetable.addGroup(4, 25, Arrays.asList(timetable.getModule(1), timetable.getModule(4)));
        timetable
                .addGroup(5, 20, Arrays.asList(timetable.getModule(2), timetable.getModule(3), timetable.getModule(5)));
        timetable
                .addGroup(6, 22, Arrays.asList(timetable.getModule(1), timetable.getModule(5), timetable.getModule(4)));
        timetable.addGroup(7, 16, Arrays.asList(timetable.getModule(1), timetable.getModule(3)));
        timetable.addGroup(8, 18, Arrays.asList(timetable.getModule(2), timetable.getModule(6)));
        timetable.addGroup(9, 24, Arrays.asList(timetable.getModule(1), timetable.getModule(6)));
        timetable.addGroup(10, 25, Arrays.asList(timetable.getModule(4), timetable.getModule(3)));
        return timetable;
    }
}
