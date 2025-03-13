package com.example.LogReader.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTrimmer {

    public static String extractJson(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        Pattern pattern = Pattern.compile("\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\}");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null; // Or handle the case where no JSON is found
        }
    }

    public static void main(String[] args) {
        String input = "Some extra text before {\n" +
                "  \"exception\": \"java.lang.CloneNotSupportedException\",\n" +
                "  \"rootCause\": \"The `CloneNotSupportedException` suggests that an attempt was made to clone an object of a class that does not implement the `Cloneable` interface or doesn't properly override the `clone()` method.\",\n" +
                "  \"solution\": \"Ensure the class being cloned implements the `Cloneable` interface and provides a proper implementation of the `clone()` method. Alternatively, consider using a copy constructor or factory method for creating copies of objects.\"\n" +
                "}\n" +
                "Some extra text after";

        String extractedJson = extractJson(input);

        if (extractedJson != null) {
            System.out.println(extractedJson);
        } else {
            System.out.println("No JSON found in the input.");
        }
    }
}