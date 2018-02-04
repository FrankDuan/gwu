package edu.gwu.cs6461.project1.cpu.execute;

public enum ExecutorFactory {
    Toyota {
        @Override
        public Execute create() {
            return new Toyota();
        }
    } ,
    Ford {
        @Override
        public Vehicle create() {
            return new Ford();
        }
    },
    ;
    public abstract Vehicle create();
}