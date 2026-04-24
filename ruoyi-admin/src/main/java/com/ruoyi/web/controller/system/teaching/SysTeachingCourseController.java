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
import com.ruoyi.common.core.domain.entity.SysTeachingCourse;
import com.ruoyi.system.service.teaching.ISysTeachingCourseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开课信息Controller
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@RestController
@RequestMapping("/system/teaching/course")
public class SysTeachingCourseController extends BaseController
{
    @Autowired
    private ISysTeachingCourseService sysTeachingCourseService;

    /**
     * 查询开课信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTeachingCourse sysTeachingCourse)
    {
        return sysTeachingCourseService.selectSysTeachingCourseList(sysTeachingCourse);
    }

    /**
     * 导出开课信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:export')")
    @Log(title = "开课信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTeachingCourse sysTeachingCourse)
    {
        List<?> rows = sysTeachingCourseService.selectSysTeachingCourseList(sysTeachingCourse).getRows();
        List<SysTeachingCourse> list = rows.stream().map(item -> (SysTeachingCourse) item).collect(java.util.stream.Collectors.toList());
        ExcelUtil<SysTeachingCourse> util = new ExcelUtil<SysTeachingCourse>(SysTeachingCourse.class);
        util.exportExcel(response, list, "开课信息数据");
    }

    /**
     * 获取开课信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:query')")
    @GetMapping(value = "/{courseId}")
    public AjaxResult getInfo(@PathVariable("courseId") Long courseId)
    {
        return AjaxResult.success(sysTeachingCourseService.selectSysTeachingCourseByCourseId(courseId));
    }

    /**
     * 新增开课信息
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:add')")
    @Log(title = "开课信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTeachingCourse sysTeachingCourse)
    {
        return toAjax(sysTeachingCourseService.insertSysTeachingCourse(sysTeachingCourse));
    }

    /**
     * 修改开课信息
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:edit')")
    @Log(title = "开课信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTeachingCourse sysTeachingCourse)
    {
        return toAjax(sysTeachingCourseService.updateSysTeachingCourse(sysTeachingCourse));
    }

    /**
     * 删除开课信息
     */
    @PreAuthorize("@ss.hasPermi('system:teaching:course:remove')")
    @Log(title = "开课信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds)
    {
        return toAjax(sysTeachingCourseService.deleteSysTeachingCourseByCourseIds(courseIds));
    }
}
