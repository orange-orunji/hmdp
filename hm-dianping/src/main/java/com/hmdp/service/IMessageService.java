package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IMessageService extends IService<Message> {

    Result sendMessage(Long receiverId, String content);

    Result queryChatHistory(Long targetUserId);

    Result queryConversations();

    Result readAll();

}
