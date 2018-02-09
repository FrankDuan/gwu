package edu.gwu.cs6461.project1.cpu.decode;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;

public class DecodeImpl implements Decode {

    static DecodeImpl _instance;
    Registers registers;

    private DecodeImpl() {
        registers = RegistersImpl.getInstance();
        initialize();
    }

    static public DecodeImpl getInstance(){
        if(_instance == null){
            _instance = new DecodeImpl();
        }
        return _instance;
    }

    void initialize() {
    }

    @Override
    public short decode(Instruction instruction) {

        switch(instruction.getOpcode()){
            case InstructionType.LDR:
            case InstructionType.STR:
            case InstructionType.LDA:

                instruction.setValA(registers.getX(instruction.getIx()));
                instruction.setValB(instruction.getAddress());
                break;
            case InstructionType.LDX:
            case InstructionType.STX:
                instruction.setValA((short)0);
                instruction.setValB(instruction.getAddress());
                break;
            default :
                //指令有问题，需要做异常处理
                break;
        }

        return 0;
    }
}
