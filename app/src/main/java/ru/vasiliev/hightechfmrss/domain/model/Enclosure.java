package ru.vasiliev.hightechfmrss.domain.model;

import org.simpleframework.xml.Attribute;

import java.io.Serializable;

/**
 * Created by vasiliev on 07/02/2018.
 */

public class Enclosure implements Serializable {

    @Attribute(name = "url")
    public String url;

    @Attribute(name = "type")
    public String type;
}
