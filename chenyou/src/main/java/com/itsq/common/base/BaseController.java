package com.itsq.common.base;

import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.token.CurrentUser;
import com.itsq.token.OperatorAware;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author: sq
 * @Description:
 * @Date: 19-4-10  下午4:36
 */
@Scope("prototype")
public abstract class BaseController{

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected CurrentUser currentUser() {
        Optional<CurrentUser> optional = OperatorAware.getCurrentUser();
        CurrentUser currentUser = optional.orElse(null);
        if (currentUser == null || currentUser.getId() == null) {
            throw new APIException(ErrorEnum.USER_AUTH_FAILED);
        }
        return currentUser;
    }


}
