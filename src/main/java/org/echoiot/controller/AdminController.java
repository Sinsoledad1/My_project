package org.echoiot.controller;

import org.echoiot.entity.bean.CommonResult;
import org.echoiot.entity.bean.InviteCode;
import org.echoiot.entity.bean.PageResult;
import org.echoiot.entity.dto.PageParamDTO;
import org.echoiot.entity.vo.UserShowVO;
import org.echoiot.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/7 16:19
 */
@RestController
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }


    /**
     *  生成邀请码
     * @param request
     * @return
     */
    @GetMapping("/Ad/invite")
    public CommonResult<InviteCode> InviteCode(HttpServletRequest request)  {
        HttpSession session = request.getSession();
        InviteCode inviteCode = service.Invite(session);
        return new CommonResult<>(200, "OK", inviteCode);
    }

    /**
     * 查询所在店铺的所有用户
     * @param ppDTO 分页查询DTO
     * @return 返回结果
     */
    @PostMapping("/sAd/user")
    public CommonResult<PageResult<UserShowVO>> FindAllUser(@Valid @RequestBody PageParamDTO ppDTO, HttpServletRequest request){
        HttpSession session=request.getSession();

        PageResult<UserShowVO> allUser = service.findAllUser(ppDTO,session);

        return new CommonResult<>(200, "OK", allUser);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/sAd/fUserByid")
    public CommonResult<Object> FindUserByID(@RequestParam Long id){

        UserShowVO user=service.findUserByID(id);

        if(user!=null){
            return new CommonResult<>(200, "OK", user);
        }else{
            return new CommonResult<>(400, "NO", "无此用户！");
        }
    }

    /**
     * 根据id移除店员
     * @param id
     * @return
     */
    @GetMapping("/Ad/user/delete")
    public CommonResult<Object> DeleteUserByID(@RequestParam Long id){

        Integer i=service.DeleteUserByID(id);

        if(i>0){
            return new CommonResult<>(200, "OK", "删除成功！");
        }else{
            return new CommonResult<>(400, "NO", "无此用户！");
        }
    }
    /**
     * 店铺创建者授予管理员
     * @param id
     * @return
     */
    @GetMapping("/sAd/user/mandate")
    public CommonResult<String> Mandate(@RequestParam Long id){

        int result = service.Mandate(id);

        if(result>0){
            return new CommonResult<>(200, "OK", "修改权限成功！");
        }else{
            return new CommonResult<>(400, "NO", "修改权限失败！");
        }

    }

    /**
     * 店铺创建者撤销管理员
     * @param id
     * @return
     */
    @GetMapping("/sAd/user/rvkmandate")
    public CommonResult<String> RevokeMandate(@RequestParam  Long id){

        int result = service.RevokeMandate(id);

        if(result>0){
            return new CommonResult<>(200, "OK", "修改权限成功！");
        }else{
            return new CommonResult<>(400, "NO", "修改权限失败！");
        }
    }

    /**
     * 店铺创建者撤销管理员
     * @param id
     * @return
     */
    @GetMapping("/sAd/user/del")
    public CommonResult<String> DeleteMember(@RequestParam  Long id){

        int result = service.DeleteMember(id);

        if(result>0){
            return new CommonResult<>(200, "OK", "删除用户成功！");
        }else{
            return new CommonResult<>(400, "NO", "删除用户失败！");
        }
    }



}
