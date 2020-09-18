package com.skgc.vrd.util.string;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import com.skgc.vrd.util.crypto.Crypto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {


    /** The Constant WHITE_SPACE. */
    private final static char WHITE_SPACE = ' ';

    /**
     * Checks if is null.
     *
     * @param str the str
     * @return true, if is null
     */
    public static boolean isNull(String str) {

        if (str != null) {
            str = str.trim();
        }

        return (str == null || "".equals(str));
    }

    /**
     * Checks if is alpha.
     *
     * @param str the str
     * @return true, if is alpha
     */
    public static boolean isAlpha(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if is alpha numeric.
     *
     * @param str the str
     * @return true, if is alpha numeric
     */
    public static boolean isAlphaNumeric(String str) {

        if (str == null) {
            return false;
        }

        int size = str.length();

        if (size == 0)
            return false;

        for (int i = 0; i < size; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Integer2string.
     *
     * @param integer the integer
     * @return the string
     */
    public static String integer2string(int integer) {
        return ("" + integer);
    }

    /**
     * Long2string.
     *
     * @param longdata the longdata
     * @return the string
     */
    public static String long2string(long longdata) {
        return String.valueOf(longdata);
    }

    /**
     * Float2string.
     *
     * @param floatdata the floatdata
     * @return the string
     */
    public static String float2string(float floatdata) {
        return String.valueOf(floatdata);
    }

    /**
     * Double2string.
     *
     * @param doubledata the doubledata
     * @return the string
     */
    public static String double2string(double doubledata) {
        return String.valueOf(doubledata);
    }

    /**
     * Null2void.
     *
     * @param str the str
     * @return the string
     */
    public static String null2void(String str) {

        if (isNull(str)) {
            str = "";
        }

        return str;
    }

    /**
     * String2integer.
     *
     * @param str the str
     * @return the int
     */
    public static int string2integer(String str) {

        if (isNull(str)) {
            return 0;
        }

        return Integer.parseInt(str);
    }

    /**
     * String2float.
     *
     * @param str the str
     * @return the float
     */
    public static float string2float(String str) {

        if (isNull(str)) {
            return 0.0F;
        }

        return Float.parseFloat(str);
    }

    /**
     * String2double.
     *
     * @param str the str
     * @return the double
     */
    public static double string2double(String str) {

        if (isNull(str)) {
            return 0.0D;
        }

        return Double.parseDouble(str);
    }

    /**
     * String2long.
     *
     * @param str the str
     * @return the long
     */
    public static long string2long(String str) {

        if (isNull(str)) {
            return 0L;
        }

        return Long.parseLong(str);
    }

    /**
     * Null2string.
     *
     * @param str the str
     * @param defaultValue the default value
     * @return the string
     */
    public static String null2string(String str, String defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return str;
    }

    /**
     * String2integer.
     *
     * @param str the str
     * @param defaultValue the default value
     * @return the int
     */
    public static int string2integer(String str, int defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Integer.parseInt(str);
    }

    /**
     * String2float.
     *
     * @param str the str
     * @param defaultValue the default value
     * @return the float
     */
    public static float string2float(String str, float defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Float.parseFloat(str);
    }

    /**
     * String2double.
     *
     * @param str the str
     * @param defaultValue the default value
     * @return the double
     */
    public static double string2double(String str, double defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Double.parseDouble(str);
    }

    /**
     * String2long.
     *
     * @param str the str
     * @param defaultValue the default value
     * @return the long
     */
    public static long string2long(String str, long defaultValue) {

        if (isNull(str)) {
            return defaultValue;
        }

        return Long.parseLong(str);
    }

    /**
     * Equals.
     *
     * @param source the source
     * @param target the target
     * @return true, if successful
     */
    public static boolean equals(String source, String target) {

        return null2void(source).equals(null2void(target));

    }

    /**
     * To sub string.
     *
     * @param str the str
     * @param beginIndex the begin index
     * @param endIndex the end index
     * @return the string
     */
    public static String toSubString(String str, int beginIndex, int endIndex) {

        if (equals(str, "")) {
            return str;
        } else if (str.length() < beginIndex) {
            return "";
        } else if (str.length() < endIndex) {
            return str.substring(beginIndex);
        } else {
            return str.substring(beginIndex, endIndex);
        }

    }

    /**
     * To sub string.
     *
     * @param source the source
     * @param beginIndex the begin index
     * @return the string
     */
    public static String toSubString(String source, int beginIndex) {

        if (equals(source, "")) {
            return source;
        } else if (source.length() < beginIndex) {
            return "";
        } else {
            return source.substring(beginIndex);
        }

    }

    /**
     * Search.
     *
     * @param source the source
     * @param target the target
     * @return the int
     */
    public static int search(String source, String target) {
        int result = 0;
        String strCheck = new String(source);
        for (int i = 0; i < source.length();) {
            int loc = strCheck.indexOf(target);
            if (loc == -1) {
                break;
            } else {
                result++;
                i = loc + target.length();
                strCheck = strCheck.substring(i);
            }
        }
        return result;
    }

    /**
     * Trim.
     *
     * @param str the str
     * @return the string
     */
    public static String trim(String str) {
        return str.trim();
    }

    /**
     * Ltrim.
     *
     * @param str the str
     * @return the string
     */
    public static String ltrim(String str) {

        int index = 0;

        while (' ' == str.charAt(index++))
            ;

        if (index > 0)
            str = str.substring(index - 1);

        return str;
    }

    /**
     * Rtrim.
     *
     * @param str the str
     * @return the string
     */
    public static String rtrim(String str) {

        int index = str.length();

        while (' ' == str.charAt(--index))
            ;

        if (index < str.length())
            str = str.substring(0, index + 1);

        return str;
    }

    /**
     * Concat.
     *
     * @param str1 the str1
     * @param str2 the str2
     * @return the string
     */
    public static String concat(String str1, String str2) {

        StringBuffer sb = new StringBuffer(str1);
        sb.append(str2);

        return sb.toString();
    }

    /**
     * L pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @return the string
     */
    public static String lPad(String str, int len, char pad) {
        return lPad(str, len, pad, false);
    }

    /**
     * L pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @param isTrim the is trim
     * @return the string
     */
    public static String lPad(String str, int len, char pad, boolean isTrim) {

        if (isNull(str)) {
            return null;
        }

        if (isTrim) {
            str = str.trim();
        }

        for (int i = str.length(); i < len; i++) {
            str = pad + str;
        }

        return str;
    }

    /**
     * R pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @return the string
     */
    public static String rPad(String str, int len, char pad) {
        return rPad(str, len, pad, false);
    }

    /**
     * R pad.
     *
     * @param str the str
     * @param len the len
     * @param pad the pad
     * @param isTrim the is trim
     * @return the string
     */
    public static String rPad(String str, int len, char pad, boolean isTrim) {

        if (isNull(str)) {
            return null;
        }

        if (isTrim) {
            str = str.trim();
        }

        for (int i = str.length(); i < len; i++) {
            str = str + pad;
        }

        return str;
    }

    /**
     * Align left.
     *
     * @param str the str
     * @param length the length
     * @return the string
     */
    public static String alignLeft(String str, int length) {
        return alignLeft(str, length, false);
    }

    /**
     * <p>
     * 문자열의 뒷쪽에 지정한 길이만큼 공백으로 채움
     * </p>.
     *
     * @param str the str
     * @param length the length
     * @param isEllipsis the is ellipsis
     * @return the string
     */
    public static String alignLeft(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(str);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");

                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    /**
     * Align right.
     *
     * @param str the str
     * @param length the length
     * @return the string
     */
    public static String alignRight(String str, int length) {

        return alignRight(str, length, false);
    }

    /**
     * Align right.
     *
     * @param str the str
     * @param length the length
     * @param isEllipsis the is ellipsis
     * @return the string
     */
    public static String alignRight(String str, int length, boolean isEllipsis) {

        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            for (int i = 0; i < (length - str.length()); i++) {
                temp.append(WHITE_SPACE);
            }
            temp.append(str);
            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }
    }

    /**
     * Align center.
     *
     * @param str the str
     * @param length the length
     * @return the string
     */
    public static String alignCenter(String str, int length) {
        return alignCenter(str, length, false);
    }

    /**
     * Align center.
     *
     * @param str the str
     * @param length the length
     * @param isEllipsis the is ellipsis
     * @return the string
     */
    public static String alignCenter(String str, int length, boolean isEllipsis) {
        if (str.length() <= length) {

            StringBuffer temp = new StringBuffer(length);
            int leftMargin = (int) (length - str.length()) / 2;

            int rightMargin;
            if ((leftMargin * 2) == (length - str.length())) {
                rightMargin = leftMargin;
            } else {
                rightMargin = leftMargin + 1;
            }

            for (int i = 0; i < leftMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            temp.append(str);

            for (int i = 0; i < rightMargin; i++) {
                temp.append(WHITE_SPACE);
            }

            return temp.toString();
        } else {
            if (isEllipsis) {

                StringBuffer temp = new StringBuffer(length);
                temp.append(str.substring(0, length - 3));
                temp.append("...");
                return temp.toString();
            } else {
                return str.substring(0, length);
            }
        }

    }

    /**
     * Capitalize.
     *
     * @param str the str
     * @return the string
     */
    public static String capitalize(String str) {
        return !isNull(str) ? str.substring(0, 1).toUpperCase()
            + str.substring(1).toLowerCase() : str;
    }

    /**
     * Checks if is pattern match.
     *
     * @param str the str
     * @param pattern the pattern
     * @return true, if is pattern match
     * @throws Exception the exception
     */
    public static boolean isPatternMatch(String str, String pattern)
            throws Exception {
        Matcher matcher = Pattern.compile(pattern).matcher(str);
        log.debug("" + matcher);

        return matcher.matches();
    }

    /**
     * To eng.
     *
     * @param kor the kor
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String toEng(String kor) throws UnsupportedEncodingException {

        if (isNull(kor)) {
            return null;
        }

        return new String(kor.getBytes("KSC5601"), "8859_1");

    }

    /**
     * To kor.
     *
     * @param en the en
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String toKor(String en) throws UnsupportedEncodingException {

        if (isNull(en)) {
            return null;
        }

        return new String(en.getBytes("8859_1"), "euc-kr");
    }

    /**
     * Count of.
     *
     * @param str the str
     * @param charToFind the char to find
     * @return the int
     */
    public static int countOf(String str, String charToFind) {
        int findLength = charToFind.length();
        int count = 0;

        for (int idx = str.indexOf(charToFind); idx >= 0; idx =
            str.indexOf(charToFind, idx + findLength)) {
            count++;
        }

        return count;
    }

    /*
     * StringUtil in Anyframe
     */

    /**
     * Encode a string using algorithm specified in
     * web.xml and return the resulting encrypted
     * password. If exception, the plain credentials
     * string is returned
     * @param password
     *        Password or other credentials to use in
     *        authenticating this username
     * @param algorithm
     *        Algorithm used to do the digest
     * @return encypted password based on the
     *         algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the
            // provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error("Exception: " + e);

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your
        // data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if (((int) encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when
     * storing passwords as cookies. This is weak
     * encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     * @param str
     *        String to be encoded
     * @return String encoding result
     */
    public static String encodeString(String str) {
		//sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        //return new String(encoder.encodeBuffer(str.getBytes())).trim();
        return new String(Base64.encodeBase64(str.getBytes())).trim();
        
    }

    /**
     * Decode a string using Base64 encoding.
     * @param str
     *        String to be decoded
     * @return String decoding String
     */
    public static String decodeString(String str) {
//        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
//        try {
//            return new String(dec.decodeBuffer(str));
//            
//        } catch (IOException io) {
//            throw new RuntimeException(io.getMessage(), io.getCause());
//        }
        return new String(Base64.decodeBase64(str.getBytes()));
    }

    /**
     * convert first letter to a big letter or a small
     * letter.<br>
     * 
     * <pre>
     * StringUtil.trim('Password') = 'password'
     * StringUtil.trim('password') = 'Password'
     * </pre>
     * @param str
     *        String to be swapped
     * @return String converting result
     */
    public static String swapFirstLetterCase(String str) {
        StringBuffer sbuf = new StringBuffer(str);
        sbuf.deleteCharAt(0);
        if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0])) {
            sbuf.insert(0, str.substring(0, 1).toUpperCase());
        } else {
            sbuf.insert(0, str.substring(0, 1).toLowerCase());
        }
        return sbuf.toString();
    }

    /**
     * If original String has a specific String, remove
     * specific Strings from original String.
     * 
     * <pre>
     * StringUtil.trim('pass*word', '*') = 'password'
     * </pre>
     * @param origString
     *        original String
     * @param trimString
     *        String to be trimmed
     * @return converting result
     */
    public static String trim(String origString, String trimString) {
        int startPosit = origString.indexOf(trimString);
        if (startPosit != -1) {
            int endPosit = trimString.length() + startPosit;
            return origString.substring(0, startPosit)
                + origString.substring(endPosit);
        }
        return origString;
    }

    /**
     * Break a string into specific tokens and return a
     * String of last location.
     * 
     * <pre>
     * StringUtil.getLastString('password*password*a*b*c', '*') = 'c'
     * </pre>
     * @param origStr
     *        original String
     * @param strToken
     *        specific tokens
     * @return String of last location
     */
    public static String getLastString(String origStr, String strToken) {
        StringTokenizer str = new StringTokenizer(origStr, strToken);
        String lastStr = "";
        while (str.hasMoreTokens()) {
            lastStr = str.nextToken();
        }
        return lastStr;
    }

    /**
     * If original String has token, Break a string
     * into specific tokens and change String Array. If
     * not, return a String Array which has original
     * String as it is.
     * 
     * <pre>
     * StringUtil.getStringArray('passwordabcpassword', 'abc') 		= String[]{'password','password'}
     * StringUtil.getStringArray('pasword*password', 'abc') 		= String[]{'pasword*password'}
     * </pre>
     * @param str
     *        original String
     * @param strToken
     *        specific String token
     * @return String[]
     */
    public static String[] getStringArray(String str, String strToken) {
        if (str.indexOf(strToken) != -1) {
            StringTokenizer st = new StringTokenizer(str, strToken);
            String[] stringArray = new String[st.countTokens()];
            for (int i = 0; st.hasMoreTokens(); i++) {
                stringArray[i] = st.nextToken();
            }
            return stringArray;
        }
        return new String[] {str };
    }

    /**
     * If string is null or empty string, return false. <br>
     * If not, return true.
     * 
     * <pre>
     * StringUtil.isNotEmpty('') 		= false
     * StringUtil.isNotEmpty(null) 		= false
     * StringUtil.isNotEmpty('abc') 	= true
     * </pre>
     * @param str
     *        original String
     * @return which empty string or not.
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * If string is null or empty string, return true. <br>
     * If not, return false.
     * 
     * <pre>
     * StringUtil.isEmpty('') 		= true
     * StringUtil.isEmpty(null) 	= true
     * StringUtil.isEmpty('abc') 	= false
     * </pre>
     * @param str
     *        original String
     * @return which empty string or not.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * replace replaced string to specific string from
     * original string. <br>
     * 
     * <pre>
     * StringUtil.replace('work$id', '$', '.') 	= 'work.id'
     * </pre>
     * @param str
     *        original String
     * @param replacedStr
     *        to be replaced String
     * @param replaceStr
     *        replace String
     * @return converting result
     */
    public static String replace(String str, String replacedStr,
            String replaceStr) {
        String newStr = "";
        if (str.indexOf(replacedStr) != -1) {
            String s1 = str.substring(0, str.indexOf(replacedStr));
            String s2 = str.substring(str.indexOf(replacedStr) + 1);
            newStr = s1 + replaceStr + s2;
        }
        return newStr;
    }

    /**
     * It converts the string representation of a
     * number to integer type (eg. '27' -> 27)
     * 
     * <pre>
     * StringUtil.string2integer('14') 	= 14
     * </pre>
     *
     * @param str original String
     * @param pattern pattern String
     * @return integer integer type of string public
     * static int string2integer(String str) {
     * int ret = Integer.parseInt(str.trim());
     * return ret; } /** It converts integer
     * type to String ( 27 -> '27')
     * 
     * <pre>
     * StringUtil.integer2string(14) 	= '14'
     * </pre>
     * String string representation of a number
     * public static String integer2string(int
     * integer) { return ("" + integer); } /**
     * It returns true if str matches the
     * pattern string. It performs regular
     * expression pattern matching.
     * 
     * <pre>
     * StringUtil.isPatternMatching('abc-def', '*-*') 	= true
     * StringUtil.isPatternMatching('abc', '*-*') 	= false
     * </pre>
     * boolean which matches the pattern string
     * or not.
     * @throws Exception fail to check pattern matched
     */
    public static boolean isPatternMatching(String str, String pattern)
            throws Exception {
        // if url has wild key, i.e. "*", convert it to
        // ".*" so that we can
        // perform regex matching
        if (pattern.indexOf('*') >= 0) {
            pattern = pattern.replaceAll("\\*", ".*");
        }

        pattern = "^" + pattern + "$";

        return Pattern.matches(pattern, str);
    }

    /**
     * It returns true if string contains a sequence of
     * the same character.
     * 
     * <pre>
     * StringUtil.containsMaxSequence('password', '2') 	= true
     * StringUtil.containsMaxSequence('my000', '3') 	= true
     * StringUtil.containsMaxSequence('abbbbc', '5')	= false
     * </pre>
     * @param str
     *        original String
     * @param maxSeqNumber
     *        a sequence of the same character
     * @return which contains a sequence of the same
     *         character
     */
    public static boolean containsMaxSequence(String str, String maxSeqNumber) {
        int occurence = 1;
        int max = string2integer(maxSeqNumber);
        if (str == null) {
            return false;
        }

        int sz = str.length();
        for (int i = 0; i < (sz - 1); i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                occurence++;

                if (occurence == max)
                    return true;
            } else {
                occurence = 1;
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks that the String contains certain
     * characters.
     * </p>
     * <p>
     * A <code>null</code> String will return
     * <code>false</code>. A <code>null</code> invalid
     * character array will return <code>false</code>.
     * An empty String ("") always returns false.
     * </p>
     * 
     * <pre>
     * StringUtil.containsInvalidChars(null, *)       			= false
     * StringUtil.containsInvalidChars(*, null)      			= false
     * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
     * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
     * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
     * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
     * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
     * </pre>
     * @param str
     *        the String to check, may be null
     * @param invalidChars
     *        an array of invalid chars, may be null
     * @return false if it contains none of the invalid
     *         chars, or is null
     */

    public static boolean containsInvalidChars(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return false;
        }
        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; j++) {
                if (invalidChars[j] == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks that the String contains certain
     * characters.
     * </p>
     * <p>
     * A <code>null</code> String will return
     * <code>false</code>. A <code>null</code> invalid
     * character array will return <code>false</code>.
     * An empty String ("") always returns false.
     * </p>
     * 
     * <pre>
     * StringUtil.containsInvalidChars(null, *)       			= false
     * StringUtil.containsInvalidChars(*, null)      			= false
     * StringUtil.containsInvalidChars(&quot;&quot;, *)         = false
     * StringUtil.containsInvalidChars(&quot;ab&quot;, '')      = false
     * StringUtil.containsInvalidChars(&quot;abab&quot;, 'xyz') = false
     * StringUtil.containsInvalidChars(&quot;ab1&quot;, 'xyz')  = false
     * StringUtil.containsInvalidChars(&quot;xbz&quot;, 'xyz')  = true
     * </pre>
     * @param str
     *        the String to check, may be null
     * @param invalidChars
     *        a String of invalid chars, may be null
     * @return false if it contains none of the invalid
     *         chars, or is null
     */
    public static boolean containsInvalidChars(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        return containsInvalidChars(str, invalidChars.toCharArray());
    }

    /**
     * <p>
     * Checks if the String contains only unicode
     * letters or digits.
     * </p>
     * <p>
     * <code>null</code> will return <code>false</code>
     * . An empty String ("") will return
     * <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.isAlphaNumeric(null)   			 = false
     * StringUtil.isAlphaNumeric(&quot;&quot;)     = false
     * StringUtil.isAlphaNumeric(&quot;  &quot;)   = false
     * StringUtil.isAlphaNumeric(&quot;abc&quot;)  = true
     * StringUtil.isAlphaNumeric(&quot;ab c&quot;) = false
     * StringUtil.isAlphaNumeric(&quot;ab2c&quot;) = true
     * StringUtil.isAlphaNumeric(&quot;ab-c&quot;) = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains
     * letters or digits, and is non-null
     * public static boolean
     * isAlphaNumeric(String str) { if (str ==
     * null) { return false; } int sz =
     * str.length(); if (sz == 0) return false;
     * for (int i = 0; i < sz; i++) { if
     * (!Character
     * .isLetterOrDigit(str.charAt(i))) {
     * return false; } } return true; } /**
     * <p>
     * Checks if the String contains only
     * unicode letters.
     * </p>
     * <p>
     * <code>null</code> will return
     * <code>false</code>. An empty String ("")
     * will return <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.isAlpha(null)   			= false
     * StringUtil.isAlpha(&quot;&quot;)     = false
     * StringUtil.isAlpha(&quot;  &quot;)   = false
     * StringUtil.isAlpha(&quot;abc&quot;)  = true
     * StringUtil.isAlpha(&quot;ab2c&quot;) = false
     * StringUtil.isAlpha(&quot;ab-c&quot;) = false
     * </pre>
     * <code>true</code> if only contains
     * letters, and is non-null public static
     * boolean isAlpha(String str) { if (str ==
     * null) { return false; } int sz =
     * str.length(); if (sz == 0) return false;
     * for (int i = 0; i < sz; i++) { if
     * (!Character.isLetter(str.charAt(i))) {
     * return false; } } return true; } /**
     * <p>
     * Checks if the String contains only
     * unicode digits. A decimal point is not a
     * unicode digit and returns false.
     * </p>
     * <p>
     * <code>null</code> will return
     * <code>false</code>. An empty String ("")
     * will return <code>false</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.isNumeric(null)   		   = false
     * StringUtil.isNumeric(&quot;&quot;)     = false
     * StringUtil.isNumeric(&quot;  &quot;)   = false
     * StringUtil.isNumeric(&quot;123&quot;)  = true
     * StringUtil.isNumeric(&quot;12 3&quot;) = false
     * StringUtil.isNumeric(&quot;ab2c&quot;) = false
     * StringUtil.isNumeric(&quot;12-3&quot;) = false
     * StringUtil.isNumeric(&quot;12.3&quot;) = false
     * </pre>
     * <code>true</code> if only contains
     * digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        if (sz == 0)
            return false;
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Reverses a String as per.
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, <code>null</code>
     * if null String input
     * {@link StringBuffer#reverse()}.
     * </p>
     * <p>
     * <A code>null</code> String returns
     * <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtil.reverse(null)  		   = null
     * StringUtil.reverse(&quot;&quot;)    = &quot;&quot;
     * StringUtil.reverse(&quot;bat&quot;) = &quot;tab&quot;
     * </pre>
     */

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * Make a new String that filled original to a
     * special char as cipers.
     *
     * @param originalStr original String
     * @param ch a special char
     * @param cipers cipers
     * @return filled String
     */
    public static String fillString(String originalStr, char ch, int cipers) {
        int originalStrLength = originalStr.length();

        if (cipers < originalStrLength)
            return null;

        int difference = cipers - originalStrLength;

        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < difference; i++)
            strBuf.append(ch);

        strBuf.append(originalStr);
        return strBuf.toString();
    }

    /**
     * Determine whether a (trimmed) string is empty.
     *
     * @param foo The text to check.
     * @return Whether empty.
     */
    public static final boolean isEmptyTrimmed(String foo) {
        return (foo == null || foo.trim().length() == 0);
    }

    /**
     * Return token list.
     *
     * @param lst the lst
     * @param separator the separator
     * @return the tokens
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getTokens(String lst, String separator) {
        List tokens = new ArrayList();

        if (lst != null) {
            StringTokenizer st = new StringTokenizer(lst, separator);
            while (st.hasMoreTokens()) {
                try {
                    String en = st.nextToken().trim();
                    tokens.add(en);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tokens;
    }

    /**
     * Return token list which is separated by ",".
     *
     * @param lst the lst
     * @return the tokens
     */
    @SuppressWarnings("rawtypes")
	public static List getTokens(String lst) {
        return getTokens(lst, ",");
    }

    /**
     * This method convert "string_util" to
     * "stringUtil".
     *
     * @param targetString the target string
     * @param posChar the pos char
     * @return String result
     */
    public static String convertToCamelCase(String targetString, char posChar) {
        StringBuffer result = new StringBuffer();
        boolean nextUpper = false;
        String allLower = targetString.toLowerCase();

        for (int i = 0; i < allLower.length(); i++) {
            char currentChar = allLower.charAt(i);
            if (currentChar == posChar) {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    currentChar = Character.toUpperCase(currentChar);
                    nextUpper = false;
                }
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    /**
     * Convert a string that may contain underscores to
     * camel case.
     * @param underScore
     *        Underscore name.
     * @return Camel case representation of the
     *         underscore string.
     */
    public static String convertToCamelCase(String underScore) {
        return convertToCamelCase(underScore, '_');
    }

    /**
     * Convert a camel case string to underscore
     * representation.
     * @param camelCase
     *        Camel case name.
     * @return Underscore representation of the camel
     *         case string.
     */
    public static String convertToUnderScore(String camelCase) {
        String result = "";
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            // This is starting at 1 so the result does
            // not end up with an
            // underscore at the begin of the value
            if (i > 0 && Character.isUpperCase(currentChar)) {
                result = result.concat("_");
            }
            result =
                result.concat(Character.toString(currentChar).toLowerCase());
        }
        return result;
    }
    
    /**
     * 
     * 개요: 랜덤 암호화 문구 발생
     * @Method Name : getRandomSHA
     * @history
     * ---------------------------------------------------------------------------------
     *  변경일                    작성자                    변경내용
     * ---------------------------------------------------------------------------------
     *  2017. 1. 12.     taihyun      최초 작성   
     * ---------------------------------------------------------------------------------
     * @return
     */
    public static String getRandomSHA(){
		Random random = new Random();
		int min = 100000, max = 999999;
		int randomNum = random.nextInt(max - min + 1) + min;
		
		return Crypto.getSHA256(String.valueOf(randomNum));
    }
    
    /**
     * 
     * 개요: 랜덤 비밀번호 리턴
     * @Method Name : getRandomPwd
     * @history
     * ---------------------------------------------------------------------------------
     *  변경일                    작성자                    변경내용
     * ---------------------------------------------------------------------------------
     *  2017. 1. 21.     taihyun      최초 작성   
     * ---------------------------------------------------------------------------------
     * @return
     */
    public static String getRandomPwd(){
    	
    	Random rnd = new Random();
    	StringBuffer buf = new StringBuffer();
    	 
    	for(int i=0; i < 10; i++){
    	    if(rnd.nextBoolean()){
    	        buf.append((char)((int)(rnd.nextInt(26))+97));
    	    }else{
    	        buf.append((rnd.nextInt(10))); 
    	    }
    	}    
    	return buf.toString();
    }
    
    /**
     * 
     * 개요: 랜덤 문자
     * @Method Name : getRandomTxt
     * @history
     * ---------------------------------------------------------------------------------
     *  변경일                    작성자                    변경내용
     * ---------------------------------------------------------------------------------
     *  2017. 1. 21.     taihyun      최초 작성   
     * ---------------------------------------------------------------------------------
     * @return
     */
    public static String getRandomTxt(int length){
    	
    	Random rnd = new Random();
    	StringBuffer buf = new StringBuffer();
    	 
    	for(int i=0; i < length; i++){
    	   buf.append((char)((int)(rnd.nextInt(26))+97));
    	}    
    	return buf.toString();
    }    
    
    /**
     * 
     * 개요: 랜덤 숫자
     * @Method Name : getRandomNum
     * @history
     * ---------------------------------------------------------------------------------
     *  변경일                    작성자                    변경내용
     * ---------------------------------------------------------------------------------
     *  2017. 1. 21.     taihyun      최초 작성   
     * ---------------------------------------------------------------------------------
     * @return
     */
    public static String getRandomNum(int length){
    	
    	Random rnd = new Random();
    	StringBuffer buf = new StringBuffer();
    	 
    	for(int i=0; i < length; i++){
    		buf.append((rnd.nextInt(10))); 
    	}    
    	return buf.toString();
    }     
    
    /**
     * 개요: 랜덤 영문+숫자
     * @Method Name : getRandomTxtNum
     * @history
     * ---------------------------------------------------------------------------------
     *  변경일                    작성자                    변경내용
     * ---------------------------------------------------------------------------------
     *  2017. 12. 13.     BD_PC      최초 작성   
     * ---------------------------------------------------------------------------------
     * @param length
     * @return
     */
    public static String getRandomTxtNum(int length){
    	
    	Random rnd = new Random();
    	StringBuffer buf = new StringBuffer();
    	 
    	for(int i=0; i < length; i++){
    	    if(rnd.nextBoolean()){
    	        buf.append((char)((int)(rnd.nextInt(26))+97));
    	    }else{
    	        buf.append((rnd.nextInt(10))); 
    	    }
    	}    
    	return buf.toString();
    }
    
}
