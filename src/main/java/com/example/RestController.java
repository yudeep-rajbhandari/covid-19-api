package com.example;

import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private Scrapper scrapper;

    @RequestMapping(value = "/arduino", method = RequestMethod.GET)
    @ResponseBody
    public String arduino() {

        JSONArray array = scrapper.getDocument();
        if (array != null) {
            return array.toString();
        } else {
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/ukraine", method = RequestMethod.GET)
    @ResponseBody
    public String ukraine() {

        JSONArray array = scrapper.getDocument();
        if (array != null) {
            return array.toString();
        } else {
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    @ResponseBody
    public String countries() {

        JSONArray array = scrapper.getDocument();
        if (array != null) {
            return array.toString();
        } else {
            return new JSONArray().toString();
        }
    }

//    @RequestMapping(value = "/update/country/{country}", method = RequestMethod.GET)
//    @ResponseBody
//    public String updatebyCountry(@PathVariable("country") final String country) {
//
//        JSONObject array = scrapper.getdocumentbyCountry(country);
//        if (array != null) {
//            return array.toString();
//        } else {
//            return new JSONArray().toString();
//        }
//    }

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    @ResponseBody
    public String world() {

        JSONObject array = scrapper.worldStats();
        if (array != null) {
            return array.toString();
        } else {
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {

        return "Welcome to covid-19-data-centre. \n \nVisit Github repo: https://github.com/yudeep-rajbhandari/covid-19-api";
    }
}
