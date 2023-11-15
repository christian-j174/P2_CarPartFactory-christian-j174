package main;

import interfaces.Stack;




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import interfaces.HashFunction;
import interfaces.List;
import interfaces.Map;
import data_structures.ArrayList;
import data_structures.HashTableSC;
import data_structures.LinkedStack;


public class CarPartFactory {

    List<PartMachine> machines;
    List<Order> orders;

    Stack<CarPart> productionBin = new LinkedStack<>();

    


    Map<Integer, CarPart> partCatalog;

    Map<Integer, List<CarPart>> inventory;
    
    Map<Integer, Integer> defectives = new HashTableSC<>(20, new SimpleHashFunction());;

    class SimpleHashFunction implements HashFunction<Integer> {
        @Override
        public int hashCode(Integer key) {
            // Simple hash code implementation
            return key.hashCode();
        }
    }



        
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        
        setupOrders(orderPath);
        setupMachines(partsPath);
        setupInventory();

        
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
        //HashFunction<Integer> intHashFunction = new BasicHashFunction<Integer>();
        ArrayList<Order> tmpOrders = new ArrayList<Order>(110);
        Map<Integer, Order> ordersMap = new HashTableSC<>(105,new SimpleHashFunction());
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip the header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 3);
                int id = Integer.parseInt(values[0].trim());
                String customer = values[1].trim();
                Map<Integer, Integer> requestedParts = parseRequestedParts(values[2]);

                Order order = new Order(id, customer, requestedParts, false);
                tmpOrders.add(order);
                ordersMap.put(id, order); // key id client, and his order
            }
        }
        setOrders(tmpOrders);
    }

    private Map<Integer, Integer> parseRequestedParts(String partsString) {
        Map<Integer, Integer> parts = new HashTableSC<>(10,new SimpleHashFunction());
        String[] splitParts = partsString.split("-");
        for (String part : splitParts) {
            part = part.replaceAll("[()]", ""); // Remove parentheses
            String[] partDetails = part.split(" ");
            int partId = Integer.parseInt(partDetails[0].trim());
            int amount = Integer.parseInt(partDetails[1].trim());
            parts.put(partId, amount);
        }
        return parts;
     }



    public void setupMachines(String path) throws IOException {
       // here you read and add from the csv
       List<PartMachine> machines1 = new ArrayList<>();
       Map<Integer, CarPart> partCatalogTmp = new HashTableSC<>(20,new SimpleHashFunction());
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

               partCatalogTmp.put(carPart.getId(), carPart);
               machines1.add(machine);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
       setMachines(machines1);
       setPartCatalog(partCatalogTmp);

    }






    public void setupInventory() {
        Map<Integer, List<CarPart>> inventoryTmp = new HashTableSC<>(20,new SimpleHashFunction());
        for(PartMachine part1: getMachines())
            inventoryTmp.put(part1.getPart().getId(), new ArrayList<>());
        
        setInventory(inventoryTmp);

    }

    public void storeInInventory() {
        while (!getProductionBin().isEmpty()) {
            CarPart part = getProductionBin().pop();
    
            if (!part.isDefective()) {
                // Check if the inventory has a list for this part ID, initialize it if not
                List<CarPart> partsList = getInventory().get(part.getId());
                if (partsList == null) {
                    partsList = new ArrayList<>();
                    getInventory().put(part.getId(), partsList);
                }
                partsList.add(part);
            } else {
                // Increment defective count
                int currentDefectiveCount = defectives.containsKey(part.getId()) ? defectives.get(part.getId()) : 0;
                defectives.put(part.getId(), currentDefectiveCount + 1);
            }
        }
    }
    
    


    public void runFactory(int days, int minutes) {
        for (int day = 0; day < days; day++) {
            for (int minute = 0; minute < minutes; minute++) {
                for (PartMachine machine : getMachines()) {
                    CarPart producedPart = machine.produceCarPart();
                    if (producedPart != null) {
                        getProductionBin().push(producedPart);
                    }
                }
            }
            // Empty conveyor belts into production bin
            for (PartMachine machine : getMachines()) {
                while (!machine.getConveyorBelt().isEmpty()) {
                    CarPart part = machine.getConveyorBelt().dequeue();
                    if (part != null) {
                        getProductionBin().push(part);
                    }
                }
            }
            storeInInventory();
        }
        processOrders();
    }
    

   
    public void processOrders() {
        for (Order order : getOrders()) {
            boolean canFulfill = true;
            Map<Integer, Integer> requestedParts = order.getRequestedParts();
    
            // Check if all parts are available in required quantities
            for (Integer partId : requestedParts.getKeys()) {
                int requiredQuantity = requestedParts.get(partId);
                List<CarPart> partsList = getInventory().get(partId);
    
                if (partsList == null || partsList.size() < requiredQuantity) {
                    canFulfill = false;
                    break;
                }
            }
    
            // Fulfill the order if possible
            if (canFulfill) {
                for (Integer partId : requestedParts.getKeys()) {
                    int requiredQuantity = requestedParts.get(partId);
                    List<CarPart> partsList = getInventory().get(partId);
    
                    for (int i = 0; i < requiredQuantity; i++) {
                        partsList.remove(0); // Assuming remove(0) removes the first element
                    }
                }
                order.setFulfilled(true); // Mark the order as fulfilled
            }
        }
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
