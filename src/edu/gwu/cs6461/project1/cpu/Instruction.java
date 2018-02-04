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
}
