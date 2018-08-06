package org.iit.genetics.algorithm;

import org.iit.genetics.bean.Batch;
import org.iit.genetics.bean.Module;
import org.iit.genetics.bean.Timetable;

public class Individual {
    private int[] chromosome;
    private double fitness = -1;

    Individual(Timetable timetable) {
        int numClasses = timetable.getNumClasses();

        // 1 gene for room, 1 for time, 1 for professor
        int chromosomeLength = numClasses * 3;
        // Create random individual
        int newChromosome[] = new int[chromosomeLength];
        int chromosomeIndex = 0;
        // Loop through groups
        for (Batch batch : timetable.getGroupsAsArray()) {
            // Loop through modules
            for (Module module : batch.getModules()) {
                // Add random time
                int timeSlotId = timetable.getRandomTimeslot().
                        getId();
                newChromosome[chromosomeIndex] = timeSlotId;
                chromosomeIndex++;

                // Add random room
                int roomId = timetable.getRandomRoom().getId();
                newChromosome[chromosomeIndex] = roomId;
                chromosomeIndex++;

                // Add random professor
                newChromosome[chromosomeIndex] = module.getRandomProfessor().getId();
                chromosomeIndex++;
            }

        }
        this.chromosome = newChromosome;
    }

    Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            if (0.5 < Math.random()) {
                this.setGene(gene, 1);
            } else {
                this.setGene(gene, 0);
            }
        }

    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    int getChromosomeLength() {
        return this.chromosome.length;
    }

    void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    int getGene(int offset) {
        return this.chromosome[offset];
    }

    void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int chromosome : this.chromosome) {
            output.append(chromosome);
        }
        return output.toString();
    }
}
