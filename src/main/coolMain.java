
package main;

import java.io.IOException;

public class coolMain { 
    public static void main(String[] args) throws IOException {
       CarPartFactory cpf = new CarPartFactory("input/orders.csv", "input/parts.csv");
       // cpf.setupMachines("input/parts.csv");
        //System.out.println(cpf.getMachines());
    
    
        // CarPart part = new CarPart(10, "Transmission", 15.0, true);
        // PartMachine tmp1 = new PartMachine(0, part, 0, 0, 0);
        // System.out.println(tmp1.toString());
    }
}
