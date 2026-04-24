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
import com.ruoyi.common.core.domain.entity.SysResearchPaper;
import com.ruoyi.system.service.research.ISysResearchPaperService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科研论文Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/research/paper")
public class SysResearchPaperController extends BaseController
{
    @Autowired
    private ISysResearchPaperService sysResearchPaperService;

    /**
     * 查询科研论文列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysResearchPaper sysResearchPaper)
    {
        return sysResearchPaperService.selectSysResearchPaperList(sysResearchPaper);
    }

    /**
     * 导出科研论文列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:export')")
    @Log(title = "科研论文", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysResearchPaper sysResearchPaper)
    {
        List<?> rows = sysResearchPaperService.selectSysResearchPaperList(sysResearchPaper).getRows();
        List<SysResearchPaper> list = rows.stream().map(item -> (SysResearchPaper) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysResearchPaper> util = new ExcelUtil<SysResearchPaper>(SysResearchPaper.class);
        util.exportExcel(response, list, "科研论文数据");
    }

    /**
     * 获取科研论文详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:query')")
    @GetMapping(value = "/{paperId}")
    public AjaxResult getInfo(@PathVariable("paperId") Long paperId)
    {
        return AjaxResult.success(sysResearchPaperService.selectSysResearchPaperByPaperId(paperId));
    }

    /**
     * 新增科研论文
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:add')")
    @Log(title = "科研论文", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysResearchPaper sysResearchPaper)
    {
        return toAjax(sysResearchPaperService.insertSysResearchPaper(sysResearchPaper));
    }

    /**
     * 修改科研论文
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:edit')")
    @Log(title = "科研论文", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysResearchPaper sysResearchPaper)
    {
        return toAjax(sysResearchPaperService.updateSysResearchPaper(sysResearchPaper));
    }

    /**
     * 删除科研论文
     */
    @PreAuthorize("@ss.hasPermi('system:research:paper:remove')")
    @Log(title = "科研论文", businessType = BusinessType.DELETE)
    @DeleteMapping("/{paperIds}")
    public AjaxResult remove(@PathVariable Long[] paperIds)
    {
        return toAjax(sysResearchPaperService.deleteSysResearchPaperByPaperIds(paperIds));
    }
}
