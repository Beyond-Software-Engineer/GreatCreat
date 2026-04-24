package com.ruoyi.system.mapper.data;

import com.ruoyi.common.core.domain.entity.SysDataCheck;

import java.util.List;

/**
 * 数据核对Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface SysDataCheckMapper 
{
    /**
     * 查询数据核对
     * 
     * @param checkId 数据核对主键
     * @return 数据核对
     */
    public SysDataCheck selectSysDataCheckByCheckId(Long checkId);

    /**
     * 查询数据核对列表
     * 
     * @param sysDataCheck 数据核对
     * @return 数据核对集合
     */
    public List<SysDataCheck> selectSysDataCheckList(SysDataCheck sysDataCheck);

    /**
     * 新增数据核对
     * 
     * @param sysDataCheck 数据核对
     * @return 结果
     */
    public int insertSysDataCheck(SysDataCheck sysDataCheck);

    /**
     * 修改数据核对
     * 
     * @param sysDataCheck 数据核对
     * @return 结果
     */
    public int updateSysDataCheck(SysDataCheck sysDataCheck);

    /**
     * 删除数据核对
     * 
     * @param checkId 数据核对主键
     * @return 结果
     */
    public int deleteSysDataCheckByCheckId(Long checkId);

    /**
     * 批量删除数据核对
     * 
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDataCheckByCheckIds(Long[] checkIds);
}
