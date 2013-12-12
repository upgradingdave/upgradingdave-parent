package com.upgradingdave.models;

import com.upgradingdave.jdbc.dao.SpringJdbcModelDaoImpl;

import javax.sql.DataSource;

public class UserAccountDao extends SpringJdbcModelDaoImpl<UserAccount, Integer> {

  public UserAccountDao(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public Class getClazz() {
    return UserAccount.class;
  }

  @Override
  public String getTableName() {
    return "USER_ACCOUNTS";
  }

}
