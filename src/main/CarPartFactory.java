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


/**
 * Factory class for managing car parts production and orders.
 */
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
            return key.hashCode();
        }
    }



    /**
     * Constructs a new CarPartFactory with specified paths for orders and parts data.
     *
     * @param orderPath Path to the orders CSV file.
     * @param partsPath Path to the parts CSV file.
     * @throws IOException If an I/O error occurs.
     */
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
        setupOrders(orderPath);
        setupMachines(partsPath);
        setupInventory();
    }



    /**
     * Returns the list of part machines.
     *
     * @return The list of part machines.
     */
    public List<PartMachine> getMachines() {
       return this.machines;
    }


    /**
     * Sets the list of part machines.
     *
     * @param machines The list of PartMachine instances.
     */
    public void setMachines(List<PartMachine> machines) {
        this.machines = machines;
    }

    /**
     * Returns the production bin stack.
     *
     * @return The stack of CarPart in the production bin.
     */
    public Stack<CarPart> getProductionBin() {
      return this.productionBin;
    }

    /**
     * Sets the production bin stack.
     *
     * @param production The stack of CarPart for the production bin.
     */
    public void setProductionBin(Stack<CarPart> production) {
       this.productionBin = production;
    }

    /**
     * Returns the part catalog map.
     *
     * @return The map of car parts.
     */
    public Map<Integer, CarPart> getPartCatalog() {
        return partCatalog;
    }

    /**
     * Sets the part catalog map.
     *
     * @param partCatalog The map of car parts.
     */
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        this.partCatalog = partCatalog;
    }

    /**
     * Returns the inventory map.
     *
     * @return The map of lists of CarPart for the inventory.
     */
    public Map<Integer, List<CarPart>> getInventory() {
       return inventory;
    }
    /**
     * Sets the inventory map.
     *
     * @param inventory The map of lists of CarPart for the inventory.
     */
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the list of orders.
     *
     * @return The list of orders.
     */
    public List<Order> getOrders() {
        return orders;
    }
    /**
     * Sets the list of orders.
     *
     * @param orders The list of Order instances.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Returns the map of defective counts.
     *
     * @return The map of integers representing defective counts.
     */
    public Map<Integer, Integer> getDefectives() {
        return defectives;
    }

    /**
     * Sets the map of defective counts.
     *
     * @param defectives The map of integers representing defective counts.
     */
    public void setDefectives(Map<Integer, Integer> defectives) {
        this.defectives = defectives;
    }

    /**
     * Sets up the orders based on a provided CSV file path.
     *
     * @param path Path to the orders CSV file.
     * @throws IOException If an I/O error occurs.
     */
    public void setupOrders(String path) throws IOException {
        ArrayList<Order> tmpOrders = new ArrayList<Order>(110);
        Map<Integer, Order> ordersMap = new HashTableSC<>(105,new SimpleHashFunction());
        try (BufferedReader lines2 = new BufferedReader(new FileReader(path))) {
            lines2.readLine(); // Skip the header line
            String line;
            while ((line = lines2.readLine()) != null) {
                String[] values = line.split(",", 3);
                int id = Integer.parseInt(values[0].trim());
                String customer = values[1].trim();
                Map<Integer, Integer> requestedParts = helperRequestedParts(values[2]);

                Order order = new Order(id, customer, requestedParts, false);
                tmpOrders.add(order);
                ordersMap.put(id, order); // key id client, and his order
            }
        }
        setOrders(tmpOrders);
    }

    /**
     * Parses the requested parts from a string.
     *
     * @param partsString The string containing parts information.
     * @return A map of part IDs to their quantities.
     */
    private Map<Integer, Integer> helperRequestedParts(String partsString) {
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


/**
 * Initializes the machines based on the data from the provided CSV file.
 *
 * @param path Path to the parts CSV file.
 * @throws IOException If an I/O error occurs.
 */
    public void setupMachines(String path) throws IOException {
       // here you read and add from the csv
       List<PartMachine> machines1 = new ArrayList<>();
       Map<Integer, CarPart> partCatalogTmp = new HashTableSC<>(20,new SimpleHashFunction());
       try (BufferedReader lines1 = new BufferedReader(new FileReader(path))) {
           lines1.readLine();
           String line;
           while ((line = lines1.readLine()) != null) {
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





/**
 * Initializes the inventory. Each car part ID will have an empty list in the inventory.
 */
    public void setupInventory() {
        Map<Integer, List<CarPart>> inventoryTmp = new HashTableSC<>(20,new SimpleHashFunction());
        for(PartMachine part1: getMachines())
            inventoryTmp.put(part1.getPart().getId(), new ArrayList<>());
        
        setInventory(inventoryTmp);

    }

/**
 * Processes the content of the production bin and updates the inventory.
 * Defective parts are not added to the inventory but are counted separately.
 */
    public void storeInInventory() {
        while (!getProductionBin().isEmpty()) {
            CarPart part = getProductionBin().pop();
    
            if (!part.isDefective()) {
                // Check if the inventory has a list for this part ID
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
    
    

/**
 * Simulates the factory operation for a specified number of days and minutes per day.
 * It processes the production of car parts and updates the inventory at the end of each day.
 *
 * @param days The number of days to simulate.
 * @param minutes The number of minutes per day to simulate.
 */
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
            // Empty conveyor belts 
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
    

/**
 * Processes the orders based on the current inventory.
 * An order is fulfilled if all requested parts are available in the required quantities.
 */
    public void processOrders() {
        for (Order order : getOrders()) {
            boolean canFulfill = true;
            Map<Integer, Integer> requestedParts = order.getRequestedParts();
    
            // Check if all parts are available  qty
            for (Integer partId : requestedParts.getKeys()) {
                int requiredQuantity = requestedParts.get(partId);
                List<CarPart> partsList = getInventory().get(partId);
    
                if (partsList == null || partsList.size() < requiredQuantity) {
                    canFulfill = false;
                    break;
                }
            }
    
            
            if (canFulfill) {
                for (Integer partId : requestedParts.getKeys()) {
                    int requiredQuantity = requestedParts.get(partId);
                    List<CarPart> partsList = getInventory().get(partId);
    
                    for (int i = 0; i < requiredQuantity; i++) {
                        partsList.remove(0); 
                    }
                }
                order.setFulfilled(true); 
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
