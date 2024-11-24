import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;

public class ProgramArguments {
    String inputImagePath;
    String maskImagePath;
    String outputImagePath;
    IWeightingFunctionFactory weightingFunctionType;
    int z;
    float epsilon;
    String connectivityType;

    public ProgramArguments(String inputImagePath, String maskImagePath, String outputImagePath,
                            IWeightingFunctionFactory weightingFunctionType, int z, float epsilon, String connectivityType) {
        this.inputImagePath = inputImagePath;
        this.maskImagePath = maskImagePath;
        this.outputImagePath = outputImagePath;
        this.weightingFunctionType = weightingFunctionType;
        this.z = z;
        this.epsilon = epsilon;
        this.connectivityType = connectivityType;
    }

    public int getZ() {
        return z;
    }

    public float getEpsilon() {
        return epsilon;
    }

    public String getConnectivityType() {
        return connectivityType;
    }

    public String getInputImagePath() {
        return inputImagePath;
    }

    public String getMaskImagePath() {
        return maskImagePath;
    }

    public String getOutputImagePath() {
        return outputImagePath;
    }

}
