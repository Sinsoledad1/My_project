package org.echoiot.controller;

import org.echoiot.entity.bean.CommonResult;

import org.echoiot.entity.dto.CreateDTO;
import org.echoiot.entity.dto.JoinDTO;
import org.echoiot.entity.pojo.Store;
import org.echoiot.exception.BasicException;
import org.echoiot.service.StoreService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 23:28
 */
@RestController
public class StoreController {
    private final StoreService service;
    private final RedisTemplate redisTemplate;

    public StoreController(StoreService service, RedisTemplate redisTemplate) {
        this.service = service;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 创建店铺
     * @param createDTO
     * @param request
     * @return
     */
    @PostMapping("/store/create")
    public CommonResult<String> Create(@Valid @RequestBody CreateDTO createDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        int result = service.Create(createDTO,session);

        if(result > 0){
            return new CommonResult<>(200,"OK","创建成功！");
        }
        else{
            return new CommonResult<>(400,"NO","创建失败！");
        }
    }

    /**
     * 加入店铺
     * @param code
     * @param request
     * @return
     */
    @PostMapping("/store/join")
    public CommonResult<String> Join(@Valid @RequestBody JoinDTO code, HttpServletRequest request){

        if (null == redisTemplate.opsForValue().get("ic_" + code.getCode())) {
            throw new BasicException("邀请码错误！");
        }

        HttpSession session = request.getSession();
        int result = service.Join(code.getCode(),session);

        if(result > 0){
            return new CommonResult<>(200,"OK","加入成功！");
        }
        else{
            return new CommonResult<>(400,"NO","加入失败！");
        }
    }

    /**
     * 通过id查找店铺
     * @param id
     * @return
     */
    @GetMapping("/Store/fStoreByid")
    public CommonResult<Store> FindStoreByID(@RequestParam Long id) {
        Store store=service.FindStoreByID(id);
        return new CommonResult<>(200, "OK", store);
    }


    /**
     * 通过用户id查找店铺
     * @param uid
     * @return
     */
    @GetMapping("/Store/fStoreByUid")
    public CommonResult<List<Store>> FindStoreByUID(@RequestParam Long uid) {
        List<Store> store=service.FindStoreByUID(uid);
        return new CommonResult<>(200, "OK", store);
    }

    /**
     * 删除店铺
     * @param id
     * @return
     */
    @GetMapping("/Store/del")
    public CommonResult<String> deleteStore(@RequestParam Long id) {
        int i=service.DeleteStore(id);
        if(i>0){
            return new CommonResult<>(200, "OK","删除成功");
        }else{
            return new CommonResult<>(500, "NO","删除失败");
        }

    }
}
