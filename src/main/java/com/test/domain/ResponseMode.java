package com.test.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.util.StringUtils;

import java.util.function.BiFunction;

/**
 * //TODO add comment before commit
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/08/2018.
 *
 * @author Aliaksandr Kazlou
 */
@JsonRootName(value = "mode")
public enum ResponseMode {

    LINES("lines",
            (subString, text) -> {
                String[] lines = text.split("\\r?\\n");
                StringBuilder stringBuilder = new StringBuilder(lines.length);
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    if (line.contains(subString)) {
                        stringBuilder.append(i).append(": ").append(line).append("\r\n");
                    }
                }
                return stringBuilder.toString();
            }
    ),

    LINES_COUNT("lines count",
            (subString, text) -> {
                String[] lines = text.split("\\r?\\n");
                int linesCount = 0;
                for (String line : lines) {
                    if (line.contains(subString)) {
                        linesCount++;
                    }
                }
                return String.valueOf(linesCount);
            }
    ),

    SUBSTRING_COUNT("substring count",
            (subString, text) -> String.valueOf(
                    StringUtils.countOccurrencesOf(text.replace("\n", "").replace("\r", ""),
                    subString))
    );

    ResponseMode(String name, BiFunction<String, String, String> searchFunction) {
        this.name = name;
        this.searchFunction = searchFunction;
    }

    private String name;
    private BiFunction<String, String, String> searchFunction;

    public BiFunction<String, String, String> getSearchFunction() {
        return searchFunction;
    }

    public String getName() {
        return name;
    }
}
