package com.ruoyi.system.mapper.research;

import com.ruoyi.common.core.domain.entity.SysResearchFunding;

import java.util.List;

/**
 * 横向项目经费Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface SysResearchFundingMapper 
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
    public List<SysResearchFunding> selectSysResearchFundingList(SysResearchFunding sysResearchFunding);

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
     * 删除横向项目经费
     * 
     * @param fundingId 横向项目经费主键
     * @return 结果
     */
    public int deleteSysResearchFundingByFundingId(Long fundingId);

    /**
     * 批量删除横向项目经费
     * 
     * @param fundingIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysResearchFundingByFundingIds(Long[] fundingIds);
}
