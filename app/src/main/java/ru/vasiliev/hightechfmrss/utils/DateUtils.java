package ru.vasiliev.hightechfmrss.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by vasiliev on 28/02/2018.
 */

public class DateUtils {

    private static DateTimeFormatter DATE_FORMATTER_REGULAR = DateTimeFormat.forPattern(
            "dd MMM HH:mm");
    private static DateTimeFormatter DATE_FORMATTER_HHMM = DateTimeFormat.forPattern("HH:mm");

    private static final DateTimeParser[] PARSERS = {
            DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss Z").getParser(),
            DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss ZZZ").getParser(),
    };

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .append(null, PARSERS).toFormatter().withLocale(
                    Locale.ENGLISH).withOffsetParsed();

    public static DateTime parse(String date) {
        return FORMATTER.parseDateTime(date);
    }

    public static String toHumanReadable(String date) {
        try {
            DateTime dateTime = parse(date);
            Interval today = new Interval(DateTime.now().withTimeAtStartOfDay(), Days.ONE);
            if (today.contains(dateTime)) {
                return String.format(Locale.forLanguageTag("ru"), "Сегодня, %s",
                        DATE_FORMATTER_HHMM.print(dateTime));
            }
            if (today.contains(dateTime.minusDays(1))) {
                return String.format(Locale.forLanguageTag("ru"), "Вчера, %s",
                        DATE_FORMATTER_HHMM.print(dateTime));
            }

            return DATE_FORMATTER_REGULAR.withLocale(Locale.forLanguageTag("ru")).print(dateTime);
        } catch (Throwable t) {
            Timber.e(t, "");
            return date;
        }
    }
}
