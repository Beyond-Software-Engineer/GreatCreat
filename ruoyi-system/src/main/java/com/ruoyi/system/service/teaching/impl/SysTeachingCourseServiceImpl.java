package com.ruoyi.system.service.teaching.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysTeachingCourse;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.teaching.SysTeachingCourseMapper;
import com.ruoyi.system.service.teaching.ISysTeachingCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开课信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysTeachingCourseServiceImpl implements ISysTeachingCourseService 
{
    @Autowired
    private SysTeachingCourseMapper sysTeachingCourseMapper;

    /**
     * 查询开课信息
     * 
     * @param courseId 开课信息主键
     * @return 开课信息
     */
    @Override
    public SysTeachingCourse selectSysTeachingCourseByCourseId(Long courseId)
    {
        return sysTeachingCourseMapper.selectSysTeachingCourseByCourseId(courseId);
    }

    /**
     * 查询开课信息列表
     * 
     * @param sysTeachingCourse 开课信息
     * @return 开课信息
     */
    @Override
    public TableDataInfo selectSysTeachingCourseList(SysTeachingCourse sysTeachingCourse)
    {
        startPage();
        List<SysTeachingCourse> list = sysTeachingCourseMapper.selectSysTeachingCourseList(sysTeachingCourse);
        return getDataTable(list);
    }

    /**
     * 新增开课信息
     * 
     * @param sysTeachingCourse 开课信息
     * @return 结果
     */
    @Override
    public int insertSysTeachingCourse(SysTeachingCourse sysTeachingCourse)
    {
        return sysTeachingCourseMapper.insertSysTeachingCourse(sysTeachingCourse);
    }

    /**
     * 修改开课信息
     * 
     * @param sysTeachingCourse 开课信息
     * @return 结果
     */
    @Override
    public int updateSysTeachingCourse(SysTeachingCourse sysTeachingCourse)
    {
        return sysTeachingCourseMapper.updateSysTeachingCourse(sysTeachingCourse);
    }

    /**
     * 批量删除开课信息
     * 
     * @param courseIds 需要删除的开课信息主键集合
     * @return 结果
     */
    @Override
    public int deleteSysTeachingCourseByCourseIds(Long[] courseIds)
    {
        return sysTeachingCourseMapper.deleteSysTeachingCourseByCourseIds(courseIds);
    }

    /**
     * 删除开课信息信息
     * 
     * @param courseId 开课信息主键
     * @return 结果
     */
    @Override
    public int deleteSysTeachingCourseByCourseId(Long courseId)
    {
        return sysTeachingCourseMapper.deleteSysTeachingCourseByCourseId(courseId);
    }

    /**
     * 分页查询
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 封装分页数据
     */
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new com.github.pagehelper.PageInfo(list).getTotal());
        return rspData;
    }
}
