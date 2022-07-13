package com.kraken.interview;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Utilities
{

    @SneakyThrows
    public static JSONObject stringToJsonObject (String jsonString)
    {
        JSONObject jsonObj = new JSONObject (jsonString);
        return jsonObj;
    }

    public static JSONArray stringToJsonArray (String jsonString)
    {
        JSONArray jsonArray = new JSONArray (jsonString);
        return jsonArray;
    }

    public static List<String> filterList (List<String> list)
    {
        return list.stream ().filter (s -> s.matches ("\\[.+\\]")).collect (Collectors.toList ());
    }

    @SneakyThrows
    public static String getJson (String fileName) {
        return new String(Files.readAllBytes (Paths.get(Utilities.class.getResource(fileName).toURI())));
    }

}
