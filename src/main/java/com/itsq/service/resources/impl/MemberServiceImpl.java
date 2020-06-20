package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.entity.Member;
import com.itsq.mapper.MemberMapper;
import com.itsq.pojo.entity.UserOrder;
import com.itsq.service.resources.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsq.service.resources.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-12
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
@Autowired
    private UserOrderService userOrderService;


    @Override
    public int addMember(Member member) {

        int i = super.baseMapper.insert(member);
        UserOrder userOrder=new UserOrder(member.getManagerId(),member.getUserId(),member.getName(),member.getId(),0,member.getAmount(),member.getMoney());
        userOrderService.addUserOrder(userOrder);
        return i;
    }

    @Override
    @Transactional
    public int updateMemberById(Member member) {

        UserOrder userOrder=new UserOrder(member.getManagerId(),member.getUserId(),member.getName(),member.getId(),member.getStatus(),member.getAmount(),member.getMoney());
        userOrderService.addUserOrder(userOrder);
        if(member.getAmount()!=null&&member.getMoney()!=null){
        if(member.getStatus()!=null&&member.getStatus()==0){
          member.setAmount(member.getAmount().add(new BigDecimal(member.getMoney())));
            }
            if(member.getStatus()!=null&&member.getStatus()==1){
                member.setAmount(member.getAmount().subtract(new BigDecimal(member.getMoney())));
            }
        }
        member.setUpDate(new Date());
        return super.baseMapper.updateById(member);
    }

    @Override
    public Member selectById(Integer id) {
        return super.baseMapper.selectById(id);
    }

    @Override
    public int removeMember(Integer id) {
        return super.baseMapper.deleteById(id);
    }

    @Override
    public Page<Member> selectMemberPage(String name, String phone, Integer managerId,Integer userId, Integer pageIndex, Integer pageSize) {

      Page<Member> page=new Page<>(pageIndex,pageSize);

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("manager_id",managerId);
        if(userId!=null&&userId>0){
            queryWrapper.like("user_id",userId);
        }
        if(name!=null&&!name.equals("")){
            queryWrapper.like("name",name);
        }
        if(phone!=null&&!phone.equals("")){
            queryWrapper.like("phone",phone);
        }
        queryWrapper.orderByDesc("cre_date");
        return super.baseMapper.selectPage(page,queryWrapper);
    }
}
