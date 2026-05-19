package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IBlogService extends IService<Blog> {

    /**
     * 查询最热博客
     * @param current
     * @return
     */
    List<Blog> queryHotBlogs(Integer current);

    /**
     * 查询博客详情
     * @param id
     * @return
     */
    Object queryBlog(Long id);

    /**
     * 查询是否点赞
     * @param id
     * @return
     */
    Result isLiked(Long id);

    /**
     * 查询点赞
     * @param id
     * @return
     */
    Result queryBlogLikes(Long id);

    /**
     * 保存博客
     * @param blog
     * @return
     */
    Result saveBlog(Blog blog);

    /**
     * 查询关注用户
     * @return
     */
    Result queryBlogOfFollow(Long max, Long offset);
}
