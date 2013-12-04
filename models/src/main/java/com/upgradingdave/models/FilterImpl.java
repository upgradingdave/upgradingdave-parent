package com.upgradingdave.models;

public class FilterImpl implements Filter{

  String field;
  String value;

  public FilterImpl(String field, String value) {
    this.field = field;
    this.value = value;
  }

  public String getField() {
    return field;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString(){

    StringBuilder sb = new StringBuilder();
    sb.append("[FIELD { ");
    sb.append("field: ").append(field == null ? "null" : field);
    sb.append(", value: ").append(value == null ? "null" : value);
    sb.append(" }]");

    return sb.toString();
  }
}