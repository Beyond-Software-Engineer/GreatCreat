package com.ruoyi.system.mapper.teaching;

import com.ruoyi.common.core.domain.entity.SysTeachingProgram;

import java.util.List;

/**
 * 培养方案Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface SysTeachingProgramMapper 
{
    /**
     * 查询培养方案
     * 
     * @param programId 培养方案主键
     * @return 培养方案
     */
    public SysTeachingProgram selectSysTeachingProgramByProgramId(Long programId);

    /**
     * 查询培养方案列表
     * 
     * @param sysTeachingProgram 培养方案
     * @return 培养方案集合
     */
    public List<SysTeachingProgram> selectSysTeachingProgramList(SysTeachingProgram sysTeachingProgram);

    /**
     * 新增培养方案
     * 
     * @param sysTeachingProgram 培养方案
     * @return 结果
     */
    public int insertSysTeachingProgram(SysTeachingProgram sysTeachingProgram);

    /**
     * 修改培养方案
     * 
     * @param sysTeachingProgram 培养方案
     * @return 结果
     */
    public int updateSysTeachingProgram(SysTeachingProgram sysTeachingProgram);

    /**
     * 删除培养方案
     * 
     * @param programId 培养方案主键
     * @return 结果
     */
    public int deleteSysTeachingProgramByProgramId(Long programId);

    /**
     * 批量删除培养方案
     * 
     * @param programIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTeachingProgramByProgramIds(Long[] programIds);
}
