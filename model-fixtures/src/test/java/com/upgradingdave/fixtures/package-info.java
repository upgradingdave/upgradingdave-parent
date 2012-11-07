@XmlJavaTypeAdapters(
        @XmlJavaTypeAdapter(value=DateXmlAdapter.class,type=Date.class))
package com.upgradingdave.fixtures;

import com.upgradingdave.jaxb.DateXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.Date;