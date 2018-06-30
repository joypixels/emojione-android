package com.emojione.tools;

import android.util.Log;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    private boolean ascii = true;               // convert ascii smileys?
    private boolean riskyMatchAscii = false;     // set true to match ascii without leading/trailing space char
    private boolean shortcodes = true;          // convert shortcodes?
    private boolean unicodeAlt = true;          // use the unicode char as the alt attribute (makes copy and pasting the resulting text better)
    private String emojiVersion = "3.1";
    private String emojiSize = "32";            //available sizes are '32', '64', and '128'
    private boolean greedyMatch = false;
    private String blacklistChars = "";
    private boolean sprites = false;
    private String spriteSize = "32";           // available sizes are '32' and '64'
    private String imagePathPNG = "https://cdn.jsdelivr.net/emojione/assets";
    private boolean imageTitleTag = true;

    private String unicodeRegexp = "(?:\\x{1F3F3}\\x{FE0F}?\\x{200D}?\\x{1F308}|\\x{1F441}\\x{FE0F}?\\x{200D}?\\x{1F5E8}\\x{FE0F}?)|[\\x{0023}-\\x{0039}]\\x{FE0F}?\\x{20e3}|(?:\\x{1F3F4}[\\x{E0060}-\\x{E00FF}]{1,6})|[\\x{1F1E0}-\\x{1F1FF}]{2}|(?:[\\x{1F468}\\x{1F469}])\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?(?:[\\x{2695}\\x{2696}\\x{2708}\\x{1F4BB}\\x{1F4BC}\\x{1F527}\\x{1F52C}\\x{1F680}\\x{1F692}\\x{1F33E}-\\x{1F3ED}])|[\\x{1F468}-\\x{1F469}\\x{1F9D0}-\\x{1F9DF}][\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}\\x{2695}\\x{2696}\\x{2708}]?\\x{FE0F}?|(?:[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}][\\x{200D}\\x{FE0F}]{0,2})|[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}]|(?:[\\x{2764}\\x{1F466}-\\x{1F469}\\x{1F48B}]\\x{FE0F}?)|(?:[\\x{1f46e}\\x{1F468}\\x{1F469}\\x{1f575}\\x{1f471}-\\x{1f487}\\x{1F645}-\\x{1F64E}\\x{1F926}\\x{1F937}]|[\\x{1F460}-\\x{1F482}\\x{1F3C3}-\\x{1F3CC}\\x{26F9}\\x{1F486}\\x{1F487}\\x{1F6A3}-\\x{1F6B6}\\x{1F938}-\\x{1F93E}]|\\x{1F46F})\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}]?\\x{FE0F}?|(?:[\\x{26F9}\\x{261D}\\x{270A}-\\x{270D}\\x{1F385}-\\x{1F3CC}\\x{1F442}-\\x{1F4AA}\\x{1F574}-\\x{1F596}\\x{1F645}-\\x{1F64F}\\x{1F6A3}-\\x{1F6CC}\\x{1F918}-\\x{1F93E}]\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}])|(?:[\\x{2194}-\\x{2199}\\x{21a9}-\\x{21aa}]\\x{FE0F}?|[\\x{0023}-\\x{002a}]|[\\x{3030}\\x{303d}]\\x{FE0F}?|(?:[\\x{1F170}-\\x{1F171}]|[\\x{1F17E}-\\x{1F17F}]|\\x{1F18E}|[\\x{1F191}-\\x{1F19A}]|[\\x{1F1E6}-\\x{1F1FF}])\\x{FE0F}?|\\x{24c2}\\x{FE0F}?|[\\x{3297}\\x{3299}]\\x{FE0F}?|(?:[\\x{1F201}-\\x{1F202}]|\\x{1F21A}|\\x{1F22F}|[\\x{1F232}-\\x{1F23A}]|[\\x{1F250}-\\x{1F251}])\\x{FE0F}?|[\\x{203c}\\x{2049}]\\x{FE0F}?|[\\x{25aa}-\\x{25ab}\\x{25b6}\\x{25c0}\\x{25fb}-\\x{25fe}]\\x{FE0F}?|[\\x{00a9}\\x{00ae}]\\x{FE0F}?|[\\x{2122}\\x{2139}]\\x{FE0F}?|\\x{1F004}\\x{FE0F}?|[\\x{2b05}-\\x{2b07}\\x{2b1b}-\\x{2b1c}\\x{2b50}\\x{2b55}]\\x{FE0F}?|[\\x{231a}-\\x{231b}\\x{2328}\\x{23cf}\\x{23e9}-\\x{23f3}\\x{23f8}-\\x{23fa}]\\x{FE0F}?|\\x{1F0CF}|[\\x{2934}\\x{2935}]\\x{FE0F}?)|[\\x{2700}-\\x{27bf}]\\x{FE0F}?|[\\x{1F000}-\\x{1F6FF}\\x{1F900}-\\x{1F9FF}]\\x{FE0F}?|[\\x{2600}-\\x{26ff}]\\x{FE0F}?|[\\x{0030}-\\x{0039}]\\x{FE0F}";
    private String shortnameRegexp = ":([-+\\w]+):";

    private Ruleset ruleset = new Ruleset();

    public void Client() {
        this.imagePathPNG = this.imagePathPNG + "/" + this.emojiVersion + "/png/" + this.emojiSize + "/";
        this.spriteSize = (this.spriteSize.equals("32") || this.spriteSize.equals("64")) ? this.spriteSize : "32";
    }

    /**
     * This will return the shortname from unicode input.
     *
     * @param   @string  $string The input string.
     * @return  @string  shortname
     */
    public String toShort(String string) {
        Pattern pattern = Pattern.compile(this.unicodeRegexp);
        Matcher matcher = pattern.matcher(string);
        return this.replaceUnicodeWithShortname(string, matcher);
    }

    /**
     * First pass changes unicode characters into emoji markup.
     * Second pass changes any shortnames into emoji markup.
     *
     * @param   @string  $string The input string.
     * @return  @string  String with appropriate html for rendering emoji.
     */
    public String toImage(String string) {
        string = unicodeToImage(string);
        string = shortnameToImage(string);
        return string;
    }

    /**
     * This will output unicode from shortname input.
     * If Client/$ascii is true it will also output unicode from ascii.
     * This is useful for sending emojis back to mobile devices.
     *
     * @param   @string  $string The input string.
     * @return  @string  String with unicode replacements.
     */
    public String shortnameToUnicode(String string) {
        if(this.shortcodes) {
            Pattern pattern = Pattern.compile(this.shortnameRegexp);
            Matcher matches = pattern.matcher(string);
            string = replaceShortnameWithUnicode(string, matches);
        }
        if(this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "(()"+asciiRegexp+"())" : "((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matches = pattern.matcher(string);
            string = replaceAsciiWithUnicode(string, matches);
        }
        return string;
    }

    /**
     * This will output image markup from shortname input.
     *
     * @param   @string  $string The input string.
     * @return  @string  String with appropriate html for rendering emoji.
     */
    public String shortnameToImage(String string) {
        if(this.shortcodes) {
            Pattern pattern = Pattern.compile(this.shortnameRegexp);
            Matcher matches = pattern.matcher(string);
            string = shortnameToImageCallback(matches);
        }
        if (this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "|(()" + asciiRegexp + "())" : "|((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matches = pattern.matcher(string);
            string = asciiToImageCallback(matches);
        }
        return string;
    }

    /**
     * This will output image markup from shortname input.
     *
     * @param   @string  $string The input string.
     * @return  @string  String with appropriate html for rendering emoji.
     */
    public String unicodeToImage(String string) {
        Pattern pattern = Pattern.compile(this.unicodeRegexp);
        Matcher matcher = pattern.matcher(string);
        string = this.replaceUnicodeWithShortname(string, matcher);
        if(this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "|(()" + asciiRegexp + "())" : "|((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            pattern = Pattern.compile(asciiRX);
            matcher = pattern.matcher(string);
            string = replaceAsciiWithUnicode(string, matcher);
        }
        return string;
    }

    /**
     * Uses toShort to transform all unicode into a standard shortname
     * then transforms the shortname into unicode.
     * This is done for standardization when converting several unicode types.
     *
     * @param   @string  $string The input string.
     * @return  @string  String with standardized unicode.
     */
    private String unifyUnicode(String string) {
        string = toShort(string);
        string = shortnameToUnicode(string);
        return string;
    }

    /**
     * @param   @matcher  results of the pattern.
     * @return  @string  Image HTML replacement result.
     */
    private String shortnameToImageCallback(Matcher matches) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()<2) {
            return "";
        } else {
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();

            String shortname = matchList.get(1);

            if(shortcode_replace.containsKey(shortname)) {
                return matchList.get(0);
            }

            String unicode = shortcode_replace.get(shortname).get(0);
            String filename = shortcode_replace.get(shortname).get(2);
            String category = filename.contains("-f3f") ? "diversity" : shortcode_replace.get(shortname).get(3);
            String titleTag = this.imageTitleTag ? "title=\"" + URLEncoder.encode(shortname) + "\"" : "";

            String alt = "";
            if(this.unicodeAlt) {
                alt = this.convert(unicode);
            } else {
                alt = shortname;
            }

            if(this.sprites) {
                return "<span class=\"emojione emojione-" + this.spriteSize + "-" + category + " _" + filename + "\" " + titleTag + ">" + alt + "</span>";
            }
            else {
                return "<img class=\"emojione\" alt=\"" + alt + "\" " + titleTag + " src=\"" + this.imagePathPNG + filename + ".png\"/>";
            }
        }
    }

    /**
     * This will replace shortnames with their ascii equivalent.
     * ex. :wink: --> ;^)
     * This is useful for systems that don't support unicode or images.
     *
     * @param   string  $string The input string.
     * @return  string  String with ascii replacements.
     */
    private String shortnameToAscii(String string) {
        Pattern pattern = Pattern.compile(this.shortnameRegexp);
        Matcher matches = pattern.matcher(string);
        return replaceShortnameWithUnicode(string, matches);
    }

    /**
     * @param   @matcher  results of the pattern.
     * @return  @string  Unicode replacement result.
     */
    private String replaceShortnameWithUnicode(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            for(String shortname : matchList) {
                try {
                    if (shortcode_replace.containsKey(shortname)) {
                        string = string.replace(shortname, shortcode_replace.get(shortname).get(0));
                    }
                } catch (Exception e) {
                    Log.e("ShortnameWithUnicode",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * @param   @matcher  results of the pattern.
     * @return  @string  Unicode replacement result.
     */
    private String replaceAsciiWithUnicode(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, String> ascii_replace = this.ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            for(String ascii : matchList) {
                try {
                    if (ascii_replace.containsKey(ascii)) {
                        string = string.replace(ascii, ascii_replace.get(ascii));
                        if(shortcode_replace.containsKey(ascii_replace.get(ascii))) {
                            string = string.replace(ascii_replace.get(ascii), shortcode_replace.get(ascii_replace.get(ascii)).get(0));
                        }
                    }
                } catch (Exception e) {
                    Log.e("replaceAsciiWithUnicode",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * @param   @matcher  results of the pattern.
     * @return  @string  Image HTML replacement result.
     */
    private String asciiToImageCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()!=4) {
            return "";
        } else {
            LinkedHashMap<String, String> ascii_replace = this.ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();

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
     * @param   @matcher  results of the pattern.
     * @return  @string  shortname result
     */
    private String replaceUnicodeWithShortname(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, String> unicode_replace = this.ruleset.getUnicodeReplace();
            for(String unicode : matchList) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] xxx = unicode.getBytes("UTF-8");
                    String hexString = "";
                    for (byte x : xxx) {
                        stringBuilder.append(String.format("%02X", x));
                    }
                    if (unicode_replace.containsKey(stringBuilder.toString())) {
                        string = string.replace(unicode, unicode_replace.get(stringBuilder.toString()));
                    }
                } catch (Exception e) {
                    Log.e("MatchesWithShortnam",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * @param   @matcher  results of the pattern.
     * @return  @string  Image HTML replacement result.
     */
    private String unicodeToImageCallback(Matcher matches)
    {
        ArrayList<String> matchList = new ArrayList<>();
        while (matches.find()) {
            matchList.add(matches.group(0));
        }
        if(matchList.size()==0) {
            return "";
        } else {
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            LinkedHashMap<String, String> unicode_replace = this.ruleset.getUnicodeReplace();
            LinkedHashMap<String, String> unicode_replace_greedy = this.ruleset.getUnicodeReplaceGreedy();

            String unicode = matchList.get(0).toUpperCase();

            String[] bList = this.blacklistChars.split(",");

            boolean found = false;
            for(int i=0; i<bList.length; i++) {
                if(bList[i].equals(unicode)) {
                    found = true;
                }
            }

            String shortname = "";
            if(unicode_replace.containsKey(unicode) && !found) {
                shortname = unicode_replace.get(unicode);
            } else if (this.greedyMatch && unicode_replace_greedy.containsKey(unicode) && !found) {
                shortname = unicode_replace_greedy.get(unicode);
            } else {
                return unicode;
            }

            String filename = shortcode_replace.get(shortname).get(2);
            String category = filename.contains("-1f3f") == false ? "diversity" : shortcode_replace.get(shortname).get(3);
            String titleTag = this.imageTitleTag ? "title=\"" + URLEncoder.encode(shortname) + "\"" : "";

            String alt = "";
            if(this.unicodeAlt) {
                alt = unicode;
            } else {
                alt = shortname;
            }

            if(this.sprites) {
                return "<span class=\"emojione emojione-" + this.spriteSize + "-" + category + " _" + filename + "\" " + titleTag + ">" + alt + "</span>";
            }
            else {
                return "<img class=\"emojione\" alt=\"" + alt + "\" " + titleTag + " src=\"" + this.imagePathPNG + filename + ".png\"/>";
            }
        }
    }

    /**
     * Converts from unicode to hexadecimal NCR.
     *
     * @param   @string  unicode characters
     * @return  @string  hexadecimal NCR
     * */
    private String convert(String unicode)
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
     * @param @string[] elements
     * @param @string   string to join them with
     */
    private String implode(String[] items, String separator) {
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

    public boolean is$ascii() {
        return ascii;
    }
    public void setascii(boolean ascii) {
        this.ascii = ascii;
    }
    public boolean isRiskyMatchAscii() {
        return riskyMatchAscii;
    }
    public void setRiskyMatchAscii(boolean riskyMatchAscii) {
        this.riskyMatchAscii = riskyMatchAscii;
    }
    public boolean isShortcodes() {
        return shortcodes;
    }
    public void setShortcodes(boolean shortcodes) {
        this.shortcodes = shortcodes;
    }
    public boolean isUnicodeAlt() {
        return unicodeAlt;
    }
    public void setUnicodeAlt(boolean unicodeAlt) {
        this.unicodeAlt = unicodeAlt;
    }
    public String getEmojiVersion() {
        return emojiVersion;
    }
    public void setEmojiVersion(String emojiVersion) {
        this.emojiVersion = emojiVersion;
    }
    public String getEmojiSize() {
        return emojiSize;
    }
    public void setEmojiSize(String emojiSize) {
        this.emojiSize = emojiSize;
    }
    public boolean isGreedyMatch() {
        return greedyMatch;
    }
    public void setGreedyMatch(boolean greedyMatch) {
        this.greedyMatch = greedyMatch;
    }
    public String getBlacklistChars() {
        return blacklistChars;
    }
    public void setBlacklistChars(String blacklistChars) {
        this.blacklistChars = blacklistChars;
    }
    public boolean isSprites() {
        return sprites;
    }
    public void setSprites(boolean sprites) {
        this.sprites = sprites;
    }
    public String getSpriteSize() {
        return spriteSize;
    }
    public void setSpriteSize(String spriteSize) {
        this.spriteSize = spriteSize;
    }
    public String getImagePathPNG() {
        return imagePathPNG;
    }
    public void setImagePathPNG(String imagePathPNG) {
        this.imagePathPNG = imagePathPNG;
    }
    public boolean isImageTitleTag() {
        return imageTitleTag;
    }
    public void setImageTitleTag(boolean imageTitleTag) {
        this.imageTitleTag = imageTitleTag;
    }
    public String getUnicodeRegexp() {
        return unicodeRegexp;
    }
    public void setUnicodeRegexp(String unicodeRegexp) {
        this.unicodeRegexp = unicodeRegexp;
    }
    public String getshortnameRegexp() {
        return shortnameRegexp;
    }
    public void setshortnameRegexp(String shortnameRegexp) {
        this.shortnameRegexp = shortnameRegexp;
    }
}
