package HoleFillingPackage.Connectivity;

public class ConnectivityFactory {
    public static Connectivity createConnectivity(String type) {
        ConnectivityEnum connectivityType = ConnectivityEnum.valueOf(type);
        switch (connectivityType) {
            case FourWayConnectivity:
                return new FourConnectivity();
            case EightWayConnectivity:
                return new EightConnectivity();
            default:
                throw new IllegalArgumentException("Unknown connectivity type: " + type);
        }
    }
}
