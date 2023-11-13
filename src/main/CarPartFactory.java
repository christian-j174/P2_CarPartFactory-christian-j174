package main;

import interfaces.Stack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import interfaces.List;
import interfaces.Map;
import data_structures.ArrayList;

public class CarPartFactory {

    // CSV Data 
    String orderPath; 
    String partsPath;

    // Objects Models
    List<PartMachine> machines;
    Stack<CarPart> productionBin;

    // Que hace?
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

    public List<Order> setupOrders(String path) throws IOException {
       // here you read and add from the csv, lets start!
       List<Order> orders = new ArrayList<>();
       try(BufferedReader br = new BufferedReader(new FileReader(path))){
            br.readLine(); // skip first line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0].trim());
                String customer = values[1].trim();
                List<String> requestedParts = parseRequestedParts(values[2]);

                orders.add(new Order(id, customer, null, false));
            }
       } catch (IOException e) {
        e.printStackTrace();
       }
       return orders;

    }
        // Helper function to setup Orders 
        List<String> parseRequestedParts(String partsString) {
            List<String> tmp = new ArrayList<>();
            String[] splitParts = partsString.split("-");
            for (String part : splitParts) {
                tmp.add(part.trim());
            }
            return tmp;
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
        //this.orders = setupOrders(orderPath);
        
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
