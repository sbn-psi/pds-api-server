package gov.nasa.pds.search.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.nasa.pds.nlp.lex.PdsLexer;
import gov.nasa.pds.nlp.ner.NamedEntityRecognizer;
import gov.nasa.pds.nlp.ner.NerToken;
import gov.nasa.pds.search.solr.query.run.DocsQueryRunner;


@RestController
@RequestMapping(path = "/api/v1")
public class DocsSearchController
{
    private static final Logger LOG = LoggerFactory.getLogger(DocsSearchController.class);
    
    @Autowired
    private NamedEntityRecognizer ner;

    
    @GetMapping(path = "/search/document")
    public void getName(HttpServletRequest httpReq, HttpServletResponse httpResp) throws Exception
    {
        SearchContext ctx = new SearchContext(httpReq, httpResp);
        if(!ctx.validateAndContinue()) return;
        
        // Call NER
        PdsLexer lexer = new PdsLexer();
        List<String> lexTokens = lexer.parse(ctx.qParam);
        List<NerToken> nerTokens = ner.parse(lexTokens);

        // Run Solr query
        QueryResponse qResp = DocsQueryRunner.runDocsQuery(nerTokens, ctx.reqParams);
        if(!ctx.validateAndContinue(qResp)) return;
        
        // Write documents
        ctx.respWriter.write(qResp);
    }

}
