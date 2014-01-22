package com.upgradingdave.models;

import java.util.ArrayList;
import java.util.List;

public class PageImpl implements Pageable{

  int pageNumber;
  int pageSize;
  SortImpl sortOrder;

  public PageImpl(int pageNumber, int pageSize) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.sortOrder = null;
  }

  public PageImpl(int pageNumber, int pageSize, String sortByName) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    List<String> sortByNames = new ArrayList<String>(1);
    sortByNames.add(sortByName);
    this.sortOrder = new SortImpl(sortByNames, true);
  }

  public PageImpl(int pageNumber, int pageSize, String sortByName, boolean isAscending) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    List<String> sortByNames = new ArrayList<String>(1);
    sortByNames.add(sortByName);
    this.sortOrder = new SortImpl(sortByNames, isAscending);
  }

  public PageImpl(int pageNumber, int pageSize, List<String> sortByNames) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.sortOrder = new SortImpl(sortByNames, true);
  }

  public PageImpl(int pageNumber, int pageSize, List<String> sortByNames, boolean isAscending) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.sortOrder = new SortImpl(sortByNames, isAscending);
  }

  /**
   * Determine the location to start counting based on pageNumber and pageSize
   * @return
   */
  @Override
  public int getOffset() {
    return ((pageNumber+1)*pageSize) - pageSize;
  }

  /**
   * Page Numbers start at 0
   * @return pageNumber
   */
  @Override
  public int getPageNumber() {
    return this.pageNumber;
  }

  @Override
  public int getPageSize() {
    return this.pageSize;
  }

  @Override
  public Sortable getSortOrder() {
    return this.sortOrder;
  }
}
