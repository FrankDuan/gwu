package edu.gwu.cs6461.project1.simulator;

import edu.gwu.cs6461.project1.cpu.CPU;
import edu.gwu.cs6461.project1.memory.Memory;

public class Simulator{

    CPU cpu;
    Memory memory;

    /**
     * Initialize CPU and it's registers, memory.
     */
    void initialize(){
        cpu.initialize();
        memory.initialize();
    }

    /**
     * Run from position of PC to HLT instruction.
     * Stop when exception occurs.
     */
    void run(){
        cpu.run();
    }

    /**
     * Run instruction pointed by PC and stop.
     */
    void runSingleStep(){
        cpu.singleStepRun();
    }

    /**
     * Return the CPU instance.
     * Refers to CPU interface for operation supported.
     */
    CPU getCPU(){
        return cpu;
    }

    /**
     * Return the CPU instance.
     * Refers to CPU interface for operation supported.
     */
    Memory getMemory(){
        return memory;
    }
}
