package main;

import interfaces.Stack;

import java.io.IOException;

import interfaces.List;
import interfaces.Map;

public class CarPartFactory {

    // Data 
    String orderPath; 
    String partsPath;

    // Objects Models
    List<PartMachine> machines;
    Stack<CarPart> productionBin;
    Map<Integer, CarPart> partCatalog;
    Map<Integer, List<CarPart>> inventory;
    List<Order> orders;
    Map<Integer, Integer> defectives;


        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
                
    }


    public List<PartMachine> getMachines() {
       return this.machines;
    }
    public void setMachines(List<PartMachine> machines) {
        this.machines = machines;
    }


    public Stack<CarPart> getProductionBin() {
      return this.productionBin;
    }
    public void setProductionBin(Stack<CarPart> production) {
       this.productionBin = production;
    }


    public Map<Integer, CarPart> getPartCatalog() {
        return partCatalog;
    }
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        this.partCatalog = partCatalog;
    }


    public Map<Integer, List<CarPart>> getInventory() {
       return inventory;
    }
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        this.inventory = inventory;
    }


    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public Map<Integer, Integer> getDefectives() {
        return defectives;
    }
    public void setDefectives(Map<Integer, Integer> defectives) {
        this.defectives = defectives;
    }

    public void setupOrders(String path) throws IOException {
       // here you read and add from the csv
    }


    public void setupMachines(String path) throws IOException {
       // here you read and add from the csv
    }
    public void setupCatalog() {
        
    }


    public void setupInventory() {
        
    }
    public void storeInInventory() {
       
    }


    public void runFactory(int days, int minutes) {
        
    }

   
    public void processOrders() {
        
    }
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
       
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}
