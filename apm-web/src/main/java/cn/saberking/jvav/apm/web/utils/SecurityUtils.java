package cn.saberking.jvav.apm.web.utils;

import org.springframework.util.DigestUtils;

/**
 * 安全服务工具类
 *
 * @author apm
 */
public class SecurityUtils {
    /**
     * 生成加密后的密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptApmUserPass(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchApmUserPass(String rawPassword, String encodedPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes()).equals(encodedPassword);
    }

    public static String idFormat(String id){
        // 可以用 StringBuilder 这个类，里面有一个接口replace，如下
        StringBuilder sb = new StringBuilder(id);
        sb.replace(6, 14, "********");
        return sb.toString();
    }

    public static void main(String[] args) {
        String raw = encryptApmUserPass("qwezxc123");
        System.out.println(raw);
        System.out.println(matchApmUserPass("admin123456", raw));
        System.out.println(idFormat("370687199811191570"));
    }

    /**
     * 生成订单号
     * @param userId 用户Id
     * @return 结果
     */
    public static String genOrderId(String userId){
        //时间戳前十位
        String str = StringUtils.substring(Long.toString(System.currentTimeMillis()), 0, 10);
        //生成商户订单号
        StringBuilder sb = new StringBuilder();
        //加入用户Id
        sb.append(str).append(userId);
        int targetL = 15;
        while (targetL <= sb.length()) {
            ++targetL;
        }
        //获取补位随机数
        String random = StringUtils.getRandom(targetL - sb.length());
        //加入
        sb.append(random);
        return sb.toString();
    }
}
