package org.echoiot.service;

import org.echoiot.dao.StoreDao;
import org.echoiot.dao.UserDao;
import org.echoiot.entity.bean.InviteCode;
import org.echoiot.entity.dto.CreateDTO;
import org.echoiot.entity.pojo.Store;
import org.echoiot.entity.pojo.User;
import org.echoiot.exception.BasicException;
import org.echoiot.util.DateTimeTransferUtil;
import org.echoiot.util.IdWorker;
import org.echoiot.util.InviteCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/1 15:18
 */
@Service
@Transactional(rollbackForClassName = "Exception.class")
public class StoreService {
    private final UserDao userDao;
    private final StoreDao storeDao;

    private final IdWorker idWorker;

    private final Pattern p = Pattern.compile("^1(3|5|7|8|4|9)\\d{9}");

    public StoreService(UserDao userDao, StoreDao storeDao, IdWorker idWorker, RedisTemplate redisTemplate) {
        this.userDao = userDao;
        this.storeDao = storeDao;
        this.idWorker = idWorker;

    }

    /**
     * 创建店铺
     * @param createDTO
     * @param session
     * @return
     */
    public int Create(CreateDTO createDTO, HttpSession session){

//        if (!createDTO.getVerifyCode().toUpperCase().equals(session.getAttribute("verifyCode"))) {
//            throw new BasicException("验证码错误");
//        }
//        session.removeAttribute("verifyCode");

        Matcher m = p.matcher(createDTO.getStore_phone());
        if (!m.matches()){
            throw new BasicException("请输入正确的手机号!");
        }

        Store store=new Store();
        BeanUtils.copyProperties(createDTO,store);
        //设置店铺id
        store.setId(idWorker.nextId());


        User user = (User) session.getAttribute("User");
        //设置创建者id
        store.setCreator_id(user.getId());

        //设置店长id
        store.setBoss_id(user.getId());

        //设置用户的店铺id和角色
        user.setStore_id(store.getId());
        user.setRole("SuperAdmin");
        userDao.updateStoreId(user);
        userDao.updateRole(user);

        //将原来session中的user去掉，存入新的user
        System.out.println("修改前==========>"+session.getAttribute("User").toString());
        session.removeAttribute("User");
        session.setAttribute("User",user);
        System.out.println("修改后==========>"+session.getAttribute("User").toString());
        //设置添加时间
        store.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());

        return storeDao.create(store);
    }

    /**
     * 加入店铺
     * @param code
     * @param session
     * @return
     */
    public int Join(String code ,HttpSession session){

        //通过邀请码，找到对应店铺信息
        InviteCode inviteCode=storeDao.findcode(code);
        //加入店铺
        User user = (User) session.getAttribute("User");
        user.setStore_id(inviteCode.getStore_id());
        user.setRole("Member");

        //将原来session中的user去掉，存入新的user
        session.removeAttribute("User");
        session.setAttribute("User",user);

        return userDao.updateRS(user);
    }
    /**
     * 通过id查找店铺
     *
     * @param id
     * @return
     */
    public Store FindStoreByID(Long id) {

        Store store = storeDao.FindStorebyID(id);

        return store;
    }


    /**
     * 通过id查找店铺
     *
     * @param uid
     * @return
     */
    public List<Store> FindStoreByUID(Long uid) {

        List<Store> store = storeDao.FindStorebyUID(uid);

        return store;
    }

    /**
     * 删除店铺
     * @param id
     * @return
     */
    public int DeleteStore(Long id){
        return storeDao.delete(id);
    }
}
