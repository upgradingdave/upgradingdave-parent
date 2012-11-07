package com.upgradingdave.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Use this to control how JAXB marshals to xml. By default, this will create xml like so:
 *
 * <list>
 *     <item xsi:type="person" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">...</list>
 * </list>
 *
 * To control the tag names, creat a class that extends this one, and then annotate with
 * @XmlRootElement(name="classType(s)") and @XmlElement(name="classType")
 *
 * Another solution is to use the Wrapper class and that way it's not necessary to create a new JaxbList
 * implementation for every one of your beans.
 */
@XmlRootElement(name = "list")
public class JaxbList<T> {

    protected List<T> list;

    public JaxbList() {}

    public JaxbList(List<T> list) {
        this.list = list;
    }

    @XmlElement(name = "item")
    public List<T> getList() {
        return list;
    }

}

