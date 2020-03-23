package com.example.scrap;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class Scrapper {
    Document doc;

    public JSONArray getdocument(){
        try {
            doc = SSLHelper.getConnection("https://www.worldometers.info/coronavirus/").userAgent(USER_AGENT).get();
            Elements table = doc.select("#main_table_countries_today");
            Elements rows = table.select("tr");
            Elements headers = rows.get(0).select("tr").select("th").select("th");
//            ArrayList arrayList = Arrays.asList(new String(rows.get(0).select("tr").select("th").get(0).text()),);
            List header = headers.stream().map(i -> i.text().replaceAll(" ","_")).collect(Collectors.toList());
            List<Elements> list = rows.stream().map(i -> i.select("td").select("td")).collect(Collectors.toList());
            JSONArray array = getJSONArray(list, header);
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray getJSONArray(List<Elements> list, List header) {
        JSONArray array = new JSONArray();
        if (list != null) {
            for (Elements li : list) {
                if (li.size() != 0) {
                    int i = 0;
                    JSONObject obj = new JSONObject();
                    for (Element l : li) {
                        obj.put(header.get(i).toString(), l.text());
                        i = i + 1;
                    }
                    array.put(obj);
                }
            }
        }

        return array;
    }

}
