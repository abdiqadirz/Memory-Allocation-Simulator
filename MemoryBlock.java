
public class MemoryBlock {
    public int start;        // where the block starts
    public int size;         // how big the block is
    public boolean isFree;   // true if no process is using it
    public Process process;  // the process that is using the block

    // make a memory block
    public MemoryBlock(int start, int size) {
        this.start = start;
        this.size = size;
        this.isFree = true;    // starts as free
        this.process = null;   // no process in it yet
    }

    // put a process in the block
    public void allocate(Process p) {
        this.process = p;
        this.isFree = false;
        p.start(); // start the process
    }

    // remove the process from the block
    public void free() {
        this.process = null;
        this.isFree = true;
    }

    // this prints what the block looks like
    @Override
    public String toString() {
        if (isFree) return "| Free (" + size + " KB) ";
        return "| " + process.toString() + " ";
    }
}
