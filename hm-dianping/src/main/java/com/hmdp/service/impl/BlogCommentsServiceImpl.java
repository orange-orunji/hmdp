package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.BlogComments;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogCommentsMapper;
import com.hmdp.service.IBlogCommentsService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

    @Resource
    private IUserService userService;

    @Override
    public Result saveComment(BlogComments comment) {
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请先登录");
        }
        comment.setUserId(user.getId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        comment.setLiked(0);
        comment.setStatus(false);
        save(comment);
        return Result.ok(comment.getId());
    }

    @Override
    public Result queryCommentsByBlogId(Long blogId) {
        List<BlogComments> comments = query()
                .eq("blog_id", blogId)
                .eq("parent_id", 0)
                .orderByAsc("create_time")
                .list();
        List<Map<String, Object>> result = new ArrayList<>();
        for (BlogComments comment : comments) {
            User user = userService.getById(comment.getUserId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", comment.getId());
            map.put("content", comment.getContent());
            map.put("liked", comment.getLiked());
            map.put("createTime", comment.getCreateTime());
            map.put("userName", user != null ? user.getNickName() : "匿名用户");
            map.put("userIcon", user != null ? user.getIcon() : "/imgs/icons/default-icon.png");
            result.add(map);
        }
        return Result.ok(result);
    }
}
