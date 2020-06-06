package com.itsq.pojo.dto;

import com.itsq.utils.RandomUtil;
import lombok.Data;

@Data
public class normalBuyParamV2DTO {
    private String outTradeNo;
    private String productId;
    private String tradeUrl;

    public normalBuyParamV2DTO() {
    }

    public normalBuyParamV2DTO(String outTradeNo, String productId, String tradeUrl) {
        this.outTradeNo = outTradeNo;
        this.productId = productId;
        this.tradeUrl = tradeUrl;
    }
}
