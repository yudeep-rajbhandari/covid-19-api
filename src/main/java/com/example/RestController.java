package com.example;

import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private Scrapper scrapper;

    @RequestMapping(value = "/allUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String allcountryUpdate() {

        JSONArray array = scrapper.getdocument();
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/update/country/{country}", method = RequestMethod.GET)
    @ResponseBody
    public String updatebyCountry(@PathVariable("country") final String country) {

        JSONObject array = scrapper.getdocumentbyCountry(country);
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/update/currentTotalcases", method = RequestMethod.GET)
    @ResponseBody
    public String totalupdate() {

        JSONObject array = scrapper.getTotalcases();
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {

     return "Welcome to covid-19-data-centre";
    }
}
