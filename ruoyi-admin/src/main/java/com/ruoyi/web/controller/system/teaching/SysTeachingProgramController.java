package com.ruoyi.web.controller.system.teaching;

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
import com.ruoyi.common.core.domain.entity.SysTeachingProgram;
import com.ruoyi.system.service.teaching.ISysTeachingProgramService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 培养方案Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/teaching/program")
public class SysTeachingProgramController extends BaseController
{
    @Autowired
    private ISysTeachingProgramService sysTeachingProgramService;

    /**
     * 查询培养方案列表
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTeachingProgram sysTeachingProgram)
    {
        return sysTeachingProgramService.selectSysTeachingProgramList(sysTeachingProgram);
    }

    /**
     * 导出培养方案列表
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:export')")
    @Log(title = "培养方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTeachingProgram sysTeachingProgram)
    {
        List<?> rows = sysTeachingProgramService.selectSysTeachingProgramList(sysTeachingProgram).getRows();
        List<SysTeachingProgram> list = rows.stream().map(item -> (SysTeachingProgram) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysTeachingProgram> util = new ExcelUtil<SysTeachingProgram>(SysTeachingProgram.class);
        util.exportExcel(response, list, "培养方案数据");
    }

    /**
     * 获取培养方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:query')")
    @GetMapping(value = "/{programId}")
    public AjaxResult getInfo(@PathVariable("programId") Long programId)
    {
        return AjaxResult.success(sysTeachingProgramService.selectSysTeachingProgramByProgramId(programId));
    }

    /**
     * 新增培养方案
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:add')")
    @Log(title = "培养方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTeachingProgram sysTeachingProgram)
    {
        return toAjax(sysTeachingProgramService.insertSysTeachingProgram(sysTeachingProgram));
    }

    /**
     * 修改培养方案
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:edit')")
    @Log(title = "培养方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTeachingProgram sysTeachingProgram)
    {
        return toAjax(sysTeachingProgramService.updateSysTeachingProgram(sysTeachingProgram));
    }

    /**
     * 删除培养方案
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:program:remove')")
    @Log(title = "培养方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{programIds}")
    public AjaxResult remove(@PathVariable Long[] programIds)
    {
        return toAjax(sysTeachingProgramService.deleteSysTeachingProgramByProgramIds(programIds));
    }
}
