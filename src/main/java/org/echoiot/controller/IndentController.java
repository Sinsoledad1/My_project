package org.echoiot.controller;

import org.echoiot.entity.bean.CommonResult;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.IndentGoodsDTO;
import org.echoiot.entity.vo.IndentShowVO;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.dto.ReturnsDTO;
import org.echoiot.entity.pojo.Indent;
import org.echoiot.service.IndentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 14:07
 */
@RestController
public class IndentController {

    private final IndentService indentService;

    public IndentController(IndentService indentService) {
        this.indentService = indentService;
    }

    /**
     * 查询所在店铺的所有订单
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/Ad/indent")
    public CommonResult<PageResult<IndentShowVO>> FindAllIndent(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();
        PageResult<IndentShowVO> AllIndent= indentService.FindAllIndent(ppDTO,session);

        return new CommonResult<>(200, "OK", AllIndent);
    }

    /**
     * 查询所在店铺的所有未支付的订单
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/Ad/npindent")
    public CommonResult<PageResult<IndentShowVO>> NotPay(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();
        PageResult<IndentShowVO> AllIndent= indentService.NotPay(ppDTO,session);

        return new CommonResult<>(200, "OK", AllIndent);
    }

    /**
     * 查询所在店铺的所有已支付的订单
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/Ad/pdindent")
    public CommonResult<PageResult<IndentShowVO>> Payed(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();
        PageResult<IndentShowVO> AllIndent= indentService.Payed(ppDTO,session);

        return new CommonResult<>(200, "OK", AllIndent);
    }
    /**
     * 查询登录者创建的订单
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/Ad/myindent")
    public CommonResult<PageResult<IndentShowVO>> MyIndent(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();
        PageResult<IndentShowVO> AllIndent= indentService.MyIndent(ppDTO,session);

        return new CommonResult<>(200, "OK", AllIndent);
    }


    /**
     * 添加订单
     * @param indentGoodsDTO
     * @param request
     * @return
     */
    @PostMapping("/indent/add")
    public CommonResult<Object> Add(@Valid @RequestBody IndentGoodsDTO indentGoodsDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        Indent indent=indentService.Add(indentGoodsDTO,session);
        if(indent!=null){
            return new CommonResult<>(200,"OK",indent);
        }
        else{
            return new CommonResult<>(400,"NO","订单添加失败！");
        }
    }

    /**
     * 支付订单
     * @param id
     * @return
     */
    @GetMapping("/indent/pay")
    public CommonResult<String> Pay(@RequestParam  Long id){
       int result=indentService.Pay(id);
        if(result>0){
            return new CommonResult<>(200,"OK","支付成功！");
        }
        else{
            return new CommonResult<>(400,"NO","支付失败！");
        }
    }

    /**
     * 取消支付
     * @param id
     * @return
     */
    @GetMapping("/indent/cpay")
    public CommonResult<String> CancelPay(@RequestParam Long id){
        int result=indentService.CancelPay(id);
        if(result>0){
            return new CommonResult<>(200,"OK","取消支付成功！");
        }
        else{
            return new CommonResult<>(400,"NO","取消支付失败！");
        }
    }

    /**
     * 退货
     * @param returnsDTO
     * @return
     */
    @PostMapping("/indent/returns")
    public CommonResult<String> Returns(@Valid @RequestBody ReturnsDTO returnsDTO,HttpServletRequest request){
        HttpSession session=request.getSession();
        int result=indentService.Returns(returnsDTO,returnsDTO.getId(),session);
        if(result>0){
            return new CommonResult<>(200,"OK","退货成功！");
        }
        else{
            return new CommonResult<>(400,"NO","退货失败！");
        }
    }

    /**
     * 删除订单信息
     * @param id
     * @return
     */
    @GetMapping("/Ad/indent/del")
    public CommonResult<String> Delete(@RequestParam Long id){

        int result = indentService.Delete(id);

        if(result>0){
            return new CommonResult<>(200, "OK", "删除订单信息成功！");
        }else{
            return new CommonResult<>(400, "NO", "删除订单信息失败！");
        }
    }
}
