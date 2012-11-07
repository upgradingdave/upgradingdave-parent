package com.upgradingdave.fixtures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Provides convenience methods for managing json files that represent model data. By convention, the methods
 * try to find json files under {@see Fixture.FIXTURE_DIR}. Use {@see setPathToFixtureFile} to change this behavior.
 *
 */
public class JsonFixture<T> extends Fixture<T> {

    final Logger log = LoggerFactory.getLogger(JsonFixture.class);
    Gson gson;

    public JsonFixture(Class<T> clazz){

        super(clazz);
        gson = new GsonBuilder().setDateFormat(getDateFormat()).create();

    }

    @Override
    public String marshal(T thing) {

        return gson.toJson(thing);

    }

    @Override
    public String marshal(List<T> things) {

        return gson.toJson(things);

    }

    @Override
    public T unMarshal(String fixture) {

        return gson.fromJson(fixture, clazz);

    }

    @Override
    protected String getFixtureFileNameFromClass() {
        return String.format("%ss.json", clazz.getSimpleName().toLowerCase());
    }

    @Override
    public void eachFixtureFromFile(String filePath, FixtureProcessor<T> processor) {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                T thing = gson.fromJson(reader, clazz);
                processor.process(thing);
            }
            reader.endArray();
        } catch (UnsupportedEncodingException e) {
            log.error("Unable to read encoding of file. Expected UTF-8", e);
        } catch (IOException e) {
            log.error("Unable to read file {}", filePath, e);
        } catch (NullPointerException e) {
            log.error("Unable to find file '{}'. Expected to find file inside '{}'. Use setPathToFixtureFile to look" +
                    " inside a different directory.", new Object[] {filePath, getPathToFixtureFile()}, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("Unable to close input stream!", e);
                }
            }
        }
    }

}

