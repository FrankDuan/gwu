package edu.gwu.cs6461.project1.simulator

import edu.gwu.cs6461.project1.cpu.Instruction
import edu.gwu.cs6461.project1.cpu.InstructionType
import edu.gwu.cs6461.project1.cpu.Registers
import edu.gwu.cs6461.project1.cpu.RegistersImpl
import edu.gwu.cs6461.project1.memory.Memory
import edu.gwu.cs6461.project1.memory.MemoryImpl

class SimulatorTest extends GroovyTestCase {
    void testRunSingleStep() {
        Simulator simulator = new Simulator();
        Memory memory = MemoryImpl.getInstance();
        Registers registers = RegistersImpl.getInstance();

        simulator.initialize();
        Instruction temp = new Instruction();
        temp.opcode = InstructionType.LDR;
        temp.r = 3;
        temp.ix = 1;
        temp.address = 5;
        temp.i = 0;
        registers.setX((short)1, (short)0x100);
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)));
        memory.setMemory((short)0x105, (short)0x5A);
        simulator.runSingleStep();
        assertEquals((short)0x5A, registers.getGPR((Short)3));

    }

    Short generateInstruction(Instruction instruction){
        Short insWord = instruction.address;
        insWord |= instruction.i << 5;
        insWord |= instruction.ix << 6;
        insWord |= instruction.r << 8;
        insWord |= instruction.opcode << 10;

        System.out.printf("%16s", Integer.toBinaryString((Integer)insWord).replace(' ', '0'))
        return insWord;
    }
}
