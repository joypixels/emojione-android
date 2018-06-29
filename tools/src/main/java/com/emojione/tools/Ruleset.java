package com.emojione.tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ruleset implements RulesetInterface {

    @Override
    public LinkedHashMap<String, ArrayList<String>> getShortcodeReplace() {
        return new LinkedHashMap<String, ArrayList<String>>();
    }

    @Override
    public LinkedHashMap<String, String> getAsciiReplace() {
        return new LinkedHashMap<String, String>();
    }

    @Override
    public LinkedHashMap<String, String> getUnicodeReplace() {
        return new LinkedHashMap<String, String>();
    }

    @Override
    public LinkedHashMap<String, String> getUnicodeReplaceGreedy() {
        return new LinkedHashMap<String, String>();
    }

    @Override
    public String getAsciiRegexp() {
        return null;
    }
}
