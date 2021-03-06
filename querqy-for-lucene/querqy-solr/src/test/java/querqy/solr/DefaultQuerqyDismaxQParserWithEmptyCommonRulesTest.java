package querqy.solr;


import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.common.params.DisMaxParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QueryParsing;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SolrTestCaseJ4.SuppressSSL
public class DefaultQuerqyDismaxQParserWithEmptyCommonRulesTest extends SolrTestCaseJ4 {

    public void index() throws Exception {

        assertU(adoc("id", "1", "f1", "a", "f2", "c"));

        assertU(adoc("id", "2", "f1", "a", "f2", "b"));
        
        assertU(adoc("id", "3", "f1", "a", "f2", "c"));
        


        assertU(commit());
     }

     @BeforeClass
     public static void beforeTests() throws Exception {
        initCore("solrconfig-commonrules-empty.xml", "schema.xml");
     }


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        clearIndex();
        index();
    }

    @Test
    public void testSolrIsLoadedAndQueryIsAnswered() {
        
        String q = "a";

        SolrQueryRequest req = req("q", q,
              DisMaxParams.QF, "f1 f2 f3",
              DisMaxParams.MM, "1",
              QueryParsing.OP, "OR",
              "defType", "querqy"
              );

        assertQ("Solr filter query fails",
              req,
              "//result[@name='response' and @numFound='3']"

        );

        req.close();
        
        
    }
    
   
    

}
