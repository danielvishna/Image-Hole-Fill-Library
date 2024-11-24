package HoleFillingPackage.Connectivity;

/**
 * Factory class for creating connectivity instances based on input type.
 */
public class ConnectivityFactory {

    /**
     * Creates a connectivity instance based on the specified type.
     *
     * @param type The type of connectivity ("FourWayConnectivity" or "EightWayConnectivity").
     * @return The corresponding connectivity instance.
     * @throws IllegalArgumentException if the type is invalid.
     */
    public static Connectivity createConnectivity(String type) {
        ConnectivityEnum connectivityType = ConnectivityEnum.valueOf(type);
        return switch (connectivityType) {
            case FourWayConnectivity -> new FourConnectivity();
            case EightWayConnectivity -> new EightConnectivity();
        };
    }
}