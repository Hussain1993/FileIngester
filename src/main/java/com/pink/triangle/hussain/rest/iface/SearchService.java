package com.pink.triangle.hussain.rest.iface;

import com.pink.triangle.hussain.model.SearchQuery;

import java.util.List;

/**
 * Created by Hussain on 12/02/2016.
 */
public interface SearchService {

    List<?> performBasicSearch(SearchQuery basicSearchQuery);
    List<?> performWildcardSearch(SearchQuery wildcardSearch);
    String previewDocument(String fileHash);
}
