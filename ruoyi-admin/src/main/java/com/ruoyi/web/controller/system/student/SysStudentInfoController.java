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
import com.ruoyi.common.core.domain.entity.SysStudentInfo;
import com.ruoyi.system.service.student.ISysStudentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/student/info")
public class SysStudentInfoController extends BaseController
{
    @Autowired
    private ISysStudentInfoService sysStudentInfoService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysStudentInfo sysStudentInfo)
    {
        return sysStudentInfoService.selectSysStudentInfoList(sysStudentInfo);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysStudentInfo sysStudentInfo)
    {
        List<?> rows = sysStudentInfoService.selectSysStudentInfoList(sysStudentInfo).getRows();
        List<SysStudentInfo> list = rows.stream().map(item -> (SysStudentInfo) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysStudentInfo> util = new ExcelUtil<SysStudentInfo>(SysStudentInfo.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:query')")
    @GetMapping(value = "/{studentId}")
    public AjaxResult getInfo(@PathVariable("studentId") Long studentId)
    {
        return AjaxResult.success(sysStudentInfoService.selectSysStudentInfoByStudentId(studentId));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysStudentInfo sysStudentInfo)
    {
        return toAjax(sysStudentInfoService.insertSysStudentInfo(sysStudentInfo));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysStudentInfo sysStudentInfo)
    {
        return toAjax(sysStudentInfoService.updateSysStudentInfo(sysStudentInfo));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('system:student:info:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{studentIds}")
    public AjaxResult remove(@PathVariable Long[] studentIds)
    {
        return toAjax(sysStudentInfoService.deleteSysStudentInfoByStudentIds(studentIds));
    }
}
