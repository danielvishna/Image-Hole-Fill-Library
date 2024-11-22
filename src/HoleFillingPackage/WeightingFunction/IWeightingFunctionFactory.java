package HoleFillingPackage.WeightingFunction;

public interface IWeightingFunctionFactory {
    IWeightingFunction Create(int z, double epsilon);
}
