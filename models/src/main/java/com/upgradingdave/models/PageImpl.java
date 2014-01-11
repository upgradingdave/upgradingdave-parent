package com.upgradingdave.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of pageContext.
 *
 * For filters, expects a list of maps with string key/value pairs.
 * Will also accept a string formatted like so: [{key1:value,key1:value},{key1:value}]
 *
 * Implementations should treat this map so that key/value pairs inside maps get ANDed together. Maps get ORed together.
 *
 */
public class PageImpl implements PageContext<List<Map<String, String>>, List<String>> {

  int page;
  int size;
  List<String> sortOrder;
  Boolean sortDirection;  //true for low to high (e.g. A-Z, 1-10), false for reverse
  List<Map<String, String>> filters;

  Gson gson;

  public PageImpl(int page, int size) {

    this.gson = new GsonBuilder().create();

    this.page = page;
    this.size = size;
  }

  public PageImpl(int page, int size, String sortedBy) {

    this.gson = new GsonBuilder().create();

    this.page = page;
    this.size = size;
    this.sortOrder = parseSortOrder(sortedBy);
  }

  public PageImpl(int page, int size, String sortedBy, String filteredBy) {

    this.gson = new GsonBuilder().create();

    this.page = page;
    this.size = size;
    this.sortOrder = parseSortOrder(sortedBy);
    this.filters = parseFilterString(filteredBy);
  }

  public int getEnd(){
    return ((page+1) * size)-1;
  }

  /**
   * Start is zero index based. For ex, if page=2, size=10, then start is 20
   */
  public int getStart(){
    return ((page+1) * size) - size;
  }

  @Override
  public int getPage() {
    return page;
  }

  @Override
  public int getSize() {
    return size;
  }

  /**
   * This implementation assumes that sort order will be a comma delimited string of fields to sort by.
   */
  @Override
  public List<String> getSortOrder() {
    return sortOrder;
  }

  public void setSortDirection(Boolean sortDirection) {
    this.sortDirection = sortDirection;
  }

  @Override
  public Boolean getSortDirection() {
    if(sortDirection == null){
      return true;
    } else {
      return sortDirection;
    }
  }

  @Override
  public List<Map<String, String>> getFilters() {
    return filters;
  }

  /**
   * Parse a comma delimited key:value pair string into a List<Map<String,String>>
   */
  private List<Map<String, String>> parseFilterString(String filterByString) {

    List<Map<String, String>> result = new ArrayList<Map<String, String>>();
    return gson.fromJson(filterByString, result.getClass());

  }

  /**
   * Parse comma delimited string to a List<String>
   */
  private List<String> parseSortOrder(String sortedBy) {
    List<String> result = new ArrayList<String>();

    if(sortedBy != null && sortedBy.length()>0) {

      String[] split = sortedBy.split(",");
      for(String next : split) {
        if(next != null && next.length()>0) {
          result.add(next);
        }
      }
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PageImpl page1 = (PageImpl) o;

    return page == page1.page && size == page1.size
            && !(filters != null ? !filters.equals(page1.filters) : page1.filters != null)
            && !(sortDirection != null ? !sortDirection.equals(page1.sortDirection) : page1.sortDirection != null)
            && !(sortOrder != null ? !sortOrder.equals(page1.sortOrder) : page1.sortOrder != null);
  }

  @Override
  public int hashCode() {
    int result = page;
    result = 31 * result + size;
    result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
    result = 31 * result + (sortDirection != null ? sortDirection.hashCode() : 0);
    result = 31 * result + (filters != null ? filters.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PageImpl{" +
            "page=" + page +
            ", size=" + size +
            ", sortOrder=" + sortOrder +
            ", sortDirection=" + sortDirection +
            ", filters=" + filters +
            '}';
  }
}
