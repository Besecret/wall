package com.wanding.util.queryhelper;

import java.io.Serializable;

/**
 * Created by lacan on 2017/5/26.
 */
public class Sort implements Serializable {

  private static final long serialVersionUID = 3837720889163095819L;
  private final String field;

  private final SortType type;

  public Sort(String field, SortType sortType) {
    this.type = sortType;
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public SortType getType() {
    return type;
  }

  public String getTypeString() {
    return type.toString();
  }

}
