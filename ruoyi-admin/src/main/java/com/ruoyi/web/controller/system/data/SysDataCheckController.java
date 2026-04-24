package com.ruoyi.web.controller.system.data;

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
import com.ruoyi.common.core.domain.entity.SysDataCheck;
import com.ruoyi.system.service.data.ISysDataCheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据核对Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/data/check")
public class SysDataCheckController extends BaseController
{
    @Autowired
    private ISysDataCheckService sysDataCheckService;

    /**
     * 查询数据核对列表
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDataCheck sysDataCheck)
    {
        return sysDataCheckService.selectSysDataCheckList(sysDataCheck);
    }

    /**
     * 导出数据核对列表
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:export')")
    @Log(title = "数据核对", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDataCheck sysDataCheck)
    {
        List<?> rows = sysDataCheckService.selectSysDataCheckList(sysDataCheck).getRows();
        List<SysDataCheck> list = rows.stream().map(item -> (SysDataCheck) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysDataCheck> util = new ExcelUtil<SysDataCheck>(SysDataCheck.class);
        util.exportExcel(response, list, "数据核对数据");
    }

    /**
     * 获取数据核对详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:query')")
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return AjaxResult.success(sysDataCheckService.selectSysDataCheckByCheckId(checkId));
    }

    /**
     * 新增数据核对
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:add')")
    @Log(title = "数据核对", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDataCheck sysDataCheck)
    {
        return toAjax(sysDataCheckService.insertSysDataCheck(sysDataCheck));
    }

    /**
     * 修改数据核对
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:edit')")
    @Log(title = "数据核对", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDataCheck sysDataCheck)
    {
        return toAjax(sysDataCheckService.updateSysDataCheck(sysDataCheck));
    }

    /**
     * 删除数据核对
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:remove')")
    @Log(title = "数据核对", businessType = BusinessType.DELETE)
    @DeleteMapping("/{checkIds}")
    public AjaxResult remove(@PathVariable Long[] checkIds)
    {
        return toAjax(sysDataCheckService.deleteSysDataCheckByCheckIds(checkIds));
    }

    /**
     * 自动数据核对
     */
    @PreAuthorize("@ss.hasPermi('system:data:check:auto')")
    @Log(title = "数据核对", businessType = BusinessType.OTHER)
    @PostMapping("/auto")
    public AjaxResult autoCheck()
    {
        return toAjax(sysDataCheckService.autoCheck());
    }
}
