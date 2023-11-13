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
        // this.conveyorBelt
    }
    public int tickTimer() {
        return timer.dequeue();// verify this
      // decrease timer  
    }
    public CarPart produceCarPart() {
       return new CarPart(random.nextInt(), null, 10, false);
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
