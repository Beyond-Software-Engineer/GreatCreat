package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysStudentAcademic;

/**
 * 学生学业数据 数据层
 * 
 * @author ruoyi
 */
public interface SysStudentAcademicMapper
{
    /**
     * 根据条件分页查询学业数据列表
     * 
     * @param academic 学业数据信息
     * @return 学业数据集合信息
     */
    public List<SysStudentAcademic> selectAcademicList(SysStudentAcademic academic);

    /**
     * 通过ID查询学业数据
     * 
     * @param academicId 学业数据ID
     * @return 学业数据对象信息
     */
    public SysStudentAcademic selectAcademicById(Long academicId);

    /**
     * 通过学号查询学业数据列表
     * 
     * @param studentId 学号
     * @return 学业数据集合信息
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
     * 通过ID删除学业数据
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
}
