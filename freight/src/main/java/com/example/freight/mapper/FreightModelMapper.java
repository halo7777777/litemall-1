package com.example.freight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.freight.model.po.FreightModelPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public interface FreightModelMapper extends BaseMapper<FreightModelPo> {
}
