package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.constants.CommonConstants;
import com.zzb.constants.SystemConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AddArticleDto;
import com.zzb.domain.dto.AdminArticleDto;
import com.zzb.domain.dto.ArticleDto;
import com.zzb.domain.entity.Article;
import com.zzb.domain.entity.ArticleTag;
import com.zzb.domain.entity.Category;
import com.zzb.domain.vo.*;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.mapper.ArticleMapper;
import com.zzb.service.ArticleTagService;
import com.zzb.service.CategoryService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.BeanCopyUtils;
import com.zzb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.zzb.service.ArticleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 14:37
 * @mail: 2218722664@qq.com
 * @info:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService  {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        //bean拷贝

        //常见一个Vo实体类
        //List<HoArricleVo> arricleVos= new ArrayList<>();
        //用for循环把Article赋值给HoArricleVo
       // for(Article article:articles){
       //   HoArricleVo vo =new HoArricleVo();
       //     BeanUtils.copyProperties(article,vo);
       //     arricleVos.add(vo);
       // }

        //使用BeanCopyUtils封装的方法进行拷贝
        List<HoArricleVo> vs = BeanCopyUtils.copyBeanList(articles,HoArricleVo.class);

        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //如果 有categoryId 就要查询时和传入的相同
        //eq  布尔型形式的，先判断category不为空，并且大于0
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式的发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page =new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName

        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        List<Article> articles =page.getRecords();
        articles.stream()
                .map((Article article) -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();

        Category category = categoryService.getById(categoryId);

        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
            System.out.println("category.getName()"+category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 游览次数增加
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }


    /**
     * 添加博客
     *
     * @param articleDto 文章dto
     * @return {@link ResponseResult}
     */
    @Override
    //事务标签
    @Transactional
    public ResponseResult addArticle(AddArticleDto articleDto) {
//        1.将AddArticleDto对象转换为Article对象
        Article article = BeanCopyPropertiesUtils.copyBean(articleDto, Article.class);
//        2.保存Article对象
        save(article);

//        3.获取到AddArticleDto对象当中的tags属性，
//        使用Stream流遍历，操作ArticleTag对象，将当前Article和Tag的对应关系添加到sg_article_tag中
        List<ArticleTag> articleTags = articleDto
                .getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 查询博客列表
     *
     * @param pageNum    页面num
     * @param pageSize   页面大小
     * @param articleDto 文章dto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getAllArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
//        1.根据文章标题(模糊查询)和摘要进行查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());
//        2.规定文章是未发布状态不能显示
        queryWrapper.eq(Article::getStatus, CommonConstants.ARTICLE_STATUS_PUBLISH);
//        3.分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        3.将当前页中的Article对象转换为ArticleDetailsVo对象
        List<Article> articles = page.getRecords();
        List<ArticleDetailsVo> articleDetailsVos = BeanCopyPropertiesUtils.copyBeanList(articles, ArticleDetailsVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        AdminArticleVo adminArticleVo = new AdminArticleVo(articleDetailsVos, page.getTotal());
        return ResponseResult.okResult(adminArticleVo);
    }

    /**
     * 通过id获取博客
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getArticleById(Long id) {
//        1.根据id查询博客
        Article article = getById(id);
//        2.将Link对象封装为UpdateArticleVo对象
        UpdateArticleVo updateArticleVo = BeanCopyPropertiesUtils.copyBean(article, UpdateArticleVo.class);
//        3.根据文章id获取到文章标签列表
        List<Long> tagList = articleTagService.getTagList(id);
        updateArticleVo.setTags(tagList);
        return ResponseResult.okResult(updateArticleVo);
    }

    /**
     * 修改博客
     * @param adminArticleDto 文章细节签证官
     * @return {@link ResponseResult}
     */
    @Override
    //事务标签
    @Transactional
    public ResponseResult updateArticle(AdminArticleDto adminArticleDto) {
//        1.将AdminArticleDto对象转换为Article对象
        Article article = BeanCopyPropertiesUtils.copyBean(adminArticleDto, Article.class);
//        2.将博客的标签信息存入标签表
//          2.1根据当前博客id获取到已有的标签列表
        List<Long> tagList = articleTagService.getTagList(article.getId());
//          2.2得到修改过后的标签列表
        List<Long> tags = article.getTags();
        //自己的2.3
  //    先根据id删除这条文章所有的标签关联表
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);
        for (Long tag:tags){
            articleTagService.save(new ArticleTag(article.getId(), tag));
        }


//       别人的   2.3遍历修改过后的标签列表，判断当前博客是否已经有此标签，没有则一条数据添加到sg_article_tag表中
//        for (Long tag:tags){
//            //表示taglist列表中没有的
//            if (!tagList.contains(tag)){
//                articleTagService.save(new ArticleTag(article.getId(), tag));
//            }
//        }

        updateById(article);
        return ResponseResult.okResult();
    }

    /**
     * 删除博客
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult deleteArticle(Long id) {
        boolean result = removeById(id);
        if (result == false){
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ARTICLE_FAIL);
        }
        return ResponseResult.okResult();
    }


}
