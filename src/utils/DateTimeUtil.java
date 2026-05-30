package src.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Pattern;

public final class DateTimeUtil {
    public static final Pattern FORMAT_REGEX = Pattern.compile("^($|\\d{2}\\.\\d{2}\\.\\d{4}( \\d{2}:\\d{2})?)$");
    public static final String FORMAT = "dd.MM.yyyy [HH:mm]";

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd.MM.yyyy")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
            .toFormatter();

    private DateTimeUtil() {}

    public static LocalDateTime parseDateTime(String input) {
        if (input == null || input.isBlank()) { 
            return null; 
        }

        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + e.getLocalizedMessage());
            return null;
        }
    }
}