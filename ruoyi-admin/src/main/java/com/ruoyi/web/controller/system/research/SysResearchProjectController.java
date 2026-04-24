package com.ruoyi.web.controller.system.research;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.domain.entity.SysResearchProject;
import com.ruoyi.system.service.research.ISysResearchProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科研项目Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/research/project")
public class SysResearchProjectController extends BaseController
{
    @Autowired
    private ISysResearchProjectService sysResearchProjectService;

    /**
     * 查询科研项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysResearchProject sysResearchProject)
    {
        return sysResearchProjectService.selectSysResearchProjectList(sysResearchProject);
    }

    /**
     * 导出科研项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:export')")
    @Log(title = "科研项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysResearchProject sysResearchProject)
    {
        List<?> rows = sysResearchProjectService.selectSysResearchProjectList(sysResearchProject).getRows();
        List<SysResearchProject> list = rows.stream().map(item -> (SysResearchProject) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysResearchProject> util = new ExcelUtil<SysResearchProject>(SysResearchProject.class);
        util.exportExcel(response, list, "科研项目数据");
    }

    /**
     * 获取科研项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return AjaxResult.success(sysResearchProjectService.selectSysResearchProjectByProjectId(projectId));
    }

    /**
     * 新增科研项目
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:add')")
    @Log(title = "科研项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysResearchProject sysResearchProject)
    {
        return toAjax(sysResearchProjectService.insertSysResearchProject(sysResearchProject));
    }

    /**
     * 修改科研项目
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:edit')")
    @Log(title = "科研项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysResearchProject sysResearchProject)
    {
        return toAjax(sysResearchProjectService.updateSysResearchProject(sysResearchProject));
    }

    /**
     * 删除科研项目
     */
    @PreAuthorize("@ss.hasPermi('system:research:project:remove')")
    @Log(title = "科研项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(sysResearchProjectService.deleteSysResearchProjectByProjectIds(projectIds));
    }
}
