package com.example;

import com.example.scrap.NepalCrawler;
import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public String totalupdate() {

        JSONObject array = scrapper.getTotalcases();
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {

     return "Welcome to covid-19-data-centre. \n \nVisit Github repo: https://github.com/yudeep-rajbhandari/covid-19-api";
    }
}
