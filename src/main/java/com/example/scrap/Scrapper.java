package com.example.scrap;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class Scrapper {
    Document doc;
    List header;
    List<Elements> list;


    public JSONArray getdocument() {
        getRows();
        JSONArray array = getJSONArray(list, header);
        return array;
    }

    public JSONObject getdocumentbyCountry(String country) {
        getRows();
        JSONObject array = getJSONObject(list, header, country);
        return array;
    }

    public JSONArray getdocumentbySAARC() {
        getRows();
        JSONArray array = getSAARCJSONObject(list, header);
        return array;
    }

    private void getRows() {
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
        List<String> countyList = new ArrayList<>();
//        list.listIterator().forEachRemaining(i->countyList.add(i.));
//        Collections.sort(list);
    }

    private JSONArray getJSONArray(List<Elements> list, List header) {
        JSONArray array = new JSONArray();
        ArrayList<String> lim = new ArrayList();
        if (list != null) {
            for (Elements li : list) {
                if (li.size() != 0) {
                    int i = 0;
                    JSONObject obj = new JSONObject();
                    for (Element l : li) {
                        obj.put(header.get(i).toString(), l.text());
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
     Map<Integer,String> cmap = countryMap();
     String country1 = cmap.get(Integer.valueOf(country));
            List<Elements> dataCountry = list.stream().filter(i -> i.size() != 0).filter(i -> i.select("td").get(1).text().contains(country1)).collect(Collectors.toList());

            for (Elements li : dataCountry) {
                int i = 0;
                for (Element l : li) {
                        obj.put(header.get(i).toString().replaceAll(",",""), l.text());
                        i = i + 1;

                }
            }
        }

        return obj;
    }



    private JSONArray getSAARCJSONObject(List<Elements> list, List header) {
        JSONArray array = new JSONArray();
        List<String> saarcCountry = Arrays.asList("India","Nepal","Pakistan","Bangladesh","Sri_lanka","Bhutan");
        for(String country:saarcCountry) {
            JSONObject obj = new JSONObject();
            if (list != null) {

                List<Elements> dataCountry = list.stream().filter(i -> i.size() != 0).filter(i -> i.select("td").get(0).text().replaceAll(" ", "_").equalsIgnoreCase(country)).collect(Collectors.toList());
                for (Elements li : dataCountry) {
                    int i = 0;
                    for (Element l : li) {
                        obj.put(header.get(i).toString().equalsIgnoreCase("Country,_Other")?"country":header.get(i).toString(), l.text());
                        obj.put(header.get(i).toString().equalsIgnoreCase("Tot_Cases/_1M_pop")?"TotIn1M":header.get(i).toString(), l.text());
                        obj.put(header.get(i).toString().equalsIgnoreCase("Deaths/_1M_pop")?"DIn1M":header.get(i).toString(), l.text());

                        i = i + 1;

                    }
                }
            }
            array.put(obj);
        }

        return array;
    }


    public JSONObject getTotalcases() {
        getRows();
        JSONObject array = getlastJSONObject(list, header);
        return array;
    }

    private JSONObject getlastJSONObject(List<Elements> list, List header) {
        JSONObject obj = new JSONObject();

        if (list != null) {
            List<Elements> dataCountry = Collections.singletonList(list.stream().reduce((a, b) -> b).orElse(null));;

            for (Elements li : dataCountry) {
                int i = 0;
                for (Element l : li) {
                    obj.put(header.get(i).toString(), l.text());
                    i = i + 1;
                }
            }
            System.out.println("abcd");
        }
        return obj;
    }

    private Map<Integer,String> countryMap(){
        Map<Integer,String> cmap = new HashMap<>();
        cmap.put(1, "North America");
        cmap.put(2, "Asia");
        cmap.put(3, "South America");
        cmap.put(4, "Europe");
        cmap.put(5, "Africa");
        cmap.put(6, "Oceania");
        cmap.put(7, "");
        cmap.put(8, "World");
        cmap.put(9, "USA");
        cmap.put(10, "India");
        cmap.put(11, "Brazil");
        cmap.put(12, "UK");
        cmap.put(13, "Russia");
        cmap.put(14, "Turkey");
        cmap.put(15, "France");
        cmap.put(16, "Iran");
        cmap.put(17, "Argentina");
        cmap.put(18, "Spain");
        cmap.put(19, "Colombia");
        cmap.put(20, "Italy");
        cmap.put(21, "Germany");
        cmap.put(22, "Indonesia");
        cmap.put(23, "Mexico");
        cmap.put(24, "Poland");
        cmap.put(25, "South Africa");
        cmap.put(26, "Philippines");
        cmap.put(27, "Ukraine");
        cmap.put(28, "Malaysia");
        cmap.put(29, "Peru");
        cmap.put(30, "Netherlands");
        cmap.put(31, "Iraq");
        cmap.put(32, "Japan");
        cmap.put(33, "Czechia");
        cmap.put(34, "Chile");
        cmap.put(35, "Canada");
        cmap.put(36, "Thailand");
        cmap.put(37, "Bangladesh");
        cmap.put(38, "Israel");
        cmap.put(39, "Pakistan");
        cmap.put(40, "Belgium");
        cmap.put(41, "Romania");
        cmap.put(42, "Sweden");
        cmap.put(43, "Portugal");
        cmap.put(44, "Morocco");
        cmap.put(45, "Serbia");
        cmap.put(46, "Kazakhstan");
        cmap.put(47, "Cuba");
        cmap.put(48, "Switzerland");
        cmap.put(49, "Hungary");
        cmap.put(50, "Jordan");
        cmap.put(51, "Nepal");
        cmap.put(52, "Vietnam");
        cmap.put(53, "Austria");
        cmap.put(54, "UAE");
        cmap.put(55, "Tunisia");
        cmap.put(56, "Greece");
        cmap.put(57, "Lebanon");
        cmap.put(58, "Georgia");
        cmap.put(59, "Guatemala");
        cmap.put(60, "Saudi Arabia");
        cmap.put(61, "Belarus");
        cmap.put(62, "Costa Rica");
        cmap.put(63, "Sri Lanka");
        cmap.put(64, "Ecuador");
        cmap.put(65, "Bolivia");
        cmap.put(66, "Bulgaria");
        cmap.put(67, "Azerbaijan");
        cmap.put(68, "Panama");
        cmap.put(69, "Paraguay");
        cmap.put(70, "Myanmar");
        cmap.put(71, "Kuwait");
        cmap.put(72, "Slovakia");
        cmap.put(73, "Croatia");
        cmap.put(74, "Palestine");
        cmap.put(75, "Uruguay");
        cmap.put(76, "Ireland");
        cmap.put(77, "Venezuela");
        cmap.put(78, "Honduras");
        cmap.put(79, "Denmark");
        cmap.put(80, "Dominican Republic");
        cmap.put(81, "Ethiopia");
        cmap.put(82, "Libya");
        cmap.put(83, "Lithuania");
        cmap.put(84, "S. Korea");
        cmap.put(85, "Oman");
        cmap.put(86, "Egypt");
        cmap.put(87, "Mongolia");
        cmap.put(88, "Moldova");
        cmap.put(89, "Slovenia");
        cmap.put(90, "Bahrain");
        cmap.put(91, "Armenia");
        cmap.put(92, "Kenya");
        cmap.put(93, "Qatar");
        cmap.put(94, "Bosnia and Herzegovina");
        cmap.put(95, "Zambia");
        cmap.put(96, "Nigeria");
        cmap.put(97, "Algeria");
        cmap.put(98, "North Macedonia");
        cmap.put(99, "Norway");
        cmap.put(100, "Kyrgyzstan");
        cmap.put(101, "Botswana");
        cmap.put(102, "Uzbekistan");
        cmap.put(103, "Albania");
        cmap.put(104, "Latvia");
        cmap.put(105, "Afghanistan");
        cmap.put(106, "Estonia");
        cmap.put(107, "Mozambique");
        cmap.put(108, "Finland");
        cmap.put(109, "Montenegro");
        cmap.put(110, "Zimbabwe");
        cmap.put(111, "Namibia");
        cmap.put(112, "Ghana");
        cmap.put(113, "Uganda");
        cmap.put(114, "Cyprus");
        cmap.put(115, "Cambodia");
        cmap.put(116, "El Salvador");
        cmap.put(117, "Australia");
        cmap.put(118, "Rwanda");
        cmap.put(119, "Singapore");
        cmap.put(120, "Cameroon");
        cmap.put(121, "Maldives");
        cmap.put(122, "Jamaica");
        cmap.put(123, "Luxembourg");
        cmap.put(124, "Senegal");
        cmap.put(125, "Malawi");
        cmap.put(126, "Ivory Coast");
        cmap.put(127, "DRC");
        cmap.put(128, "Angola");
        cmap.put(129, "Réunion");
        cmap.put(130, "Guadeloupe");
        cmap.put(131, "Fiji");
        cmap.put(132, "Trinidad and Tobago");
        cmap.put(133, "Eswatini");
        cmap.put(134, "Madagascar");
        cmap.put(135, "Martinique");
        cmap.put(136, "Suriname");
        cmap.put(137, "French Polynesia");
        cmap.put(138, "French Guiana");
        cmap.put(139, "Sudan");
        cmap.put(140, "Cabo Verde");
        cmap.put(141, "Malta");
        cmap.put(142, "Mauritania");
        cmap.put(143, "Syria");
        cmap.put(144, "Guyana");
        cmap.put(145, "Guinea");
        cmap.put(146, "Gabon");
        cmap.put(147, "Togo");
        cmap.put(148, "Benin");
        cmap.put(149, "Laos");
        cmap.put(150, "Haiti");
        cmap.put(151, "Seychelles");
        cmap.put(152, "Bahamas");
        cmap.put(153, "Mayotte");
        cmap.put(154, "Somalia");
        cmap.put(155, "Belize");
        cmap.put(156, "Papua New Guinea");
        cmap.put(157, "Timor-Leste");
        cmap.put(158, "Burundi");
        cmap.put(159, "Tajikistan");
        cmap.put(160, "Curaçao");
        cmap.put(161, "Taiwan");
        cmap.put(162, "Aruba");
        cmap.put(163, "Mauritius");
        cmap.put(164, "Andorra");
        cmap.put(165, "Mali");
        cmap.put(166, "Lesotho");
        cmap.put(167, "Congo");
        cmap.put(168, "Burkina Faso");
        cmap.put(169, "Nicaragua");
        cmap.put(170, "Djibouti");
        cmap.put(171, "Hong Kong");
        cmap.put(172, "South Sudan");
        cmap.put(173, "Equatorial Guinea");
        cmap.put(174, "Iceland");
        cmap.put(175, "Channel Islands");
        cmap.put(176, "CAR");
        cmap.put(177, "Saint Lucia");
        cmap.put(178, "Gambia");
        cmap.put(179, "Yemen");
        cmap.put(180, "Barbados");
        cmap.put(181, "Isle of Man");
        cmap.put(182, "New Caledonia");
        cmap.put(183, "Brunei");
        cmap.put(184, "Eritrea");
        cmap.put(185, "Sierra Leone");
        cmap.put(186, "Guinea-Bissau");
        cmap.put(187, "Niger");
        cmap.put(188, "Liberia");
        cmap.put(189, "Gibraltar");
        cmap.put(190, "San Marino");
        cmap.put(191, "Bermuda");
        cmap.put(192, "Chad");
        cmap.put(193, "Grenada");
        cmap.put(194, "Sint Maarten");
        cmap.put(195, "New Zealand");
        cmap.put(196, "Comoros");
        cmap.put(197, "Saint Martin");
        cmap.put(198, "Liechtenstein");
        cmap.put(199, "Sao Tome and Principe");
        cmap.put(200, "St. Vincent Grenadines");
        cmap.put(201, "Monaco");
        cmap.put(202, "Dominica");
        cmap.put(203, "Antigua and Barbuda");
        cmap.put(204, "Turks and Caicos");
        cmap.put(205, "British Virgin Islands");
        cmap.put(206, "Bhutan");
        cmap.put(207, "Caribbean Netherlands");
        cmap.put(208, "Saint Kitts and Nevis");
        cmap.put(209, "St. Barth");
        cmap.put(210, "Tanzania");
        cmap.put(211, "Faeroe Islands");
        cmap.put(212, "Cayman Islands");
        cmap.put(213, "Diamond Princess");
        cmap.put(214, "Greenland");
        cmap.put(215, "Wallis and Futuna");
        cmap.put(216, "Anguilla");
        cmap.put(217, "Macao");
        cmap.put(218, "Falkland Islands");
        cmap.put(219, "Montserrat");
        cmap.put(220, "Saint Pierre Miquelon");
        cmap.put(221, "Vatican City");
        cmap.put(222, "Solomon Islands");
        cmap.put(223, "Western Sahara");
        cmap.put(224, "MS Zaandam");
        cmap.put(225, "Palau");
        cmap.put(226, "Vanuatu");
        cmap.put(227, "Marshall Islands");
        cmap.put(228, "Samoa");
        cmap.put(229, "Saint Helena");
        cmap.put(230, "Micronesia");
        return cmap;
    }
}
