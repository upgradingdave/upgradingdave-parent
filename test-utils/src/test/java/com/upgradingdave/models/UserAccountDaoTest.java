package com.upgradingdave.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.PropertyDescriptor;
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
    UserAccount user = getTestModels().get(0);
    String orig = user.getUserName();
    String newUserName = "wferrel";
    user.setUserName(newUserName);
    userAccountDao.update(user);
    UserAccount updated = userAccountDao.findById(user.getId());
    assertEquals(newUserName, updated.getUserName());

    user.setUserName(orig);
    userAccountDao.update(user);
    updated = userAccountDao.findById(user.getId());
    assertEquals(orig, updated.getUserName());
  }

  @Test
  public void testFilters(){
    List<UserAccount> results = userAccountDao.findAll(new PageImpl(0, 10, "username", "username:'bcosby'"));
    assertEquals("bcosby", results.get(0).getUserName());

    results = userAccountDao.findAll(new PageImpl(0, 10, "username", "username:'bcosby',userName:'smartin'"));
    assertEquals(2, results.size());
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

  @Test
  public void total(){
    long results = userAccountDao.getTotal(new PageImpl(0, 10));
    assertEquals(2, results);

    results = userAccountDao.getTotal(new PageImpl(-1, -1, "username", "username:'bcosby'"));
    assertEquals(1, results);

  }
}
