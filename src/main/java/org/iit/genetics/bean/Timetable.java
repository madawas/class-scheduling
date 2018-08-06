package org.iit.genetics.bean;

import org.iit.genetics.algorithm.Individual;

import java.util.HashMap;
import java.util.List;

public class Timetable {
    private final HashMap<Integer, Classroom> rooms;
    private final HashMap<Integer, Professor> professors;
    private final HashMap<Integer, Module> modules;
    private final HashMap<Integer, Batch> batches;
    private final HashMap<Integer, TimeSlot> timeSlots;
    private ScheduledClass[] scheduledClasses;

    private int numClasses = 0;

    public Timetable() {
        this.rooms = new HashMap<>();
        this.professors = new HashMap<>();
        this.modules = new HashMap<>();
        this.batches = new HashMap<>();
        this.timeSlots = new HashMap<>();
    }

    public Timetable(Timetable cloneable) {
        this.rooms = cloneable.getRooms();
        this.professors = cloneable.getProfessors();
        this.modules = cloneable.getModules();
        this.batches = cloneable.getBatches();
        this.timeSlots = cloneable.getTimeSlots();
    }

    private HashMap<Integer, Batch> getBatches() {
        return this.batches;
    }

    private HashMap<Integer, TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }

    private HashMap<Integer, Module> getModules() {
        return this.modules;
    }

    private HashMap<Integer, Professor> getProfessors() {
        return this.professors;
    }

    public void addRoom(int roomId, String roomName, int capacity) {
        this.rooms.put(roomId, new Classroom(roomId, roomName, capacity));
    }

    public void addProfessor(int professorId, String professorName) {
        this.professors.put(professorId, new Professor(professorId, professorName));
    }

    public void addModule(int moduleId, String module, List<Professor> professors) {
        this.modules.put(moduleId, new Module(moduleId, module, professors));
    }

    public void addGroup(int groupId, int groupSize, List<Module> modules) {
        this.batches.put(groupId, new Batch(groupId, groupSize, modules));
        this.numClasses = 0;
    }

    public void addTimeSlot(int timeSlotId, int day, int slotIndex) {
        this.timeSlots.put(timeSlotId, new TimeSlot(timeSlotId, day, slotIndex));
    }

    public void createClasses(Individual individual) {
        // Init scheduledClasses
        ScheduledClass[] scheduledClasses = new ScheduledClass[this.getNumClasses()];
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (Batch batch : this.getGroupsAsArray()) {
            List<Module> modules = batch.getModules();
            for (Module module: modules) {
                scheduledClasses[classIndex] = new ScheduledClass(classIndex, batch, module);

                // Add timeSlot
                scheduledClasses[classIndex].setTimeSlot(getTimeSlot(chromosome[chromosomePos]));
                chromosomePos++;

                // Add room
                scheduledClasses[classIndex].setClassroom(getRoom(chromosome[chromosomePos]));
                chromosomePos++;

                // Add professor
                scheduledClasses[classIndex].setProfessor(getProfessor(chromosome[chromosomePos]));
                chromosomePos++;

                classIndex++;
            }
        }
        this.scheduledClasses = scheduledClasses;
    }

    public Classroom getRoom(int roomId) {
        if (!this.rooms.containsKey(roomId)) {
            System.out.println("Rooms doesn't contain key " + roomId);
        }
        return (Classroom) this.rooms.get(roomId);
    }

    public HashMap<Integer, Classroom> getRooms() {
        return this.rooms;
    }

    public Classroom getRandomRoom() {
        Object[] roomsArray = this.rooms.values().toArray();
        Classroom classroom = (Classroom) roomsArray[(int) (roomsArray.length * Math.random())];
        return classroom;
    }

    public Professor getProfessor(int professorId) {
        return this.professors.get(professorId);
    }

    public Module getModule(int moduleId) {
        return this.modules.get(moduleId);
    }

    public List<Module> getBatchModules(int batchId) {
        Batch batch = this.batches.get(batchId);
        return batch.getModules();
    }

    public Batch getGroup(int groupId) {
        return this.batches.get(groupId);
    }

    public Batch[] getGroupsAsArray() {
        return this.batches.values().toArray(new Batch[this.batches.size()]);
    }

    public TimeSlot getTimeSlot(int timeSlotId) {
        return this.timeSlots.get(timeSlotId);
    }

    public TimeSlot getRandomTimeslot() {
        Object[] timeSlotArray = this.timeSlots.values().toArray();
        return (TimeSlot) timeSlotArray[(int) (timeSlotArray.length * Math.random())];
    }

    public ScheduledClass[] getScheduledClasses() {
        return this.scheduledClasses;
    }

    public int getNumClasses() {
        if (this.numClasses > 0) {
            return this.numClasses;
        }
        int numClasses = 0;
        Batch batches[] = this.batches.values().toArray(new Batch[this.batches.size()]);
        for (Batch batch : batches) {
            numClasses += batch.getModules().size();
        }
        this.numClasses = numClasses;
        return this.numClasses;
    }

    public int calcClashes() {
        int clashes = 0;
        for (ScheduledClass scheduledClassA : this.scheduledClasses) {
            // Check room capacity
            int roomCapacity = scheduledClassA.getClassroom().getClassroomCapacity();
            int batchSize = scheduledClassA.getBatch().getSize();
            if (roomCapacity < batchSize) {
                clashes++;
            }

            // Check if room is taken
            for (ScheduledClass scheduledClassB : this.scheduledClasses) {
                if (scheduledClassA.getClassroom() == scheduledClassB.getClassroom() && scheduledClassA.getTimeSlot() == scheduledClassB
                        .
                        getTimeSlot() && scheduledClassA.getId() != scheduledClassB.getId()) {
                    clashes++;
                    break;
                }
            }

            // Check if professor is available
            for (ScheduledClass scheduledClassB : this.scheduledClasses) {
                if (scheduledClassA.getProfessor() == scheduledClassB.
                        getProfessor() && scheduledClassA.getTimeSlot() == scheduledClassB.getTimeSlot()
                        && scheduledClassA.getId() != scheduledClassB.getId()) {
                    clashes++;
                    break;
                }
            }
        }

        return clashes;
    }
}
