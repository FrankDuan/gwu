package edu.gwu.cs6461.project1.memory;

import edu.gwu.cs6461.project1.cpu.Registers;
import edu.gwu.cs6461.project1.cpu.RegistersImpl;

import java.util.HashMap;
import java.util.Map;

public class MemoryImpl implements Memory {
    private Map<Short, Short> memory;

    static MemoryImpl _instance = null;
    Registers registers = RegistersImpl.getInstance();

    private MemoryImpl() {
        memory = new HashMap<Short, Short>();
        initialize();
    }

    static public Memory getInstance(){
        if(_instance == null){
            _instance = new MemoryImpl();
        }
        return _instance;
    }

    @Override
    public void initialize() {
        System.out.println("memory initiated");
        for (short i = 0; i < 4096; i++) {
            memory.put(i, (short) 0);
        }
    }

    @Override
    public void setMemory(short address, short value) {
        if (address < 4096 && address >= 0) {
            registers.setMAR(address);
            registers.setMBR(value);
            memory.put(address, value);
        } else {
            System.out.println("Out of Bound");
        }
    }

    @Override
    public void setMemory(short address, short[] value, short length) {
        if (value.length + address < 4096) {
            for (int i = 0; i < value.length; i++) {
                memory.put((short) (address + i), value[i]);
            }
            registers.setMAR((short)(address + value.length - 1));
            registers.setMBR(value[value.length - 1]);
        } else {
            System.out.println("Out of Bound");
        }
    }

    @Override
    public short getMemory(short address) {
        if (address < 4096 && address >= 0) {
            short value = memory.get(address);
            registers.setMAR(address);
            registers.setMBR(value);
            return value;
        } else {
            System.out.println("Out of Bound");
            //todo: throw an exception.
        }
        return 0;
    }
    @Override
    public short[] getMemory(short address, short length) {
        if (address < 4096 && address >= 0) {
            short[] tmp = new short[length];
            for (short i = 0; i < length; i++) {
                tmp[i] = memory.get(address + i);
            }
            registers.setMAR((short)(address + length - 1));
            registers.setMBR(tmp[length - 1]);
            return tmp;
        } else {
            System.out.println("Out of Bound");
        }
        return null;
    }
}

