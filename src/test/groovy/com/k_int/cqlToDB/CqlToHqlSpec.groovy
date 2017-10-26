package com.k_int.cqlToDB;

import spock.lang.Specification
import spock.lang.Unroll
import com.k_int.cqlToDB.HqlBuilder;

class CqlToHqlSpec extends Specification {

  /**
   * Assuming a HQL structure Book ---< author >--- Person
   *
   *
   *
   */
  def "Test ability to convert CQL to HQL"() {

    when:
      String cql = 'title=foo and author=(bar or baz)'

    then:
      HqlBuilder b = new HqlBuilder()
      DBBuilderConfig cfg = new DBBuilderConfig();
      DBQuery builderResult = b.parse(cql, cfg);

    expect:
      builderResult.query == 'select b from Book as b join b.authors as a where b.title=:t1 and ( a.person.name=:a1 OR a.person.name=:a2 )'
  }

}
