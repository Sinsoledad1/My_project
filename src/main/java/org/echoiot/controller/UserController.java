package org.echoiot.controller;

import org.echoiot.entity.bean.CommonResult;
import org.echoiot.entity.dto.LoginDTO;
import org.echoiot.entity.dto.RegisterDTO;
import org.echoiot.entity.vo.UserShowVO;
import org.echoiot.entity.dto.UserUpdatePwDTO;
import org.echoiot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/28 19:24
 */
@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * 用户注册
     * @param registerDTO
     * @param request
     * @return
     */
    @PostMapping("/register")
    public CommonResult<String> Register(@Valid @RequestBody RegisterDTO registerDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        int result = service.Register(registerDTO,session);

        if(result > 0){
            return new CommonResult<>(200,"OK","注册成功！");
        }
        else{
            return new CommonResult<>(400,"NO","注册失败！");
        }
    }

    /**
     * 用户登录
     * @param ulDTO
     * @param request
     * @return
     */
    @PostMapping("/login")
    public CommonResult<UserShowVO> Login(@Valid @RequestBody LoginDTO ulDTO, HttpServletRequest request)  {
        HttpSession session=request.getSession();
        UserShowVO userShowVO = service.Login(ulDTO, session);
        return new CommonResult<>(200, "OK", userShowVO);
    }

    /**
     * 注销账号
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public CommonResult<String> Logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        service.signOutUser(session);
        return new CommonResult<>(200,"OK","已退出账号");
    }

    /**
     * 用户修改自己密码
     * @param Newuser
     * @param request
     * @return
     */
    @PostMapping("/updatepassword")
    public CommonResult<String> UpdatePassword(@Valid @RequestBody UserUpdatePwDTO Newuser, HttpServletRequest request){
        service.UpdatePassword(Newuser, request);
        return new CommonResult<>(200,"OK","修改成功");
    }


}
