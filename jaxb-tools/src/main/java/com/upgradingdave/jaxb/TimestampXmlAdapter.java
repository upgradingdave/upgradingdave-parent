package com.upgradingdave.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampXmlAdapter extends XmlAdapter<String, Timestamp> {

    String dateFormat;

    public TimestampXmlAdapter(){}

    public TimestampXmlAdapter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Timestamp unmarshal(String v) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = sdf.parse(v);
        return new Timestamp(date.getTime());

    }

    @Override
    public String marshal(Timestamp v) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(v.getTime());
        return sdf.format(date);

    }
}
