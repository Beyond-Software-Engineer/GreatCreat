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
import com.ruoyi.common.core.domain.entity.SysResearchFunding;
import com.ruoyi.system.service.research.ISysResearchFundingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 横向项目经费Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/research/funding")
public class SysResearchFundingController extends BaseController
{
    @Autowired
    private ISysResearchFundingService sysResearchFundingService;

    /**
     * 查询横向项目经费列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysResearchFunding sysResearchFunding)
    {
        return sysResearchFundingService.selectSysResearchFundingList(sysResearchFunding);
    }

    /**
     * 导出横向项目经费列表
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:export')")
    @Log(title = "横向项目经费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysResearchFunding sysResearchFunding)
    {
        List<?> rows = sysResearchFundingService.selectSysResearchFundingList(sysResearchFunding).getRows();
        List<SysResearchFunding> list = rows.stream().map(item -> (SysResearchFunding) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysResearchFunding> util = new ExcelUtil<SysResearchFunding>(SysResearchFunding.class);
        util.exportExcel(response, list, "横向项目经费数据");
    }

    /**
     * 获取横向项目经费详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:query')")
    @GetMapping(value = "/{fundingId}")
    public AjaxResult getInfo(@PathVariable("fundingId") Long fundingId)
    {
        return AjaxResult.success(sysResearchFundingService.selectSysResearchFundingByFundingId(fundingId));
    }

    /**
     * 新增横向项目经费
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:add')")
    @Log(title = "横向项目经费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysResearchFunding sysResearchFunding)
    {
        return toAjax(sysResearchFundingService.insertSysResearchFunding(sysResearchFunding));
    }

    /**
     * 修改横向项目经费
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:edit')")
    @Log(title = "横向项目经费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysResearchFunding sysResearchFunding)
    {
        return toAjax(sysResearchFundingService.updateSysResearchFunding(sysResearchFunding));
    }

    /**
     * 删除横向项目经费
     */
    @PreAuthorize("@ss.hasPermi('system:research:funding:remove')")
    @Log(title = "横向项目经费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fundingIds}")
    public AjaxResult remove(@PathVariable Long[] fundingIds)
    {
        return toAjax(sysResearchFundingService.deleteSysResearchFundingByFundingIds(fundingIds));
    }
}
