package com.itsq.service.resources.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsq.pojo.entity.*;
import com.itsq.mapper.PlayersArmsMapper;
import com.itsq.service.resources.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Service
public class PlayersArmsServiceImpl extends ServiceImpl<PlayersArmsMapper, PlayersArms> implements PlayersArmsService {


    @Autowired
    private PlayerBoxArmsService playerBoxArmsService;
    @Autowired
    private BoxArmsService boxArmsService;
    @Autowired
    private PlayersService playersService;
    @Autowired
    private BoxService boxService;
    /**
     * 按比例随机抽取一项
     * @param list 奖品列表
     * @return 类型值
     */
    @Transactional
    public PlayerBoxArms ratioExtract(PlayerBoxArms playerBoxArms) {
        //获得箱子关联武器
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("box_id",playerBoxArms.getBoxId());
        wrapper.eq("is_status","0");
        List<BoxArms> list=boxArmsService.list(wrapper);
        //非空判断
        if (list==null || list.size()<1) {
            return null;
        }
        //占比之和
        double sum=0.00;
        //分段数组(20,30,60)
        double[] subArray=new double[list.size()+1];
        //将概率分段
        for (int i = 0; i < list.size(); i++) {
            subArray[i]=sum;
            //这里除要考虑奖品所占比重外还要将奖品数量计算分段其中
            sum+=list.get(i).getChance()*list.get(i).getCount();
        }
        //加上取最大的值
        subArray[subArray.length-1]=sum;

        /* 产生随机数 */
        Random random=new Random();
        double rand = random.nextDouble()*sum;

        //返回字符
        Integer field=null;
        for (int i = 0; i < subArray.length; i++) {
            if (i==subArray.length-1) {
                break;
            }
            if (rand>=subArray[i] && rand<subArray[i+1]) {
                field=list.get(i).getArmsId();
                break;
            }
        }
        if(field!=null){
            playerBoxArms.setArmsId(field);
            playerBoxArms.setType(0);
            if(!playerBoxArmsService.save(playerBoxArms)){
                return null;
            }
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("box_id",playerBoxArms.getBoxId());
            queryWrapper.eq("arms_id",playerBoxArms.getArmsId());
            BoxArms boxArms=boxArmsService.getOne(queryWrapper);
            boxArms.setCount(boxArms.getCount()-1);
            if(!boxArmsService.updateById(boxArms)){
                return null;
            }
            Players players=playersService.getById(playerBoxArms.getPlayerId());
            Box box=boxService.getById(playerBoxArms.getBoxId());
            BigDecimal count=players.getBalance().subtract(box.getPrice());
            players.setBalance(count);
            if(! playersService.updateById(players)){
                return null;
            }
        }

        return playerBoxArms;
    }

}
