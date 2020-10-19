package cn.saberking.jvav.apm.web.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/6/27
 */
@Configuration
public class AlipayConfig {
    /**
     * 作为身份标识的应用ID
     */
    public static String app_id = "2016102500756852";

    /**
     * 商户私钥
     */
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZOTGU8rbUepMchVAZym+3lx4hpirv/EUSw0WSjqflpGkKyG/sDysGgI4DZcL1VKyKG8xb/f9GEQ6pS+JjWpN8lH8k7Y5FyNqLwCCtgDCg4GYmnZMwEQcC3eZuJYHEh2NQFG3ebYDE/PVlvAoXFnLm8xNx2FYH7TBfOgux35Ecl7DgrXgFlzOvRnJq0JBM9fHEnY4mylfnOnww76HszFPfuW60DgDLp/8LZgY5ro5IGFNE8/vfyb/8SkmktFb3DRHyGhl8mIFEaWYYJHnH0WkVPkoSQeP7n1gYMRsyMYN8Mm+Xmqt3zNMALtHKktKu6uAphIDUeAEt+0wLu15itkJJAgMBAAECggEAW/nFFZAMx8c8f6ZWyuSF40LHR5OjuqNII1cscClKB8OFen+PD7ibLzAED24KdZH6cHz3U1FI5xpeHqn6n4FDf0j0eYpQGPvUlIckFoIYrrmFGDEGpZfTCZmCNSQ2nW5X4z4gJaC954GhHQyfUfSbzJ6lUXV3TxLGMqN5WbDGPs6wz/cjFsjlVJbd5xhlL/L2va2fozK3H1i9fcm+/OqYQjoImjaiB9ymLGTd0zSPY4eB02XXQnPgJPLnFLGbgd3WUXThuQcyJ3zxzOVNBE4d/W5NzluVJePiOFCvsFVIcggemdhvLjdCUEZDOfvWUeaNM4h7xSkW89gaQv/xgU3OwQKBgQDbSXlfjhf4EPsXUt5AP0+9UTIChVMSLrz8ynPfdFh4EtKkuhur63mYFSH/+6EIJkWJ75kOi/YN9gbM8l40+5Uoj9slZ6A8GuWHrEX8/6CzBgz0Ws5Qj6fQN9n+PSM4cQM9XKnAte6gLck0j/wOQEb2UUgYTI2iNNFNdMzLdpzN3QKBgQCy4ETCtpEJSy5RFeAQb1zqFJehfXpUJ1Mdv00OxYqnHILD6O2lB8Ex/DTpsAU6zV1pIrQ3iR1riAAzX8rfqZ95q3jZLlezF99790NJhPzfHFyZzjE0MkC5l2MCMTrMaimLL601KInVYQjmgBFWk5IJe6NcBqxriho0+JSI3eRNXQKBgQDJl+VeBEpiwnGjQoqRs+KBn9iC2sDA0XjqSNB3mAUQ9fTyfSmdRlA1kQ/9vf2PeWeqZDtoVSEyOoCL/zC+Mp8wd/e2jmqPdwaWULjS2sqIakKUjbpn83kKAZ6FjP0JEVQyIDLquBaU6SK1EL1e+JaEMbs+o3n9qeLU+1Cep/+FfQKBgDi2yLZfwi6LKr1EzU0IshH5w3XNXfrOmRjGbb0wPtzrOj65XJfFlKiviWtKhu307RgHi9JL5AsTDNU9Gwq9MjYWevOJMP80o4QfirMFulNXH7kYWhttj34IlLi+OSFUy6U40ZcYKlOAN501n8muclAVZl8oMoTKPbLr2dRnXhp1AoGAHBXYep+LvJ5wGxjcpktWsda0AK3SK3ey986aRlXzmfPbPKjkk97GJiBByLxUGstJNdMnFJLneF2eUYIfXP/cLO+UtIqvetMRHsLgpNclrO0iM4vP/OWW2kqjOJYdIffvV/dwbXCdCuj2lqRjOEBsoPhBSk2zV5aAsn4V8+LJ7do=";

    /**
     * 支付宝公钥
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAul9kwVqZA/mrLIm3fFHKGoGQfkYvjL7akQ/5LaEgQeVfzAmyo0hVceCqWEL+PWYOecHbWWhH/cdrp+6IcuynW+iuXjKryEBcbIWwa1dtII0iaLerApm7KLSEg3Czd7pb/KpX1Q6MDGI2FOIJZhnNmwIo32jIzl301ZwXbtQbuP4ZCv4RzQO5wPwcxMsg8lcEKRmeQEAmJtE/LwAVvbBRAkNbL1z5UrG/GGjy3V/1BZSr12p8JrPtZxk0Le/8S9vF5cikGR82qu1p2GkDnvpqbak7PkcbyriLSZlXa+MP/rDHYWpx2LVOYBjub0M6VXkJ20pju/JrMdIntQFWGMomwwIDAQAB";

    /**
     * 服务器异步通知页面路径
     */
    public static String notify_url = "http://localhost:8080/pay/notifyUrl";

    /**
     * 页面跳转同步通知页面路径
     */
    public static String return_url = "http://localhost:8080/pay/returnUrl";

    /**
     * 签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";

    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
