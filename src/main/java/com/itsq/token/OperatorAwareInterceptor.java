package com.itsq.token;

import com.alibaba.fastjson.JSON;
import com.itsq.enums.EnumTokenType;
import com.itsq.pojo.entity.Manager;
import com.itsq.pojo.entity.User;
import com.itsq.service.resources.ManagerService;
import com.itsq.service.resources.UserService;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.CastUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 拦截请求，设置当前操作者
 *
 * @author sq
 */
@Slf4j
@Component
public class OperatorAwareInterceptor extends HandlerInterceptorAdapter {

    public static final String SESSION_USER = "X-Token";

    private final UserService userService;
    private final ManagerService managerService;

    public OperatorAwareInterceptor(UserService userService, ManagerService managerService) {
        this.userService = userService;
        this.managerService = managerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        try {
            OperatorAware.clear(); // MUST clear
            String token = req.getHeader(SESSION_USER);
            if(StringUtils.isNotEmpty(token)){
                String tokenType = token.split(" ")[0];
                String tokenInfo =token.split(" ")[1];
                AuthToken authToken = JWTTokenUtil.buildAuthToken(tokenInfo);
                if(tokenType.equals(EnumTokenType.MANAGER.getCode())){
                    Optional<Manager> managerOptional = this.managerService.findManagerById(CastUtil.castInt(authToken.getUserId()));
                    managerOptional.ifPresent(manager -> OperatorAware.setCurrentUser(BeanUtils.copyProperties(manager, CurrentUser.class)));
                }
                if(tokenType.equals(EnumTokenType.BEARER.getCode())){
                    User user = userService.getById(authToken.getUserId());
                    if (user != null) {
                        OperatorAware.setCurrentUser(BeanUtils.copyProperties(user,CurrentUser.class));
                        log.debug("CurrentAuditor: {}", JSON.toJSONString(user));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return true;
    }
}
