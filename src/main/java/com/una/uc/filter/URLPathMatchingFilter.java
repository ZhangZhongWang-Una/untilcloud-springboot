package com.una.uc.filter;

import com.una.uc.service.AdminPermissionService;
import com.una.uc.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Set;

/**
 * @author Una
 * @date 2020/3/6 22:44
 */
@Slf4j
public class URLPathMatchingFilter extends PathMatchingFilter {
    @Autowired
    AdminPermissionService adminPermissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        if (null==adminPermissionService) {
            adminPermissionService = SpringContextUtil.getContext().getBean(AdminPermissionService.class);
        }

        String requestAPI = getPathWithinApplication(request);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            log.info("---------------- 需要登陆 ----------------");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("code","403");
            res.put("message","需要登录");
            res.put("data", null);
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();

            return false;
        }

        // 判断访问接口是否需要过滤（数据库中是否有对应信息）
        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if (!needFilter) {
            log.info("---------------- 接口： "+ requestAPI + " 无需权限----------------");
            return true;
        } else {
            // 判断当前用户是否有相应权限
            boolean hasPermission = false;
            String account = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(account);
            for (String api : permissionAPIs) {
                // 匹配前缀
                if (requestAPI.startsWith(api)) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                log.info("---------------- 访问权限： "+ requestAPI + " 验证成功----------------");
                return true;
            } else {
                log.info("---------------- 当前用户没有访问接口： "+ requestAPI + " 的权限----------------");
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                JSONObject res = new JSONObject();
                res.put("code","403");
                res.put("message","没有权限");
                res.put("data", null);
                PrintWriter out = null ;
                out = response.getWriter();
                out.write(res.toString());
                out.flush();
                out.close();
                return false;
            }
        }
    }
}
