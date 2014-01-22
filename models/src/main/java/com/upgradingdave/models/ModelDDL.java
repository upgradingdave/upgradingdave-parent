package com.upgradingdave.models;

import java.util.List;

public interface ModelDDL<T, ID> {

  void createTable();

  void createTable(Boolean dropIfExists);

  void dropTable();

}
