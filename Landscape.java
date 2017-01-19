/**
 * Created by prashant on 10/31/16.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;

public class Landscape {
    private int rows = 0;
    private int cols = 0;
    private Random gen = new Random();
    private ArrayList<CustomerAgent> customers;
    private ArrayList<CheckoutAgent> checkoutAgents;

    public Landscape (int rows, int cols, int numCashiers) {
        //Constructor initialises row and col to the parameter row and col
        //Creates new arraylists for customer Agent and checkout agent
        this.rows = rows;
        this.cols = cols;
        customers = new ArrayList<CustomerAgent>();
        checkoutAgents = new ArrayList<CheckoutAgent>();

        for (int i= 0; i < numCashiers; i++) {
            CheckoutAgent checkoutCounter = new CheckoutAgent(0+((this.cols/numCashiers*i)),90);
            checkoutAgents.add(checkoutCounter);
        }
    }


    public void spawner(int num) {
        //Randomly creates a number of customer agents based on the parameter num
        //Has a 20% chance of creating new agents
        double percentVal = gen.nextDouble();
        if (percentVal < 0.2) {
            for (int i = 0; i < num + 1; i++) {
                CustomerAgent tempCustomer = new CustomerAgent(gen.nextInt(this.rows), gen.nextInt(this.cols/3),gen.nextInt(10),3);
                customers.add(tempCustomer);
            }
        }
    }

    public int getRows() {
        //Returns the number of rows
        return this.rows;
    }

    public int getCols() {
        // Returns the number of Columns
        return this.cols;
    }

    public void draw(Graphics g, int gridScale) {
        //Draws the landscape by looping through the customers list and checkoutAgent list and calling their
        //individual draw methods.
        if(customers != null) {
            for (CustomerAgent CA : customers) {
                CA.draw(g, gridScale);
            }
        }
        for (CheckoutAgent CHA : checkoutAgents) {
            CHA.draw(g, gridScale, (int)(this.getCols()/7));
        }
    }

    public int getStatistics() {
        //returns the avg wait time for the simulation
        int simAvgVal = 0;
        for (CheckoutAgent CHA: checkoutAgents) {
            simAvgVal += CHA.getStatistics();
        }
        simAvgVal =(simAvgVal/checkoutAgents.size());
        return simAvgVal;
    }

    public int getMostEfficientCashier() {
        //Loops through all the checkout agents and finds one with smallest average wait time
        //returns the index value of that most efficient cashier.
        int mostEfficientTime = checkoutAgents.get(0).getStatistics();
        int mostEfficientAgent = 0;
        int counter = 0;
        for (CheckoutAgent CHA : checkoutAgents) {
            if (CHA.getStatistics() < mostEfficientTime) {
                mostEfficientTime = CHA.getStatistics();
                mostEfficientAgent = counter;
                counter += 1;
            }
        }
        return mostEfficientAgent;
    }

    public void updateState() {
        //Updates the state of the landscape by looping through the customers and checkout agents
        //and calls each agent's update state.
        ArrayList<CustomerAgent> delme = new ArrayList<CustomerAgent>();

        if(customers != null) {
            for (CustomerAgent CA : customers) {
                if (CA.updateState(this.checkoutAgents) >= 0) {
                    delme.add(CA);
                    checkoutAgents.get(CA.updateState(this.checkoutAgents)).addCustomer(CA);
                }
            }
        }
        if (delme.size() != 0) {
            for (CustomerAgent CA : delme) {
                customers.remove(CA);
            }
        }
        for (CheckoutAgent CHA : checkoutAgents) {
            CHA.updateState();
        }
    }

}
