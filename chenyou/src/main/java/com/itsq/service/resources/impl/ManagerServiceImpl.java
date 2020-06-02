package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsq.common.bean.ErrorEnum;
import com.itsq.common.constant.APIException;
import com.itsq.pojo.dto.*;
import com.itsq.pojo.entity.Manager;
import com.itsq.mapper.ManagerMapper;
import com.itsq.pojo.entity.SysMenu;
import com.itsq.service.resources.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.utils.BeanUtils;
import com.itsq.utils.CardNumVerify;
import com.itsq.utils.DateWhere;
import com.itsq.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
@Slf4j
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
    @Override
    public Optional<Manager> findManagerById(int userId) {
        Manager manager = super.baseMapper.selectById(userId);
        return Optional.ofNullable(manager);
    }

    @Override
    public Manager login(ManagerLoginReqDto dto) {
        Boolean check = dto == null || StringUtils.isEmpty(dto.getUserName()) || StringUtils.isEmpty(dto.getPassword());
        if (check) {
            throw new APIException(ErrorEnum.INVALID_PARAM);
        }
        String userName = dto.getUserName();
        String password = dto.getPassword();
        Optional<Manager> managerOptional = super.lambdaQuery().eq(Manager::getUserName, userName).oneOpt();//登录名是否存在
        if (!managerOptional.isPresent()) {
            throw new APIException(ErrorEnum.USER_NOT_EXITES);
        }
        if (managerOptional.get().getIsStatus() == 1) {
            throw new APIException(ErrorEnum.USER_ISSTATUS);
        }
        Manager manager = managerOptional.get();
        if (!MD5.getMd5(password, 32).equals(manager.getPassword())) {
            throw new APIException(ErrorEnum.USER_INFO_ERROR);
        }
        return manager;
    }

    @Override
    public Page pageAdmins(FindPageManagerParmeters dto) {
        Boolean check = dto == null || dto.getPageIndex() == null || dto.getPageSize() == null;
        if (check) {
            throw new APIException(ErrorEnum.INVALID_PARAM);
        }
        Page<Manager> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
        Manager manager = BeanUtils.copyProperties(dto, Manager.class);
        String phone = manager.getPhone();
        String identity = manager.getIdentity();
        String userName = manager.getUserName();
        String address = manager.getAddress();
        Integer isStatus = manager.getIsStatus();
        Integer sex = manager.getSex();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (sex != null) {
            queryWrapper.eq("sex", sex);
        }
        if (isStatus != null) {
            queryWrapper.eq("is_status", isStatus);
        }
        if (address != null) {
            queryWrapper.like("address", address);
        }
        if (userName != null) {
            queryWrapper.like("user_name", userName);
        }
        if (phone != null) {
            queryWrapper.like("phone", phone);
        }
        if (identity != null) {
            queryWrapper.like("identity", identity);
        }
        String creDate = dto.getCreDate();//创建时间
        String updDate = dto.getUpdDate();//修改时间
        //today tomorrow
        if (creDate != null && !"".equals(creDate)) {
            Map<String, Object> where = DateWhere.where(creDate);
            Object today = where.get("today");
            Object tomorrow = where.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("upd_date", tomorrow);
        }
        if (updDate != null && !"".equals(updDate)) {
            Map<String, Object> where1 = DateWhere.where(updDate);
            Object today = where1.get("today");
            Object tomorrow = where1.get("tomorrow");
            queryWrapper.gt("cre_date", today);
            queryWrapper.lt("upd_date", tomorrow);
        }

        queryWrapper.orderByDesc("cre_date");
        Page<Manager> page1 = super.page(page, queryWrapper);
        return page1;
    }


    @Override
    public void updatePassWord(updatePassWordDto dto) {
        Boolean check = dto == null || StringUtils.isEmpty(dto.getNewPassWord()) || StringUtils.isEmpty(dto.getOldPassWord());
        if (check) {
            throw new APIException(ErrorEnum.INVALID_PARAM);
        }
        if (dto.getChushiorUpdatePassWord() == 1) {//恢复初始密码
            Manager manager = new Manager();
            manager.setId(dto.getId());
            manager.setPassword(MD5.getMd5("123456", 32));
            super.baseMapper.updateById(manager);
        }
        if (dto.getChushiorUpdatePassWord() == 2) {//修改密码
            //判断原密码是否正确
            //根据用户ID查询原密码
            Manager manager = super.getBaseMapper().selectById(dto.getId());
            String managerOldPassWord = manager.getPassword();
            if (MD5.getMd5(dto.getOldPassWord(), 32).equals(managerOldPassWord)) {
                Manager manager1 = new Manager();
                manager1.setId(dto.getId());
                manager1.setPassword(MD5.getMd5(dto.getNewPassWord(), 32));
                super.baseMapper.updateById(manager1);
            } else {
                throw new APIException(ErrorEnum.USER_PASSWORDERROR);
            }
        }
    }

    @Override
    public void updateManagerInfo(Manager manager) {
        super.baseMapper.updateById(manager);
    }

    @Override
    public void saveManagerInfo(AddManagerReqDto addManagerReqDto) {
        Manager manager = BeanUtils.copyProperties(addManagerReqDto, Manager.class);
        String userName = addManagerReqDto.getUserName();
        String phone = addManagerReqDto.getPhone();
        String identity = addManagerReqDto.getIdentity();
        //判断手机号 登录名称 身份证
        if (!CardNumVerify.cardNumIsLegal(identity)) {
            log.info("身份证号码格式错误！");
            throw new APIException(ErrorEnum.USER_IDENTITYNOT);
        }
        Manager one = super.lambdaQuery().eq(Manager::getPhone, phone).one();
        Manager one1 = super.lambdaQuery().eq(Manager::getUserName, userName).one();
        Manager one2 = super.lambdaQuery().eq(Manager::getIdentity, identity).one();
        if (one != null) {
            throw new APIException(ErrorEnum.USER_PHONE);
        }
        if (one1 != null) {
            throw new APIException(ErrorEnum.USER_ADMINNAME);
        }
        if (one2 != null) {
            throw new APIException(ErrorEnum.USER_IDENTITY);
        }
        Integer sex = manager.getSex();
        if (sex == 0) {
            manager.setHeadImage("heardImages1.png");
        } else if (sex == 1) {
            manager.setHeadImage("heardImages2.png");
        }
        super.baseMapper.insert(manager);
    }
}
