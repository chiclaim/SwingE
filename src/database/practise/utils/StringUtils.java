package database.practise.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
