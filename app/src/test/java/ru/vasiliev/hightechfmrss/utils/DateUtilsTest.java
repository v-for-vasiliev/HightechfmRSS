package ru.vasiliev.hightechfmrss.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

/**
 * Created by vasiliev on 24/03/2018.
 */
public class DateUtilsTest {

    @Test
    public void parse() throws Exception {
        DateTime dt = DateUtils.parseToLocalTimeZone("Fri, 23 Mar 2018 03:58:01 +0000");
        DateTimeFormatter fmt = ISODateTimeFormat.dateHourMinuteSecond();
        System.out.println(fmt.print(dt));
        System.out.println(fmt.print(DateTime.now()));
    }

    @Test
    public void toHumanReadable() throws Exception {
        String date = DateUtils.toHumanReadable("Fri, 23 Mar 2018 03:58:01 +0000");
        System.out.println(date);
    }
}