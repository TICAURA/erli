package com.erli.argylewh.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JSONPathUtil {
    Logger logger = LoggerFactory.getLogger(JSONPathUtil.class);

    public static Map<String,String> evaluateJSONPath(String jsonString, List<String> jsonPaths){
        Map<String,String> jsonPathValues = new HashMap<String,String>();

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
        for (String jsonPath : jsonPaths) {
            try {
                Object oResultEvaluation = JsonPath.read(document, jsonPath);
                //logger.info("key path : " + jsonPath);
                //logger.info("val path : " + oResultEvaluation);
                if (oResultEvaluation != null) jsonPathValues.put(jsonPath, oResultEvaluation.toString());
            }
            catch (Exception jpe){
                System.out.println("Parse Error JSON Path: " + jsonPath + " on JSON " + jsonString);
                jsonPathValues.put(jsonPath, "");
            }
        }
        return jsonPathValues;
    }

    public static Map<String, String> getAllPathWithValues(String jsonString){
        Map jsonPaths = new HashMap<String,String>();

        List<String> paths = JsonPath
                .using(Configuration.builder().options(Option.AS_PATH_LIST).build())
                .parse(jsonString)
                .read("$..*",List.class);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        //System.out.println("paths:" + paths);
        for (String jpath : paths){
            //System.out.println("jpath" + jpath);
            Object node = JsonPath.read(document,jpath);
            //System.out.println("node.getClass()" + node.getClass());
            if (node != null){

                if (node instanceof java.util.Map) { //String jPathValue = evaluateJSONPath(jsonString,jpath)
                    logger.info("node" + node);
                    //jsonPaths.put(new String(jpath), new String(node.toString()));
                    jsonPaths.putAll((Map) node);
                }
                else{
                    jsonPaths.put(new String(jpath), new String(node.toString()));
                }
            }

        }
        //System.out.println("jsonPaths:" + jsonPaths);
        return jsonPaths;
    }
}
