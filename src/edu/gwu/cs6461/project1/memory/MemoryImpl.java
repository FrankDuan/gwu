package edu.gwu.cs6461.project1.memory;

public class MemoryImpl implements Memory{


    static MemoryImpl instance = null;

    private MemoryImpl() {
        initialize();
    }

    static public Memory getInstance(){
        if(instance == null){
            instance = new MemoryImpl();
        }
        return instance;
    }

    @Override
    public void initialize() {

    }

    @Override
    public short getMemory(short address) {
        return 0;
    }

    @Override
    public void setMemory(short address, short value) {

    }

    @Override
    public short[] getMemory(short address, short length) {
        return new short[0];
    }

    @Override
    public short setMemory(short address, short[] value, short length) {
        return 0;
    }
}
