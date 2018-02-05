package edu.gwu.cs6461.project1.cpu.execute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.gwu.cs6461.project1.cpu.Instruction;

public class ExecuteFactory {

    private interface Creator {
        Execute create();
    }

    private static final Map<Short, Creator> factoryMap =
            Collections.unmodifiableMap(new HashMap<Short,Creator>() {{
                put((short)1, new Creator() {
                            public Execute create() {
                                return new LDRImpl();
                            }
                       }
                );

                put((short)2, new Creator() {
                            public Execute create() {
                                return new STRImpl();
                            }
                        }
                );
            }});

    public static Execute createExecutor(Instruction instruction) {
        Creator factory = factoryMap.get(instruction.getOpcode());
        if (factory == null) {
            return null;
        }
        return factory.create();
    }
}



