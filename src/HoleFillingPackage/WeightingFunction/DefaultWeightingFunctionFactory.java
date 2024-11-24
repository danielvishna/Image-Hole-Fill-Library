package HoleFillingPackage.WeightingFunction;

public class DefaultWeightingFunctionFactory implements IWeightingFunctionFactory {

    @Override
    public IWeightingFunction Create(int z, float epsilon) {
        return new DefaultWeightingFunction(z, epsilon);
    }
}
