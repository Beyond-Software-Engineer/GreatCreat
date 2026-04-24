package com.ruoyi.system.service.research.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysResearchFunding;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.research.SysResearchFundingMapper;
import com.ruoyi.system.service.research.ISysResearchFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 横向项目经费Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysResearchFundingServiceImpl implements ISysResearchFundingService 
{
    @Autowired
    private SysResearchFundingMapper sysResearchFundingMapper;

    /**
     * 查询横向项目经费
     * 
     * @param fundingId 横向项目经费主键
     * @return 横向项目经费
     */
    @Override
    public SysResearchFunding selectSysResearchFundingByFundingId(Long fundingId)
    {
        return sysResearchFundingMapper.selectSysResearchFundingByFundingId(fundingId);
    }

    /**
     * 查询横向项目经费列表
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 横向项目经费
     */
    @Override
    public TableDataInfo selectSysResearchFundingList(SysResearchFunding sysResearchFunding)
    {
        startPage();
        List<SysResearchFunding> list = sysResearchFundingMapper.selectSysResearchFundingList(sysResearchFunding);
        return getDataTable(list);
    }

    /**
     * 新增横向项目经费
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 结果
     */
    @Override
    public int insertSysResearchFunding(SysResearchFunding sysResearchFunding)
    {
        return sysResearchFundingMapper.insertSysResearchFunding(sysResearchFunding);
    }

    /**
     * 修改横向项目经费
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 结果
     */
    @Override
    public int updateSysResearchFunding(SysResearchFunding sysResearchFunding)
    {
        return sysResearchFundingMapper.updateSysResearchFunding(sysResearchFunding);
    }

    /**
     * 批量删除横向项目经费
     * 
     * @param fundingIds 需要删除的横向项目经费主键集合
     * @return 结果
     */
    @Override
    public int deleteSysResearchFundingByFundingIds(Long[] fundingIds)
    {
        return sysResearchFundingMapper.deleteSysResearchFundingByFundingIds(fundingIds);
    }

    /**
     * 删除横向项目经费信息
     * 
     * @param fundingId 横向项目经费主键
     * @return 结果
     */
    @Override
    public int deleteSysResearchFundingByFundingId(Long fundingId)
    {
        return sysResearchFundingMapper.deleteSysResearchFundingByFundingId(fundingId);
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
