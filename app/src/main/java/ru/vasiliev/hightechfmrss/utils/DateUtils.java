package ru.vasiliev.hightechfmrss.utils;

import android.content.res.Resources;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.util.Locale;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.R;
import timber.log.Timber;

/**
 * Created by vasiliev on 28/02/2018.
 */

public class DateUtils {
    private static DateTimeFormatter DATE_FORMATTER_REGULAR = DateTimeFormat.forPattern(
            "dd MMM HH:mm");
    private static DateTimeFormatter DATE_FORMATTER_HHMM = DateTimeFormat.forPattern("HH:mm");

    private static DateTimeParser GMT_PARSER = DateTimeFormat.forPattern("ZZZ").getParser();

    private static DateTimeParser OFFSET_PARSER = DateTimeFormat.forPattern("Z").getParser();

    private static DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("EEE, dd MMM yyyy HH:mm:ss ") // Common pattern
            .appendOptional(GMT_PARSER)    // Optional parser for GMT
            .appendOptional(OFFSET_PARSER) // Optional parser for +0000
            .toFormatter().withOffsetParsed().withLocale(Locale.US);


    private static final int LATEST_HOURS_INTERVAL = 6;

    private static final int PM_HACK_MARKER = 1;

    private static Resources getResources() {
        return App.getContext().getResources();
    }

    public static DateTime parseToLocalTimeZone(String date) {
        // FIXME: hack fixes time always comes in PM (no 'half of a day' marker in rss xml)
        // return FORMATTER.parseDateTime(date).withZone(DateTimeZone.getDefault()).withField
        // (DateTimeFieldType.halfdayOfDay(), PM_HACK_MARKER);
        return FORMATTER.parseDateTime(date).withZone(DateTimeZone.getDefault());
    }

    public static String toHumanReadable(String date) {
        try {
            DateTime dateTime = parseToLocalTimeZone(date);

            if (isToday(dateTime)) {
                int wh = withinHours(dateTime);
                if (wh == 0) {
                    int wm = withinMinutes(dateTime);
                    return getResources().getQuantityString(R.plurals.plurals_minutes_ago, -wm,
                            -wm);
                } else if (wh <= 0 && wh >= -LATEST_HOURS_INTERVAL) {
                    return getResources().getQuantityString(R.plurals.plurals_hours_ago, -wh, -wh);
                } else {
                    return String.format(Locale.forLanguageTag("ru"), "Сегодня, %s",
                            DATE_FORMATTER_HHMM.print(dateTime));
                }
            }

            if (isYesterday(dateTime)) {
                return String.format(Locale.forLanguageTag("ru"), "Вчера, %s",
                        DATE_FORMATTER_HHMM.print(dateTime));
            }

            return DATE_FORMATTER_REGULAR.withLocale(Locale.forLanguageTag("ru")).print(dateTime);
        } catch (Throwable t) {
            Timber.e(t, "");
            return date;
        }
    }

    public static boolean isToday(DateTime time) {
        return DateTime.now().compareTo(time) == 0;
    }

    public static boolean isTomorrow(DateTime time) {
        return DateTime.now().plusDays(1).compareTo(new DateTime(time)) == 0;
    }

    public static boolean isYesterday(DateTime time) {
        return DateTime.now().minusDays(1).compareTo(new DateTime(time)) == 0;
    }

    public static boolean withinLatestHours(String targetDate) {
        try {
            return withinLatestHours(parseToLocalTimeZone(targetDate));
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean withinLatestHours(DateTime targetDate) {
        int wh = withinHours(targetDate);
        return (wh <= 0 && wh >= -LATEST_HOURS_INTERVAL);
    }

    public static int withinHours(DateTime targetDate) {
        return Hours.hoursBetween(DateTime.now(), targetDate).getHours();
    }

    public static int withinMinutes(DateTime targetDate) {
        return Minutes.minutesBetween(DateTime.now(), targetDate).getMinutes();
    }
}
