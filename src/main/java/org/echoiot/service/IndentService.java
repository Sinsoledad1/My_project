package org.echoiot.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.echoiot.dao.*;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.IndentGoodsDTO;
import org.echoiot.entity.vo.IndentShowVO;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.dto.ReturnsDTO;
import org.echoiot.entity.pojo.*;
import org.echoiot.exception.BasicException;
import org.echoiot.util.DateTimeTransferUtil;
import org.echoiot.util.IdWorker;
import org.echoiot.util.PageParamCheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 15:06
 */
@Service
@Transactional(rollbackForClassName = "Exception.class")
public class IndentService {
    private final IdWorker idWorker;
    private final PageParamCheckUtil ppC;

    private final CustomerDao customerDao;
    private final GoodsDao goodsDao;
    private final IndentGoodsDao indentGoodsDao;
    private final IndentDao indentDao;
    private final UserDao userDao;
    private final StoreDao storeDao;
    public IndentService(IdWorker idWorker, PageParamCheckUtil ppC, CustomerDao customerDao, GoodsDao goodsDao, IndentGoodsDao indentGoodsDao, IndentDao indentDao, UserDao userDao, StoreDao storeDao) {
        this.idWorker = idWorker;
        this.ppC = ppC;
        this.customerDao = customerDao;
        this.goodsDao = goodsDao;
        this.indentGoodsDao = indentGoodsDao;
        this.indentDao = indentDao;
        this.userDao = userDao;
        this.storeDao = storeDao;
    }

    /**
     * 添加订单
     * @param indentGoodsDTO
     * @param session
     * @return
     */
    public Indent Add(IndentGoodsDTO indentGoodsDTO, HttpSession session){

        //取出session中的用户
        User user = (User) session.getAttribute("User");
        //创建一个订单
        Indent indent=new Indent();
        //设置id
        indent.setId(idWorker.nextId());
        //设置店铺id
        indent.setStore_id(user.getStore_id());
        //设置店铺名字
        Store store=storeDao.FindStorebyID(user.getStore_id());
        indent.setStore_name(store.getStore_name());
        //创建顾客id
        indent.setCustomer_id(idWorker.nextId());
        //设置销售者id
        indent.setSalesman_id(user.getId());
        //设置支付状态
        indent.setPay_status(0);
        //设置退货状态
        indent.setReturn_status(0);
        //设置买家电话
        indent.setDelivery_phone(indentGoodsDTO.getDelivery_phone());
        //添加顾客进入数据库
        Customer customer=new Customer();
        customer.setId(indent.getId());
        customer.setStore_id(user.getStore_id());
        customer.setIndent_id(indent.getId());
        customer.setPhone(indentGoodsDTO.getDelivery_phone());
        customer.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());
        customerDao.add(customer);

        //该订单打折前价格
        BigDecimal Total=new BigDecimal(0);
        //该订单的总价格
        BigDecimal TotalAfter=new BigDecimal(0);
        for (Map.Entry<Long,Integer> entry:indentGoodsDTO.getGoods().entrySet()) {
            //设置订单子表的数据
            IndentGoods indentGood=new IndentGoods();
            //生成id
            indentGood.setId(idWorker.nextId());
            indentGood.setIndent_id(indent.getId());
            //通过用户传入的id找到货物信息
            Goods goods=goodsDao.findgoods(entry.getKey());
            indentGood.setGoods_name(goods.getGoods_name());
            indentGood.setStore_id(goods.getStore_id());
            //设置货物id
            indentGood.setGoods_id(goods.getId());
            //退货状态(0.未退货, 1.申请退货, 2.已退货)
            indentGood.setReturn_status(0);
            indentGood.setGoods_price(goods.getGoods_price());
            indentGood.setGoods_discount(goods.getGoods_discount());
            //获取商品数量
            indentGood.setNumber(entry.getValue());
            //修改该商品库存
            if(goods.getGoods_repertory()<=0||goods.getGoods_repertory()<indentGood.getNumber()){
                throw new BasicException("库存不足！");
            }
            goods.setGoods_repertory(goods.getGoods_repertory()-indentGood.getNumber());
            goodsDao.updateRepertory(goods);
            //计算商品价格 P：单价 num:数量 T：总价 D：折扣
            Integer num=entry.getValue();
            BigDecimal P=goods.getGoods_price();
            BigDecimal T=P.multiply(BigDecimal.valueOf(num));
            //将打折前价格加入总订单
            Total=T.add(Total);
            BigDecimal D=goods.getGoods_discount();
            if(D!=null&&D.compareTo(BigDecimal.ZERO)!=0){
                D=D.divide(BigDecimal.valueOf(10));
                T=T.multiply(D);
            }
            //将打折后计算出的价格加入总订单
            TotalAfter=TotalAfter.add(T);
            //设置最终该商品的总价格
            indentGood.setTotal_price(T);
            indentGood.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());
            indentGood.setStore_id(user.getStore_id());
            //加入表格中
            indentGoodsDao.add(indentGood);
        }

        //设置该订单打折前的总价格
        indent.setTotal_price(Total);
        //设置该订单打折后的总价格
        indent.setDiscount_price(TotalAfter);
        //设置备注
        indent.setRemark(indentGoodsDTO.getRemark());
        //设置添加时间
        indent.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());
        //设置配送状态
        indent.setDelivery_status(indentGoodsDTO.getDelivery_status());
        if(indentGoodsDTO.getDelivery_status()==1){
            //设置配送地址
            indent.setDelivery_address(indentGoodsDTO.getDelivery_address());
            //设置配送人员id
            User user1=userDao.userLogin(indentGoodsDTO.getDelivery_man_phone());
            indent.setDelivery_man_id(user1.getId());
        }

        indentDao.add(indent);

        return indent;
    }

    /**
     * 支付订单
     * @param id
     * @return
     */
    public int Pay(Long id){
        //通过id查找订单
       Indent indent=indentDao.find(id);

        if(indent==null){
            throw new BasicException("没有此订单！");
        }
        if(indent.getPay_status()==1){
            throw new BasicException("该订单已支付！");
        }
        //修改支付状态至1：已支付
        indent.setPay_status(1);
        //修改支付时间
        indent.setPay_time(DateTimeTransferUtil.getNowTimeStamp());
        if(indent.getDelivery_status()==1){
            indent.setDelivery_time(DateTimeTransferUtil.getNowTimeStamp());
            //将配送状态改成配送中
            indent.setDelivery_status(3);
        }
        return indentDao.pay(indent);

    }

    /**
     * 取消支付
     * @param id
     * @return
     */
    public int CancelPay(Long id){
        //通过id查找订单
        Indent indent=indentDao.find(id);

        if(indent==null){
            throw new BasicException("没有此订单！");
        }
        if(indent.getPay_status()==1){
            throw new BasicException("该订单已支付！");
        }
        if(indent.getPay_status()==2){
            throw new BasicException("该订单已取消支付！");
        }
        //修改支付状态至2：取消支付
        indent.setPay_status(2);
        //修改支付时间
        indent.setPay_time(null);
        if(indent.getDelivery_status()==1){
            indent.setDelivery_time(null);
        }
        return indentDao.pay(indent);
    }

    /**
     * 退货
     * @param
     * @return
     */
    public int Returns(ReturnsDTO returnsDTO,Long id,HttpSession session){
        //通过id查找订单
        Indent indent=indentDao.find(id);

        User user= (User) session.getAttribute("User");

        if(indent==null){
            throw new BasicException("没有此订单！");
        }
        if(indent.getPay_status()==2){
            throw new BasicException("该订单已取消支付！");
        }
        if(indent.getReturn_status()==1||indent.getReturn_status()==2){
            throw new BasicException("该订单已经完成过退货操作！");
        }
        //全部退货
        if(returnsDTO.getReturn_status()==1){
            //修改库存
            LinkedList<String> Name=indentGoodsDao.findName(id);
            for(String gname : Name ){
                //通过商品名称找到该商品
                Goods goods=goodsDao.findgoodsbyname(gname);
                Integer num=indentGoodsDao.findNumber(id,gname);
                goods.setGoods_repertory(goods.getGoods_repertory()+num);
                goodsDao.updateRepertory(goods);
            }

            //在订单商品单上修改
            indentGoodsDao.returnAll(id);

            //在订单单上修改
            indent.setReturn_status(1);
            indent.setReturn_salesman_id(user.getId());
            indent.setReturn_time(DateTimeTransferUtil.getNowTimeStamp());
            return indentDao.returnAll(indent);


        }else{
                //修改所有货物库存
                for(Long gid :returnsDTO.getReturn_goods()) {
                    System.out.println(gid);
                    Goods goods = goodsDao.findgoods(gid);
                    if(goods==null){
                        throw new BasicException("没有此商品！");
                    }
                    Integer num = indentGoodsDao.findNumber(id, goods.getGoods_name());
                    goods.setGoods_repertory(goods.getGoods_repertory() + num);
                    goodsDao.updateRepertory(goods);

                    Goods goods1 = goodsDao.findgoods(gid);
                    //在订单子表中修改商品的退款状态
                    indentGoodsDao.returnSome(id, goods1.getGoods_name());
                }
                //在订单单上修改
                indent.setReturn_status(2);
                indent.setReturn_salesman_id(user.getId());
                indent.setReturn_time(DateTimeTransferUtil.getNowTimeStamp());
               return  indentDao.returnAll(indent);
            }

    }

    /**
     * 店铺删除订单信息
     * @param id
     * @return
     */
    public int Delete(Long id){
        if(indentDao.delete(id)==0){
            //在订单表中删除订单
            throw new BasicException("没有此订单！");
        }
        //在订单商品表中删除
        return indentGoodsDao.delete(id);
    }


    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有订单
     */
    public PageResult<IndentShowVO> FindAllIndent(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有订单
        List<Indent> Indent = indentDao.findAllIndent(user.getStore_id());

        //将所有订单信息转化为要展示的信息的类型
        List<IndentShowVO> ShowIndent= new LinkedList<>();

        for(Indent indent : Indent){
            IndentShowVO indentShowVO =new IndentShowVO();
            BeanUtils.copyProperties(indent, indentShowVO);

            //查询该订单下所有商品
            indentShowVO.setGoods(indentGoodsDao.findAll(indent.getId()));
            //加入要返回的链表
            ShowIndent.add(indentShowVO);
        }

        return new PageResult<>(page.getPages(), page.getTotal(),ShowIndent);
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有未支付的订单
     */
    public PageResult<IndentShowVO> NotPay(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有订单
        List<Indent> Indent = indentDao.notPay(user.getStore_id());

        //将所有订单信息转化为要展示的信息的类型
        List<IndentShowVO> ShowIndent= new LinkedList<>();

        for(Indent indent : Indent){
            IndentShowVO indentShowVO =new IndentShowVO();
            BeanUtils.copyProperties(indent, indentShowVO);

            //查询该订单下所有商品
            indentShowVO.setGoods(indentGoodsDao.findAll(indent.getId()));
            //加入要返回的链表
            ShowIndent.add(indentShowVO);
        }

        return new PageResult<>(page.getPages(), page.getTotal(),ShowIndent);
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有已支付的订单
     */
    public PageResult<IndentShowVO> Payed(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有订单
        List<Indent> Indent = indentDao.Payed(user.getStore_id());

        //将所有订单信息转化为要展示的信息的类型
        List<IndentShowVO> ShowIndent= new LinkedList<>();

        for(Indent indent : Indent){
            IndentShowVO indentShowVO =new IndentShowVO();
            BeanUtils.copyProperties(indent, indentShowVO);

            //查询该订单下所有商品
            indentShowVO.setGoods(indentGoodsDao.findAll(indent.getId()));
            //加入要返回的链表
            ShowIndent.add(indentShowVO);
        }

        return new PageResult<>(page.getPages(), page.getTotal(),ShowIndent);
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回登陆者所创建的订单
     */
    public PageResult<IndentShowVO> MyIndent(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有订单
        List<Indent> Indent = indentDao.MyIndent(user.getId(),user.getStore_id());

        //将所有订单信息转化为要展示的信息的类型
        List<IndentShowVO> ShowIndent= new LinkedList<>();

        for(Indent indent : Indent){
            IndentShowVO indentShowVO =new IndentShowVO();
            BeanUtils.copyProperties(indent, indentShowVO);

            //查询该订单下所有商品
            indentShowVO.setGoods(indentGoodsDao.findAll(indent.getId()));
            //加入要返回的链表
            ShowIndent.add(indentShowVO);
        }

        return new PageResult<>(page.getPages(), page.getTotal(),ShowIndent);
    }
}
