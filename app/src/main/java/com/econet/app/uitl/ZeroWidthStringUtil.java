package com.econet.app.uitl;// Siyu Chen Apr 14, 2018
// java use UTF-16
// zero length unicode chars, see https://www.compart.com/en/unicode/category/Cf
// U+200B 	Zero Width Space
// U+200C 	Zero Width Non-joiner
// U+200D 	Zero Width Joiner
// U+FEFF 	Zero Width No-break Space

import java.util.HashMap;
import java.util.Map;

// map U+200B as binary 0
// map U+200C as binary 1
public class ZeroWidthStringUtil {
    // or use BiMap
    private static Map<Character, Character> binaryZeroWidthMap = new HashMap<>();
    static{
        binaryZeroWidthMap.put('\u200b', '0');
        binaryZeroWidthMap.put('\u200c', '1');
    }

    private static Map<Character, Character> binaryZeroWidthMap2 = new HashMap<>();
    static{
        binaryZeroWidthMap2.put('0', '\u200b');
        binaryZeroWidthMap2.put('1', '\u200c');
    }

    public static void main(String[] args) {
//        System.out.println("\u2030");
//        System.out.println(Integer.toBinaryString('\u03a6'));
        System.out.println(encodeString("This is fun (Note: Info is hidden in this String)", "unicorn"));
        System.out.println(decodeString(encodeString("This is fun (Note: Info is hidden in this String)", "unicorn")));
    }

    public static String decodeString(String s) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(!binaryZeroWidthMap.keySet().contains(s.charAt(i))) i++;
        while(i < s.length()){
            String currentChar = s.substring(i, i + 16);
            sb.append(zeroWidthDecoding(currentChar));
            i += 16;
        }
        return sb.toString();
    }

    private static char zeroWidthDecoding(String currentChar) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(i < currentChar.length()) sb.append(binaryZeroWidthMap.get(currentChar.charAt(i++)));
        return (char) Integer.parseInt(sb.toString(), 2);
    }

    public static String encodeString(String input, String textToHide){
        if(input == null) return null;
        return input + zeroWidthEncoding(textToHide);
    }

    private static String zeroWidthEncoding(String textToHide) {
        char[] charArr = textToHide.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(char c: charArr){
            String unicode = Integer.toBinaryString(c);
            unicode = nZeros(16 - unicode.length()) + unicode;
            for(int i = 0; i < unicode.length(); i++)
                sb.append(binaryZeroWidthMap2.get(unicode.charAt(i)));
        }
        return sb.toString();
    }

    private static String nZeros(int n){
        StringBuffer sb = new StringBuffer();
        while(n-- > 0) sb.append('0');
        return sb.toString();
    }
}