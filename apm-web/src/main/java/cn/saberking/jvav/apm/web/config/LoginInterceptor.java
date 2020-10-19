package cn.saberking.jvav.apm.web.config;

import cn.saberking.jvav.apm.web.constants.Constants;
import cn.saberking.jvav.apm.web.utils.HttpContextUtil;
import cn.saberking.jvav.apm.web.utils.JwtUtils;
import cn.saberking.jvav.apm.web.utils.Result;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求投中获取token
        String token = request.getHeader("Authorization");

        token = token.replace(Constants.TOKEN_PREFIX, "");

        System.out.println("进入拦截器" + token);

        try {
            JwtUtils.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            setReturn(response, 400, "用户未登录");
        } catch (Exception e) {
            setReturn(response, 601, "token失效");
        }
        return false;
    }


    /**
     * 返回错误信息
     *
     * @param response 响应
     * @param status   状态码
     * @param msg      提示
     * @throws IOException 异常
     */
    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        //UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result build = Result.build(status, msg);
        String json = JSON.toJSONString(build);
        response.getWriter().print(json);
    }

}
