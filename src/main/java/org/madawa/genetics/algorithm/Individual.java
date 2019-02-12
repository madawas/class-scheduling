/*
 * Copyright (c) 2019 Madawa Soysa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.madawa.genetics.algorithm;

import org.madawa.genetics.bean.Module;
import org.madawa.genetics.bean.StudentGroup;
import org.madawa.genetics.main.ClassSchedulerUI;
import org.madawa.genetics.main.Timetable;

public class Individual extends ClassSchedulerUI {
    private int[] chromosome;
    private double fitness = -1;

    Individual(Timetable timetable) {
        int numClasses = timetable.getClassesToSchedule();

        // 1 gene for room, 1 for time, 1 for professor
        int chromosomeLength = numClasses * 3;
        // Create random individual
        int[] newChromosome = new int[chromosomeLength];
        int chromosomeIndex = 0;
        // Loop through groups
        for (StudentGroup studentGroup : timetable.getAppData().getStudentGroups()) {
            // Loop through modules
            for (Module module : studentGroup.getEnrollments()) {
                // Add random time
                int timeSlotId = timetable.getRandomTimeSlot().
                        getId();
                newChromosome[chromosomeIndex] = timeSlotId;
                chromosomeIndex++;

                // Add random room
                int roomId = timetable.getRandomClassroom().getId();
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
        for (int gene = 0; gene < chromosomeLength; ++gene) {
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
