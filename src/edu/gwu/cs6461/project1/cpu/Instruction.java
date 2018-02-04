package edu.gwu.cs6461.project1.cpu;

public class Instruction {

    /**
     * Set in fetching phase by the fetcher
     */
    short instruct_code;
    short opcode;
    short ix;
    short r;
    short i;
    short address;
    short setCC;
    short valP;

    /**
     * Set in decoding phase by the decoder
     * Decoder get operand from memory and save it to valM
     */
    short valM;

    /**
     *  Set in executing phase by the executor
     */
    short valE;
    short cc;


    /**
     * Set in every phase;
     */
    short ticks;    //only add;
    short state;    //0: success, 1: memory error, 2: instruction invalid

    public short getInstruct_code() {
        return instruct_code;
    }

    public void setInstruct_code(short instruct_code) {
        this.instruct_code = instruct_code;
    }

    public short getOpcode() {
        return opcode;
    }

    public void setOpcode(short opcode) {
        this.opcode = opcode;
    }

    public short getIx() {
        return ix;
    }

    public void setIx(short ix) {
        this.ix = ix;
    }

    public short getR() {
        return r;
    }

    public void setR(short r) {
        this.r = r;
    }

    public short getI() {
        return i;
    }

    public void setI(short i) {
        this.i = i;
    }

    public short getAddress() {
        return address;
    }

    public void setAddress(short address) {
        this.address = address;
    }

    public short getSetCC() {
        return setCC;
    }

    public void setSetCC(short setCC) {
        this.setCC = setCC;
    }

    public short getValP() {
        return valP;
    }

    public void setValP(short valP) {
        this.valP = valP;
    }

    public short getValM() {
        return valM;
    }

    public void setValM(short valM) {
        this.valM = valM;
    }

    public short getValE() {
        return valE;
    }

    public void setValE(short valE) {
        this.valE = valE;
    }

    public short getCc() {
        return cc;
    }

    public void setCc(short cc) {
        this.cc = cc;
    }

    public short getTicks() {
        return ticks;
    }

    public void setTicks(short ticks) {
        this.ticks = ticks;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }
}
