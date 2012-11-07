package com.upgradingdave.fixtures;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class XmlFixturesTest {

    @Test
    public void toXml() throws ParseException, JAXBException {

        Person person = FixtureTestData.testData.get(0);

        Fixture<Person> personXmlFixture = new XmlFixture<Person>(Person.class);
        personXmlFixture.setDateFormat("yyyy-MM-dd");

        String xml = personXmlFixture.marshal(person);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<person>\n" +
                "    <email>bcosby@gmail.com</email>\n" +
                "    <firstName>Bill</firstName>\n" +
                "    <lastName>Cosby</lastName>\n" +
                "    <lastUpdated>2012-07-03</lastUpdated>\n" +
                "</person>\n", xml);

    }

    @Test
    public void fromXml() throws JAXBException, ParseException {

        String orig = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<person>\n" +
                "    <email>bcosby@gmail.com</email>\n" +
                "    <firstName>Bill</firstName>\n" +
                "    <lastName>Cosby</lastName>\n" +
                "    <lastUpdated>2012-11-06 21:06:48</lastUpdated>\n" +
                "</person>\n";

        Fixture<Person> personFixture = new XmlFixture<Person>(Person.class);
        Person result = personFixture.unMarshal(orig);

        DateFormat df = new SimpleDateFormat(Fixture.FIXTURE_DEFAULT_DATE_FORMAT);
        Date updated = df.parse("2012-11-06 21:06:48");
        Person person = new Person(updated, "Bill", "Cosby", "bcosby@gmail.com");

        assertEquals(person, result);
    }

    @Test
    public void allXmlfromDefaultFile() throws JAXBException {

        Fixture<Person> personFixture = new XmlFixture<Person>(Person.class);

        List<Person> results = personFixture.allFixturesFromFile();

        assertNotNull(results);

        assertEquals(1, results.size());
        assertEquals("Bill", results.get(0).getFirstName());

        personFixture.setPathToFixtureFile("fixtures/people.xml");

        results = personFixture.allFixturesFromFile();
        assertEquals("Big", results.get(0).getFirstName());

    }

    @Test
    public void marshalXmlList() throws JAXBException {

        Fixture<Person> personFixture = new XmlFixture<Person>(Person.class);

        String actual = personFixture.marshal(FixtureTestData.testData);

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<persons>\n" +
                "    <person>\n" +
                "        <email>bcosby@gmail.com</email>\n" +
                "        <firstName>Bill</firstName>\n" +
                "        <lastName>Cosby</lastName>\n" +
                "        <lastUpdated>2012-07-03 21:06:48</lastUpdated>\n" +
                "    </person>\n" +
                "</persons>\n";

        assertEquals(expected, actual);

    }


}
