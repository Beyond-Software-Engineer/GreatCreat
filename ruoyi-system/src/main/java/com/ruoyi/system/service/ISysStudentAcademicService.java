package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysStudentAcademic;

/**
 * 学生学业数据服务接口
 * 
 * @author ruoyi
 */
public interface ISysStudentAcademicService
{
    /**
     * 查询学业数据列表
     * 
     * @param academic 学业数据信息
     * @return 学业数据集合
     */
    public List<SysStudentAcademic> selectAcademicList(SysStudentAcademic academic);

    /**
     * 查询学业数据
     * 
     * @param academicId 学业数据ID
     * @return 学业数据
     */
    public SysStudentAcademic selectAcademicById(Long academicId);

    /**
     * 根据学号查询学业数据
     * 
     * @param studentId 学号
     * @return 学业数据集合
     */
    public List<SysStudentAcademic> selectAcademicByStudentId(String studentId);

    /**
     * 新增学业数据
     * 
     * @param academic 学业数据信息
     * @return 结果
     */
    public int insertAcademic(SysStudentAcademic academic);

    /**
     * 修改学业数据
     * 
     * @param academic 学业数据信息
     * @return 结果
     */
    public int updateAcademic(SysStudentAcademic academic);

    /**
     * 删除学业数据
     * 
     * @param academicId 学业数据ID
     * @return 结果
     */
    public int deleteAcademicById(Long academicId);

    /**
     * 批量删除学业数据
     * 
     * @param academicIds 需要删除的学业数据ID
     * @return 结果
     */
    public int deleteAcademicByIds(Long[] academicIds);

    /**
     * 核算学生总学分
     * 
     * @param studentId 学号
     * @return 总学分
     */
    public Double calculateTotalCredits(String studentId);

    /**
     * 更新学生学业状态
     * 
     * @param studentId 学号
     * @return 结果
     */
    public int updateAcademicStatus(String studentId);
}
