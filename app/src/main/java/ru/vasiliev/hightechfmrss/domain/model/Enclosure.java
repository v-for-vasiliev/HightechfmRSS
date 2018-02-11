package ru.vasiliev.hightechfmrss.domain.model;

import org.simpleframework.xml.Attribute;

/**
 * Created by vasiliev on 07/02/2018.
 */

public class Enclosure {

    @Attribute(name = "url")
    public String url;

    @Attribute(name = "type")
    public String type;
}
