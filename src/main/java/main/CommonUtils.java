package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {
    public static String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }
}
