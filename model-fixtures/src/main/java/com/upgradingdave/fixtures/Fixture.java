package com.upgradingdave.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Fixture<T> {

    final Logger log = LoggerFactory.getLogger(Fixture.class);
    public static final String FIXTURE_DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    public final static String FIXTURE_DIR = "fixtures";

    private String pathToFixtureFile;
    private String dateFormat;
    Class<T> clazz;

    /**
     * Convert Java object into some other format (like xml or json).
     */
    public abstract String marshal(T thing);

    /**
     * Convert list of objects into some other format (like xml or json).
     */
    public abstract String marshal(List<T> things);

    /**
     * Convert Java object into some other format
     */
    public abstract T unMarshal(String fixture);

    /**
     * Read objects from file, processing each one.
     */
    public abstract void eachFixtureFromFile(String filePath, FixtureProcessor<T> processor);

    public Fixture(Class<T> clazz) {
        this.clazz = clazz;
        this.pathToFixtureFile = FIXTURE_DIR + "/" + getFixtureFileNameFromClass();
        this.dateFormat = FIXTURE_DEFAULT_DATE_FORMAT;
    }

    /**
     * Use this to change the directory where fixture file lives
     */
    public void setPathToFixtureFile(String fixtureDir) {
        this.pathToFixtureFile = fixtureDir;
    }

    public String getPathToFixtureFile() {
        return pathToFixtureFile;
    }

    /**
     * Most of the time, you need to specify the format of date to use. Otherwise the default
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Use this instead of allObjectsFromFile if you want to use a non-standard json file name. For example,
     * if you'd rather use people.json instead of persons.json.
     */
    public List<T> allFixturesFromFile(String fileName) {

        log.info("Attempting to load {}", fileName);

        final List<T> results = new ArrayList<T>();
        FixtureProcessor<T> jsonProcessor = new FixtureProcessor<T>() {
            @Override
            public void process(T object) {
                results.add(object);
            }
        };

        eachFixtureFromFile(fileName, jsonProcessor);

        return results;

    }

    /**
     * Try to find a file named class(s).json (users.json, for example), iterate over all json objects
     * and return complete list
     */
    public List<T> allFixturesFromFile() {
        return allFixturesFromFile(getPathToFixtureFile());
    }

    /**
     * Try to find a file named class(s).[json|xml] (users.json, for example), iterate over all objects
     * and do something with each.
     */
    public void withEachFixtureFromFile(FixtureProcessor<T> processor) {
        eachFixtureFromFile(getPathToFixtureFile(), processor);
    }

    /**
     * Build a file name using convention of class(s).json. For example: User.class becomes users.json
     */
    protected abstract String getFixtureFileNameFromClass();

}
