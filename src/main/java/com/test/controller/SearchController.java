package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.domain.ResponseMode;
import com.test.domain.SearchParameter;
import com.test.service.IDummyComponentBean;
import com.test.service.IDummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * //TODO add comment before commit
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
@RestController
public class SearchController {

    private ObjectReader objectReader;
    private XmlMapper xmlMapper;

    @Autowired
    private Environment environment;
    @Autowired
    private IDummyComponentBean dummyComponentBean;

    @Autowired
    @Qualifier("FirstDummyService")
    private IDummyService firstDummyService;
    @Autowired
    @Qualifier("SecondDummyService")
    private IDummyService secondDummyService;

    @PostConstruct
    private void init() {
        objectReader = new ObjectMapper().readerFor(SearchParameter.class);
        xmlMapper = new XmlMapper();
    }

    //TODO: add throwing custom exception and exceptionHandler
    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String findSubstringsByPlainText(@RequestBody String incomingText) {
        String subString = environment.getProperty("search.substring");
        ResponseMode responseMode = environment.getProperty("search.mode", ResponseMode.class);
        return responseMode.getSearchFunction().apply(subString, incomingText);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String findSubstringsByJson(@RequestBody String jsonSearchParameter) throws IOException {
        SearchParameter searchParameter = objectReader.readValue(jsonSearchParameter);
        return searchParameter.getMode().getSearchFunction().apply(
                searchParameter.getSubString(), searchParameter.getText());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.TEXT_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String findSubstringsByXml(@RequestBody String xmlSearchParameter) throws IOException {
        SearchParameter searchParameter = xmlMapper.readValue(xmlSearchParameter, SearchParameter.class);
        return searchParameter.getMode().getSearchFunction().apply(
                searchParameter.getSubString(), searchParameter.getText());
    }
}
