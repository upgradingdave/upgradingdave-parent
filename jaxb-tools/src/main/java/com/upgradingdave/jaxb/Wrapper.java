package com.upgradingdave.jaxb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.List;

public class Wrapper<T> {

    final static Logger log = LoggerFactory.getLogger(Wrapper.class);

    private List<T> items;

    public Wrapper() {}

    public Wrapper(List<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax=true)
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
