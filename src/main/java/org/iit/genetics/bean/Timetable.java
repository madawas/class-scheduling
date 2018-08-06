package org.iit.genetics.bean;

import org.iit.genetics.algorithm.Individual;

import java.util.HashMap;
import java.util.List;

public class Timetable {
    private final HashMap<Integer, Room> rooms;
    private final HashMap<Integer, Professor> professors;
    private final HashMap<Integer, Module> modules;
    private final HashMap<Integer, Batch> batches;
    private final HashMap<Integer, TimeSlot> timeSlots;
    private org.iit.genetics.bean.Class[] classes;

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
        this.rooms.put(roomId, new Room(roomId, roomName, capacity));
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

    public void addTimeSlot(int timeSlotId, String timeSlot) {
        this.timeSlots.put(timeSlotId, new TimeSlot(timeSlotId, timeSlot));
    }

    public void createClasses(Individual individual) {
        // Init classes
        org.iit.genetics.bean.Class[] classes = new org.iit.genetics.bean.Class[this.getNumClasses()];
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (Batch batch : this.getGroupsAsArray()) {
            List<Module> modules = batch.getModules();
            for (Module module: modules) {
                classes[classIndex] = new org.iit.genetics.bean.Class(classIndex, batch, module);

                // Add timeSlot
                classes[classIndex].setTimeSlot(getTimeSlot(chromosome[chromosomePos]));
                chromosomePos++;

                // Add room
                classes[classIndex].setRoom(getRoom(chromosome[chromosomePos]));
                chromosomePos++;

                // Add professor
                classes[classIndex].setProfessor(getProfessor(chromosome[chromosomePos]));
                chromosomePos++;

                classIndex++;
            }
        }
        this.classes = classes;
    }

    public Room getRoom(int roomId) {
        if (!this.rooms.containsKey(roomId)) {
            System.out.println("Rooms doesn't contain key " + roomId);
        }
        return (Room) this.rooms.get(roomId);
    }

    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    public Room getRandomRoom() {
        Object[] roomsArray = this.rooms.values().toArray();
        Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
        return room;
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

    public org.iit.genetics.bean.Class[] getClasses() {
        return this.classes;
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
        for (org.iit.genetics.bean.Class classA : this.classes) {
            // Check room capacity
            int roomCapacity = classA.getRoom().getRoomCapacity();
            int batchSize = classA.getBatch().getSize();
            if (roomCapacity < batchSize) {
                clashes++;
            }

            // Check if room is taken
            for (org.iit.genetics.bean.Class classB : this.classes) {
                if (classA.getRoom() == classB.getRoom() && classA.getTimeSlot() == classB.
                        getTimeSlot() && classA.getId() != classB.getId()) {
                    clashes++;
                    break;
                }
            }

            // Check if professor is available
            for (org.iit.genetics.bean.Class classB : this.classes) {
                if (classA.getProfessor() == classB.
                        getProfessor() && classA.getTimeSlot() == classB.getTimeSlot()
                        && classA.getId() != classB.getId()) {
                    clashes++;
                    break;
                }
            }
        }

        return clashes;
    }
}
