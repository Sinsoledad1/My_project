package org.echoiot.service;

import org.echoiot.dao.UserDao;
import org.echoiot.entity.dto.LoginDTO;
import org.echoiot.entity.dto.RegisterDTO;
import org.echoiot.entity.vo.UserShowVO;
import org.echoiot.entity.dto.UserUpdatePwDTO;
import org.echoiot.entity.pojo.User;
import org.echoiot.exception.BasicException;
import org.echoiot.util.DateTimeTransferUtil;
import org.echoiot.util.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/28 19:54
 */
@Service
@Transactional(rollbackForClassName = "Exception.class")
public class UserService {
    private final UserDao userdao;

    private final IdWorker idWorker;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Pattern p = Pattern.compile("^1(3|5|7|8|4|9)\\d{9}");

    public UserService(UserDao userdao, IdWorker idWorker, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userdao = userdao;
        this.idWorker = idWorker;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 用户注册
     * @param UserDTO
     * @return
     */
    public int Register(RegisterDTO UserDTO, HttpSession session){

//        if (!UserDTO.getVerifyCode().toUpperCase().equals(session.getAttribute("verifyCode"))) {
//            throw new BasicException("验证码错误");
//        }
        session.removeAttribute("verifyCode");

        Matcher m = p.matcher(UserDTO.getUsername());
        if (!m.matches()){
            throw new BasicException("请输入正确的手机号");
        }
        if(UserDTO.getPassword().length() < 6){
            throw new BasicException("密码长度不能小于6");
        }
        //判断密码是否为纯数字
        if(UserDTO.getPassword().matches("[0-9]+")){
            throw new BasicException("密码不能为纯数字");
        }

        User user=new User();
        BeanUtils.copyProperties(UserDTO,user);
        //设置id
        user.setId(idWorker.nextId());
        //设置添加时间
        user.setAdd_time(DateTimeTransferUtil.getNowTimeStamp());
        //加密密码
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

         try {
            return userdao.register(user);
        } catch (Exception e) {
            throw new BasicException("该号码已被注册！");
        }
    }

    /**
     * 用户登录
     * @param ulDTO
     * @param session
     * @return
     */
    public UserShowVO Login(LoginDTO ulDTO, HttpSession session) {

//        if (!ulDTO.getVerifyCode().toUpperCase().equals(session.getAttribute("verifyCode"))) {
//            throw new BasicException("验证码错误");
//        }
        session.removeAttribute("verifyCode");
//        //调用方法，根据获得的username获取数据库中的user信息

        User trueUser = userdao.userLogin(ulDTO.getUsername());
        if (trueUser == null){
            //trueUser为空说明无此用户名的用户
            throw new BasicException("无此用户");
        }
        //匹配密码
        if (bCryptPasswordEncoder.matches(ulDTO.getPassword(), trueUser.getPassword())) {
            //修改最后登录时间
            trueUser.setLast_login(DateTimeTransferUtil.getNowTimeStamp());
            userdao.updateLastLogin(trueUser);
            //存入session
            session.setAttribute("User", trueUser);
            UserShowVO userShowVO = new UserShowVO();
            //返回用户详细信息（除了密码）
            BeanUtils.copyProperties(trueUser, userShowVO);
            return userShowVO;
        }
        throw new BasicException("用户名或密码有误");
    }

    /**
     * 用于注销用户
     * @param session 获取到的session
     */
    public void signOutUser(HttpSession session){
            session.invalidate();
    }

    public void UpdatePassword(UserUpdatePwDTO userUpdatePwDTO, HttpServletRequest request){
        if(!userUpdatePwDTO.getNewPassword().equals(userUpdatePwDTO.getConfirmPassword())){
            throw new BasicException("两次输入的新密码不相同!");
        }
        if (userUpdatePwDTO.getNewPassword().length() < 6){
            throw new BasicException("密码长度不能小于6!");
        }
        if(userUpdatePwDTO.getNewPassword().matches("[0-9]+")){
            throw new BasicException("密码不能为纯数字!");
        }
        User trueUser = new User();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("User");
        if(user == null){
            throw new BasicException("用户未登录!");
        }
        trueUser.setUsername(user.getUsername());
        //判断用户原密码与输入的密码是否相等
        if (bCryptPasswordEncoder.matches(userUpdatePwDTO.getPassword(),user.getPassword())){
            //判断新密码是否与原密码相同
            if(bCryptPasswordEncoder.matches(userUpdatePwDTO.getNewPassword(),user.getPassword())){
                throw new BasicException("不能与原密码相同");
            }
            //将trueUser的密码设为新密码并加密密码
            trueUser.setPassword(bCryptPasswordEncoder.encode(userUpdatePwDTO.getNewPassword()));
            //判断数据库是否修改成功
            int result = userdao.updatePassword(trueUser);
            if(result == 0){
                throw  new BasicException("无此用户!");
            }
            //更改成功后 修改session里存储的user对象
            user = userdao.userLogin(user.getUsername());
            session.setAttribute("User",user);
        }else {
            throw new BasicException("用户原密码错误!");
        }
    }
}
