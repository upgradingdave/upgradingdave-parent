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
import java.util.ArrayList;
import java.util.List;

/**
 * Provides convenience methods for managing json files that represent model data. By convention, the methods
 * try to find json files under {@see JSON_FILES_DIR}. Use {@see setFixturesDir} to change this behavior.
 *
 */
public class JsonFixture<T> {

    Logger log = LoggerFactory.getLogger(JsonFixture.class);

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String JSON_FILES_DIR = "fixtures";

    Class clazz;
    Gson gson;
    String jsonFilesDir;

    public JsonFixture(Class clazz){

        this.clazz = clazz;
        gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

    }

    /**
     * Use this to change the directory where json files live.
     */
    public void setFixturesDir(String jsonFilesDir) {

        this.jsonFilesDir = jsonFilesDir;

    }

    public String getFixturesDir() {

        if(jsonFilesDir == null) {
            return JSON_FILES_DIR;
        } else {
            return jsonFilesDir;
        }
    }

    public String toJson(T thing) {

        return gson.toJson(thing);

    }

    /**
     * Build a file name using convention of class(s).json. For example: User.class becomes users.json
     */
    public String getJsonFileNameFromClass() {

        return String.format("fixtures/%ss.json", clazz.getSimpleName().toLowerCase());

    }

    /**
     * Use this instead of allJsonObjectsFromFile if you want to use a non-standard json file name. For example,
     * if you'd rather use people.json instead of persons.json.
     */
    public List<T> allJsonObjectsFromFile(String fileName) {

        log.info("Attempting to load {} objects from file {}", clazz.getSimpleName().toLowerCase(), fileName);

        final List<T> results = new ArrayList<>();
        JsonProcessor<T> jsonProcessor = new JsonProcessor<T>() {
            @Override
            public void process(T object) {
                results.add(object);
            }
        };

        eachJsonObjectFomFile(fileName, jsonProcessor);

        return results;

    }

    /**
     * Try to find a file named class(s).json (users.json, for example), iterate over all json objects
     * and return complete list
     */
    public List<T> allJsonObjectsFromFile() {

        String fileName = getJsonFileNameFromClass();
        return allJsonObjectsFromFile(fileName);

    }

    /**
     * Try to find a file named class(s).json (users.json, for example), iterate over all json objects
     * and do something with each.
     */
    public void withEachJsonObjectFromFile(JsonProcessor<T> processor) {

        eachJsonObjectFomFile(getJsonFileNameFromClass(), processor);

    }

    /**
     * Read json objects from file, processing each one.
     */
    public void eachJsonObjectFomFile(String filePath, JsonProcessor<T> processor){

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
            log.error("Unable to find file '{}'. Expected to find file inside '{}'. Use setFixturesDir to look" +
                    " inside a different directory.",
                    filePath, getFixturesDir());
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

    public interface JsonProcessor<T> {

        public void process(T object);

    }

}

