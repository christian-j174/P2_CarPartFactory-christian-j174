package main;

import interfaces.Map;

/**
 * Represents an order with its details including ID, customer name, requested parts, and fulfillment status.
 */
public class Order {
   
    int id;
    String customerName;
    Map<Integer, Integer> requestedParts;
    boolean fulfilled;

    /**
     * Constructs an Order instance with specified details.
     *
     * @param id The identifier of the order.
     * @param customerName The name of the customer who placed the order.
     * @param requestedParts A map representing the parts requested in the order with their quantities.
     * @param fulfilled Indicates whether the order has been fulfilled.
     */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled;
        
    }

    /**
     * Returns the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The new ID of the order.
     */    
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the fulfillment status of the order.
     *
     * @return True if the order is fulfilled, false otherwise.
     */
    public boolean isFulfilled() {
      return fulfilled;
    }

    /**
     * Sets the fulfillment status of the order.
     *
     * @param fulfilled The new fulfillment status of the order.
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Returns the map of requested parts and their quantities for the order.
     *
     * @return A map representing the requested parts and their quantities.
     */
    public Map<Integer, Integer> getRequestedParts() {
       return requestedParts;
    }

    /**
     * Sets the map of requested parts and their quantities for the order.
     *
     * @param requestedParts The new map of requested parts and their quantities.
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
       this.requestedParts = requestedParts;
    }

    /**
     * Returns the name of the customer who placed the order.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
      return this.customerName;
    }

    /**
     * Sets the name of the customer who placed the order.
     *
     * @param customerName The new name of the customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
