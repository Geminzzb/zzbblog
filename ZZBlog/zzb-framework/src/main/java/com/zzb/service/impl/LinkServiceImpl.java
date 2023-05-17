package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.constants.SystemConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.LinkDto;
import com.zzb.domain.entity.Link;
import com.zzb.domain.vo.AdminLinkVo;
import com.zzb.domain.vo.LinkVo;
import com.zzb.mapper.LinkMapper;
import com.zzb.service.LinkService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-12 21:32:46
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    /**
     * 分页查询所有友链-Admin
     *
     * @param pageNum
     * @param pageSize
     * @param linkDto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getAllLinkByAdmin(Integer pageNum, Integer pageSize, LinkDto linkDto) {
//        1.根据友链名(模糊查询)和状态进行查询
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());
        queryWrapper.like(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName());

//        2.分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

//        3.将当前页中的Link对象转换为LinkVo对象
        List<Link> links = page.getRecords();
        List<LinkVo> linkVos = BeanCopyPropertiesUtils.copyBeanList(links, LinkVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        AdminLinkVo adminLinkVo = new AdminLinkVo(linkVos, page.getTotal());
        return ResponseResult.okResult(adminLinkVo);
    }
}

