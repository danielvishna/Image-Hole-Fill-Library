package HoleFillingPackage;

public class ConnectivityFactory {
    public static Connectivity createConnectivity(String type) {
        switch (type.toLowerCase()) {
            case "4-connected":
                return new FourConnectivity();
            case "8-connected":
                return new EightConnectivity();
            default:
                throw new IllegalArgumentException("Unknown connectivity type: " + type);
        }
    }
}
