package com.itsq.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itsq.pojo.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 史先帅
 * @since 2020-05-12
 */
public interface MemberService extends IService<Member> {



    //添加会员
    int addMember(Member member);
    //
    int updateMemberById(Member member);

    Member selectById(Integer id);

    int removeMember(Integer id);

    Page<Member>  selectMemberPage(String name ,String phone ,Integer managerId,Integer userId,Integer pageIndex,Integer pageSize);

}
