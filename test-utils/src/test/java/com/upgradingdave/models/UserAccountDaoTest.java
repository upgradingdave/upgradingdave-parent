package com.upgradingdave.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/test-context.xml"})
public class UserAccountDaoTest extends ModelDaoTest<UserAccount, Integer>{

  @Autowired
  UserAccountDao userAccountDao;

  @Override
  public ModelDao<UserAccount, Integer> getModelDao() {
    return userAccountDao;
  }

  @Override
  public Class getClazz() {
    return UserAccount.class;
  }

  @Override
  public String getFixturePath() {
    return "fixtures/useraccounts.json";
  }

  @Override
  public void update() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Test
  public void testFilters(){
    List<UserAccount> results = userAccountDao.findAll(new PageImpl(0, 10, "username", "username:'bcosby'"));
    assertEquals("bcosby", results.get(0).getUserName());
  }

  @Test
  public void testOrder(){
    PageImpl page = new PageImpl(0, 10);
    List<UserAccount> results = userAccountDao.findAll(page);
    assertEquals("bcosby", results.get(0).getUserName());

    page.setSortDirection(false);
    results = userAccountDao.findAll(page);
    assertEquals("smartin", results.get(0).getUserName());

  }
}
