package com.itsq.pojo.dto;

import com.itsq.utils.RandomUtil;
import lombok.Data;

@Data
public class NormalBuyParamDTO {
    private String outTradeNo;
    private String productId;
    private String tradeUrl;

    public NormalBuyParamDTO() {
    }

    public NormalBuyParamDTO(String outTradeNo, String productId, String tradeUrl) {
        this.outTradeNo = outTradeNo;
        this.productId = productId;
        this.tradeUrl = tradeUrl;
    }
}
