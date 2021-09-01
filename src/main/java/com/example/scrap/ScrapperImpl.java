package com.example.scrap;


import com.example.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class ScrapperImpl implements Scrapper {
    Document doc;
    List header;
    List<Elements> list;
    Hasher hasher = new Hasher();

    public JSONObject getWorldStats() {
        getCountryTable();
        return getLastJSONObject(list, header);
    }

    public String getUkraineStats() {
        ObjectMapper mapper = new ObjectMapper();

        getCountryTable();
        JSONArray jsonArray = getJSONArray(list, header);
        try {
            System.out.println(jsonArray.toString());
            List<Country> countries = mapper.readValue(jsonArray.toString(), new TypeReference<List<Country>>() {
            });
            hasher.saveCountries(countries);
            System.out.println();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        AtomicReference<Country> ukraineStats = new AtomicReference<>();
        hasher.getCountries().forEach(it -> {
            if (it.getCountryName().equalsIgnoreCase("Ukraine")) {
                ukraineStats.set(it.copy());
            }
        });

        try {
            return mapper.writeValueAsString(ukraineStats.get());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public String getCountriesStats() {
        ObjectMapper mapper = new ObjectMapper();
        if (hasher.isCountriesAvailable()) {
            try {
                return mapper.writeValueAsString(hasher.getCountries());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new JSONArray().toString();
            }
        } else {
            getCountryTable();
            JSONArray jsonArray = getJSONArray(list, header);
            try {
                List<Country> countries = mapper.readValue(jsonArray.toString(), new TypeReference<List<Country>>() {
                });
                hasher.saveCountries(countries);
                System.out.println();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return jsonArray.toString();
        }
    }

    public JSONObject getdocumentbyCountry(String country) {
        getCountryTable();
        JSONObject array = getJSONObject(list, header, country);
        return array;
    }

    private void getCountryTable() {
        try {
            doc = SSLHelper.getConnection("https://www.worldometers.info/coronavirus/").userAgent(USER_AGENT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements table = doc.select("#main_table_countries_today");
        Elements rows = table.select("tr");
        Elements headers = rows.get(0).select("tr").select("th").select("th");
        header = headers.stream().map(i -> i.text().replaceAll(" ", "_")).collect(Collectors.toList());
        list = rows.stream().map(i -> i.select("td").select("td")).collect(Collectors.toList());
    }

    private JSONArray getJSONArray(List<Elements> list, List header) {
        JSONArray array = new JSONArray();
        if (list != null) {
            for (Elements li : list) {
                if (li.size() != 0) {
                    int i = 0;
                    JSONObject obj = new JSONObject();
                    for (Element l : li) {
                        String key = header.get(i).toString();
                        String value = l.text();
                        obj.put(key, value);
                        i = i + 1;
                    }
                    if (list.indexOf(li) != list.size() - 1) {
                        array.put(obj);
                    }

                }
            }
        }
        return array;
    }

    private JSONObject getJSONObject(List<Elements> list, List header, String country) {
        JSONObject obj = new JSONObject();
        if (list != null) {
            List<Elements> dataCountry = list.stream().filter(i -> i.size() != 0).filter(i -> i.select("td").get(0).text().replaceAll(" ", "_").equalsIgnoreCase(country)).collect(Collectors.toList());
            for (Elements li : dataCountry) {
                int i = 0;
                for (Element l : li) {
                    obj.put(header.get(i).toString(), l.text());
                    i = i + 1;
                }
            }
        }

        return obj;
    }


    private JSONObject getLastJSONObject(List<Elements> list, List header) {
        JSONObject obj = new JSONObject();

        if (list != null) {
            List<Elements> dataCountry = Collections.singletonList(list.stream().reduce((a, b) -> b).orElse(null));

            for (Elements li : dataCountry) {
                int i = 0;
                for (Element l : li) {
                    obj.put(header.get(i).toString(), l.text());
                    i = i + 1;
                }
            }
        }
        return obj;
    }
}
