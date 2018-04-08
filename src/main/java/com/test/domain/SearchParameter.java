package com.test.domain;

/**
 * //TODO add comment before commit
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
public class SearchParameter {

    private String subString;
    private ResponseMode mode;
    private String text;

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public ResponseMode getMode() {
        return mode;
    }

    public void setMode(ResponseMode mode) {
        this.mode = mode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
