package com.ruoyi.system.mapper.teaching;

import com.ruoyi.common.core.domain.entity.SysTeachingCourse;

import java.util.List;

/**
 * 开课信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface SysTeachingCourseMapper 
{
    /**
     * 查询开课信息
     * 
     * @param courseId 开课信息主键
     * @return 开课信息
     */
    public SysTeachingCourse selectSysTeachingCourseByCourseId(Long courseId);

    /**
     * 查询开课信息列表
     * 
     * @param sysTeachingCourse 开课信息
     * @return 开课信息集合
     */
    public List<SysTeachingCourse> selectSysTeachingCourseList(SysTeachingCourse sysTeachingCourse);

    /**
     * 新增开课信息
     * 
     * @param sysTeachingCourse 开课信息
     * @return 结果
     */
    public int insertSysTeachingCourse(SysTeachingCourse sysTeachingCourse);

    /**
     * 修改开课信息
     * 
     * @param sysTeachingCourse 开课信息
     * @return 结果
     */
    public int updateSysTeachingCourse(SysTeachingCourse sysTeachingCourse);

    /**
     * 删除开课信息
     * 
     * @param courseId 开课信息主键
     * @return 结果
     */
    public int deleteSysTeachingCourseByCourseId(Long courseId);

    /**
     * 批量删除开课信息
     * 
     * @param courseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTeachingCourseByCourseIds(Long[] courseIds);
}
