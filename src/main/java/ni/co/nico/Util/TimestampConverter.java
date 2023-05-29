package ni.co.nico.Util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TimestampConverter {
    public static List<String> convertTimestamp(String timestampStr) {
        long timestamp = Long.parseLong(timestampStr);
        Instant instant = Instant.ofEpochSecond(0, timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        List<String> yearMonth = new ArrayList<>();

        yearMonth.add(String.valueOf(year));
        yearMonth.add(String.valueOf(month));
        return yearMonth;
    }
}
