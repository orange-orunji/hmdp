package com.hmdp.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import jodd.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    private IUserService userService;
    @Resource
    private IBlogService blogService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Blog> queryHotBlogs(Integer current) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog -> {
            this.extracted(blog);
            this.isBlogLiked(blog);
        });
        return records;
    }

    private void extracted(Blog blog) {
        Long userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }
    private void isBlogLiked(Blog blog){
        UserDTO user = UserHolder.getUser();
        if (user == null){
            blog.setIsLike(false);
            return;
        }
        long id = blog.getUserId();
        Double score = stringRedisTemplate.opsForZSet().score(RedisConstants.BLOG_LIKED_KEY + id, String.valueOf(id));
        blog.setIsLike(score != null);
    }

    @Override
    public Object queryBlog(Long id) {
        //1.根据id查询博客
        Blog blog = blogService.getById(id);
        //2.根据博客id查询用户相关信息
        extracted(blog);
        //3.查询当前用户是否点赞过该博客
        isBlogLiked(blog);
        return blog;
    }

    /**
     * 获取当前用户是否点赞过该博客
     * @param id 博客id
     * @return
     */
    public Result isLiked(Long id) {
        String userId = UserHolder.getUser().getId().toString();
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        // 判断当前用户是否点赞过该博客
        Double score = stringRedisTemplate.opsForZSet().score(key, userId);
        if(score == null) {
            // 如果没有点赞
            boolean b = update().setSql("liked = liked + 1").eq("id", id).update();
            // 更新缓存
            if(b){
                stringRedisTemplate.opsForZSet().add(key, userId, System.currentTimeMillis());
            }
        }
        else {
            // 如果有点赞
            boolean b = update().setSql("liked = liked - 1").eq("id", id).update();
            // 更新缓存
            if(b){
                stringRedisTemplate.opsForZSet().remove(key,userId);
            }
        }
        return Result.ok(true);
    }

    /**
     * 查询博客点赞排行榜
     * @param id
     * @return
     */
    @Override
    public Result queryBlogLikes(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        Set<String> top5 = stringRedisTemplate.opsForZSet().range(key, 0, 4);
        if(top5 == null || top5.isEmpty()) return Result.ok();
        List<Long> ids = top5.stream().map(Long::parseLong).collect(Collectors.toList());
        String linkedId = StringUtil.join(ids, ",");
        List<User> users = userService.query().select("id", "nick_name", "icon").in("id", ids)
                .last("order by field(id," + linkedId +")").list();
        List<UserDTO> collect = users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }).collect(Collectors.toList());
        return Result.ok(collect);
    }
}
