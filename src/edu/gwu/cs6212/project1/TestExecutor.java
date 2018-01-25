package edu.gwu.cs6212.project1;

public class TestExecutor {

    Meter meter;

    public TestExecutor(ExperimentBase experiment) {
        meter = new Meter(experiment);
    }

    public static void main(String[] args) {
        TestExecutor executor = new TestExecutor(new Experiment8());
        for (double i = 10; i < Math.pow(10, 50); i = i * 100) {
            executor.run_test1(i);
        }
    }

    void run_test1(double n){
        //System.out.printf("%10d: %5d\n", n, meter.run(n));
        //System.out.printf("%d\n", meter.run(n));
        meter.run(n);
    }
}
