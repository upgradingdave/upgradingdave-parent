package com.upgradingdave.models;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PageImpl implements PageContext<List<Filter>, List<String>> {

  int page;
  int size;
  String sortedBy;
  Boolean sortDirection;  //true for low to high (e.g. A-Z, 1-10), false for reverse
  String filterBy;

  public PageImpl(int page, int size) {

    if(page < 0 || size < 0) {
      throw new IllegalStateException("Page number and size of results must be >= 0");
    }

    this.page = page;
    this.size = size;
  }

  public PageImpl(int page, int size, String sortedBy) {

    if(page < 0 || size < 0) {
      throw new IllegalStateException("Page number and size of results must be >= 0");
    }

    this.page = page;
    this.size = size;
    this.sortedBy = sortedBy;

  }

  public PageImpl(int page, int size, String sortedBy, String filteredBy) {

    if(page < 0 || size < 0) {
      throw new IllegalStateException("Page number and size of results must be >= 0");
    }

    this.page = page;
    this.size = size;
    this.sortedBy = sortedBy;
    this.filterBy = filteredBy;

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
   * This implementation assume that sort order will be a comma delimited string of fields to sort by.
   */
  @Override
  public List getSortOrder() {

    List<String> result = new ArrayList<String>();

    if(sortedBy != null && sortedBy.length()>0) {

      String[] split = sortedBy.split(",");
      for(int i=0;i<split.length;i++) {
        if(split[i].length()>0 && split[i] != null) {
          result.add(split[i]);
        }
      }

    }

    return result;
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
  public List getFilters() {

    List<Filter> result = new ArrayList<Filter>();

    if(filterBy != null && filterBy.length()>0) {

      String[] split = filterBy.split(",");
      for(int i=0;i<split.length;i++) {
        if(split[i].length()>0 && split[i] != null) {

          String[] pair = split[i].split(":");

          if(pair!= null && pair.length == 2) {
            result.add(new FilterImpl(pair[0], pair[1]));
          }

        }
      }

    }

    return result;

  }

  @Override
  public boolean equals(Object o){

    if(this == o) {
      return true;
    }

    if(!(o instanceof PageImpl)){
      return false;
    }

    PageImpl model = (PageImpl)o;

    return (this.page == model.page)
      && (this.size == model.size)
      && (this.sortedBy == null ? model.sortedBy == null : this.sortedBy.equals(model.sortedBy))
      && (this.sortDirection == null ? model.sortDirection == null : this.sortDirection.equals(model.sortDirection))
      && (this.filterBy == null ? model.filterBy == null : this.filterBy.equals(model.filterBy));

  }

  @Override
  public int hashCode() {

    int hash = 7;
    hash = 31 * hash + page;
    hash = 31 * hash + size;
    hash = 31 * hash + (null == sortedBy ? 0 : sortedBy.hashCode());
    hash = 31 * hash + (null == sortDirection ? 0 : sortDirection.hashCode());
    hash = 31 * hash + (null == filterBy ? 0 : filterBy.hashCode());

    return hash;

  }

  @Override
  public String toString(){

    StringBuilder builder = new StringBuilder();
    builder.append("[");
    builder.append(" page: "+ page);
    builder.append(" size: "+ size);
    builder.append(this.sortedBy == null ? " sortedby: null" : " sortedby: "+sortedBy);
    builder.append(this.sortDirection == null ? " sortDirection: null" : " sortDirection: "+sortDirection);
    builder.append(this.filterBy == null ? " filterBy: null" : " filterBy: "+filterBy);

    return builder.toString();
  }

}
