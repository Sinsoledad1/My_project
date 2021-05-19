package org.echoiot.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.echoiot.dao.AdminDao;
import org.echoiot.dao.UserDao;
import org.echoiot.entity.bean.InviteCode;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.vo.UserShowVO;
import org.echoiot.entity.pojo.User;
import org.echoiot.exception.BasicException;
import org.echoiot.util.DateTimeTransferUtil;
import org.echoiot.util.IdWorker;
import org.echoiot.util.InviteCodeUtil;
import org.echoiot.util.PageParamCheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/7 16:20
 */
@Service
@Transactional(rollbackForClassName = "Exception.class")
public class AdminService {
    private final AdminDao adminDao;
    private final UserDao userDao;

    private final IdWorker idWorker;
    private final RedisTemplate redisTemplate;
    private final PageParamCheckUtil ppC;
    public AdminService(AdminDao adminDao, UserDao userDao, IdWorker idWorker, RedisTemplate redisTemplate, PageParamCheckUtil ppC) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.redisTemplate = redisTemplate;
        this.ppC = ppC;
    }

    /**
     * 生成邀请码
     * @param session
     * @return
     */
    public InviteCode Invite(HttpSession session){
        User user = (User) session.getAttribute("User");
        if(user.getStore_id()==null){
            throw new BasicException("请先加入店铺！");
        }

        //创建一个邀请码实体类
        InviteCode inviteCode=new InviteCode();
        //生成邀请码
        String code= InviteCodeUtil.generateInviteCode(8);

        //在控制台打印邀请码
        System.out.println(code);
        //设置邀请码
        inviteCode.setId(idWorker.nextId());
        inviteCode.setCode(code);
        inviteCode.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());
        //设置店铺id和邀请者id

        inviteCode.setStore_id(user.getStore_id());
        inviteCode.setPostman_id(user.getId());
        //添加到数据库
        adminDao.invite(inviteCode);

        //将验证码中的字符写入redis、过期时间为600秒钟
        redisTemplate.opsForValue().set("ic_" + code, "1", 600, TimeUnit.SECONDS);

        return inviteCode;
    }

    /**
     * @param ppDTO 分页查询DTO
     * @return 返回所有用户
     */
    public PageResult<UserShowVO> findAllUser(PageParamDTO ppDTO, HttpSession session) {
        Page<Object> page = PageHelper.startPage(ppDTO.getCp(), ppDTO.getPs(), ppC.CheckOrder(ppDTO.getOrder()));

        //查询所在店铺
        User user = (User) session.getAttribute("User");

        //调用方法查询所有用户
        List<UserShowVO> allUser = adminDao.findAllUser(user.getStore_id());

        return new PageResult<>(page.getPages(), page.getTotal(), allUser);
    }

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    public UserShowVO findUserByID(Long id){

        //通过id查询信息
        User trueUser=userDao.finduser(id);

        UserShowVO userShowVO = new UserShowVO();
        //返回用户详细信息（除了密码）
        BeanUtils.copyProperties(trueUser, userShowVO);

        return userShowVO;
    }

    /**
     * 移除店员
     * @param id
     * @return
     */
    public Integer DeleteUserByID(Long id){

        return adminDao.delete(id);
    }
    /**
     * 店铺创建者授予管理员权限
     * @param id
     * @return
     */
    public int Mandate(Long id){
        User user= userDao.finduser(id);
        user.setRole("Admin");
        return userDao.updateRole(user);
    }

    /**
     * 店铺创建者撤销管理员权限
     * @param id
     * @return
     */
    public int RevokeMandate(Long id){
        User user= userDao.finduser(id);
        user.setRole("Member");
        return userDao.updateRole(user);
    }

    /**
     * 店铺创建者删除用户
     * @param id
     * @return
     */
    public int DeleteMember(Long id){
        //根据id找到用户
        User user= userDao.finduser(id);
        user.setRole(null);
        user.setStore_id(null);
        return userDao.updateRS(user);
    }
}
