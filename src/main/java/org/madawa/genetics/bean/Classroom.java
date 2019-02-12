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

package org.madawa.genetics.bean;

import java.util.List;
import java.util.Map;

/**
 * The class {@link Classroom} represents a class room which is identified by a room number and has a capacity
 */
public class Classroom {
    private int id;
    private String number;
    private int capacity;
    private Map<String, List<String>> unavailability;

    public Classroom() {

    }

    public Classroom(int id, String number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<String, List<String>> getUnavailability() {
        return unavailability;
    }

    public void setUnavailability(Map<String, List<String>> unavailability) {
        this.unavailability = unavailability;
    }
}
