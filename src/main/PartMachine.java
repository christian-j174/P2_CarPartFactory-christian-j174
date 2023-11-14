package main;

import java.util.Random;

import interfaces.Queue;

public class PartMachine {

    int id;
    CarPart p1;
    int period;
    double weightError;
    int chanceOfDefective;
    Queue<Integer> timer;
    Queue<CarPart> conveyorBelt;
    int totalPartProduced = 0;
    Random random = new Random();

   
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
        this.id = id;
        this.p1 = p1;
        this.period = period;
        this.weightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        
        for(int i = period - 1; i >= 0; i--)
            this.timer.enqueue(i);

        for(int j = 0; j < 10; j++)
            this.conveyorBelt.enqueue(null);
        
        
        

    }
    public int getId() {
       return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Queue<Integer> getTimer() {
       return timer;
    }
    public void setTimer(Queue<Integer> timer) {
        this.timer = timer;
    }
    public CarPart getPart() {
       return p1;
    }
    public void setPart(CarPart part1) {
        this.p1 = p1;
    }
    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	this.conveyorBelt = conveyorBelt;
    }

    public int getTotalPartsProduced() {
         return totalPartProduced;
    }
    public void setTotalPartsProduced(int count) {
    	this.totalPartProduced = count;
    }
    public double getPartWeightError() {
        return weightError;
    }
    public void setPartWeightError(double partWeightError) {
        this.weightError = partWeightError;
    }
    public int getChanceOfDefective() {
        return chanceOfDefective;
    }
    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    public void resetConveyorBelt() {
        // clear the conveyor belt
        while (!conveyorBelt.isEmpty()) {
            conveyorBelt.dequeue();
        }
    
        // fill the conveyor belt with null values
        for (int i = 0; i < 10; i++) 
            conveyorBelt.enqueue(null);
    }
    


    public int tickTimer() {
        int frontTimer = timer.front(); // get the value at the front without removing it
        timer.enqueue(timer.dequeue()); // remove the front value and places it at the back
        return frontTimer;
    }
    
    public CarPart produceCarPart() {
        CarPart frontPart = conveyorBelt.dequeue(); // Get and remove the front part from the conveyor belt
    
        if (tickTimer() == 0) { // Check if a part is being produced
            double weight = p1.getWeight() - weightError + (2 * weightError * random.nextDouble()); // Random weight within the error range
            boolean isDefective = (totalPartProduced % chanceOfDefective) == 0; // Check if part is defective
    
            CarPart newPart = new CarPart(p1.getId(), p1.getName(), weight, isDefective); // Create new part
            conveyorBelt.enqueue(newPart); // Add new part to the conveyor belt
            totalPartProduced++; // Increment the count of total parts produced
        } else {
            conveyorBelt.enqueue(null); // Add null to the conveyor belt if no part is being produced
        }
    
        return frontPart; // Return the part that was at the front of the conveyor belt
    }
    

    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}
