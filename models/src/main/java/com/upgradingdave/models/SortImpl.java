package com.upgradingdave.models;

import java.util.List;

public class SortImpl implements Sortable {

  private List<String> cols;
  private boolean isAscending;

  public SortImpl(List<String> cols, boolean ascending) {

    this.cols = cols;
    this.isAscending = ascending;

  }

  @Override
  public List<String> sortedBy() {
    return cols;
  }

  @Override
  public boolean isAscending() {
    return isAscending;
  }
}
