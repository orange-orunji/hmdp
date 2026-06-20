package src.main.java.com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import src.main.java.com.hmdp.dto.LoginFormDTO;
import src.main.java.com.hmdp.dto.Result;
import src.main.java.com.hmdp.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 验证码发送结果
     */
    Result sentCode(String phone, HttpSession session);

    /**
     * 登录功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     * @return 登录结果
     */
    Result login(LoginFormDTO loginForm, HttpSession session);

    /**
     * 签到功能
     * @return 签到结果
     */
    Result sign();

    /**
     * 统计签到功能
     * @return 签到结果
     */
    Result signCount();
}
