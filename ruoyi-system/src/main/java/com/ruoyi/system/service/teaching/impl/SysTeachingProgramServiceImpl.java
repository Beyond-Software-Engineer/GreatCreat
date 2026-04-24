package com.ruoyi.system.service.teaching.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysTeachingProgram;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.teaching.SysTeachingProgramMapper;
import com.ruoyi.system.service.teaching.ISysTeachingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 培养方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysTeachingProgramServiceImpl implements ISysTeachingProgramService 
{
    @Autowired
    private SysTeachingProgramMapper sysTeachingProgramMapper;

    /**
     * 查询培养方案
     * 
     * @param programId 培养方案主键
     * @return 培养方案
     */
    @Override
    public SysTeachingProgram selectSysTeachingProgramByProgramId(Long programId)
    {
        return sysTeachingProgramMapper.selectSysTeachingProgramByProgramId(programId);
    }

    /**
     * 查询培养方案列表
     * 
     * @param sysTeachingProgram 培养方案
     * @return 培养方案
     */
    @Override
    public TableDataInfo selectSysTeachingProgramList(SysTeachingProgram sysTeachingProgram)
    {
        startPage();
        List<SysTeachingProgram> list = sysTeachingProgramMapper.selectSysTeachingProgramList(sysTeachingProgram);
        return getDataTable(list);
    }

    /**
     * 新增培养方案
     * 
     * @param sysTeachingProgram 培养方案
     * @return 结果
     */
    @Override
    public int insertSysTeachingProgram(SysTeachingProgram sysTeachingProgram)
    {
        return sysTeachingProgramMapper.insertSysTeachingProgram(sysTeachingProgram);
    }

    /**
     * 修改培养方案
     * 
     * @param sysTeachingProgram 培养方案
     * @return 结果
     */
    @Override
    public int updateSysTeachingProgram(SysTeachingProgram sysTeachingProgram)
    {
        return sysTeachingProgramMapper.updateSysTeachingProgram(sysTeachingProgram);
    }

    /**
     * 批量删除培养方案
     * 
     * @param programIds 需要删除的培养方案主键集合
     * @return 结果
     */
    @Override
    public int deleteSysTeachingProgramByProgramIds(Long[] programIds)
    {
        return sysTeachingProgramMapper.deleteSysTeachingProgramByProgramIds(programIds);
    }

    /**
     * 删除培养方案信息
     * 
     * @param programId 培养方案主键
     * @return 结果
     */
    @Override
    public int deleteSysTeachingProgramByProgramId(Long programId)
    {
        return sysTeachingProgramMapper.deleteSysTeachingProgramByProgramId(programId);
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
