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

package org.madawa.genetics.configuration;

/**
 * This class holds all the configuration parameters of the application.
 */
public class AppConfig {
    private Double mutationRate;
    private Double crossoverRate;
    private Integer initialPopulationSize;
    private Integer elitismCount;
    private Integer tournamentCount;
    private Integer maxGenerations;
    private Integer weightFollowOnClasses;

    public Double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(Double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public Double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(Double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public Integer getInitialPopulationSize() {
        return initialPopulationSize;
    }

    public void setInitialPopulationSize(Integer initialPopulationSize) {
        this.initialPopulationSize = initialPopulationSize;
    }

    public Integer getElitismCount() {
        return elitismCount;
    }

    public void setElitismCount(Integer elitismCount) {
        this.elitismCount = elitismCount;
    }

    public Integer getTournamentCount() {
        return tournamentCount;
    }

    public void setTournamentCount(Integer tournamentCount) {
        this.tournamentCount = tournamentCount;
    }

    public Integer getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(Integer maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public Integer getWeightFollowOnClasses() {
        return weightFollowOnClasses;
    }

    public void setWeightFollowOnClasses(Integer weightFollowOnClasses) {
        this.weightFollowOnClasses = weightFollowOnClasses;
    }
}