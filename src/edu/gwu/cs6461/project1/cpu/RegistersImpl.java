package edu.gwu.cs6461.project1.cpu;

public class RegistersImpl implements Registers{
    /* 4 General Purpose Registers (GPRs) – each 16 bits in length */
    short[] gpr = new short[4];

    /* 16 bits	    Index Register: contains a base address that supports base register */
    short[] ir = new short[3];

    /* 12 bits	    Program Counter: address of next instruction to be executed. */
    short PC;

    /* 4 bits	    Condition Code: set when arithmetic/logical operations are executed;
    it has four 1-bit elements: overflow, underflow, division by zero,
    equal-or-not. They may be referenced as cc(0), cc(1), cc(2), cc(3).
    Or by the names OVERFLOW, UNDERFLOW, DIVZERO, EQUALORNOT */
    short CC;

    //16 bits	    Instruction Register: holds the instruction to be executed
    short IR;

    //16 bits	    Memory Address Register: holds the address of the word to be
    //     fetched from memory
    short MAR;

    // MBR	    16 bits	    Memory Buffer Register: holds the word just fetched from or the
    //     word to be /last stored into memory
    short MBR;

    //16 bits	    Machine Status Register: certain bits record the status of the
    //health of the machine
    short MSR;

    //4 bits	    Machine Fault Register: contains the ID code if a machine fault
    //     after it occurs
    short MFR;

    static Registers instance = null;

    private RegistersImpl() {
        initialize();
    }

    static public Registers getInstance(){
        if(instance == null){
            instance = new RegistersImpl();
        }
        return instance;
    }

    @Override
    public void initialize() {
        //todo: set all registers to 0
    }

    @Override
    public short getGPR(short index) {
        return gpr[index];
    }

    @Override
    public short setGPR(short index, short value) {
        gpr[index] = value;
        return value;
    }

    @Override
    public short getIR(short index) {
        return ir[index];
    }

    @Override
    public short setIR(short index, short value) {
        ir[index] = value;
        return value;
    }

    @Override
    public short getPC() {
        return PC;
    }

    @Override
    public short setPC(short value) {
        PC = value;
        return value;
    }

    @Override
    public short setCC(short value, short bitsMask) {
        return 0;
    }

    @Override
    public short getCC() {
        return CC;
    }

    @Override
    public short getMAR() {
        return MAR;
    }

    @Override
    public short setMAR(short value) {
        MAR = value;
        return value;
    }

    @Override
    public short getMBR() {
        return MBR;
    }

    @Override
    public short setMBR(short value) {
        return 0;
    }

    @Override
    public short getMSR() {
        return MSR;
    }

    @Override
    public short setMSR(short value) {
        return 0;
    }

    @Override
    public short setMFR(short value, short bitsMask) {
        return 0;
    }

    @Override
    public short getMFR() {
        return MFR;
    }
}
