
public class Process {
    public String id;          // name of the process like "P1"
    public int size;           // how big the process is
    public int lifetime;       // how long it runs (in milliseconds)
    public long startTime;     // when it started running

    // make a process with name, size, and time it runs
    public Process(String id, int size, int lifetime) {
        this.id = id;
        this.size = size;
        this.lifetime = lifetime;
    }

    // check if the process is done
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= lifetime;
    }

    // mark the process as started now
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    // show info about the process
    @Override
    public String toString() {
        int remaining = Math.max(0, (int)((lifetime - (System.currentTimeMillis() - startTime)) / 1000));
        return id + " [" + remaining + "s] (" + size + " KB)";
    }
}
