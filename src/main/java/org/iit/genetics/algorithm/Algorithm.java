package org.iit.genetics.algorithm;

import org.apache.log4j.Logger;
import org.iit.genetics.main.Timetable;

public class Algorithm {
    private static final Logger log = Logger.getLogger(Algorithm.class);
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    private int tournamentSize;

    public Algorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
            int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    private Population initPopulation(Timetable timetable) {
        return new Population(this.populationSize, timetable);
    }

    private double calcFitness(Individual individual, Timetable timetable) {

        // Create new timetable object to use -- cloned from an existing timetable
        Timetable clone = new Timetable(timetable);
        clone.createClasses(individual);

        // Calculate fitness
        int clashes = clone.calcClashes();
        double fitness = 1 / (double) (clashes + 1);
        individual.setFitness(fitness);
        return fitness;
    }

    private void evalPopulation(Population population, Timetable timetable) {
        double populationFitness = 0;

        // Loop over population evaluating individuals and summing population fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual, timetable);
        }
        log.info("Population fitness: " +  populationFitness);
        log.info("Fittest: " + population.getFittest(0).getFitness());

        population.setPopulationFitness(populationFitness);
    }

    private boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getFitness() == 1.0;
    }

    private boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        log.warn("Max generations limit reached..!!");
        return (generationsCount > maxGenerations);
    }

    private Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }

    private Population crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
                // Initialize offspring
                Individual offspring = new Individual(parent1.getChromosomeLength());

                // Find second parent
                Individual parent2 = selectParent(population);
                // Loop over genome
                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    // Use half of parent1's genes and half of parent2's genes
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                // Add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    private Population mutatePopulation(Population population, Timetable timetable) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.
                    getFittest(populationIndex);

            // Create random individual to swap genes with
            Individual randomIndividual = new Individual(timetable);

            // Loop over individual's genes
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                // Skip mutation if this is an elite individual
                if (populationIndex > this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }

    public Timetable runGA(Timetable timetable, int maxGenerations) {
        Population population = this.initPopulation(timetable);
        this.evalPopulation(population, timetable);
        int generation = 1;

        while (!this.isTerminationConditionMet(generation, maxGenerations) && !this
                .isTerminationConditionMet(population)) {
            log.info("Generation: " + generation + ", Best fitness: " + population.getFittest(0).getFitness());
            population = this.crossoverPopulation(population);
            population = this.mutatePopulation(population, timetable);
            this.evalPopulation(population, timetable);
            generation++;
        }

        timetable.createClasses(population.getFittest(0));
        log.info("Solution found in " + generation + " generations.");
        log.info("Final solution fitness: " + population.getFittest(0).getFitness());
        log.info("Clashes: " + timetable.calcClashes());

        return timetable;
    }
}
