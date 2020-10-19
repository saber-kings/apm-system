package cn.saberking.jvav.apm.web.constants;

/**
 * @author Saber污妖王
 * TODO: 类文件简单描述
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/11
 * @package cn.saberking.jvav.apm.web.Constants
 */
public class Constants {
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    public static final String BAN_OR_DEL = "1";

    public static final String BOOLEAN_TRUE = "true";

    public static final String BOOLEAN_FALSE = "false";

    /**
     * 商品分类顶级父类
     */
    public static final Integer PRODUCT_CATEGORY_ROOT_PID = 0;

    /**
     *商品顶级分类名称
     */
    public static final String PRODUCT_CATEGORY_ROOT_NAME = "顶级分类";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
}
