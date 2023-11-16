package main;

/**
 * Represents a car part with its properties and defectiveness status.
 */
public class CarPart {
    int id; 
    String name; 
    double weight; 
    boolean isDefective;

    /**
     * Constructs a CarPart instance with specified details.
     *
     * @param id The identifier of the car part.
     * @param name The name of the car part.
     * @param weight The weight of the car part.
     * @param isDefective Indicates whether the car part is defective.
     */
    public CarPart(int id, String name, double weight, boolean isDefective) {
    this.id = id;
    this.name = name;
    this.weight = weight;
    this.isDefective = isDefective;
    }
   /**
     * Returns the ID of the car part.
     *
     * @return The ID of the car part.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the ID of the car part.
     *
     * @param id The new ID of the car part.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the name of the car part.
     *
     * @return The name of the car part.
     */
    public String getName() {
       return this.name; 
    }

    /**
     * Sets the name of the car part.
     *
     * @param name The new name of the car part.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the weight of the car part.
     *
     * @return The weight of the car part.
     */
    public double getWeight() {
      return this.weight;
    }

    /**
     * Sets the weight of the car part.
     *
     * @param weight The new weight of the car part.
     */
    public void setWeight(double weight) {
      this.weight = weight;
    }

    /**
     * Returns the defectiveness status of the car part.
     *
     * @return True if the car part is defective, false otherwise.
     */
    public boolean isDefective() {
      return this.isDefective;
    }

    /**
     * Sets the defectiveness status of the car part.
     *
     * @param isDefective The new defectiveness status of the car part.
     */
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