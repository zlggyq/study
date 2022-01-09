package com.zlgg.study.common.elasticsearch;

import org.elasticsearch.client.RequestOptions;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 22:56
 * Description:
 */
public class EsConstant {

    public static final RequestOptions DEFAULT_OPTIONS = RequestOptions.DEFAULT;

    public static final String ID = "id";

    public static final String INDEX_MAPPING_PROPERTIES = "properties";

    public static final String INDEX_MAPPING_DYNAMIC = "dynamic";

    public static final String INDEX_MAPPING_DYNAMIC_STRICT = "strict";

    public static final String INDEX_COMPANY = "index_company";

    public static final String DATE_FORMAT = "format";

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || yyyy/MM/dd HH:mm:ss|| yyyy/MM/dd ||epoch_millis";
}
