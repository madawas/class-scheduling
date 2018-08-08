package org.iit.genetics.algorithm;

import org.iit.genetics.main.ApplicationDataHolder;

public class Algorithm {
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

    public Population initPopulation(ApplicationDataHolder applicationDataHolder) {
        return new Population(this.populationSize, applicationDataHolder);
    }

    private double calcFitness(Individual individual, ApplicationDataHolder applicationDataHolder) {

        // Create new applicationDataHolder object to use -- cloned from an existing applicationDataHolder
        ApplicationDataHolder threadApplicationDataHolder = new ApplicationDataHolder(applicationDataHolder);
        threadApplicationDataHolder.createClasses(individual);

        // Calculate fitness
        int clashes = threadApplicationDataHolder.calcClashes();
        double fitness = 1 / (double) (clashes + 1);
        individual.setFitness(fitness);
        return fitness;
    }

    public void evalPopulation(Population population, ApplicationDataHolder applicationDataHolder) {
        double populationFitness = 0;

        // Loop over population evaluating individuals and summing population
        // fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual, applicationDataHolder);
        }

        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getFitness() == 1.0;
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
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

    public Population crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
                // Initialize offspring
                Individual offspring = new Individual(parent1.
                        getChromosomeLength());

                // Find second parent
                Individual parent2 = selectParent(population);
                // Loop over genome
                for (int geneIndex = 0; geneIndex < parent1.
                        getChromosomeLength(); geneIndex++) {
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

    public Population mutatePopulation(Population population, ApplicationDataHolder applicationDataHolder) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.
                    getFittest(populationIndex);

            // Create random individual to swap genes with
            Individual randomIndividual = new Individual(applicationDataHolder);

            // Loop over individual's genes
            for (int geneIndex = 0; geneIndex < individual.
                    getChromosomeLength(); geneIndex++) {
                // Skip mutation if this is an elite individual
                if (populationIndex > this.elitismCount) {
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
                        // Swap for new gene
                        individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                    }
                }
            }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }
}
