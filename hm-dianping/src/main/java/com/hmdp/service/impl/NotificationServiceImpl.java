package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Notification;
import com.hmdp.entity.User;
import com.hmdp.mapper.NotificationMapper;
import com.hmdp.service.INotificationService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements INotificationService {

    @Resource
    private IUserService userService;

    @Override
    public Result queryNotifications() {
        UserDTO user = UserHolder.getUser();
        List<Notification> list = query()
                .eq("user_id", user.getId())
                .orderByDesc("create_time")
                .last("limit 50")
                .list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Notification n : list) {
            User from = userService.getById(n.getFromUserId());
            Map<String, Object> m = new HashMap<>();
            m.put("id", n.getId());
            m.put("type", n.getType());
            m.put("content", n.getContent());
            m.put("relatedId", n.getRelatedId());
            m.put("isRead", n.getIsRead());
            m.put("createTime", n.getCreateTime());
            m.put("fromUserName", from != null ? from.getNickName() : "匿名");
            m.put("fromUserIcon", from != null ? from.getIcon() : "/imgs/icons/default-icon.png");
            result.add(m);
        }
        return Result.ok(result);
    }

    @Override
    public Result readAll() {
        UserDTO user = UserHolder.getUser();
        update(new UpdateWrapper<Notification>()
                .eq("user_id", user.getId()).eq("is_read", false)
                .set("is_read", true));
        return Result.ok();
    }
}
