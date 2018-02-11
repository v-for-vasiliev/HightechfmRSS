package ru.vasiliev.hightechfmrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Root(name = "rss", strict = false)
public class RssFeed {
    @Element(name = "title")
    @Path("channel")
    public String title;

    @Element(name = "link")
    @Path("channel")
    public String link;

    @Element(name = "description")
    @Path("channel")
    public String description;

    @Element(name = "language")
    @Path("channel")
    public String language;

    @Element(name = "copyright")
    @Path("channel")
    public String copyright;

    @Element(name = "pubDate")
    @Path("channel")
    public String pubDate;

    @Element(name = "ttl")
    @Path("channel")
    public String ttl;

    @ElementList(name = "item", inline = true)
    @Path("channel")
    public List<Article> articleList;

    @Override
    public String toString() {
        return "RssFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", copyright='" + copyright + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", ttl='" + ttl + '\'' +
                ", articleList=" + articleList +
                '}';
    }
}
