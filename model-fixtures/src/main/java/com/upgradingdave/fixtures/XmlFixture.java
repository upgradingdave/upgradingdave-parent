package com.upgradingdave.fixtures;

import com.upgradingdave.jaxb.DateXmlAdapter;
import com.upgradingdave.jaxb.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class XmlFixture<T> extends Fixture<T> {

    final static Logger log = LoggerFactory.getLogger(XmlFixture.class);

    private JAXBContext jaxbContext;

    public XmlFixture(Class<T> clazz) throws JAXBException {

        super(clazz);
        jaxbContext = JAXBContext.newInstance(clazz, Wrapper.class);

    }

    @Override
    public String marshal(T thing) {

        try {

            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

            /**
             * REMEMBER to also associate the DateXmlAdapter with Date via the @XmlJavaTypeAdapter annotation.
             * It's convenient to define it inside package-info.java.
             */
            DateXmlAdapter dateXmlAdapter = new DateXmlAdapter(getDateFormat());
            m.setAdapter(DateXmlAdapter.class, dateXmlAdapter);

            StringWriter result = new StringWriter();
            m.marshal(thing, result);

            return result.toString();

        } catch (PropertyException e) {

            log.error("Unable to configure JAXB Marshaller", e);
            return null;

        } catch (JAXBException e) {

            log.error("Unable to perform JAXB unmarshal from object to xml", e);
            return null;
        }

    }

    @Override
    public String marshal(List<T> things) {

        try {

            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

            /**
             * REMEMBER to also associate the DateXmlAdapter with Date via the @XmlJavaTypeAdapter annotation.
             * It's convenient to define it inside package-info.java.
             */
            DateXmlAdapter dateXmlAdapter = new DateXmlAdapter(getDateFormat());
            m.setAdapter(DateXmlAdapter.class, dateXmlAdapter);

            StringWriter result = new StringWriter();

            /*
             * Tricky! Wrap the class in Wrapper<T> with @XmlAnyElement annotation and then manually create a
             * JAXBElement<Wrapper> and marshal that. This makes it possible to control xml tag names
             * http://stackoverflow.com/questions/13272288/is-it-possible-to-programmatically-configure-jaxb/
             */
            Wrapper<T> wrapper = new Wrapper<T>(things);
            JAXBElement<Wrapper> wrapperJAXBElement = new JAXBElement<Wrapper>(new QName(clazz.getSimpleName().toLowerCase()+"s"), Wrapper.class, wrapper);
            m.marshal(wrapperJAXBElement, result);

            return result.toString();

        } catch (PropertyException e) {

            log.error("Unable to configure JAXB Marshaller", e);
            return null;

        } catch (JAXBException e) {

            log.error("Unable to perform JAXB unmarshal from list of objects to xml", e);
            return null;
        }

    }

    @Override
    public T unMarshal(String fixture) {

        try {

            Unmarshaller m = jaxbContext.createUnmarshaller();

            /**
             * REMEMBER to also associate the DateXmlAdapter with Date via the @XmlJavaTypeAdapter annotation.
             * It's convenient to define it inside package-info.java.
             */
            DateXmlAdapter dateXmlAdapter = new DateXmlAdapter(getDateFormat());
            m.setAdapter(DateXmlAdapter.class, dateXmlAdapter);

            return (T) m.unmarshal(new StringReader(fixture));

        } catch (JAXBException e) {

            log.error("Unable to perform JAXB unmarshal from xml to object", e);
            return null;
        }
    }

    @Override
    protected String getFixtureFileNameFromClass() {
        return String.format("%ss.xml", clazz.getSimpleName().toLowerCase());
    }

    @Override
    public void eachFixtureFromFile(String filePath, FixtureProcessor<T> processor) {

        try {

            Unmarshaller um = jaxbContext.createUnmarshaller();

            /**
             * REMEMBER to also associate the DateXmlAdapter with Date via the @XmlJavaTypeAdapter annotation.
             * It's convenient to define it inside package-info.java.
             */
            DateXmlAdapter dateXmlAdapter = new DateXmlAdapter(getDateFormat());
            um.setAdapter(DateXmlAdapter.class, dateXmlAdapter);

            StreamSource stream = new StreamSource(this.getClass().getClassLoader().getResource(filePath).getFile());
            JAXBElement<Wrapper> result = um.unmarshal(stream, Wrapper.class);

            for (Object thing : result.getValue().getItems()) {
                processor.process((T)thing);
            }

        } catch (NullPointerException e) {
            log.error("Problems encountered trying to read file {}", filePath, e);
        } catch (JAXBException e) {
            log.error("Unable to unmarshal objects from {}", filePath, e);
        }

    }
}
