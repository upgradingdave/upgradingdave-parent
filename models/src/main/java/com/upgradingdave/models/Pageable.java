package com.upgradingdave.models;

public interface Pageable {

  public int getOffset();

  public int getPageNumber();

  public int getPageSize();

  public Sortable getSortOrder();

}
