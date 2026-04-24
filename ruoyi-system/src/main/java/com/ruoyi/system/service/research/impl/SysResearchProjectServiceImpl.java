package com.ruoyi.system.service.research.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysResearchProject;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.research.SysResearchProjectMapper;
import com.ruoyi.system.service.research.ISysResearchProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 科研项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysResearchProjectServiceImpl implements ISysResearchProjectService 
{
    @Autowired
    private SysResearchProjectMapper sysResearchProjectMapper;

    /**
     * 查询科研项目
     * 
     * @param projectId 科研项目主键
     * @return 科研项目
     */
    @Override
    public SysResearchProject selectSysResearchProjectByProjectId(Long projectId)
    {
        return sysResearchProjectMapper.selectSysResearchProjectByProjectId(projectId);
    }

    /**
     * 查询科研项目列表
     * 
     * @param sysResearchProject 科研项目
     * @return 科研项目
     */
    @Override
    public TableDataInfo selectSysResearchProjectList(SysResearchProject sysResearchProject)
    {
        startPage();
        List<SysResearchProject> list = sysResearchProjectMapper.selectSysResearchProjectList(sysResearchProject);
        return getDataTable(list);
    }

    /**
     * 新增科研项目
     * 
     * @param sysResearchProject 科研项目
     * @return 结果
     */
    @Override
    public int insertSysResearchProject(SysResearchProject sysResearchProject)
    {
        return sysResearchProjectMapper.insertSysResearchProject(sysResearchProject);
    }

    /**
     * 修改科研项目
     * 
     * @param sysResearchProject 科研项目
     * @return 结果
     */
    @Override
    public int updateSysResearchProject(SysResearchProject sysResearchProject)
    {
        return sysResearchProjectMapper.updateSysResearchProject(sysResearchProject);
    }

    /**
     * 批量删除科研项目
     * 
     * @param projectIds 需要删除的科研项目主键集合
     * @return 结果
     */
    @Override
    public int deleteSysResearchProjectByProjectIds(Long[] projectIds)
    {
        return sysResearchProjectMapper.deleteSysResearchProjectByProjectIds(projectIds);
    }

    /**
     * 删除科研项目信息
     * 
     * @param projectId 科研项目主键
     * @return 结果
     */
    @Override
    public int deleteSysResearchProjectByProjectId(Long projectId)
    {
        return sysResearchProjectMapper.deleteSysResearchProjectByProjectId(projectId);
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
