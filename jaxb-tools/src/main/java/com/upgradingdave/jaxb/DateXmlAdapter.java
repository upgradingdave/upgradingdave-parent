package com.upgradingdave.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateXmlAdapter extends XmlAdapter<String, Date> {

    String dateFormat;

    public DateXmlAdapter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date unmarshal(String v) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(v);

    }

    @Override
    public String marshal(Date v) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(v);

    }
}
