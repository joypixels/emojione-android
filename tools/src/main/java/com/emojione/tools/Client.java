package com.emojione.tools;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public boolean $ascii = false;          // convert ascii smileys?
    public boolean riskyMatchAscii = false; // set true to match ascii without leading/trailing space char
    public boolean shortcodes = true;       // convert shortcodes?
    public boolean unicodeAlt = true;       // use the unicode char as the alt attribute (makes copy and pasting the resulting text better)
    public String emojiVersion = "3.1";
    public String emojiSize = "32";         //available sizes are '32', '64', and '128'
    public boolean greedyMatch = false;
    public String blacklistChars = "";
    public boolean sprites = false;
    public String spriteSize = "32";        // available sizes are '32' and '64'
    public String imagePathPNG = "https://cdn.jsdelivr.net/emojione/assets";
    public boolean imageTitleTag = true;
    public boolean unicode_replaceWith = false;
    public String ignoredRegexp = "<object[^>]*>.*?<\\/object>|<span[^>]*>.*?<\\/span>|<(?:object|embed|svg|img|div|span|p|a)[^>]*>";
    public String unicodeRegexp = "(?:\\x{1F3F3}\\x{FE0F}?\\x{200D}?\\x{1F308}|\\x{1F441}\\x{FE0F}?\\x{200D}?\\x{1F5E8}\\x{FE0F}?)|[\\x{0023}-\\x{0039}]\\x{FE0F}?\\x{20e3}|(?:\\x{1F3F4}[\\x{E0060}-\\x{E00FF}]{1,6})|[\\x{1F1E0}-\\x{1F1FF}]{2}|(?:[\\x{1F468}\\x{1F469}])\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?(?:[\\x{2695}\\x{2696}\\x{2708}\\x{1F4BB}\\x{1F4BC}\\x{1F527}\\x{1F52C}\\x{1F680}\\x{1F692}\\x{1F33E}-\\x{1F3ED}])|[\\x{1F468}-\\x{1F469}\\x{1F9D0}-\\x{1F9DF}][\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}\\x{2695}\\x{2696}\\x{2708}]?\\x{FE0F}?|(?:[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}][\\x{200D}\\x{FE0F}]{0,2})|[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}]|(?:[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}]\\x{FE0F}?)|(?:[\\x{1f46e}\\x{1F468}\\x{1F469}\\x{1f575}\\x{1f471}-\\x{1f487}\\x{1F645}-\\x{1F64E}\\x{1F926}\\x{1F937}]|[\\x{1F460}-\\x{1F482}\\x{1F3C3}-\\x{1F3CC}\\x{26F9}\\x{1F486}\\x{1F487}\\x{1F6A3}-\\x{1F6B6}\\x{1F938}-\\x{1F93E}]|\\x{1F46F})\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}]?\\x{FE0F}?|(?:[\\x{26F9}\\x{261D}\\x{270A}-\\x{270D}\\x{1F385}-\\x{1F3CC}\\x{1F442}-\\x{1F4AA}\\x{1F574}-\\x{1F596}\\x{1F645}-\\x{1F64F}\\x{1F6A3}-\\x{1F6CC}\\x{1F918}-\\x{1F93E}]\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}])|(?:[\\x{2194}-\\x{2199}\\x{21a9}-\\x{21aa}]\\x{FE0F}?|[\\x{0023}-\\x{002a}]|[\\x{3030}\\x{303d}]\\x{FE0F}?|(?:[\\x{1F170}-\\x{1F171}]|[\\x{1F17E}-\\x{1F17F}]|\\x{1F18E}|[\\x{1F191}-\\x{1F19A}]|[\\x{1F1E6}-\\x{1F1FF}])\\x{FE0F}?|\\x{24c2}\\x{FE0F}?|[\\x{3297}\\x{3299}]\\x{FE0F}?|(?:[\\x{1F201}-\\x{1F202}]|\\x{1F21A}|\\x{1F22F}|[\\x{1F232}-\\x{1F23A}]|[\\x{1F250}-\\x{1F251}])\\x{FE0F}?|[\\x{203c}\\x{2049}]\\x{FE0F}?|[\\x{25aa}-\\x{25ab}\\x{25b6}\\x{25c0}\\x{25fb}-\\x{25fe}]\\x{FE0F}?|[\\x{00a9}\\x{00ae}]\\x{FE0F}?|[\\x{2122}\\x{2139}]\\x{FE0F}?|\\x{1F004}\\x{FE0F}?|[\\x{2b05}-\\x{2b07}\\x{2b1b}-\\x{2b1c}\\x{2b50}\\x{2b55}]\\x{FE0F}?|[\\x{231a}-\\x{231b}\\x{2328}\\x{23cf}\\x{23e9}-\\x{23f3}\\x{23f8}-\\x{23fa}]\\x{FE0F}?|\\x{1F0CF}|[\\x{2934}\\x{2935}]\\x{FE0F}?)|[\\x{2700}-\\x{27bf}]\\x{FE0F}?|[\\x{1F000}-\\x{1F6FF}\\x{1F900}-\\x{1F9FF}]\\x{FE0F}?|[\\x{2600}-\\x{26ff}]\\x{FE0F}?|[\\x{0030}-\\x{0039}]\\x{FE0F}";
    public String shortcodeRegexp = ":([-+\\w]+):";
    public int startTime = 0;
    public int endTime = 0;
    protected RulesetInterface ruleset = null;

    public void contruct() {
        this.imagePathPNG = this.imagePathPNG + "/" + this.emojiVersion + "/png/" + this.emojiSize + "/";
        this.spriteSize = (this.spriteSize == "32" || this.spriteSize == "64") ? this.spriteSize : "32";
    }

    public void contruct(RulesetInterface ruleset) {
        this.ruleset = ruleset;
        this.imagePathPNG = this.imagePathPNG + "/" + this.emojiVersion + "/png/" + this.emojiSize + "/";
        this.spriteSize = (this.spriteSize == "32" || this.spriteSize == "64") ? this.spriteSize : "32";
    }

    /**
     * First pass changes unicode characters into emoji markup.
     * Second pass changes any shortnames into emoji markup.
     *
     * @param   string  $string The input string.
     * @return  string  String with appropriate html for rendering emoji.
     */
    public static String toImage(String string) {
        string = unicodeToImage(string);
        string = shortnameToImage(string);
        return string;
    }

    /**
     * Uses toShort to transform all unicode into a standard shortname
     * then transforms the shortname into unicode.
     * This is done for standardization when converting several unicode types.
     *
     * @param   string  $string The input string.
     * @return  string  String with standardized unicode.
     */
    public static String unifyUnicode(String string) {
        string = toShort(string);
        string = shortnameToUnicode(string);
        return string;
    }

    /**
     * This will output unicode from shortname input.
     * If Client/$ascii is true it will also output unicode from ascii.
     * This is useful for sending emojis back to mobile devices.
     *
     * @param   string  $string The input string.
     * @return  string  String with unicode replacements.
     */
    public String shortnameToUnicode(String string) {
        if(this.shortcodes) {
            Pattern pattern = Pattern.compile("/"+this.ignoredRegexp+"|("+this.shortcodeRegexp+")/Si");
            Matcher matches = pattern.matcher(string);
            string = shortnameToUnicodeCallback(matches);
        }
        if(this.$ascii) {
            RulesetInterface ruleset = getRuleset();
            String asciiRegexp = ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "|(()"+asciiRegexp+"())" : "|((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile("/"+this.ignoredRegexp+asciiRX+"/S");
            Matcher matches = pattern.matcher(string);
            string = asciiToUnicodeCallback(matches);
        }
        return string;
    }

    /**
     * This will return the shortname from unicode input.
     *
     * @param   string  $string The input string.
     * @return  string  shortname
     */
    public static String toShort(String string) {
        return "";
    }

    /**
     * This will output image markup from shortname input.
     *
     * @param   string  $string The input string.
     * @return  string  String with appropriate html for rendering emoji.
     */
    public static String shortnameToImage(String string) {
        return "";
    }

    /**
     * This will output image markup from shortname input.
     *
     * @param   string  $string The input string.
     * @return  string  String with appropriate html for rendering emoji.
     */
    public static String unicodeToImage(String string) {
        return "";
    }

    /**
     * This will replace shortnames with their ascii equivalent.
     * ex. :wink: --> ;^)
     * This is useful for systems that don't support unicode or images.
     *
     * @param   string  $string The input string.
     * @return  string  String with ascii replacements.
     */
    public static String shortnameToAscii(String string) {
        return "";
    }

    public String shortnameToUnicodeCallback(Matcher matcher) {
        List<String> matches = new ArrayList<String>();
        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            matches.add(matcher.group());
        }
        matcher.reset();
        if(matchCount==0) {

        }
        return "";

    }

    /**
     * @param   array   $m  Results of preg_replace_callback().
     * @return  string  Unicode replacement result.
     */
    public String asciiToUnicodeCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()!=4) {
            return "";
        } else {
            RulesetInterface ruleset = this.getRuleset();
            LinkedHashMap<String, String> ascii_replace = ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = ruleset.getShortcodeReplace();
            String ascii = matchList.get(3);

            String shortname = ascii_replace.get(ascii);
            String uc_output = shortcode_replace.get(shortname).get(0);
            return matchList.get(2) + this.convert(uc_output);
        }
    }

    /**
     * @param   array   $m  Results of preg_replace_callback().
     * @return  string  Shortname replacement result.
     */
    public String asciiToShortnameCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()!=4) {
            return "";
        } else {
            RulesetInterface ruleset = this.getRuleset();
            LinkedHashMap<String, String> ascii_replace = ruleset.getAsciiReplace();

            LinkedHashMap<String, ArrayList<String>> shortcode_replace = ruleset.getShortcodeReplace();
            LinkedHashMap<ArrayList<String>, String> flip_reversed_shortcode_replace = new LinkedHashMap<ArrayList<String>, String>();
            Set<String> keys = shortcode_replace.keySet();
            List<String> keyList = new ArrayList<String>(keys);
            for (int i = keyList.size() - 1; i >= 0; i--) {
                flip_reversed_shortcode_replace.put(shortcode_replace.get(keyList.get(i)), keyList.get(i));
            }
            String shortname = matchList.get(3);

            if(!ascii_replace.containsKey(shortname)){
                return matchList.get(3);
            } else {
                String unicode = ascii_replace.get(shortname);
                return matchList.get(2) + flip_reversed_shortcode_replace.get(unicode);
            }
        }
    }

    /**
     * @param   array   $m  Results of preg_replace_callback().
     * @return  string  Image HTML replacement result.
     */
    public String asciiToImageCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()!=4) {
            return "";
        } else {
            RulesetInterface ruleset = this.getRuleset();
            LinkedHashMap<String, String> ascii_replace = ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = ruleset.getShortcodeReplace();

            String ascii = URLEncoder.encode(matchList.get(3));
            if(!ascii_replace.containsKey(ascii)){
                return matchList.get(3);
            } else {
                String shortname = ascii_replace.get(ascii);
                String filename = shortcode_replace.get(shortname).get(2);
                String uc_output = shortcode_replace.get(shortname).get(0);
                String category = filename.contains("-1f3f") ? "diversity" : shortcode_replace.get(shortname).get(3);
                String titleTag = this.imageTitleTag ? "title=\"" + URLEncoder.encode(shortname) + "\"" : "";

                String alt;
                if(this.unicodeAlt) {
                    alt = this.convert(uc_output);
                } else {
                    alt = URLEncoder.encode(ascii);
                }
                if (this.sprites)
                {
                    return matchList.get(2) + "<span class=\"emojione emojione-" + this.spriteSize + "-" + category + " _" + filename + "\" " + titleTag + ">" + alt + "</span>";
                } else
                {
                    return matchList.get(2) + "<img class=\"emojione\" alt=\"" + alt + "\" " + titleTag + " src=\"" + this.imagePathPNG + filename + ".png\"/>";
                }
            }
        }
    }

    /**
     * @param   array   $m  Results of preg_replace_callback().
     * @return  string  shortname result
     */
    public String toShortCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()==0) {
            return "";
        } else {
            RulesetInterface ruleset = this.getRuleset();
            LinkedHashMap<String, String> unicode_replace = ruleset.getUnicodeReplace();

            String unicode = matchList.get(0);

            if(!)unicode_replace.containsKey(unicode)) {
                return matchList.get(0);
            }

            return unicode_replace.get(unicode);
        }
    }

    /**
     * @param   array   $m  Results of preg_replace_callback().
     * @return  string  Image HTML replacement result.
     */
    public String unicodeToImageCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()==0) {
            return "";
        } else {
            RulesetInterface ruleset = this.getRuleset();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = ruleset.getShortcodeReplace();
            LinkedHashMap<String, String> unicode_replace = ruleset.getUnicodeReplace();
            LinkedHashMap<String, String> unicode_replace_greedy = ruleset.getUnicodeReplaceGreedy();

            String unicode = matchList.get(0).toUpperCase();

            
        }
        if ((!is_array($m)) || (!isset($m[0])) || (empty($m[0])))
        {
            return $m[0];
        }
        else
        {
            $ruleset = $this->getRuleset();
            $shortcode_replace = $ruleset->getShortcodeReplace();
            $unicode_replace = $ruleset->getUnicodeReplace();
            $unicode_replace_greedy = $ruleset->getUnicodeReplaceGreedy();

            $unicode = strtoupper($m[0]);

            $bList = explode(',', $this->blacklistChars);

            if ( array_key_exists($unicode, $unicode_replace) && !in_array($unicode, $bList) )
            {
                $shortname = $unicode_replace[$unicode];
            }
            else if ( $this->greedyMatch && array_key_exists($unicode, $unicode_replace_greedy) && !in_array($unicode, $bList) )
            {
                $shortname = $unicode_replace_greedy[$unicode];
            }
            else
            {
                return $unicode;
            }

            $filename = $shortcode_replace[$shortname][2];
            $category = (strpos($filename, '-1f3f') !== false) ? 'diversity' : $shortcode_replace[$shortname][3];
            $titleTag = $this->imageTitleTag ? 'title="'.htmlspecialchars($shortname).'"' : '';

            if ($this->unicodeAlt)
            {
                $alt = $unicode;
            }
            else
            {
                $alt = $shortname;
            }

            if ($this->sprites)
            {
                return '<span class="emojione emojione-'.$this->spriteSize.'-'.$category.' _'.$filename.'" '.$titleTag.'>'.$alt.'</span>';
            }
            else
            {
                return '<img class="emojione" alt="'.$alt.'" '.$titleTag.' src="'.$this->imagePathPNG.$filename.'.png"/>';
            }
        }
    }

    /**
     * Converts from unicode to hexadecimal NCR.
     *
     * @param   string  $unicode unicode character/s
     * @return  string  hexadecimal NCR
     * */
    public String convert(String unicode)
    {
        String[] items = unicode.split("-");
        if(items.length>0) {
            return "&#x"+implode(items,"&#x")+";";
        } else {
            return "&#x"+unicode+";";
        }
    }

    /**
     * Join array elements with a string
     *
     * @param items
     * @param separator
     */
    public String implode(String[] items, String separator) {
        if (items == null || items.length==0) {
            return null;
        }
        String delimiter = "";
        StringBuilder builder = new StringBuilder();
        for (String item : items) {
            builder.append(delimiter).append(item);
            delimiter = separator;
        }
        return builder.toString();
    }

    /**
     * Get the Ruleset
     *
     * @return RulesetInterface The Ruleset
     */
    public RulesetInterface getRuleset() {
        if ( this.ruleset == null ) {
            this.ruleset = new Ruleset();
        }
        return this.ruleset;
    }
}
