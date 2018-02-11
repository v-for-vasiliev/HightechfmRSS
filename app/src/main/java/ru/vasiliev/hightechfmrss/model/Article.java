package ru.vasiliev.hightechfmrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Root(name = "item", strict = false)
public class Article {
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
    //@Element(type = Enclosure.class, name = "enclosure")
    public List<Enclosure> enclosure;

    @Element(name = "pubDate")
    public String pubDate;

    @Element(name = "content:encoded", required = false)
    public String content;

    @Element(name = "pdalink", required = false)
    public String pdalink;

    @Element(name = "media:rating", required = false)
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
                ", content='" + content + '\'' +
                ", pdalink='" + pdalink + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
