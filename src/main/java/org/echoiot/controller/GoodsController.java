package org.echoiot.controller;

import org.echoiot.entity.bean.CommonResult;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.AddGoodsDTO;
import org.echoiot.entity.dto.UpdateGoodsDTO;
import org.echoiot.entity.vo.InventoryVO;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.pojo.Goods;
import org.echoiot.entity.vo.SalesVO;
import org.echoiot.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/10 15:50
 */
@RestController
public class GoodsController {
    private final GoodsService service;

    public GoodsController(GoodsService service) {
        this.service = service;
    }

    /**
     * 查询所在店铺的所有商品
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/goods")
    public CommonResult<PageResult<Goods>> FindAllGoods(@Valid @RequestBody PageParamDTO ppDTO,HttpServletRequest request){
        HttpSession session=request.getSession();
        PageResult<Goods> allGoods = service.FindAllGoods(ppDTO,session);

        return new CommonResult<>(200, "OK", allGoods);
    }

    /**
     * 添加商品
     * @param goods
     * @param request
     * @return
     */
    @PostMapping("/Ad/goods/add")
    public CommonResult<String> Add(@Valid @RequestBody AddGoodsDTO goods, HttpServletRequest request){
        HttpSession session = request.getSession();
        int result =service.Add(goods,session);

        if(result > 0){
            return new CommonResult<>(200,"OK","商品添加成功！");
        }
        else{
            return new CommonResult<>(400,"NO","商品添加失败！");
        }
    }
    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/Ad/goods/fGoodById")
    public CommonResult<Object> FindUserByID(@RequestParam Long id){

        Goods goods=service.findGoodsByID(id);

        if(goods!=null){
            return new CommonResult<>(200, "OK", goods);
        }else{
            return new CommonResult<>(400, "NO", "无此商品！");
        }
    }
    /**
     * 删除商品
     * @param id
     * @return
     */
    @GetMapping("/Ad/goods/del")
    public CommonResult<String> Delete(@RequestParam Long id){

        int result = service.Delete(id);

        if(result>0){
            return new CommonResult<>(200, "OK", "删除商品成功！");
        }else{
            return new CommonResult<>(400, "NO", "删除商品失败！");
        }
    }

    /**
     * 更新商品信息
     * @param updateGoodsDTO
     * @return
     */
    @PostMapping ("/Ad/goods/update")
    public CommonResult<String> Update(@Valid @RequestBody UpdateGoodsDTO updateGoodsDTO){

        int result = service.Update(updateGoodsDTO.getId(), updateGoodsDTO);

        if(result>0){
            return new CommonResult<>(200, "OK", "修改商品信息成功！");
        }else{
            return new CommonResult<>(400, "NO", "修改商品信息失败！");
        }
    }

    /**
     * 查询所在店铺的所有商品的库存
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @GetMapping("/goods/inventory")
    public CommonResult<PageResult<InventoryVO>>GoodsInventory(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();

         PageResult<InventoryVO> allGoods = service.Inventory(ppDTO,session);

        return new CommonResult<>(200, "OK", allGoods);
    }

    /**
     * 查询所在店铺的所有商品的销量
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @GetMapping("/goods/sales")
    public CommonResult<PageResult<SalesVO>>Salesofgoods(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();

        PageResult<SalesVO> allGoods = service.Sales(ppDTO,session);

        return new CommonResult<>(200, "OK", allGoods);
    }
}
