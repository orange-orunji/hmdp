package com.hmdp.controller;

import com.hmdp.dto.Result;
import com.hmdp.service.IMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private IMessageService messageService;

    @PostMapping
    public Result sendMessage(@RequestBody Map<String, String> body) {
        Long receiverId = Long.valueOf(body.get("receiverId"));
        String content = body.get("content");
        return messageService.sendMessage(receiverId, content);
    }

    @GetMapping("/chat/{userId}")
    public Result queryChatHistory(@PathVariable Long userId) {
        return messageService.queryChatHistory(userId);
    }

    @GetMapping("/conversations")
    public Result queryConversations() {
        return messageService.queryConversations();
    }

    @PutMapping("/read-all")
    public Result readAll() {
        return messageService.readAll();
    }

}
