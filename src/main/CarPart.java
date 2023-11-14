package main;

public class CarPart {
    int id; 
    String name; 
    double weight; 
    boolean isDefective;

    
    public CarPart(int id, String name, double weight, boolean isDefective) {
    this.id = id;
    this.name = name;
    this.weight = weight;
    this.isDefective = isDefective;
    
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
       return this.name; 
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getWeight() {
      return this.weight;
    }
    public void setWeight(double weight) {
      this.weight = weight;
    }

    public boolean isDefective() {
      return this.isDefective;
    }
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}