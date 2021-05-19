package cells.util;

import java.util.UUID;

public class Util {

    /**
     * Returns true if a value is true. Useful for method references
     */
    public static Boolean isTrue(Boolean value) {
        return value;
    }

    public static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
