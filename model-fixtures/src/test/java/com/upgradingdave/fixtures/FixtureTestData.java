package com.upgradingdave.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FixtureTestData {

    final static Logger log = LoggerFactory.getLogger(FixtureTestData.class);

    public static List<Person> testData;

    static {
        DateFormat df = new SimpleDateFormat(Fixture.FIXTURE_DEFAULT_DATE_FORMAT);

        Person person = null;
        try {
            person = new Person(df.parse("2012-07-03 21:06:48"), "Bill", "Cosby", "bcosby@gmail.com");
        } catch (ParseException e) {
            log.error("unable to parse date");
        }

        testData = new ArrayList<Person>();
        testData.add(person);
    }

}
