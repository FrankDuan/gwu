package edu.gwu.cs6461.project1.cpu.pc_update;
import edu.gwu.cs6461.project1.cpu.Instruction;
import edu.gwu.cs6461.project1.cpu.InstructionType;
import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdate;

public class PC_UpdateImpl implements PC_Update {
    static PC_UpdateImpl _instance = null;
    Registers registers;
    private PC_UpdateImpl() {
        registers = RegistersImpl.getInstance();
    }

    static public PC_UpdateImpl getInstance(){
        if(_instance == null){
            _instance = new PC_UpdateImpl();
        }
        return _instance;
    }

    public short pcUpdate(Instruction instruction){
        switch(instruction.getOpcode()){
            case InstructionType.LDR:
            case InstructionType.STR:
            case InstructionType.LDA:
            case InstructionType.LDX:
            case InstructionType.STX:
            case InstructionType.AMR:
            case InstructionType.SMR:
            case InstructionType.AIR:
            case InstructionType.SIR:
            case InstructionType.MLT:
            case InstructionType.DVD:
            case InstructionType.TRR:
            case InstructionType.AND:
            case InstructionType.ORR:
            case InstructionType.NOT:
            case InstructionType.SRC:
            case InstructionType.RRC:
            case InstructionType.IN:
            case InstructionType.OUT:
            case InstructionType.CHK:
            case InstructionType.FADD:
            case InstructionType.FSUB:
            case InstructionType.CNVRT:
            case InstructionType.LDFR:
            case InstructionType.STFR:
            case InstructionType.MOV:
                registers.setPC(instruction.getValP()); // PC = valP
                registers.setStop(false);
                break;
            case InstructionType.JZ:
                short isZero = registers.getCC(3);// check the value of cc[3]
                if(isZero == 1){
                    registers.setPC(instruction.getEA());
                }
                else{
                    registers.setPC(instruction.getValP());
                } // PC = CC[3] ? EA:valP
                registers.setStop(false);
                break;
            case InstructionType.JNE:
                short isEqual = registers.getCC(3); // check the value of cc[3]
                if(isEqual == 1){
                    registers.setPC(instruction.getValP());
                }
                else{
                    registers.setPC(instruction.getEA());
                }  // PC = CC[3]? valP :EA
                registers.setStop(false);
                break;
            case InstructionType.JCC:
                short isEqual1 = registers.getCC(3);// check the value of cc[3]
                if(isEqual1 == 1){
                    registers.setPC(instruction.getEA());
                }
                else{
                    registers.setPC(instruction.getValP());
                }  // PC = CC[3] ? EA:valP
                registers.setStop(false);
                break;
            case InstructionType.JMA:
                registers.setPC(instruction.getEA()); // PC = EA
                registers.setStop(false);
                break;
            case InstructionType.JSR:
                registers.setPC(instruction.getEA()); // PC = EA
                registers.setStop(false);
                break;
            case InstructionType.RFS:
                registers.setPC(instruction.getValA()); // PC = valA
                registers.setStop(false); // set stop flag register
                break;
            case InstructionType.SOB:
                short isLoop = registers.getCC(0);
                if(isLoop == 1){
                    registers.setPC(instruction.getEA());
                }
                else{
                    registers.setPC(instruction.getValP());
                } //PC = CC[0]?(overflow?) EA:valP
                registers.setStop(false);
                break;
            case InstructionType.JGE:
                short isBigger = registers.getCC(0);
                if(isBigger == 1){
                    registers.setPC(instruction.getEA());
                }
                else{
                    registers.setPC(instruction.getValP());
                } // PC = CC[0]? EA:valP
                registers.setStop(false);
                break;
            case InstructionType.HLT:
                registers.setPC(instruction.getValP());
                registers.setStop(true); // set stop flag register as true, it means the CPU should stop running
                break;
            default:
                break;


        }
        return 0;
    }
}
