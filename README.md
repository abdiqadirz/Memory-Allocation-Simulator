# Memory Allocation Simulator (CS 405 - Project 2)

A Java-based simulation of **contiguous memory allocation** strategies, built for our CS405 Operating Systems. This project models how processes are allocated and deallocated in memory using the **First-Fit**, **Best-Fit**, and **Worst-Fit** strategies.

> **Team Members**:  
> Osman Abdiqadir  
> Sharmarke Nur

---

## Features

- Simulates memory allocation over time with second-by-second updates
- Three allocation strategies: **First-Fit**, **Best-Fit**, **Worst-Fit**
- Configurable process sizes, memory limits, and lifetimes via a config file
- Real-time memory display and fragmentation stats
- Clean modular code written in Java

---


- `STRATEGY` can be `first-fit`, `best-fit`, or `worst-fit`
- `MEMORY_MAX` is total memory in KB
- `NUM_PROC` is the number of processes to simulate
- `PROC_SIZE_MAX` is the maximum process size (KB)
- `MAX_PROC_TIME` is the longest lifespan for any process (ms)

---
Sample Output: 

Cannot allocate memory for P7
Memory: | P1 [3s] (170 KB) | P2 [5s] (234 KB) | ... | Free (1 KB) |
Stats -- Holes: 1, Avg Hole Size: 1.0, Total Free: 1 KB (0.10%)

Process P1 and P8 finished.
Memory: | Free (170 KB) | ... | P9 [6s] (98 KB) |
Stats -- Holes: 3, Avg Hole Size: 63.7, Total Free: 191 KB (18.65%)
---
##How to Run

1. Make sure Java (JDK 11 or higher) is installed
2. Clone the repo:

```bash
git clone https://github.com/yourusername/memory-allocation-sim.git
cd memory-allocation-sim



