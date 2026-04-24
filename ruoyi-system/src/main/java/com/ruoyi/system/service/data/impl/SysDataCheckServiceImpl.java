package com.ruoyi.system.service.data.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDataCheck;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.data.SysDataCheckMapper;
import com.ruoyi.system.service.data.ISysDataCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据核对Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysDataCheckServiceImpl implements ISysDataCheckService 
{
    @Autowired
    private SysDataCheckMapper sysDataCheckMapper;

    /**
     * 查询数据核对
     * 
     * @param checkId 数据核对主键
     * @return 数据核对
     */
    @Override
    public SysDataCheck selectSysDataCheckByCheckId(Long checkId)
    {
        return sysDataCheckMapper.selectSysDataCheckByCheckId(checkId);
    }

    /**
     * 查询数据核对列表
     * 
     * @param sysDataCheck 数据核对
     * @return 数据核对
     */
    @Override
    public TableDataInfo selectSysDataCheckList(SysDataCheck sysDataCheck)
    {
        startPage();
        List<SysDataCheck> list = sysDataCheckMapper.selectSysDataCheckList(sysDataCheck);
        return getDataTable(list);
    }

    /**
     * 新增数据核对
     * 
     * @param sysDataCheck 数据核对
     * @return 结果
     */
    @Override
    public int insertSysDataCheck(SysDataCheck sysDataCheck)
    {
        return sysDataCheckMapper.insertSysDataCheck(sysDataCheck);
    }

    /**
     * 修改数据核对
     * 
     * @param sysDataCheck 数据核对
     * @return 结果
     */
    @Override
    public int updateSysDataCheck(SysDataCheck sysDataCheck)
    {
        return sysDataCheckMapper.updateSysDataCheck(sysDataCheck);
    }

    /**
     * 批量删除数据核对
     * 
     * @param checkIds 需要删除的数据核对主键集合
     * @return 结果
     */
    @Override
    public int deleteSysDataCheckByCheckIds(Long[] checkIds)
    {
        return sysDataCheckMapper.deleteSysDataCheckByCheckIds(checkIds);
    }

    /**
     * 删除数据核对信息
     * 
     * @param checkId 数据核对主键
     * @return 结果
     */
    @Override
    public int deleteSysDataCheckByCheckId(Long checkId)
    {
        return sysDataCheckMapper.deleteSysDataCheckByCheckId(checkId);
    }

    /**
     * 自动数据核对
     * 
     * @return 结果
     */
    @Override
    public int autoCheck() {
        // 这里实现自动核对逻辑
        // 例如检查科研数据、教学数据、学生数据的一致性
        // 暂时返回成功
        return 1;
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
