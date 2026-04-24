package com.ruoyi.system.service.student;

import com.ruoyi.common.core.domain.entity.SysStudentSource;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生源数据Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysStudentSourceService 
{
    /**
     * 查询生源数据
     * 
     * @param sourceId 生源数据主键
     * @return 生源数据
     */
    public SysStudentSource selectSysStudentSourceBySourceId(Long sourceId);

    /**
     * 查询生源数据列表
     * 
     * @param sysStudentSource 生源数据
     * @return 生源数据集合
     */
    public TableDataInfo selectSysStudentSourceList(SysStudentSource sysStudentSource);

    /**
     * 新增生源数据
     * 
     * @param sysStudentSource 生源数据
     * @return 结果
     */
    public int insertSysStudentSource(SysStudentSource sysStudentSource);

    /**
     * 修改生源数据
     * 
     * @param sysStudentSource 生源数据
     * @return 结果
     */
    public int updateSysStudentSource(SysStudentSource sysStudentSource);

    /**
     * 批量删除生源数据
     * 
     * @param sourceIds 需要删除的生源数据主键集合
     * @return 结果
     */
    public int deleteSysStudentSourceBySourceIds(Long[] sourceIds);

    /**
     * 删除生源数据信息
     * 
     * @param sourceId 生源数据主键
     * @return 结果
     */
    public int deleteSysStudentSourceBySourceId(Long sourceId);
}
