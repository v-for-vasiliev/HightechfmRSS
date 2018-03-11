package ru.vasiliev.hightechfmrss.utils;

import android.content.res.Resources;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
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

    private static final DateTimeParser[] PARSERS = {
            DateTimeFormat.forPattern("EEE, dd MMM yyyy hh:mm:ss Z").getParser(),
            DateTimeFormat.forPattern("EEE, dd MMM yyyy hh:mm:ss ZZZ").getParser(),
    };

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .append(null, PARSERS).toFormatter().withLocale(
                    Locale.US).withOffsetParsed();

    private static final int LATEST_HOURS_INTERVAL = 7;

    private static final int PM_HACK_MARKER = 1;

    private static Resources getResources() {
        return App.getContext().getResources();
    }

    public static DateTime parse(String date) {
        // FIXME: hack fixes time always comes in PM (no 'half of a day' marker in rss xml)
        return FORMATTER.parseDateTime(date).withField(DateTimeFieldType.halfdayOfDay(), PM_HACK_MARKER);
    }

    public static String toHumanReadable(String date) {
        try {
            DateTime dateTime = parse(date);

            if (isToday(dateTime)) {
                int wh = withinHours(dateTime);
                if (wh == 0) {
                    int wm = withinMinutes(dateTime);
                    return getResources().getQuantityString(R.plurals.plurals_minutes_ago, -wm, -wm);
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
        return LocalDate.now().compareTo(new LocalDate(time)) == 0;
    }

    public static boolean isTomorrow(DateTime time) {
        return LocalDate.now().plusDays(1).compareTo(new LocalDate(time)) == 0;
    }

    public static boolean isYesterday(DateTime time) {
        return LocalDate.now().minusDays(1).compareTo(new LocalDate(time)) == 0;
    }

    public static boolean withinLatestHours(String targetDate) {
        try {
            return withinLatestHours(parse(targetDate));
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
