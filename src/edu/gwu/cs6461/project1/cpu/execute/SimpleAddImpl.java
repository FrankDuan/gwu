package edu.gwu.cs6461.project1.cpu.execute;

import edu.gwu.cs6461.project1.cpu.Instruction;

public class SimpleAddImpl extends ExecuteBase{
    @Override
    public short execute(Instruction instruction) {
        short valA = instruction.getValA();
        short valB = instruction.getValB();
        short valE = (short)(valA + valB);
        instruction.setValE(valE);
        return 0;
    }
}
