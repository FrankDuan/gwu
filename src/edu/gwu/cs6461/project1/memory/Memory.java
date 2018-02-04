package edu.gwu.cs6461.project1.memory;

public interface Memory {
    void initialize();
    short getMemory(short address);
    void  setMomory(short address, short value);
}
