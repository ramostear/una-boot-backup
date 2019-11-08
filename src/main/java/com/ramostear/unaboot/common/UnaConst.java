package com.ramostear.unaboot.common;

import java.io.File;

/**
 * @ClassName UnaConst
 * @Description 系统常量
 * @Author ramostear
 * @Date 2019/11/8 0008 23:39
 * @Version 1.0
 **/
public class UnaConst {
    /**
     * User home directory.
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    /**
     * Frontend default theme name.
     */
    public final static String DEFAULT_THEME_ID = "una_pro";

    /**
     * Una System Version.
     */
    public final static String UNA_VERSION = "1.0";

    /**
     * Path separator.
     */
    public static final String FILE_SEPARATOR = File.separator;

    /**
     * mobile phone number pattern.
     */
    public static final String MOBILE_PHONE_PATTERN = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    /**
     * telephone number pattern.
     */
    public static final String TEL_PHONE_PATTERN = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";

    /**
     * IDCard (China) pattern.
     */
    public static final String ID_CARD_PATTERN = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";

    /**
     * email number pattern.
     */
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * status is open.
     */
    public static final int STATE_ON = 1;

    /**
     * status is close.
     */
    public static final int STATE_OFF = 0;
    /**
     * Forbidden.
     */
    public static final int STATE_FORBIDDEN = -1;
    /**
     * System render tag prefix.
     */
    public static final String RENDER_TAG_PREFIX = "una_";

    /**
     * File upload root directory.
     */
    public static final String FILE_UPLOAD_ROOT_DIR = USER_HOME+FILE_SEPARATOR+".una"+FILE_SEPARATOR+"www"+FILE_SEPARATOR;

    /**
     * ISO-8859-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";
    /**
     * GBK
     */
    public static final String GBK = "GBK";
    /**
     * GBK-2312
     */
    public static final String GBK_2312 = "GBK-2312";

    /**
     * img tag pattern.
     */
    public static final String IMG_PATTERN_1 = "(?i)(\\<img)([^\\>]+\\>)";
    /**
     * img src var pattern.
     */
    public static final String IMG_PATTERN_2 = "<(img|IMG)(.*?)(/>|></img>|>)";
    /**
     * img src var value pattern.
     */
    public static final String IMG_SRC_PATTERN = "(src|SRC)=(\"|\')(.*?)(\"|\')";

    public static final String UNKNOWN = "unknown";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss" ;

    public static final String HTTP_GET = "GET";

    public static final String HTTP_POST = "POST";

    public static final String HTTP_PUT = "PUT";

    public static final String HTTP_DELETE = "DELETE";

    public static final String DEFAULT_ROLE_NAME = "administrator";
}
