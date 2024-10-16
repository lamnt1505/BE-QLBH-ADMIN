package com.vnpt.mini_project_java.dto;

import com.vnpt.mini_project_java.entity.Product;
import com.vnpt.mini_project_java.entity.Trademark;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Getter
@Setter
public class TrademarkDTO {

    private Long tradeID;

    private String tradeName;

    public TrademarkDTO() {
    }

    public TrademarkDTO(Trademark trademark){
        this.tradeID = trademark.getTradeID();
        this.tradeName = trademark.getTradeName();
    }
}
