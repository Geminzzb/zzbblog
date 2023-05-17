package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.TagByIdDto;
import com.zzb.domain.dto.TagDto;
import com.zzb.domain.vo.PageVo;
import com.zzb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangzongbin
 * @date: 2023/3/19 - 18:35
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDto tagDto){
        return tagService.pageTagList(pageNum,pageSize,tagDto);
    }

    /**
     * 添加标签
     * @return {@link ResponseResult}
     */
    @PostMapping()
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        return tagService.addTag(tagDto);
    }

    /**
     * 根据标签id删除标签
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }
    /**
     * 根据标签id查询标签信息
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    /**
     * 更新标签
     * @return {@link ResponseResult}
     */
    @PutMapping()
    public ResponseResult updateTag(@RequestBody TagByIdDto tagByIdDto){
        return tagService.updateTag(tagByIdDto);
    }

    /**
     * 得到名字标记列表
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/listAllTag")
    public ResponseResult getNameTagList(){
        return tagService.getNameTagList();
    }
}

