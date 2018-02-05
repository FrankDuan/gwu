package edu.gwu.cs6461.project1.cpu;

import edu.gwu.cs6461.project1.cpu.decode.Decode;
import edu.gwu.cs6461.project1.cpu.execute.Execute;
import edu.gwu.cs6461.project1.cpu.execute.ExecuteFactory;
import edu.gwu.cs6461.project1.cpu.fetch.Fetch;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdate;
import edu.gwu.cs6461.project1.cpu.memory_update.MemoryUpdateImpl;
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdate;
import edu.gwu.cs6461.project1.cpu.register_update.RegisterUpdateImpl;
import edu.gwu.cs6461.project1.memory.Memory;

public class CPUImpl implements CPU {
    Boolean isFault;
    Registers registers;

    Fetch fetch;
    Decode decode;
    Execute execute;
    MemoryUpdate memoryUpdate;
    RegisterUpdate registerUpdate;

    public CPUImpl() {
        isFault = false;
        registers = RegistersImpl.getInstance();
        memoryUpdate = MemoryUpdateImpl.getInstance();
        registerUpdate = RegisterUpdateImpl.getInstance();
    }

    @Override
    public void initialize() {
        registers.initialize();
        //Todo: initialize the five components.
    }

    @Override
    public void run() {
        while(!isFault){
            singleStepRun();
        }
    }

    @Override
    public void singleStepRun() {
        try {
            Instruction instruction = fetch.fetch(registers.getPC());
            decode.decode(instruction);
            execute = ExecuteFactory.createExecutor(instruction);
            execute.execute(instruction);
            memoryUpdate.updateMemory(instruction);
            registerUpdate.updateRegister(instruction);
        }
        catch (Exception e){
            System.out.println(e);
            isFault = true;
        }
    }

    @Override
    public Registers getRegisters() {
        return registers;
    }

    @Override
    public void setRegisters(Registers registers) {
        this.registers = registers;
    }
}
