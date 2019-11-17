package com.github.alperkurtul.firebaseuserauthentication.utility;

public class StringUtility {

    public String findAndReplaceStringIntoString(String master, String target, String replacement) {

        int startIndex = master.indexOf(target);
        int stopIndex = startIndex + target.length();

        StringBuilder builder = new StringBuilder(master);
        builder.delete(startIndex, stopIndex);
        builder.replace(startIndex, stopIndex, replacement);

        String result = builder.toString();

        return result;
    }
}
