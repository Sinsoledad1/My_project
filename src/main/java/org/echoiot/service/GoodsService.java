package org.echoiot.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.echoiot.dao.GoodsDao;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.AddGoodsDTO;
import org.echoiot.entity.dto.UpdateGoodsDTO;
import org.echoiot.entity.vo.InventoryVO;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.pojo.Goods;
import org.echoiot.entity.pojo.User;
import org.echoiot.entity.vo.SalesVO;
import org.echoiot.exception.BasicException;
import org.echoiot.util.DateTimeTransferUtil;
import org.echoiot.util.IdWorker;
import org.echoiot.util.PageParamCheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/10 16:01
 */
@Service
@Transactional(rollbackForClassName = "Exception.class")
public class GoodsService {
    private final GoodsDao goodsDao;


    private final IdWorker idWorker;
    private final PageParamCheckUtil ppC;


    public GoodsService(GoodsDao goodsDao, IdWorker idWorker, PageParamCheckUtil ppC) {
        this.goodsDao = goodsDao;
        this.idWorker = idWorker;
        this.ppC = ppC;
    }

    /**
     * 添加商品
     * @param goods
     * @param session
     * @return
     */
    public int Add(AddGoodsDTO goods, HttpSession session){


        Goods newgoods= new Goods();
        BeanUtils.copyProperties(goods,newgoods);


        //设置商品id
        newgoods.setId(idWorker.nextId());

        User user = (User) session.getAttribute("User");
        //设置店铺id
        if(user.getStore_id()==null){
            throw new BasicException("未加入任何店铺，添加失败！");
        }
        else{
            newgoods.setStore_id(user.getStore_id());
        }


        //设置添加时间
        newgoods.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());

        try{
            return goodsDao.add(newgoods);
        }catch (Exception e){
            throw new BasicException("已存在该商品！");
        }
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有商品
     */
    public PageResult<Goods> FindAllGoods(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有商品
        List<Goods> allGoods =goodsDao.findAllGoods(user.getStore_id());

        return new PageResult<>(page.getPages(), page.getTotal(), allGoods);
    }

    /**
     * 通过id查询商品
     * @param id
     * @return
     */
    public Goods findGoodsByID(Long id){
        return goodsDao.findgoods(id);
    }

    /**
     * 店铺删除商品
     * @param id
     * @return
     */
    public int Delete(Long id){
        //删除商品
        return goodsDao.delete(id);
    }

    /**
     * 修改商品内容
     * @param id
     * @param updateGoodsDTO
     */
    public int Update(Long id, UpdateGoodsDTO updateGoodsDTO){
        //通过id查找商品
        Goods goods=goodsDao.findgoods(id);

        if(goods==null){
            throw new BasicException("没有此商品");
        }else{
            BeanUtils.copyProperties(updateGoodsDTO,goods);
            return goodsDao.update(goods);
        }

    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有商品的库存
     */
    public PageResult<InventoryVO> Inventory(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        List<InventoryVO> List= goodsDao.Inventory(user.getStore_id());
        return new PageResult<>(page.getPages(), page.getTotal(),List );
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有商品的销量
     */
    public PageResult<SalesVO> Sales(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        List<SalesVO> List= goodsDao.Sales(user.getStore_id());

        return new PageResult<>(page.getPages(), page.getTotal(),List );
    }
}
