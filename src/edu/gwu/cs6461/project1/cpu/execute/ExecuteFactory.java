package edu.gwu.cs6461.project1.cpu.execute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.gwu.cs6461.project1.cpu.Instruction;

import edu.gwu.cs6461.project1.cpu.InstructionType;

public class ExecuteFactory {

    private interface Creator {
        Execute create();
    }

    private static final Map<Short, Creator> factoryMap =
            Collections.unmodifiableMap(new HashMap<Short,Creator>() {{
                put(InstructionType.LDR, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.STR, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.LDA, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.LDX, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});

                put(InstructionType.STX, new Creator() {
                            public Execute create() { return new SimpleAddImpl(); }});
            }});

    public static Execute createExecutor(Instruction instruction) {
        Creator factory = factoryMap.get(instruction.getOpcode());
        if (factory == null) {
            return null;
        }
        return factory.create();
    }
}



