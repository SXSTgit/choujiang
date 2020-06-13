package com.itsq.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Data
public class PlayersSellDto implements Serializable {

    private Integer  id;

    List<Integer> pbaIdIntegers;


}
