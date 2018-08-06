package org.iit.genetics.algorithm;

import org.iit.genetics.bean.Timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
    private List<Individual> population;
    private double populationFitness = -1;

    public Population(int populationSize) {
        this.population = new ArrayList<>();
    }

    public Population(int populationSize, Timetable timetable) {
        this.population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual(timetable);
            this.population.add(individual);
        }
    }

    public List<Individual> getIndividuals() {
        return this.population;
    }

    public Individual getFittest(int offset) {
        Collections.sort(this.population, (i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
        return this.population.get(offset);
    }


    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    public int size() {
        return this.population.size();
    }

    public void setIndividual(int offset, Individual individual) {
        this.population.add(offset, individual);
    }

    public Individual getIndividual(int offset) {
        return population.get(offset);
    }

    public void shuffle() {
        Collections.shuffle(this.population);
    }
}
