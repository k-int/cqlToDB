package com.k_int.cqlToDB;

/**
 * The result of converting CQL to SQL, HQL or some other structured query which has
 * the query separate to the bind variables.
 */
public class DBQuery {
  String query;
  java.util.Map bindVariables;
}
