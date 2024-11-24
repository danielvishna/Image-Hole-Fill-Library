package HoleFillingPackage.Connectivity;

public class ConnectivityFactory {
    public static Connectivity createConnectivity(String type) {
        ConnectivityEnum connectivityType = ConnectivityEnum.valueOf(type);
        Connectivity connectivity = switch (connectivityType) {
            case FourWayConnectivity -> new FourConnectivity();
            case EightWayConnectivity -> new EightConnectivity();
            default -> throw new IllegalArgumentException("Unknown connectivity type: " + type);
        };
        return connectivity;
    }
}
