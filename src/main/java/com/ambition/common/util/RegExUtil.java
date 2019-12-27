package com.ambition.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author : 孙超飞
 * @title :
 * @description :
 * @copyright :润投科技
 * @date : 2018-01-03 16:05
 */

public class RegExUtil {
    /**
     * 特殊字符
     */
    private static final String SPECIAL_CHARACTER_REG = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(SPECIAL_CHARACTER_REG);

    private static final String SPECIAL_CHARACTER_AND_EMOJI_REG = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\ud83c\\udc00-\\ud83c\\udfff\\ud83d\\udc00-\\ud83d\\udfff\\u2600-\\u27ff]";
    //只匹配ip的格式
    private static final String IP_CHARACTER_REG = "[0-9]{1,3}[/.][0-9]{1,3}[/.][0-9]{1,3}[/.][0-9]{1,3}";

    private static final String TAG_CHARACTER_REG_D = "(^[1-9][0-9]*){1}([,][1-9][0-9]*){0,2}";

    private static final String TAG_CHARACTER_REG_F = "(^[1-9][0-9]*){1}([;][1-9][0-9]*){0,2}";

    private static final String TAG_CHARACTER_REG_D_6 = "(^[1-9][0-9]*){1}([,][1-9][0-9]*){0,5}";

    private static final String TAG_CHARACTER_REG_F_D = "(^[1-9][0-9]*){1}([,|;][1-9][0-9]*){0,5}";


    private static final Pattern IP_CHARACTER_PATTERN = Pattern.compile(IP_CHARACTER_REG);


    private static final Pattern SPECIAL_CHARACTER_AND_EMOJI_D = Pattern.compile(SPECIAL_CHARACTER_AND_EMOJI_REG);

    private static final Pattern TAG_CHARACTER_PATTERN_F_D = Pattern.compile(TAG_CHARACTER_REG_F_D);

    private static final Pattern TAG_CHARACTER_PATTERN_D_6 = Pattern.compile(TAG_CHARACTER_REG_D_6);

    private static final Pattern TAG_CHARACTER_PATTERN_D = Pattern.compile(TAG_CHARACTER_REG_D);

    private static final Pattern TAG_CHARACTER_PATTERN_F = Pattern.compile(TAG_CHARACTER_REG_F);

    /**
     * 除去特殊字符，除去逗号
     */
    private static final String SPECIAL_CHARACTER_WITHOUT_COMMA_REG = "[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private static final Pattern SPECIAL_CHARACTER_WITHOUT_COMMA_PATTERN = Pattern
            .compile(SPECIAL_CHARACTER_WITHOUT_COMMA_REG);

    /**
     * 除去特殊字符，除去逗号和分号
     */
    private static final String SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_REG = "[`~!@#$%^&*()+=|{}':'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private static final Pattern SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_PATTERN = Pattern
            .compile(SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_REG);

    /**
     * 中国其他地区号码
     */
    private static final String CHINA_OTHER_PHONE_REG = "^(\\+?0?86\\-?)?1[3456789]\\d{9}$";

    private static final Pattern CHINA_OTHER_PHONE_PATTERN = Pattern.compile(CHINA_OTHER_PHONE_REG);

    /**
     * 中国香港号码
     */
    private static final String CHINA_HK_PHONE_REG = "^(\\+?852\\-?)?[5689]\\d{3}\\-?\\d{4}$";


    /**
     * 中国台湾号码
     */
    private static final String CHINA_TAIWAN_PHONE_REG = "^(\\+?886\\-?|0)?9\\d{8}$";

    /**
     * 美国英文
     */
    private static final String US_PHONE_REG = "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$";
    /**
     * 英国英文
     */
    private static final String GB_PHONE_REG = "^(\\+?44|0)7\\d{9}$";

    /**
     * 澳门  66或68开头后面跟5位数字
     */
    private static final String CHINA_AOMEN_PHONE_REG = "^(\\+?853\\-?)?[6]([8|6])\\d{5}";


    private static final Pattern CHINA_HK_PHONE_PATTERN = Pattern.compile(CHINA_HK_PHONE_REG);

    public static boolean checkSpecialCharacter(String content) {
        if (!StringUtils.isBlank(content)) {
            if (content.length() > 20) {
                return true;
            }
        }
        Matcher m = SPECIAL_CHARACTER_PATTERN.matcher(content);
        return m.find();
    }

    /**
     * 逗号除外
     *
     * @param content
     * @return
     */
    public static boolean checkSpecialCharacterWithoutComma(String content) {

        if (!StringUtils.isBlank(content)) {
            if (content.length() > 20) {
                return true;
            }
        }
        Matcher m = SPECIAL_CHARACTER_WITHOUT_COMMA_PATTERN.matcher(content);
        return m.find();
    }

    /**
     * 逗号和分号除外
     *
     * @param content
     * @return
     */
    public static boolean checkSpecialCharacterWithoutCommaAndSemicolon(String content) {

        if (!StringUtils.isBlank(content)) {
            if (content.length() > 20) {
                return true;
            }
        }
        Matcher m = SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_PATTERN.matcher(content);
        return m.find();
    }


    public static boolean checkNickName(String content) {

        if (!StringUtils.isBlank(content)) {
            if (content.length() > 30) {
                return true;
            }
        }
        Matcher m = SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_PATTERN.matcher(content);
        return m.find();
    }

    public static boolean checkSpecialString(String content) {
        Matcher m = SPECIAL_CHARACTER_WITHOUT_COMMA_SEMICOLON_PATTERN.matcher(content);
        return m.find();
    }


    /**
     * 英美港澳台大陆号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return isChinaPhoneLegal(str) || isChinaHKPhoneLegal(str) || isChinaTaiWanPhoneLegal(str) || isUSPhoneLegal(str) || isGBPhoneLegal(str) || isChinaAomenPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
     * 17+除9的任意数 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_OTHER_PHONE_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isChinaHKPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_HK_PHONE_PATTERN.matcher(str);
        return m.matches();
    }


    /**
     * 中国台湾号码
     */
    public static boolean isChinaTaiWanPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = Pattern.compile(CHINA_TAIWAN_PHONE_REG).matcher(str);
        return m.matches();
    }

    /**
     * 美国英文
     */
    public static boolean isUSPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = Pattern.compile(US_PHONE_REG).matcher(str);
        return m.matches();
    }

    /**
     * 英国英文
     */
    public static boolean isGBPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = Pattern.compile(GB_PHONE_REG).matcher(str);
        return m.matches();
    }

    /**
     * 澳门  66或68开头后面跟5位数字
     */
    public static boolean isChinaAomenPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = Pattern.compile(CHINA_AOMEN_PHONE_REG).matcher(str);
        return m.matches();
    }


    public static boolean isTagRegD(String str) throws PatternSyntaxException {
        Matcher m = TAG_CHARACTER_PATTERN_D.matcher(str);
        return m.matches();
    }

    public static boolean isTagRegF(String str) throws PatternSyntaxException {
        Matcher m = TAG_CHARACTER_PATTERN_F.matcher(str);
        return m.matches();
    }

    public static boolean isTagRegFD(String str) throws PatternSyntaxException {
        Matcher m = TAG_CHARACTER_PATTERN_F_D.matcher(str);
        return m.matches();
    }

    public static boolean isTagRegD6(String str) throws PatternSyntaxException {
        Matcher m = TAG_CHARACTER_PATTERN_D_6.matcher(str);
        return m.matches();
    }

    public static String convertSpecialCharacter(String str) {

        Matcher m = SPECIAL_CHARACTER_AND_EMOJI_D.matcher(str);
        String randomStr = "0123456789abcdefghijklmnopqistuvwxyz";
        String[] split = randomStr.split("");
        while (m.find()) {
            str = m.replaceFirst(split[RandomUtil.nextInt(36)]);
            m = SPECIAL_CHARACTER_AND_EMOJI_D.matcher(str);
        }
        return str;
    }

    public static boolean isIP(String str) throws PatternSyntaxException {
        Matcher m = IP_CHARACTER_PATTERN.matcher(str);
        return m.matches();
    }


}
