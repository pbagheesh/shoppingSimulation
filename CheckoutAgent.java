import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Random;

/**
 * Created by prashant on 10/31/16.
 */
public class CheckoutAgent extends Agent {
    private int numCustomers = 0;
    private int numItems = 0;
    private Queue<CustomerAgent> line = new Queue<>();
    private CustomerAgent currentCustomer = null;
    private ArrayList<CustomerAgent> checkedOutCustomers = new ArrayList<>();

    public CheckoutAgent (int r, int c) {
        //Constructor
        super(r,c);
    }

    public int getNumCustomers() {
        //Returns the number of customers in Queue
        return this.numCustomers;
    }

    public int getNumItems() {
        //returns the number of items that the agent needs to checkout
        return this.numItems;
    }

    public int getStatistics() {
        //returns the average time waited of the checkoutcustomers  -- EXTENSION
        int avgTimeWaited = 0;
        if (checkedOutCustomers.size() == 0) {
            return 0;
        }
        else {
            for (CustomerAgent CA : checkedOutCustomers) {
                avgTimeWaited += CA.getTotalTimeWaited();
            }
            avgTimeWaited = (avgTimeWaited / checkedOutCustomers.size());
            checkedOutCustomers.clear();  //Clears the checkedOutCustomers so every batch has uniform number of customers
            return avgTimeWaited;
        }
    }

    public void addCustomer(CustomerAgent A1) {
        //Adds a customer to the Queue
        this.numItems += A1.getCartSize();
        this.numCustomers += 1;
        this.line.add(A1);
    }

    //Had a remove customer method, however I deleted that since I never used it

    public void draw(Graphics g, int gridScale, int offset) {
        //Draws the customer at location specified by row and col
        if (currentCustomer != null) {
            currentCustomer.draw(g, gridScale);
        }
        if (line.size() != 0) {
            int counter=0;
            for (CustomerAgent CA: line) {
                CA.incrementTimeWaited();
                CA.setRow((int)this.getRow());
                CA.setCol((int)this.getCol()-(counter*2));
                CA.draw(g,gridScale);
                counter += 1;
            }
        }
        g.setColor(Color.blue);
        g.fillRect((int)this.getRow()*gridScale + offset,(int)this.getCol()*gridScale,2*gridScale,4*gridScale);
        g.setColor(Color.black);
    }

    public void updateState() {
        //Updates the state of both the all the customers in the queue
        Random gen = new Random();
        if (this.numCustomers == 0) {
            return;
        }
        else {
            if (currentCustomer == null) {   //Bounds checking
                currentCustomer = this.line.poll();
            }
            if (currentCustomer.getCartSize() == 0) {
                checkedOutCustomers.add(currentCustomer);
                currentCustomer = this.line.poll();      //Extension Data gets garbaged collected
                numCustomers--;
            } else {
                currentCustomer.decrementSize(gen.nextInt(4) + 1);
            }
        }
    }
}

