package com.upgradingdave.models;

import java.util.List;

public interface ModelDao<T, ID> {

  T create(T model);

  List<T> findByExample(T example, Pageable page);

  Long findCountByExample(T example);

  T findById(ID id);

  void delete(T model);

  T update(T model);

}
