package com.ruoyi.system.service.research;

import com.ruoyi.common.core.domain.entity.SysResearchFunding;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 横向项目经费Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysResearchFundingService 
{
    /**
     * 查询横向项目经费
     * 
     * @param fundingId 横向项目经费主键
     * @return 横向项目经费
     */
    public SysResearchFunding selectSysResearchFundingByFundingId(Long fundingId);

    /**
     * 查询横向项目经费列表
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 横向项目经费集合
     */
    public TableDataInfo selectSysResearchFundingList(SysResearchFunding sysResearchFunding);

    /**
     * 新增横向项目经费
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 结果
     */
    public int insertSysResearchFunding(SysResearchFunding sysResearchFunding);

    /**
     * 修改横向项目经费
     * 
     * @param sysResearchFunding 横向项目经费
     * @return 结果
     */
    public int updateSysResearchFunding(SysResearchFunding sysResearchFunding);

    /**
     * 批量删除横向项目经费
     * 
     * @param fundingIds 需要删除的横向项目经费主键集合
     * @return 结果
     */
    public int deleteSysResearchFundingByFundingIds(Long[] fundingIds);

    /**
     * 删除横向项目经费信息
     * 
     * @param fundingId 横向项目经费主键
     * @return 结果
     */
    public int deleteSysResearchFundingByFundingId(Long fundingId);
}
