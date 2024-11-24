package HoleFillingPackage.WeightingFunction;

/**
 * A factory class for creating instances of DefaultWeightingFunction.
 */
public class DefaultWeightingFunctionFactory implements IWeightingFunctionFactory {

    /**
     * Creates a new instance of DefaultWeightingFunction with specified parameters.
     *
     * @param z       The power parameter for the weighting function.
     * @param epsilon A small float to avoid division by zero.
     * @return An instance of DefaultWeightingFunction.
     */
    @Override
    public IWeightingFunction Create(int z, float epsilon) {
        return new DefaultWeightingFunction(z, epsilon);
    }
}
