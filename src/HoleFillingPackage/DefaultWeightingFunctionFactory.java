package HoleFillingPackage;

public class DefaultWeightingFunctionFactory implements  IWeightingFunctionFactory {

    @Override
    public IWeightingFunction Create(int z, double epsilon) {
        return new DefaultWeightingFunction(z, epsilon);
    }
}
