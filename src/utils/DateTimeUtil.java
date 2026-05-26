package src.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Pattern;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    public final static Pattern FORMAT_REGEX = Pattern.compile("^$|\\d{2}\\.\\d{2}\\.\\d{4}( \\d{2}:\\d{2})?");
    public final static String FORMAT = "dd.MM.yyyy [HH:mm]";


    public static LocalDateTime parseDateTime(String input) {
        if (input == null || input.isBlank()) { return null; }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("dd.MM.yyyy")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
            .toFormatter();

        try {
            return LocalDateTime.parse(input, formatter);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
}
