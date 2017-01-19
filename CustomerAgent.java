
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by prashant on 10/31/16.
 */
public class CustomerAgent extends Agent {
    private int cartSize = 1;
    private int waitTime = 1;
    private int selectionType = 0;
    private int totalTimeWaited = 0;

    public CustomerAgent(int r, int c, int size) {
        //Constructor
        super(r,c);
        this.cartSize = size;
    }

    public CustomerAgent(int r, int c,int size,int type) {
        //Constructor with additional fields such as bucket size and selection type
        super(r,c);
        this.cartSize = size;
        Random gen = new Random();
        if (type == 0) {
            this.waitTime = 1;
            this.selectionType = 0;
        }
        else if (type == 1) {
            this.waitTime = 2;
            this.selectionType = 1;
        }
        else if (type == 2) {
            this.waitTime = 4;
            this.selectionType = 2;
        }
        else {
            this.waitTime = gen.nextInt(15)+5;
            this.selectionType = 3;
        }
    }

    public int getCartSize() {
        //returns the cart size
        return this.cartSize;
    }

    public void decrementSize(int reductionFactor) {
        //Decrements the size by the value stated as the parameter
        if (this.cartSize - reductionFactor >= 0) {
            this.cartSize = this.cartSize - (reductionFactor);
        }
        else {
            this.cartSize = 0;
        }
    }

    public void incrementTimeWaited(){
        //Increments the total time the customer has waited
        this.totalTimeWaited += 1;}

    public int getTotalTimeWaited() {
        //Returns the time each customer waited
            return this.totalTimeWaited;
        }

    public int selector (ArrayList <CheckoutAgent> checkoutLines) {
        //loops through given arraylist of checkout Agents and finds the Checkout agent with smallest number
        //of items in checkout line
        int lineToEnter = 0;
        if (selectionType == 0) {
            Random gen = new Random();
            return gen.nextInt(checkoutLines.size());
        }
        else if (selectionType == 1) {
            Random gen = new Random();
            int index1 = gen.nextInt(checkoutLines.size());
            int index2 = gen.nextInt(checkoutLines.size());

            int Val1 = checkoutLines.get(index1).getNumItems();
            int Val2 = checkoutLines.get(index2).getNumItems();

            if (Val1 > Val2) {
                return index2;
            }
            else {
               return index1;
            }

        }
        else if (selectionType == 2) {
            int smallestLine = checkoutLines.get(0).getNumItems();
            lineToEnter = 0;
            for (int i = 1; i < checkoutLines.size(); i++) {
                if (checkoutLines.get(i).getNumItems() < smallestLine) {
                    smallestLine = checkoutLines.get(i).getNumCustomers();
                    lineToEnter = i;
                }
            }
        }
        else if (selectionType == 3) {
            if (this.cartSize < 2) {
                lineToEnter = (checkoutLines.size()-1);
            }
            else {
                Random gen = new Random();
                lineToEnter = gen.nextInt((checkoutLines.size()-1));
            }
        }
        return lineToEnter;
    }

    public void draw(Graphics g, int gridScale ) {
        //Draws the customer agent as circle with size (0.5*cartsize)
        //Draws it green if wait time is less than 10, yellow if between 10 and 40 and red otherwise
        if (this.totalTimeWaited < 10) {
//            System.out.print(this.timeWaited);
            g.setColor(Color.green);
            g.fillOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2),(int) ((this.cartSize * gridScale)*0.2));
            g.setColor(Color.black);
            g.drawOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2) ,(int)((this.cartSize * gridScale)*0.2));
        }
        else if (this.totalTimeWaited >= 10 && this.totalTimeWaited < 40) {
//            System.out.print(this.timeWaited);
            g.setColor(Color.yellow);
            g.fillOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2),(int) ((this.cartSize * gridScale)*0.2));
            g.setColor(Color.black);
            g.drawOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2),(int) ((this.cartSize * gridScale)*0.2));
        }
        else {
//            System.out.println(this.timeWaited);
            g.setColor(Color.red);
            g.fillOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2),(int) ((this.cartSize * gridScale)*0.2));
            g.setColor(Color.black);
            g.drawOval((int) this.getRow() * gridScale, (int) this.getCol() * gridScale, (int) ((this.cartSize * gridScale)*0.2),(int) ((this.cartSize * gridScale)*0.2));
        }
    }

    public int updateState(ArrayList<CheckoutAgent> linesVals) {
        //Updates the row and col of the customer(within +- 2 blocks)if it is not time for the customer to leave yet and returns -1 if not leaving
        //Otherwise if it is time to leave returns the value of the checkoutline it wants to enter
        Random gen = new Random();
        this.totalTimeWaited += 1;
        if (this.waitTime == 0) {
            return this.selector(linesVals);
        }
        else {
            this.waitTime --;
            if (this.getRow() < 0 && this.getCol() < 0) {
                this.setRow((int) this.getRow() + (gen.nextInt(2)- gen.nextInt(2)));
                this.setCol((int) this.getCol() + (gen.nextInt(2)- gen.nextInt(2)));
            }
            else {
                this.setRow((int) this.getRow() - (gen.nextInt(2)- gen.nextInt(2)));
                this.setCol((int) this.getCol() - (gen.nextInt(2)- gen.nextInt(2)));
            }
            return -1;
        }
    }
}
