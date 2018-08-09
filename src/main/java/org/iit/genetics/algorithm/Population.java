package org.iit.genetics.algorithm;

import org.iit.genetics.main.Timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    private List<Individual> population;
    private double populationFitness = -1;

    Population(int populationSize) {
        this.population = new ArrayList<>();
    }

    Population(int populationSize, Timetable timetable) {
        this.population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual(timetable);
            this.population.add(individual);
        }
    }

    List<Individual> getIndividuals() {
        return this.population;
    }

    public Individual getFittest(int offset) {
        this.population.sort((i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
        return this.population.get(offset);
    }


    void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    int size() {
        return this.population.size();
    }

    void setIndividual(int offset, Individual individual) {
        this.population.add(offset, individual);
    }

    Individual getIndividual(int offset) {
        return population.get(offset);
    }

    void shuffle() {
        Collections.shuffle(this.population);
    }
}
