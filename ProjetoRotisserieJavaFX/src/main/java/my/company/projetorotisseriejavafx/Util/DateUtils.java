package my.company.projetorotisseriejavafx.Util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {

    public static LocalDate timestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDate timestampToLocalDate(long timestamp, String zoneId) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of(zoneId))
                .toLocalDate();
    }
}
