package com.ruoyi.system.service.student.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysStudentSource;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.student.SysStudentSourceMapper;
import com.ruoyi.system.service.student.ISysStudentSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生源数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysStudentSourceServiceImpl implements ISysStudentSourceService 
{
    @Autowired
    private SysStudentSourceMapper sysStudentSourceMapper;

    /**
     * 查询生源数据
     * 
     * @param sourceId 生源数据主键
     * @return 生源数据
     */
    @Override
    public SysStudentSource selectSysStudentSourceBySourceId(Long sourceId)
    {
        return sysStudentSourceMapper.selectSysStudentSourceBySourceId(sourceId);
    }

    /**
     * 查询生源数据列表
     * 
     * @param sysStudentSource 生源数据
     * @return 生源数据
     */
    @Override
    public TableDataInfo selectSysStudentSourceList(SysStudentSource sysStudentSource)
    {
        startPage();
        List<SysStudentSource> list = sysStudentSourceMapper.selectSysStudentSourceList(sysStudentSource);
        return getDataTable(list);
    }

    /**
     * 新增生源数据
     * 
     * @param sysStudentSource 生源数据
     * @return 结果
     */
    @Override
    public int insertSysStudentSource(SysStudentSource sysStudentSource)
    {
        return sysStudentSourceMapper.insertSysStudentSource(sysStudentSource);
    }

    /**
     * 修改生源数据
     * 
     * @param sysStudentSource 生源数据
     * @return 结果
     */
    @Override
    public int updateSysStudentSource(SysStudentSource sysStudentSource)
    {
        return sysStudentSourceMapper.updateSysStudentSource(sysStudentSource);
    }

    /**
     * 批量删除生源数据
     * 
     * @param sourceIds 需要删除的生源数据主键集合
     * @return 结果
     */
    @Override
    public int deleteSysStudentSourceBySourceIds(Long[] sourceIds)
    {
        return sysStudentSourceMapper.deleteSysStudentSourceBySourceIds(sourceIds);
    }

    /**
     * 删除生源数据信息
     * 
     * @param sourceId 生源数据主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentSourceBySourceId(Long sourceId)
    {
        return sysStudentSourceMapper.deleteSysStudentSourceBySourceId(sourceId);
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
