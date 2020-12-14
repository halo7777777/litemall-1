package com.example.freight.controller;

import cn.edu.xmu.ooad.annotation.Audit;
import cn.edu.xmu.ooad.annotation.LoginUser;
import cn.edu.xmu.ooad.model.VoObject;
import cn.edu.xmu.ooad.util.Common;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ResponseUtil;
import cn.edu.xmu.ooad.util.ReturnObject;
import com.example.freight.model.vo.FreightModelInfoVo;
import com.example.freight.model.vo.WeightModelInfoVo;
import com.example.freight.service.FreightService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: core
 * @description: 运输服务控制器
 * @author: alex101
 * @create: 2020-12-09 16:57
 **/
@RestController
@RequestMapping(value = "/freight", produces = "application/json;charset=UTF-8")
public class FreightController {

    private static final Logger logger = LoggerFactory.getLogger(FreightController.class);


    @Autowired
    FreightService freightService;

    //此处需要有一个删除商品关联的运费模板信息的dubbo服务




    /**
    * @Description: 管理员设置默认运费模板
    * @Param: [shopId, id]
    * @return: java.lang.Object
    * @Author: alex101
    * @Date: 2020/12/9
    */
    @ApiOperation(value = "设置默认运费模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopid", value = "商户ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })
    @Audit
    @PostMapping("shops/{shopId}/freight_models/{id}/default")
    @ResponseBody
    public Object setDefaultFreightModel(@PathVariable Long shopId, @PathVariable Long id) {
        logger.debug("setDefaultFreightModel shopid = " + shopId + " id = " + id);
        ReturnObject returnObject = freightService.setDefaultFreightModel(shopId, id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }

    }



    /** 
    * @Description: 返回模板概要
    * @Param: [shopId, id] 
    * @return: java.lang.Object 
    * @Author: alex101
    * @Date: 2020/12/10 
    */
    @ApiOperation(value = "/shops/{shopId}/freightmodels/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(name = "shopId", value = "商户ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })

    @Audit
    @GetMapping("/shops/{shopId}/freightmodels/{id}")
    @ResponseBody
    public Object getFreightModelSummary(@PathVariable Long shopId, @PathVariable Long id) {
        logger.debug("getFreightModelSummary shopId: " + shopId + " id = " + id);
        ReturnObject returnObject = freightService.getFreightModelSummary(shopId, id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    /** 
    * @Description: 增加运费模板 
    * @Param: [id, vo] 
    * @return: java.lang.Object 
    * @Author: alex101
    * @Date: 2020/12/11 
    */
    @Audit
    @PostMapping("/shops/{id}/freightmodels")
    public Object addFreightModel(@PathVariable Long id, @RequestBody FreightModelInfoVo vo) {
        logger.debug("set freight model id: " + id + " feightmodel info " + vo);
        ReturnObject returnObject = freightService.addFreightModel(id, vo);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }


    /** 
    * @Description: 获取店铺中商品的运费模板
    * @Param: [id, name, page, pageSize] 
    * @return: java.lang.Object 
    * @Author: alex101
    * @Date: 2020/12/12 
    */
    @Audit
    @GetMapping("/shops/{id}/freightmodels")
    public Object getGoodsFreightModels(@PathVariable Long id, @RequestParam(required = false) String name, @RequestParam(required = false)  Integer page, @RequestParam(required = false) Integer pageSize)
    {
        logger.debug("getGoodsFreightModels: page = "+ page +"  pageSize ="+pageSize+" name="+name);
        page = (page == null)?1:page;
        pageSize = (pageSize == null)?10:pageSize;
        ReturnObject<PageInfo<VoObject>> returnObject = freightService.getGoodsFreightModel(id,name,page,pageSize);
        return Common.getPageRetObject(returnObject);
    }

    @Audit
    @PutMapping("/shops/{shopId}/freightmodels/{id}")
    public Object modifyFreightModel(@PathVariable Long shopId,@PathVariable Long id,@RequestBody FreightModelInfoVo vo)
    {
        ReturnObject returnObject = freightService.modifyFreightModel(shopId,id,vo);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /** 删除运费模板
    * @Description:
    * @Param: [shopId, id]
    * @return: java.lang.Object
    * @Author: alex101
    * @Date: 2020/12/14
    */
    @Audit
    @DeleteMapping("/shops/{shopId}/freightmodels/{id}")
    public Object deleteFreightModel(@PathVariable Long shopId,@PathVariable Long id)
    {
        ReturnObject returnObject = freightService.deleteFreightModel(shopId,id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /**
    * @Description:  新增重量明细
    * @Param: [shopId, id, vo, result, httpServletResponse]
    * @return: java.lang.Object
    * @Author: alex101
    * @Date: 2020/12/14
    */
    @Audit
    @PostMapping("/shops/{shopId}/freightmodels/{id}/weightItems")
    public Object addWeightItems(@PathVariable Long shopId, @PathVariable Long id, @RequestBody WeightModelInfoVo vo, BindingResult result, HttpServletResponse httpServletResponse)
    {
        Object o = Common.processFieldErrors(result, httpServletResponse);
        if(o != null){
            return o;
        }
        ReturnObject returnObject = freightService.addWeightItem(shopId,id,vo);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    @Audit
    @GetMapping("/shops/{shopId}/freightmodels/{id}/weightItems")
    public Object getWeightItems(@PathVariable Long shopId,@PathVariable Long id)
    {
        ReturnObject returnObject  = freightService.getWeightItem(shopId,id);
        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getListRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }

    /*
     * @Description: 管理员克隆店铺的运费模板
     * @Param:  [shopId, id]
     * @return: {@link java.lang.Object}
     * @Author: lzn
     * @Date 2020/12/10
     **/
    @ApiOperation(value = "/shops/{shopId}/freightmodels/{id}/clone")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String",name = "authorization",value = "Token",required = true),
            @ApiImplicitParam(name = "shopId",value ="商户ID",required = true,dataType = "Integer",paramType = "path"),
            @ApiImplicitParam(name = "id",value ="id",required = true,dataType = "Integer",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0,message = "成功"),
            @ApiResponse(code = 504, message = "操作id不存在")
    })

    @Audit
    @PostMapping("shops/{shopId}/freightmodels/{id}/clone")
    @ResponseBody
    public Object cloneFreightModel(@PathVariable Long shopId, @PathVariable Long id)
    {
        logger.debug("cloneFreightModel shopId:"+shopId+" id = "+id);
        ReturnObject returnObject = freightService.cloneFreightModel(shopId, id);
        if (returnObject.getCode() == ResponseCode.OK)
        {
            return Common.getRetObject(returnObject);
        }
        else
        {
            return Common.decorateReturnObject(returnObject);
        }
    }


}
