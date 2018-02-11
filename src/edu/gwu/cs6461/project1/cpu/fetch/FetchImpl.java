package edu.gwu.cs6461.project1.cpu.fetch;

import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.memory.Memory;
import edu.gwu.cs6461.project1.memory.MemoryImpl;

public class FetchImpl implements Fetch {

    static FetchImpl _instance;

    private FetchImpl() {
        initialize();
    }

    static public FetchImpl getInstance(){
        if(_instance == null){
            _instance = new FetchImpl();
        }
        return _instance;
    }

    void initialize() {

    }

    @Override
    public Instruction fetch(short pc) {

        Registers registers= RegistersImpl.getInstance();
        Instruction instruction = new Instruction();
        Memory memory = MemoryImpl.getInstance();
        short code = memory.getMemory(registers.getPC());
        instruction.setInstruct_code(code);
        registers.setIR(code);
        registers.setX((short)0, (short)0);
        splitInstruction(instruction);//通过这一步，完成指令分段

        return instruction;
    }

    private void splitInstruction(Instruction instruction){
        short sourceInstr=instruction.getInstruct_code();
        short opcode;
        String maskStr="1111110000000000";
        short maskShort=(short)(Integer.parseUnsignedInt(maskStr,2));
        opcode=(short)((sourceInstr>>10) & 0x3f);
        switch(opcode){
            case InstructionType.LDR:
            case InstructionType.STR:
            case InstructionType.LDA:
            case InstructionType.LDX:
            case InstructionType.STX:
                maskStr="0000001100000000";
                maskShort=(short)(Integer.parseUnsignedInt(maskStr,2));
                instruction.setR((short)((sourceInstr&maskShort)>>8));
                maskStr="0000000011000000";
                maskShort=(short)(Integer.parseUnsignedInt(maskStr,2));
                instruction.setIx((short)((sourceInstr&maskShort)>>6));
                maskStr="0000000000100000";
                maskShort=(short)(Integer.parseUnsignedInt(maskStr,2));
                instruction.setI((short)((sourceInstr&maskShort)>>5));
                maskStr="0000000000011111";
                maskShort=(short)(Integer.parseUnsignedInt(maskStr,2));
                instruction.setAddress((short)(sourceInstr&maskShort));
                break;
            //此处为后续指令分段处理的地方，不同指令分段方式不一致所以需要设置的为也不同。
            default :
                //指令有问题，需要做异常处理
        }
        //instruction.addValP;
        instruction.setOpcode(opcode);
    }
}