package com.vnpt.mini_project_java.restcontroller;

import com.vnpt.mini_project_java.dto.TrademarkDTO;
import com.vnpt.mini_project_java.entity.Trademark;
import com.vnpt.mini_project_java.service.product.ProductService;
import com.vnpt.mini_project_java.service.trademark.TrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/trademark")
public class TrademarkRestController {

    @Autowired
    private TrademarkService trademarkService;

    @GetMapping("/getall")
    public ResponseEntity<?> getListtrademarkdto(){
        return ResponseEntity.ok(trademarkService.getAllTrademarkDTO());
    }

    @GetMapping("/gettrademark")
    public ResponseEntity<List<TrademarkDTO>> getList(){
        List<TrademarkDTO> trademarkDTOS = trademarkService.getAllTrademarkDTO();
        return ResponseEntity.ok(trademarkDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrademarkDTO> getTrademarkById(@PathVariable(name = "id") Long id){
        Trademark trademark = trademarkService.getTrademarkById(id);

        TrademarkDTO trademarkResponse = new TrademarkDTO(trademark);

        return ResponseEntity.ok().body(trademarkResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<TrademarkDTO> createTrademark(TrademarkDTO dto){
        try{
            TrademarkDTO createdTrademark = trademarkService.saveDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrademark);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrademarkDTO> updateTrademark(@PathVariable long id,
                                                        TrademarkDTO trademarkDTO){
       try{
           Trademark trademark = trademarkService.updateTramemark(id,trademarkDTO);
           TrademarkDTO updateDTO = new TrademarkDTO(trademark);
           System.out.println(updateDTO);
           return ResponseEntity.ok(updateDTO);
       }catch (EntityNotFoundException ex){
           System.out.println("Error" + ex.getMessage());
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrademark(@PathVariable long id){
        try{
            trademarkService.deleteTrademarkById(id);
            return ResponseEntity.ok().body("{\"status\":\"success\"}");
        }catch (EntityNotFoundException ex){
            System.out.println("Error" + ex.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
