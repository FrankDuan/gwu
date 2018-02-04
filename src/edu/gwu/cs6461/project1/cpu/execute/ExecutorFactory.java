package edu.gwu.cs6461.project1.cpu.execute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.gwu.cs6461.project1.cpu.Instruction;

public class ExecutorFactory {

    private interface Creator {
        Execute create();
    }

    private static final Map<Integer, Creator> factoryMap =
            Collections.unmodifiableMap(new HashMap<Integer,Creator>() {{
                put(1, new Creator() {
                            public Execute create() {
                                return new LDRImpl();
                            }
                       }
                );

                put(2, new Creator() {
                            public Execute create() {
                                return new STRImpl();
                            }
                        }
                );
            }});

    public Execute createExecutor(Instruction instruction) {
        Creator factory = factoryMap.get(instruction.getOpcode());
        if (factory == null) {
            return null;
        }
        return factory.create();
    }
}



