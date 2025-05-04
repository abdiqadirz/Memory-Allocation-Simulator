
import java.util.*;

public class MemoryManager {
    private List<MemoryBlock> memory; // list of memory blocks
    private final int totalSize;      // total memory size

    // make the memory manager and start with all memory free
    public MemoryManager(int totalSize) {
        this.totalSize = totalSize;
        memory = new ArrayList<>();
        memory.add(new MemoryBlock(0, totalSize)); // start with one big free block
    }

    // try to put a process in memory using a strategy
    public boolean allocate(Process p, String strategy) {
        int index = -1;

        // pick the strategy to use
        switch (strategy.toLowerCase()) {
            case "first-fit":
                index = firstFit(p.size);
                break;
            case "best-fit":
                index = bestFit(p.size);
                break;
            case "worst-fit":
                index = worstFit(p.size);
                break;
        }

        // if no spot found, return false
        if (index == -1) {
            System.out.println("Cannot allocate memory for " + p.id);
            return false;
        }

        // split the block and give it to the process
        splitAndAllocate(index, p);
        return true;
    }

    // find first block that fits
    private int firstFit(int size) {
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).isFree && memory.get(i).size >= size)
                return i;
        }
        return -1;
    }

    // find smallest free block that fits
    private int bestFit(int size) {
        int bestIndex = -1;
        int minSize = Integer.MAX_VALUE;
        for (int i = 0; i < memory.size(); i++) {
            MemoryBlock b = memory.get(i);
            if (b.isFree && b.size >= size && b.size < minSize) {
                bestIndex = i;
                minSize = b.size;
            }
        }
        return bestIndex;
    }

    // find biggest free block that fits
    private int worstFit(int size) {
        int worstIndex = -1;
        int maxSize = -1;
        for (int i = 0; i < memory.size(); i++) {
            MemoryBlock b = memory.get(i);
            if (b.isFree && b.size >= size && b.size > maxSize) {
                worstIndex = i;
                maxSize = b.size;
            }
        }
        return worstIndex;
    }

    // give part of a block to a process
    private void splitAndAllocate(int index, Process p) {
        MemoryBlock block = memory.get(index);
        if (block.size == p.size) {
            block.allocate(p); // perfect fit
        } else {
            // make two parts: one for the process, one free
            MemoryBlock newAlloc = new MemoryBlock(block.start, p.size);
            newAlloc.allocate(p);
            MemoryBlock newHole = new MemoryBlock(block.start + p.size, block.size - p.size);
            memory.remove(index);
            memory.add(index, newHole);
            memory.add(index, newAlloc);
        }
    }

    // free up blocks where process is done
    public void deallocateFinishedProcesses() {
        for (MemoryBlock block : memory) {
            if (!block.isFree && block.process.isFinished()) {
                System.out.println("Process " + block.process.id + " finished. Freeing memory.");
                block.free();
            }
        }
        mergeFreeBlocks(); // merge free spaces together
    }

    // merge two free blocks into one
    private void mergeFreeBlocks() {
        for (int i = 0; i < memory.size() - 1; ) {
            MemoryBlock current = memory.get(i);
            MemoryBlock next = memory.get(i + 1);
            if (current.isFree && next.isFree) {
                current.size += next.size; // make it one big block
                memory.remove(i + 1);
            } else {
                i++;
            }
        }
    }

    // show the memory layout
    public void printMemoryState() {
        for (MemoryBlock b : memory) {
            System.out.print(b.toString());
        }
        System.out.println("|");
    }

    // show some numbers about memory
    public void printStats() {
        int holeCount = 0;
        int totalHoleSize = 0;

        for (MemoryBlock b : memory) {
            if (b.isFree) {
                holeCount++;
                totalHoleSize += b.size;
            }
        }

        double avgHoleSize = (holeCount == 0) ? 0 : (double) totalHoleSize / holeCount;
        double percentFree = 100.0 * totalHoleSize / totalSize;

        System.out.println("Stats -- Holes: " + holeCount + ", Avg Hole Size: " + avgHoleSize +
                           ", Total Free: " + totalHoleSize + " KB (" + String.format("%.2f", percentFree) + "%)");
    }
}
