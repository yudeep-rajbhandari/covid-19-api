package com.example.scrap;

import org.json.JSONArray;
import org.json.JSONObject;

public interface Scrapper {
    JSONObject getWorldStats();
    String getUkraineStats();
    String getCountriesStats();
}
