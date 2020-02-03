package gov.nasa.pds.search.solr.query.run;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import gov.nasa.pds.nlp.ner.NerToken;
import gov.nasa.pds.search.solr.query.SolrQueryUtils;
import gov.nasa.pds.search.solr.query.bld.InstrumentQueryBuilder;
import gov.nasa.pds.search.solr.query.bld.InvestigationQueryBuilder;
import gov.nasa.pds.search.solr.query.bld.TargetQueryBuilder;
import gov.nasa.pds.search.solr.util.SolrManager;
import gov.nasa.pds.search.util.RequestParameters;


public class ContextQueryRunner
{
    private static final String COLLECTION_INVESTIGATION = "ctx_invest";
    private static final String COLLECTION_INSTRUMENT = "ctx_instrument";
    private static final String COLLECTION_TARGET = "ctx_target";

    
    public static SolrDocumentList runTargetQuery(List<NerToken> tokens, 
            RequestParameters reqParams) throws Exception
    {
        // Build Solr query
        TargetQueryBuilder queryBuilder = new TargetQueryBuilder(tokens);
        SolrQuery query = queryBuilder.build();
        if(query == null) return null;

        // Set "start" and "rows"
        SolrQueryUtils.setPageInfo(query, reqParams);
        // Set field list "fl"
        SolrQueryUtils.setFields(query, reqParams);

        // Call Solr and get results
        SolrClient solrClient = SolrManager.getInstance().getSolrClient();
        QueryResponse resp = solrClient.query(COLLECTION_TARGET, query);
        SolrDocumentList docList = resp.getResults();

        return docList;
    }

    
    public static SolrDocumentList runInstrumentQuery(List<NerToken> tokens, 
            RequestParameters reqParams) throws Exception
    {
        // Build Solr query
        InstrumentQueryBuilder queryBuilder = new InstrumentQueryBuilder(tokens);
        SolrQuery query = queryBuilder.build();
        if(query == null) return null;

        // Set "start" and "rows"
        SolrQueryUtils.setPageInfo(query, reqParams);
        // Set field list "fl"
        SolrQueryUtils.setFields(query, reqParams);

        // Call Solr and get results
        SolrClient solrClient = SolrManager.getInstance().getSolrClient();
        QueryResponse resp = solrClient.query(COLLECTION_INSTRUMENT, query);
        SolrDocumentList docList = resp.getResults();

        return docList;
    }

    
    public static SolrDocumentList runInvestigationQuery(List<NerToken> tokens, 
            RequestParameters reqParams) throws Exception
    {
        // Build Solr query
        InvestigationQueryBuilder queryBuilder = new InvestigationQueryBuilder(tokens);
        SolrQuery query = queryBuilder.build();
        if(query == null) return null;

        // Set "start" and "rows"
        SolrQueryUtils.setPageInfo(query, reqParams);
        // Set field list "fl"
        SolrQueryUtils.setFields(query, reqParams);
        
        // Call Solr and get results
        SolrClient solrClient = SolrManager.getInstance().getSolrClient();
        QueryResponse resp = solrClient.query(COLLECTION_INVESTIGATION, query);
        SolrDocumentList docList = resp.getResults();

        return docList;
    }

    
    public static SolrDocumentList runUnknownQuery(List<NerToken> tokens, 
            RequestParameters reqParams) throws Exception
    {
        return null;
    }

}
