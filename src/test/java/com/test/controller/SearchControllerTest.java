package com.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link SearchController}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    private static final String LINES_MODE_EXPECTED_RESULT = "0: my ccc asdf mydfs  \n" +
            "1:  myasdf";
    private static final String LINES_MODE_BODY = "{\n" +
            "  \"subString\":\"my\",\n" +
            "  \"mode\":\"LINES\",\n" +
            "  \"text\":\"my ccc asdf mydfs  \\r\\n myasdf\"\n" +
            "}";
    private static final String SUBSTRING_COUNT_MODE_BODY = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<searchparameter>\n" +
            "\t<subString>my</subString>\n" +
            "\t<mode>SUBSTRING_COUNT</mode>\n" +
            "\t<text>my ccc asdf mydfs  \n" +
            "\tmyasdf</text>\n" +
            "</searchparameter>";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchController searchController;

    @Test
    public void testFindSubstringsByPlainText() throws Exception {
        given(this.searchController.findSubstringsByPlainText("my ccc asdf mydfs"))
                .willReturn("3");
        this.mvc.perform(post("/search").content("my ccc asdf mydfs").contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk()).andExpect(content().string("3"));
    }

    @Test
    public void testFindSubstringsByJson() throws Exception {
        given(this.searchController.findSubstringsByJson(LINES_MODE_BODY))
                .willReturn(LINES_MODE_EXPECTED_RESULT);
        this.mvc.perform(post("/search").content(LINES_MODE_BODY).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(content().string(LINES_MODE_EXPECTED_RESULT));
    }

    @Test
    public void testFindSubstringsByXml() throws Exception {
        given(this.searchController.findSubstringsByXml(SUBSTRING_COUNT_MODE_BODY))
                .willReturn("3");
        this.mvc.perform(post("/search").content(SUBSTRING_COUNT_MODE_BODY).contentType(MediaType.TEXT_XML_VALUE))
                .andExpect(status().isOk()).andExpect(content().string("3"));
    }

}
