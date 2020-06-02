package com.itsq.token;

import com.itsq.common.base.orm.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author justin
 */
@Slf4j
public class OperatorAware implements AuditorAware<Operator> {
    // Thread local variable containing each request user

    private static final ThreadLocal<CurrentUser> userContext = new ThreadLocal<>();

    public static void setCurrentUser(CurrentUser sessionUser) {
        userContext.set(sessionUser);
    }

    public static void clear() {
        userContext.remove();
    }

    public static Optional<CurrentUser> getCurrentUser() {
        CurrentUser user = userContext.get();
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<Operator> getCurrentAuditor() {
        CurrentUser user = userContext.get();
        if (user == null) {
            return Optional.empty();
        }
        Operator operator = new Operator();
        operator.setId(user.getId());
        //如userName不存在,取手机号显示
        String userName = Optional.ofNullable(user.getUserName()).orElse(user.getPhone());
        operator.setUserName(userName);
        log.debug("OperatorAware, userName:{}, cellphone:{}, currentUserName:{}", user.getUserName(), user.getPhone(), userName);
        return Optional.of(operator);
    }
}
