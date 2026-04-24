package com.ruoyi.system.service.research;

import com.ruoyi.common.core.domain.entity.SysResearchProject;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科研项目Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysResearchProjectService 
{
    /**
     * 查询科研项目
     * 
     * @param projectId 科研项目主键
     * @return 科研项目
     */
    public SysResearchProject selectSysResearchProjectByProjectId(Long projectId);

    /**
     * 查询科研项目列表
     * 
     * @param sysResearchProject 科研项目
     * @return 科研项目集合
     */
    public TableDataInfo selectSysResearchProjectList(SysResearchProject sysResearchProject);

    /**
     * 新增科研项目
     * 
     * @param sysResearchProject 科研项目
     * @return 结果
     */
    public int insertSysResearchProject(SysResearchProject sysResearchProject);

    /**
     * 修改科研项目
     * 
     * @param sysResearchProject 科研项目
     * @return 结果
     */
    public int updateSysResearchProject(SysResearchProject sysResearchProject);

    /**
     * 批量删除科研项目
     * 
     * @param projectIds 需要删除的科研项目主键集合
     * @return 结果
     */
    public int deleteSysResearchProjectByProjectIds(Long[] projectIds);

    /**
     * 删除科研项目信息
     * 
     * @param projectId 科研项目主键
     * @return 结果
     */
    public int deleteSysResearchProjectByProjectId(Long projectId);
}
