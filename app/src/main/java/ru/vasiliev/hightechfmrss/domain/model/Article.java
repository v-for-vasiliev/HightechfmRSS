package ru.vasiliev.hightechfmrss.domain.model;

import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

import ru.vasiliev.hightechfmrss.utils.DateUtils;
import timber.log.Timber;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Root(name = "item", strict = false)
@NamespaceList({
        @Namespace(prefix = "content", reference = "http://purl.org/rss/1.0/modules/encoded/"),
        @Namespace(prefix = "dc", reference = "http://purl.org/dc/elements/1.1/"),
        @Namespace(prefix = "media", reference = "http://search.yahoo.com/mrss/"),
        @Namespace(prefix = "atom", reference = "http://www.w3.org/2005/Atom"),
        @Namespace(prefix = "georss", reference = "http://www.georss.org/georss")
})
public class Article implements Serializable, Comparable<Article> {
    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Element(name = "description")
    public String description;

    @Element(name = "author", required = false)
    public String author;

    @Element(name = "category", required = false)
    public String category;

    @ElementList(entry = "enclosure", inline = true, required = false)
    public List<Enclosure> enclosure;

    @Element(name = "pubDate")
    public String pubDate;

    @Element(name = "encoded", data = true, required = false)
    public String encoded;

    @Element(name = "pdalink", required = false)
    public String pdalink;

    @Element(name = "rating", required = false)
    public String rating;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", enclosure=" + enclosure +
                ", pubDate='" + pubDate + '\'' +
                ", encoded='" + encoded + '\'' +
                ", pdalink='" + pdalink + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Article article) {
        try {
            return DateUtils.parseToLocalTimeZone(pubDate).compareTo(
                    DateUtils.parseToLocalTimeZone(article.pubDate));
        } catch (Throwable t) {
            Timber.e(t, "");
            return 0;
        }
    }
}
