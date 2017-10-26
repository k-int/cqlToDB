package com.k_int.cqlToDB;

import spock.lang.Specification
import spock.lang.Unroll
import com.k_int.cqlToDB.HqlBuilder;

class CqlToHqlSpec extends Specification {

  def cfg = [
    'Book':[
      baseclass:'com.k_int.Book',
      title:'Books',
      qbeConfig:[
        // For querying over associations and joins, here we will need to set up scopes to be referenced in the qbeForm config
        // Until we need them tho, they are omitted. qbeForm entries with no explicit scope are at the root object.
        qbeForm:[
          [
            prompt:'Name or Title',
            qparam:'qp_name',
            placeholder:'Name or title of item',
            contextTree:['ctxtp':'qry', 'comparator' : 'ilike', 'prop':'name']
          ],
          [
            prompt:'ID',
            qparam:'qp_id',
            placeholder:'ID of item',
            contextTree:['ctxtp':'qry', 'comparator' : 'eq', 'prop':'id', 'type' : 'java.lang.Long']
          ],
          [
            prompt:'SID',
            qparam:'qp_sid',
            placeholder:'SID for item',
            contextTree:['ctxtp':'qry', 'comparator' : 'eq', 'prop':'ids.value']
          ],
        ],
        qbeGlobals:[
          ['ctxtp':'filter', 'prop':'status.value', 'comparator' : 'eq', 'value':'Deleted', 'negate' : true, 'prompt':'Hide Deleted', 'qparam':'qp_showDeleted', 'default':'on']
        ],
        qbeResults:[
          [heading:'Type', property:'class.simpleName'],
          [heading:'Name/Title', property:'name',sort:'name', link:[controller:'resource',action:'show',id:'x.r.class.name+\':\'+x.r.id'] ],
          [heading:'Status', property:'status.value',sort:'status'],
        ]
      ]
    ]
  ]


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
      builderResult.query == 'select b from com.k_int.Book as b join b.authors as a where b.title=:t1 and ( a.person.name=:a1 OR a.person.name=:a2 )'
  }

}
