package HoleFillingPackage;

public class DefaultWeightingFunction implements WeightingFunction {
    private final int z;
    private final double epsilon;

    public DefaultWeightingFunction(int z, double epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    @Override
    public double calculate(Point u, Point v) {
        double distance = Math.sqrt(Math.pow(u.getFirst() - v.getFirst(), 2) + Math.pow(u.getSecond() - v.getSecond(), 2));
        return 1 / (Math.pow(distance, z) + epsilon);
    }
}
