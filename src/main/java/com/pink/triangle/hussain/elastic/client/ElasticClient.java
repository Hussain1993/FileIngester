package com.pink.triangle.hussain.elastic.client;

import com.google.gson.JsonObject;
import com.pink.triangle.hussain.config.ApplicationConfig;
import com.pink.triangle.hussain.config.ConfigManager;
import com.pink.triangle.hussain.util.PutResult;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Hussain on 03/02/2016.
 */
public class ElasticClient {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticClient.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static JestHttpClient instance = null;

    private static ApplicationConfig applicationConfig;

    public static final int MAX_RESULT_COUNT = 100000;
    public static final int DEFAULT_UNLIMITED_RESULT_COUNT = 0;

    private ElasticClient(){}

    private static void init(){
        applicationConfig = ConfigManager.getApplicationConfig();
    }

    private static void setupJest(){
        String protocol = applicationConfig.getElasticProtocol();
        String hostName = applicationConfig.getElasticHostname();
        String portNumberString = applicationConfig.getElasticPort();

        final String url = protocol + hostName + ":" + portNumberString;

        final String httpReadTimeout = applicationConfig.getHttpReadTimeout();
        final String httpConnectionTimeout = applicationConfig.getHttpConnectionTimeout();
        final String httpMaxTotalConnection = applicationConfig.getHttpMaxTotalConnections();
        final String httpMaxTotalConnectionsPerRoute = applicationConfig.getHttpMaxTotalConnectionPerRoute();

        final HttpClientConfig.Builder httpClientConfig = new HttpClientConfig.Builder(url).multiThreaded(true);

        if(!StringUtils.isBlank(httpReadTimeout))
        {
            httpClientConfig.readTimeout(Integer.parseInt(httpReadTimeout));
        }
        else
        {
            LOG.info("No value for the property http read timeout found, using the default value");
        }
        if(!StringUtils.isBlank(httpConnectionTimeout))
        {
            httpClientConfig.connTimeout(Integer.parseInt(httpConnectionTimeout));
        }
        else
        {
            LOG.info("No value for the property http connection timeout found, using the default value");
        }
        if(!StringUtils.isBlank(httpMaxTotalConnection))
        {
            httpClientConfig.maxTotalConnection(Integer.parseInt(httpMaxTotalConnection));
        }
        else
        {
            LOG.info("No value for the property http max total connections found, using the default value");
        }
        if(!StringUtils.isBlank(httpMaxTotalConnectionsPerRoute))
        {
            httpClientConfig.defaultMaxTotalConnectionPerRoute(Integer.parseInt(httpMaxTotalConnectionsPerRoute));
        }
        else
        {
            LOG.info("No value for the property http max total connections per route found, using default value");
        }

        final HttpClientConfig clientConfig = httpClientConfig.build();
        final JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        instance = (JestHttpClient) factory.getObject();
    }

    public static JestClient getInstance(){
        if(instance ==  null)
        {
            init();
            setupJest();
        }
        return instance;
    }

    public static PutResult saveItem(String indexName, String objectType, Object item){
        Index.Builder builder = new Index.Builder(item).index(indexName).type(objectType);
        JestResult jestResult = null;
        try{
            jestResult = ElasticClient.getInstance().execute(builder.build());
        }
        catch(IOException ioException){
            LOG.error("There was an error when trying to save the item",ioException);
        }

        _checkErrors(jestResult);
        return _mapToPutResult(jestResult);
    }

    public static Object getItem(String indexName, String objectType, String id, Class<?> returnType){

        Get.Builder builder = new Get.Builder(indexName,id).type(objectType);

        JestResult jestResult = null;

        try {
            jestResult = ElasticClient.getInstance().execute(builder.build());
        }
        catch(IOException ioException){
            LOG.error("There was an error when getting the item", ioException);
        }

        _checkErrors(jestResult);

        Object result = jestResult.getSourceAsObject(returnType);

        return result;
    }

    public static JestResult doJestQuery(Search.Builder builder){
        JestResult jestResult = null;
        try{
            jestResult = ElasticClient.getInstance().execute(builder.build());
        }
        catch(IOException ioException){
            LOG.error("There was an error when executing the query");
        }

        _checkErrors(jestResult);

        return jestResult;
    }

    public static boolean doesUserExits(String index, String objectType, String[] fieldNames, String[] fieldValues){
        String query = "{\n" +
                "    \"query\": {\n" +
                "        \"bool\": {\n" +
                "            \"must\": [\n" +
                "               {\"match_phrase\": {\n" +
                "                  \""+fieldNames[0]+"\": \""+fieldValues[0]+"\"\n" +
                "               }},\n" +
                "               {\"match_phrase\": {\n" +
                "                  \""+fieldNames[1]+"\": \""+fieldValues[1]+"\"\n" +
                "               }}\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search.Builder builder = new Search.Builder(query).addIndex(index).addType(objectType).addCleanApiParameter("exists");
        JestResult jestResult = null;
        try{
            jestResult = ElasticClient.getInstance().execute(builder.build());
        }
        catch(IOException ioException){
            LOG.error("There was an error",ioException);
        }
        if(jestResult != null)
        {
            return jestResult.getJsonString().contains("\"exists\":true");
        }
        else
        {
            return false;
        }
    }

    private static PutResult _mapToPutResult(JestResult jestResult){
        JsonObject jsonObject = jestResult.getJsonObject();
        PutResult putResult = new PutResult();
        putResult.set_id(jsonObject.get("_id").getAsString());
        putResult.set_type(jsonObject.get("_type").getAsString());
        putResult.set_version(jsonObject.get("_version").getAsString());
        putResult.set_index(jsonObject.get("_index").getAsString());
        putResult.setCreated(jsonObject.get("created").getAsString());
        return putResult;
    }

    private static void _checkErrors(JestResult jestResult){
        _checkShardFailures(jestResult.getJsonString());

        if(!jestResult.isSucceeded())
        {
            if(jestResult.getErrorMessage() != null && jestResult.getErrorMessage().contains("QueryParsingException"))
            {
                LOG.error("One or more unexpected operators or values were specified");
            }
            else
            {
                LOG.error("An error occurred in ElasticSearch: "+jestResult.getJsonString());
            }
        }
        else
        {
            if(jestResult.getJsonString().contains("\"errors\":true"))
            {
                LOG.error("An error occurred in ElasticSearch: "+jestResult.getJsonString());
            }
        }
    }

    private static void _checkShardFailures(String jsonString){
        final JsonFactory jsonFactory = objectMapper.getJsonFactory();
        JsonNode rootNode = null;
        JsonParser jsonParser;

        try{
            jsonParser = jsonFactory.createJsonParser(jsonString);
            rootNode = objectMapper.readTree(jsonParser);
        }
        catch(JsonParseException jpException){
            LOG.error("There was an error when trying to parse the JSON String",jpException);
        }
        catch(IOException ioException){
            LOG.error("There was an error when trying to parse the JSON String",ioException);
        }
        JsonNode shardStatus = rootNode.get("_shards");
        if(shardStatus != null)
        {
            JsonNode failedNode = shardStatus.get("failed");
            if(failedNode != null)
            {
                if(failedNode.asLong() > 0)
                {
                    LOG.error(shardStatus.toString());
                }
            }
        }
    }

}
