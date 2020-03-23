package com.example;

import com.example.scrap.Scrapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private Scrapper scrapper;

    @RequestMapping(value = "/allUpdate", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createMessage() {

        JSONArray array = scrapper.getdocument();
        if(array != null){
            return  array.toString();
        }
        else{
            return new JSONArray().toString();
        }
    }
}
