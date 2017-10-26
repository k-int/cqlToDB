package com.k_int.cqlToDB;

/**
 * Build HQL from CQL query string and config
 */
public class HqlBuilder {

  public DBQuery parse(String cqlString, DBBuilderConfig config) {
    return new DBQuery(query:'select 1', bindVariables:[:]);
  }
}
