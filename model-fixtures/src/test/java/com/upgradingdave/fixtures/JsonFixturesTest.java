package com.upgradingdave.fixtures;

import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class JsonFixturesTest {

    @Test
    public void toJson() throws ParseException {

        Person person = FixtureTestData.testData.get(0);

        JsonFixture<Person> personJsonFixture = new JsonFixture<Person>(Person.class);
        String json = personJsonFixture.marshal(person);

        assertEquals("{\"firstName\":\"Bill\",\"lastName\":\"Cosby\",\"email\":\"bcosby@gmail.com\",\"lastUpdated\":\"2012-07-03 21:06:48\"}", json);
    }

    @Test
    public void allJsonObjects(){

        JsonFixture<Person> personJsonFixture = new JsonFixture<Person>(Person.class);
        List<Person> people = personJsonFixture.allFixturesFromFile();

        assertEquals(2, people.size());
        assertEquals("Steve", people.get(1).getFirstName());

        people = personJsonFixture.allFixturesFromFile("fixtures/people.json");

        assertEquals(2, people.size());
        assertEquals("Steve", people.get(1).getFirstName());

    }

}
