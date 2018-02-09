package edu.gwu.cs6461.project1.cpu.register_update;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdate;

public class RegisterUpdateImpl implements RegisterUpdate{

    static RegisterUpdateImpl instance;

    private RegisterUpdateImpl() {
        initialize();
    }

    static public RegisterUpdateImpl getInstance(){
        if(instance == null){
            instance = new RegisterUpdateImpl();
        }
        return instance;
    }

    void initialize() {

    }
    @Override
    public short updateRegister(Instruction instruction) {
        short value;
        Registers registers = RegistersImpl.getInstance();
        switch(instruction.getOpcode()) {
            case InstructionType.LDR:
                //R[r] <- valM
                value = instruction.getValM();
                registers.setGPR(instruction.getR(), value);
                break;

            case InstructionType.STR:
                //No operation
                break;

            case InstructionType.LDA:
                //R[r] <- valE
                if(instruction.getI() == 0) {
                    value = instruction.getValE();
                }
                else {
                    value = instruction.getValM();
                }
                registers.setGPR(instruction.getR(), value);
                break;

            case InstructionType.LDX:
                //x[ix] <- valM
                value = instruction.getValM();
                registers.setX(instruction.getIx(), value);
                break;

            case InstructionType.STX:
                //No operation
                break;

            default:
                break;

        }
        return 0;
    }
}
