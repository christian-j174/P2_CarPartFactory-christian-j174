package main;

import interfaces.Map;

public class Order {
   
    int id;
    String customerName;
    Map<Integer, Integer> requestedParts;
    boolean fulfilled;

    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isFulfilled() {
      return fulfilled;
    }
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    public Map<Integer, Integer> getRequestedParts() {
       return requestedParts;
    }
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
       this.requestedParts = requestedParts;
    }
    public String getCustomerName() {
      return this.customerName;
    }
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
