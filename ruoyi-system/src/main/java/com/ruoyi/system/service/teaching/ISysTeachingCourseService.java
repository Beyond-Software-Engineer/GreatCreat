package com.ruoyi.system.service.teaching;

import com.ruoyi.common.core.domain.entity.SysTeachingCourse;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开课信息Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysTeachingCourseService 
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
    public TableDataInfo selectSysTeachingCourseList(SysTeachingCourse sysTeachingCourse);

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
     * 批量删除开课信息
     * 
     * @param courseIds 需要删除的开课信息主键集合
     * @return 结果
     */
    public int deleteSysTeachingCourseByCourseIds(Long[] courseIds);

    /**
     * 删除开课信息信息
     * 
     * @param courseId 开课信息主键
     * @return 结果
     */
    public int deleteSysTeachingCourseByCourseId(Long courseId);
}
