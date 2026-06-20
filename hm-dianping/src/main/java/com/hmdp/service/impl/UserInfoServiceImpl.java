package src.main.java.com.hmdp.service.impl;

import src.main.java.com.hmdp.entity.UserInfo;
import src.main.java.com.hmdp.mapper.UserInfoMapper;
import src.main.java.com.hmdp.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
