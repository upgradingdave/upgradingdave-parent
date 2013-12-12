package com.upgradingdave.models;

import com.upgradingdave.fixtures.FixtureProcessor;
import com.upgradingdave.fixtures.JsonFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class ModelDaoTest<T extends Model<ID>,ID> {

  public abstract ModelDao<T, ID> getModelDao();

  public abstract Class getClazz();

  public abstract String getFixturePath();

  List<T> testModels;

  public List<T> getTestModels() {
    return testModels;
  }

  private static boolean resetDb = true;


  @Before
  public void setUp() {

    if (resetDb) {
      removeAll();
    }

    testModels = new ArrayList<T>();

    JsonFixture<T> jsonFixture = new JsonFixture<T>(getClazz());
    jsonFixture.eachFixtureFromFile(getFixturePath(), new FixtureProcessor<T>() {
      @Override
      public void process(T model) {
        try {

          if (resetDb) {
            model = getModelDao().create(model);
          }

          testModels.add(model);

        } catch (Exception e) {
          throw new AssertionError(e);
        }
      }
    });

    this.resetDb = false; // only resetDb before first test.

    assertTrue(testModels.size() > 0);

  }

  @After
  public void tearDown(){
    //removeAll();
  }

  public void removeAll() {
    JsonFixture<T> jsonFixture = new JsonFixture<T>(getClazz());
    jsonFixture.eachFixtureFromFile(getFixturePath(), new FixtureProcessor<T>() {
      @Override
      public void process(T t) {
        getModelDao().delete(t);
      }
    });
  }

  @Test
  public void findById(){

    Model<ID> model = testModels.get(0);
    Model result = getModelDao().findById(model.getId());
    assertEquals(model, result);

  }

  @Test
  public void findAll(){

    PageContext pageContext = new PageImpl(0,1);
    List<T> results = getModelDao().findAll(pageContext);
    assertTrue(results.size() == 1);

    // Test Paging
    pageContext = new PageImpl(1,1);
    results = getModelDao().findAll(pageContext);
    assertTrue(results.size() == 1);
  }

  @Test
  public abstract void update();

}