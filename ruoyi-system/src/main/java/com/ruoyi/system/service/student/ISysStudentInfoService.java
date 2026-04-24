package com.ruoyi.system.service.student;

import com.ruoyi.common.core.domain.entity.SysStudentInfo;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysStudentInfoService 
{
    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public SysStudentInfo selectSysStudentInfoByStudentId(Long studentId);

    /**
     * 查询学生信息列表
     * 
     * @param sysStudentInfo 学生信息
     * @return 学生信息集合
     */
    public TableDataInfo selectSysStudentInfoList(SysStudentInfo sysStudentInfo);

    /**
     * 新增学生信息
     * 
     * @param sysStudentInfo 学生信息
     * @return 结果
     */
    public int insertSysStudentInfo(SysStudentInfo sysStudentInfo);

    /**
     * 修改学生信息
     * 
     * @param sysStudentInfo 学生信息
     * @return 结果
     */
    public int updateSysStudentInfo(SysStudentInfo sysStudentInfo);

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteSysStudentInfoByStudentIds(Long[] studentIds);

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteSysStudentInfoByStudentId(Long studentId);
}
