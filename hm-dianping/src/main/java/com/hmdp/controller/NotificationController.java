package com.hmdp.controller;

import com.hmdp.dto.Result;
import com.hmdp.service.INotificationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Resource
    private INotificationService notificationService;

    @GetMapping
    public Result queryNotifications() {
        return notificationService.queryNotifications();
    }

    @PutMapping("/read")
    public Result readAll() {
        return notificationService.readAll();
    }
}
