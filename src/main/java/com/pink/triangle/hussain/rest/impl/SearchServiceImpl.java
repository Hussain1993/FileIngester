package com.pink.triangle.hussain.rest.impl;

import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.elastic.client.ElasticClient;
import com.pink.triangle.hussain.elastic.model.IngestFile;
import com.pink.triangle.hussain.model.SearchQuery;
import com.pink.triangle.hussain.rest.iface.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Hussain on 12/02/2016.
 */
@Path("/search")
public class SearchServiceImpl implements SearchService {
    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);

    private String filesIndex;
    private String documentType;

    public SearchServiceImpl(){
        ApplicationConfig applicationConfig = ConfigManager.getApplicationConfig();

        filesIndex = applicationConfig.getFilesIndexName();
        documentType = applicationConfig.getFilesDocumentType();
    }

    @Path("/basic")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<?> performBasicSearch(SearchQuery basicSearchQuery) {
        String query = "{\n" +
                "    \"query\": {\n" +
                "        \"match_phrase\": {\n" +
                "           \"documentContent\": \""+basicSearchQuery.getSearchQuery()+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        LOG.info("The search query that the user has entered: "+basicSearchQuery.getSearchQuery());
        List<?> files = ElasticClient.getItems(filesIndex,documentType,query,IngestFile.class);
        return files;
    }

    @Path("/wildcard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<?> performWildcardSearch(SearchQuery wildcardSearch) {
        LOG.info("The wildcard search that the user has entered: "+wildcardSearch.getSearchQuery());
        return null;
    }
}
