package com.upgradingdave.models;

import java.util.List;
import java.util.Map;

public interface ModelDao<T, ID> {

    T create(T model);

    void delete(T model);

    T update(T model);

    T findById(ID id);

    List<T> findAll(PageContext<List<Map<String, String>>, List<String>> page);

    long getTotal(PageContext<List<Map<String, String>>, List<String>> page);

}
