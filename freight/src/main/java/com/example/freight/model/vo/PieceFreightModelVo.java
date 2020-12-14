package com.example.freight.model.vo;

import com.example.freight.model.bo.FreightModelBo;
import com.example.freight.model.bo.PieceFreightModelBo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @program:
 * @despciption: PieceFreightModelVo
 * @author: lzn
 * @create: 2020/12/14 0:39
 **/

@Data
public class PieceFreightModelVo
{
    @NotNull
    private Long regionId;
    private int firstItem;
    private long firstItemPrice;
    private int additionalItems;
    private long additionalItemsPrice;
}

