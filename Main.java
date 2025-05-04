
public class Main {
    public static void main(String[] args) {
        // path to the config file
        String configPath = "config.txt";
        // choose which strategy to use (first-fit, best-fit, or worst-fit)
        String strategy = "first-fit";

        // create the simulator with the config and strategy
        Simulator sim = new Simulator(configPath, strategy);
        // make random processes
        sim.generateProcesses();
        // start running the simulation
        sim.run();
    }
}
