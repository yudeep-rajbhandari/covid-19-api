package com.example;


import com.example.scrap.ScrapperImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.scrap.NepalCrawler;
import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestController {

    @Autowired
    private ScrapperImpl scrapper;

    @RequestMapping(value = "/ukraine", method = RequestMethod.GET)
    @ResponseBody
    public String ukraine() {
        return scrapper.getUkraineStats();
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    @ResponseBody

    public String countries() {
        return scrapper.getCountriesStats();
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
    @CrossOrigin(origins = "http://localhost:4200")
    public String updatebyCountry(@PathVariable("country") final String country) {

        JSONObject array = scrapper.getdocumentbyCountry(country);
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/update/SAARC", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public String updatebySAARCCountry() {

        JSONArray array = scrapper.getdocumentbySAARC();
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @RequestMapping(value = "/update/currentTotalcases", method = RequestMethod.GET)
    @ResponseBody
    public String world() {

        JSONObject array = scrapper.getWorldStats();
        if (array != null) {
            return array.toString();
        } else {
            return new JSONArray().toString();
        }
    }

    @RequestMapping("/")
    @RequestMapping(value = "/update/Nepal", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public String updateNepal() {

        JSONObject array = null;
        try {
            array = new NepalCrawler().call();
            System.out.println(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

    @GetMapping("/test")
    public String test() {
        return "template1/index";
    }
}
