package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Notification;
import com.baomidou.mybatisplus.extension.service.IService;

public interface INotificationService extends IService<Notification> {

    Result queryNotifications();

    Result readAll();
}
