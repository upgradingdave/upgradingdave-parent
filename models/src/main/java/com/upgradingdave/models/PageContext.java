package com.upgradingdave.models;

import java.util.List;

public interface PageContext<F extends List, S> {

  int getPage();

  int getSize();

  S getSortOrder();

  Boolean getSortDirection();

  F getFilters();

}