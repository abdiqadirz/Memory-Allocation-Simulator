
import java.io.*;
import java.util.*;

public class Simulator {
    private int MEMORY_MAX = 1024;      // max memory size
    private int PROC_SIZE_MAX = 256;    // biggest process size
    private int NUM_PROC = 10;          // how many processes to make
    private int MAX_PROC_TIME = 10000;  // max time a process runs

    private List<Process> processList = new ArrayList<>(); // list of all processes
    private MemoryManager memory;       // the memory manager
    private String strategy;            // strategy to use (first-fit, best-fit, worst-fit)

    // this sets up the simulator using the config file and strategy
    public Simulator(String configPath, String strategy) {
        this.strategy = strategy;
        loadConfig(configPath);                  // read the config file
        memory = new MemoryManager(MEMORY_MAX);  // make the memory
    }

    // read settings from config file
    private void loadConfig(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());

                switch (key) {
                    case "MEMORY_MAX": MEMORY_MAX = value; break;
                    case "PROC_SIZE_MAX": PROC_SIZE_MAX = value; break;
                    case "NUM_PROC": NUM_PROC = value; break;
                    case "MAX_PROC_TIME": MAX_PROC_TIME = value; break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading config: " + e.getMessage());
        }
    }

    // randomly make all the processes
    public void generateProcesses() {
        Random rand = new Random();
        for (int i = 1; i <= NUM_PROC; i++) {
            int size = 1 + rand.nextInt(PROC_SIZE_MAX);          // size is at least 1
            int lifetime = 1000 + rand.nextInt(MAX_PROC_TIME);   // at least 1 second
            processList.add(new Process("P" + i, size, lifetime));
        }
    }

    // run the simulation
    public void run() {
        System.out.println("Starting memory allocation using strategy: " + strategy + "\n");
        Queue<Process> queue = new LinkedList<>(processList); // waiting processes

        long startTime = System.currentTimeMillis();

        // keep running until all processes are done
        while (!queue.isEmpty() || hasRunningProcesses()) {
            // try to put waiting processes into memory
            Iterator<Process> it = queue.iterator();
            while (it.hasNext()) {
                Process p = it.next();
                if (memory.allocate(p, strategy)) {
                    it.remove(); // remove from queue if placed
                }
            }

            memory.deallocateFinishedProcesses(); // free memory for done processes

            System.out.print("Memory: ");
            memory.printMemoryState(); // show memory state
            memory.printStats();       // show memory stats
            System.out.println();

            sleep(1000); // wait 1 second before next round
        }

        System.out.println("Simulation complete! Total run time: " +
                (System.currentTimeMillis() - startTime) / 1000 + " seconds");
    }

    // check if any process is still running
    private boolean hasRunningProcesses() {
        for (Process p : processList) {
            if (!p.isFinished()) return true;
        }
        return false;
    }

    // pause the program for a bit
    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
