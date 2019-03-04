package ru.vasiliev.hightechfmrss.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import ru.vasiliev.hightechfmrss.domain.model.Enclosure;

/**
 * Created by k.vasilev on 29/03/2018.
 */

public class RssTypeConverters {

    private static Gson sGson = new Gson();

    @TypeConverter
    public static List<Enclosure> stringToEnclosureList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Enclosure>>() {
        }.getType();

        return sGson.fromJson(data, listType);
    }

    @TypeConverter
    public static String enclosureListToString(List<Enclosure> someObjects) {
        return sGson.toJson(someObjects);
    }
}
