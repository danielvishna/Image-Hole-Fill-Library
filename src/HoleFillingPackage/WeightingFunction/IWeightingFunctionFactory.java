package HoleFillingPackage.WeightingFunction;

/**
 * Interface for factories that create instances of IWeightingFunction.
 */
public interface IWeightingFunctionFactory {

    /**
     * Creates a new instance of a weighting function with specified parameters.
     *
     * @param z       The power parameter for the weighting function.
     * @param epsilon A small float to avoid division by zero.
     * @return An instance of IWeightingFunction.
     */
    IWeightingFunction Create(int z, float epsilon);
}
