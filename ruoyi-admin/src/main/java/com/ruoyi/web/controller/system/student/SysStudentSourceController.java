package com.ruoyi.web.controller.system.student;

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
import com.ruoyi.common.core.domain.entity.SysStudentSource;
import com.ruoyi.system.service.student.ISysStudentSourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生源数据Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/student/source")
public class SysStudentSourceController extends BaseController
{
    @Autowired
    private ISysStudentSourceService sysStudentSourceService;

    /**
     * 查询生源数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysStudentSource sysStudentSource)
    {
        return sysStudentSourceService.selectSysStudentSourceList(sysStudentSource);
    }

    /**
     * 导出生源数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:export')")
    @Log(title = "生源数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysStudentSource sysStudentSource)
    {
        List<?> rows = sysStudentSourceService.selectSysStudentSourceList(sysStudentSource).getRows();
        List<SysStudentSource> list = rows.stream().map(item -> (SysStudentSource) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysStudentSource> util = new ExcelUtil<SysStudentSource>(SysStudentSource.class);
        util.exportExcel(response, list, "生源数据");
    }

    /**
     * 获取生源数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:query')")
    @GetMapping(value = "/{sourceId}")
    public AjaxResult getInfo(@PathVariable("sourceId") Long sourceId)
    {
        return AjaxResult.success(sysStudentSourceService.selectSysStudentSourceBySourceId(sourceId));
    }

    /**
     * 新增生源数据
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:add')")
    @Log(title = "生源数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysStudentSource sysStudentSource)
    {
        return toAjax(sysStudentSourceService.insertSysStudentSource(sysStudentSource));
    }

    /**
     * 修改生源数据
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:edit')")
    @Log(title = "生源数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysStudentSource sysStudentSource)
    {
        return toAjax(sysStudentSourceService.updateSysStudentSource(sysStudentSource));
    }

    /**
     * 删除生源数据
     */
    @PreAuthorize("@ss.hasPermi('system:student:source:remove')")
    @Log(title = "生源数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sourceIds}")
    public AjaxResult remove(@PathVariable Long[] sourceIds)
    {
        return toAjax(sysStudentSourceService.deleteSysStudentSourceBySourceIds(sourceIds));
    }
}
