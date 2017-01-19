/**
 * Created by prashant on 10/31/16.
 */
public class Simulation {

    public Simulation() {

    }

    public static void testSimulation(int shopSize, int agentNum, int runTime, int simSpeed) throws InterruptedException {
        //Tests the simulation given the parameters allowing user to change the simulation parameters easily
        Landscape scape = new Landscape(100, 100, agentNum);  //Initialises the landscape
        LandscapeDisplay display = new LandscapeDisplay(scape, 5);  //Initialises the graphic object on which landscape is drawn
        int counter = 1;
        for (int i = 0; i <= runTime; i++) {
            scape.spawner(shopSize);  //spawns agents
            scape.updateState();  //Updates the state
            display.repaint(simSpeed);  //Repaints after
            Thread.sleep(simSpeed);  //pauses so user can see changes
            if (i == (25)*counter) {
                System.out.println("Avg checkout time "+ scape.getStatistics());
                System.out.println("Most efficient cashier = number "+ scape.getMostEfficientCashier());
                counter +=1;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        testSimulation(30, 10,200,100);
    }
}
