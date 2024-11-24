package HoleFillingPackage.Connectivity;

public class ConnectivityFactory {
    public static Connectivity createConnectivity(String type) {
        ConnectivityEnum connectivityType = ConnectivityEnum.valueOf(type);
        return switch (connectivityType) {
            case FourWayConnectivity -> new FourConnectivity();
            case EightWayConnectivity -> new EightConnectivity();
        };
    }
}
