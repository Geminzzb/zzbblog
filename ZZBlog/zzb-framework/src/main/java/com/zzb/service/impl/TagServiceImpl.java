package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.TagByIdDto;
import com.zzb.domain.dto.TagDto;
import com.zzb.domain.entity.Tag;
import com.zzb.domain.vo.PageVo;
import com.zzb.domain.vo.TagVo;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.mapper.TagMapper;
import com.zzb.service.TagService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;



/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-19 17:51:33
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    private TagMapper tagMapper;


    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        //StringUtils.hasText(tagListDto.getName()判断Name()是否为空。如果为空不会进行后续的代码执行Tag::getName,tagListDto.getName()
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        //getRecords数据集合
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagDto tagDto) {
        //查询表中是否有此数据
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tagDto.getName());
        //判断这条数据如果不为空返回这条数据已存在
        Tag getTag = getOne(queryWrapper);
        if(getTag!=null){
           return ResponseResult.errorResult(AppHttpCodeEnum.TAG_IS_EXIST);
        }
        //对数据转型
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        //存入数据
        save(tag);
        return ResponseResult.okResult();
    }

    /**
     * 根据标签id删除标签
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult deleteTag(Long id) {
        //        1.根据标签id删除标签
        removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 根据标签id查询标签信息
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getTagById(Long id) {
        //        1.根据id获取到Tag对象
        Tag tag = getById(id);
        if(Objects.isNull(tag)){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_IS_NOEXIST);
        }

        //        2.将Tag对象转化为TagVo对象
        TagVo tagVo = BeanCopyPropertiesUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    /**
     * 更新标签
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateTag(TagByIdDto tagByIdDto) {
        //        1.判断数据是否为空
        if(!StringUtils.hasText(tagByIdDto.getName()) && !StringUtils.hasText(tagByIdDto.getRemark())){
            return  ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_IS_BLANK);

        }
        //        2.将TagByIdVo对象转换为Tag对象
        Tag tag = BeanCopyPropertiesUtils.copyBean(tagByIdDto, Tag.class);
        //        3.根据标签id查询对应的标签信息
        updateById(tag);
        return ResponseResult.okResult();
    }

    /**
     * 得到名字标记列表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getNameTagList() {
        //  1.查询所有文章类别列表
        List<Tag> tags = tagMapper.selectList(null);
        //  2.将Category对象转换为TagVo对象
        List<TagVo> tagVos = BeanCopyPropertiesUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}