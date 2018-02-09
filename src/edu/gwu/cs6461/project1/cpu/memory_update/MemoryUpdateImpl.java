package edu.gwu.cs6461.project1.cpu.memory_update;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;

public class MemoryUpdateImpl implements MemoryUpdate{

    static MemoryUpdateImpl _instance;

    private MemoryUpdateImpl() {
        initialize();
    }

    static public MemoryUpdateImpl getInstance(){
        if(_instance == null){
            _instance = new MemoryUpdateImpl();
        }
        return _instance;
    }

    void initialize() {

    }

    @Override
    public short updateMemory(Instruction instruction) {
        short value;
        short addr = instruction.getValE();
        short indirect = instruction.getI();

        Memory memory = MemoryImpl.getInstance();
        Registers registers = RegistersImpl.getInstance();
        if(indirect != 0) {
            addr = memory.getMemory(addr);
        }

        switch(instruction.getOpcode()) {
            case InstructionType.LDR:
            case InstructionType.LDX:
                //valM <- M[valE] or
                //valM <- M[M[valE]]
                value = memory.getMemory(addr);
                instruction.setValM(value);
                break;

            case InstructionType.STR:
                //M[valE] <- R[r]
                value = registers.getGPR(instruction.getR());
                memory.setMemory(addr, value);
                break;

            case InstructionType.LDA:
                if(indirect != 0){
                    instruction.setValM(addr);
                }
                break;

            case InstructionType.STX:
                //M[valE] <- X[ix]
                value = registers.getX(instruction.getIx());
                memory.setMemory(addr, value);
                break;

            default:
                break;

        }
        return 0;
    }
}
