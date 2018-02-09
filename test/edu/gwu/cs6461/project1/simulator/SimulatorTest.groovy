package edu.gwu.cs6461.project1.simulator

import edu.gwu.cs6461.project1.cpu.Instruction
import edu.gwu.cs6461.project1.cpu.InstructionType
import edu.gwu.cs6461.project1.cpu.Registers
import edu.gwu.cs6461.project1.cpu.RegistersImpl
import edu.gwu.cs6461.project1.memory.Memory
import edu.gwu.cs6461.project1.memory.MemoryImpl

class SimulatorTest extends GroovyTestCase {
    void testLDR() {
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
        assertEquals((short)0x105, registers.getMAR());
        assertEquals((short)0x5A, registers.getMBR());

        temp.i = 1;
        registers.setX((short)1, (short)0x100);
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)));
        memory.setMemory((short)0x105, (short)0x110);
        memory.setMemory((short)0x110, (short)0x5C);

        simulator.runSingleStep();
        assertEquals((short)0x5C, registers.getGPR((Short)3));
        assertEquals((short)0x110, registers.getMAR());
        assertEquals((short)0x5C, registers.getMBR());
    }

    void testSTR() {
        Simulator simulator = new Simulator()
        Memory memory = MemoryImpl.getInstance()
        Registers registers = RegistersImpl.getInstance()

        simulator.initialize()
        Instruction temp = new Instruction()
        temp.opcode = InstructionType.STR
        temp.r = 3
        temp.ix = 1
        temp.address = 5
        temp.i = 0
        registers.setX((short)1, (short)0x100)
        registers.setGPR(temp.r, (short)0x5A)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)));
        simulator.runSingleStep();
        assertEquals((short)0x5A, memory.getMemory((short)0x105))
        assertEquals((short)0x105, registers.getMAR())
        assertEquals((short)0x5A, registers.getMBR())

        temp.i = 1
        registers.setX((short)1, (short)0x100)
        registers.setPC((short)0x10)
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x105, (short)0x110)
        registers.setGPR((short)3, (short)0x5C)

        simulator.runSingleStep()
        assertEquals((short)0x5C, registers.getGPR((Short)3))
        assertEquals((short)0x110, registers.getMAR())
        assertEquals((short)0x5C, registers.getMBR())
    }

    void testLDA() {
        Simulator simulator = new Simulator();
        Memory memory = MemoryImpl.getInstance();
        Registers registers = RegistersImpl.getInstance();

        simulator.initialize();
        Instruction temp = new Instruction();
        temp.opcode = InstructionType.LDA;
        temp.r = 3;
        temp.ix = 1;
        temp.address = 0x15;
        temp.i = 0;
        registers.setX((short)1, (short)0x100);
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (generateInstruction(temp)));
        simulator.runSingleStep();
        assertEquals((short)0x115, registers.getGPR((Short)3));

        temp.i = 1;
        registers.setX((short)1, (short)0x100);
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (generateInstruction(temp)));
        memory.setMemory((short)0x115, (short)0x120);

        simulator.runSingleStep();
        assertEquals((short)0x120, registers.getGPR((Short)3));
    }

    void testSTX() {
        short i = -23467
        short code = i >>> 10
        short code2 = i >> 10
        println(code)
        println(code2)
    }

    void testLDX() {
        Simulator simulator = new Simulator();
        Memory memory = MemoryImpl.getInstance();
        Registers registers = RegistersImpl.getInstance();

        simulator.initialize();
        Instruction temp = new Instruction();
        temp.opcode = InstructionType.LDX;
        temp.ix = 1;
        temp.address = 0x15;
        temp.i = 0;
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)))
        memory.setMemory((short)0x15, (short)0x5B)
        simulator.runSingleStep();
        assertEquals((short)0x5B, registers.getX(temp.ix))
        assertEquals((short)0x15, registers.getMAR())
        assertEquals((short)0x5B, registers.getMBR())


        temp.i = 1;
        registers.setPC((short)0x10);
        memory.setMemory((short)0x10, (short)(generateInstruction(temp)));
        memory.setMemory((short)0x15, (short)0x20)
        memory.setMemory((short)0x20, (short)0x5E)
        simulator.runSingleStep()
        assertEquals((short)0x5E, registers.getX(temp.ix))
        assertEquals((short)0x20, registers.getMAR())
        assertEquals((short)0x5E, registers.getMBR())
    }

    Short generateInstruction(Instruction instruction){
        Short insWord = instruction.address
        insWord |= instruction.i << 5
        insWord |= instruction.ix << 6
        insWord |= instruction.r << 8
        insWord |= instruction.opcode << 10

        System.out.printf("%16s", Integer.toBinaryString((Integer)insWord).replace(' ', '0'))
        return insWord
    }
}
