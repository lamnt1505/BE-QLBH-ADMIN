package com.vnpt.mini_project_java.service.trademark;

import com.vnpt.mini_project_java.dto.TrademarkDTO;
import com.vnpt.mini_project_java.entity.Trademark;

import java.util.List;

public interface TrademarkService {
    List<TrademarkDTO> getAllTrademarkDTO();

    TrademarkDTO saveDTO(TrademarkDTO dto);

    Trademark getTrademarkById(long id);

    Trademark updateTramemark(long tradeID, TrademarkDTO dto);

    void deleteTrademarkById(long id);
}
