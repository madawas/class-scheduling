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

/**
 * The class StudentGroup represents a group of students which need to follow a set of enrollments.
 */
public class StudentGroup {
    private int id;
    private int size;
    private List<Module> enrollments;

    public void setId(int id) {
        this.id = id;
    }


    public void setSize(int size) {
        this.size = size;
    }
    public void setEnrollments(List<Module> enrollments) {
        this.enrollments = enrollments;
    }


    public int getId() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

    public List<Module> getEnrollments() {
        return this.enrollments;
    }
}
