package com.example;

import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {

    @Autowired
    private Scrapper scrapper;

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

    @RequestMapping(value = "/")
    public String welcome() {

        return "index";
    }
    @RequestMapping(value = "/test")
    public String test() {

        return "index";
    }
}
