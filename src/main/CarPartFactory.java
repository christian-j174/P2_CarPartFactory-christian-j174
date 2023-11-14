package main;

import interfaces.Stack;




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import interfaces.HashFunction;
import interfaces.List;
import interfaces.Map;
import data_structures.ArrayList;
import data_structures.HashTableSC;

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

    HashFunction<Integer> hashFunction;


        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        this.orderPath = orderPath;
        this.partsPath = partsPath;
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
        // Map<Integer, Order> ordersMap = new HashTableSC<>(0, null);
        // try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        //     br.readLine(); // Skip the header line
        //     String line;
        //     while ((line = br.readLine()) != null) {
        //         String[] values = line.split(",", 3);
        //         int id = Integer.parseInt(values[0].trim());
        //         String customer = values[1].trim();
        //         Map<Integer, Integer> requestedParts = parseRequestedParts(values[2]);

        //         Order order = new Order(customer, requestedParts); // Assuming Order has a constructor like this
        //         ordersMap.put(id, order);
        //     }
        // }
    }

    // private Map<Integer, Integer> parseRequestedParts(String partsString) {
    //     Map<Integer, Integer> parts = new HashMap<>();
    //     String[] splitParts = partsString.split("-");
    //     for (String part : splitParts) {
    //         part = part.replaceAll("[()]", ""); // Remove parentheses
    //         String[] partDetails = part.split(" ");
    //         int partId = Integer.parseInt(partDetails[0].trim());
    //         int amount = Integer.parseInt(partDetails[1].trim());
    //         parts.put(partId, amount);
    //     }
    //     return parts;
    // }



    public void setupMachines(String path) throws IOException {
       // here you read and add from the csv
       List<PartMachine> machines1 = new ArrayList<>();
       try (BufferedReader br = new BufferedReader(new FileReader(path))) {
           br.readLine(); // Skip the header line
           String line;
           while ((line = br.readLine()) != null) {
               String[] values = line.split(",");
               int id = Integer.parseInt(values[0].trim());
               String partName = values[1].trim();
               double weight = Double.parseDouble(values[2].trim());
               double weightError = Double.parseDouble(values[3].trim());
               int period = Integer.parseInt(values[4].trim());
               int chanceOfDefective = Integer.parseInt(values[5].trim());

               CarPart carPart = new CarPart(id, partName, weight,false);
               PartMachine machine = new PartMachine(id, carPart, period, weightError, chanceOfDefective);

               machines1.add(machine);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
       setMachines(machines1);

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
