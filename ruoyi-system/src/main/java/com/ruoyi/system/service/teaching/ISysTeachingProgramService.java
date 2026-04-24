package com.ruoyi.system.service.teaching;

import com.ruoyi.common.core.domain.entity.SysTeachingProgram;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 培养方案Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysTeachingProgramService 
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
    public TableDataInfo selectSysTeachingProgramList(SysTeachingProgram sysTeachingProgram);

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
     * 批量删除培养方案
     * 
     * @param programIds 需要删除的培养方案主键集合
     * @return 结果
     */
    public int deleteSysTeachingProgramByProgramIds(Long[] programIds);

    /**
     * 删除培养方案信息
     * 
     * @param programId 培养方案主键
     * @return 结果
     */
    public int deleteSysTeachingProgramByProgramId(Long programId);
}
