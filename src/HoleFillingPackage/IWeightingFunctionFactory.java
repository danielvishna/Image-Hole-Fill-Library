package HoleFillingPackage;

public interface IWeightingFunctionFactory {
    IWeightingFunction Create(int z, double epsilon);
}
