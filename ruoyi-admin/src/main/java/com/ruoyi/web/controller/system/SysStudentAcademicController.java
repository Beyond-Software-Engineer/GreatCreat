package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.domain.entity.SysStudentAcademic;
import com.ruoyi.system.service.ISysStudentAcademicService;

/**
 * 学生学业数据管理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/academic")
public class SysStudentAcademicController extends BaseController
{
    @Autowired
    private ISysStudentAcademicService academicService;

    /**
     * 获取学业数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:academic:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysStudentAcademic academic)
    {
        startPage();
        List<SysStudentAcademic> list = academicService.selectAcademicList(academic);
        return getDataTable(list);
    }

    @Log(title = "学业数据管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:academic:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysStudentAcademic academic)
    {
        List<SysStudentAcademic> list = academicService.selectAcademicList(academic);
        ExcelUtil<SysStudentAcademic> util = new ExcelUtil<SysStudentAcademic>(SysStudentAcademic.class);
        util.exportExcel(response, list, "学业数据");
    }

    @Log(title = "学业数据管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:academic:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysStudentAcademic> util = new ExcelUtil<SysStudentAcademic>(SysStudentAcademic.class);
        List<SysStudentAcademic> academicList = util.importExcel(file.getInputStream());
        String message = "";
        int successCount = 0;
        int failureCount = 0;
        for (SysStudentAcademic academic : academicList)
        {
            try
            {
                if (updateSupport)
                {
                    // 尝试更新
                    List<SysStudentAcademic> existingList = academicService.selectAcademicByStudentId(academic.getStudentId());
                    if (!existingList.isEmpty())
                    {
                        academic.setAcademicId(existingList.get(0).getAcademicId());
                        academicService.updateAcademic(academic);
                    }
                    else
                    {
                        academicService.insertAcademic(academic);
                    }
                    successCount++;
                }
                else
                {
                    academicService.insertAcademic(academic);
                    successCount++;
                }
            }
            catch (Exception e)
            {
                failureCount++;
            }
        }
        message = "导入成功" + successCount + "条，失败" + failureCount + "条";
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysStudentAcademic> util = new ExcelUtil<SysStudentAcademic>(SysStudentAcademic.class);
        util.importTemplateExcel(response, "学业数据");
    }

    /**
     * 根据学业数据ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:academic:query')")
    @GetMapping(value = { "/", "/{academicId}" })
    public AjaxResult getInfo(@PathVariable(value = "academicId", required = false) Long academicId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (academicId != null)
        {
            ajax.put(AjaxResult.DATA_TAG, academicService.selectAcademicById(academicId));
        }
        return ajax;
    }

    /**
     * 新增学业数据
     */
    @PreAuthorize("@ss.hasPermi('system:academic:add')")
    @Log(title = "学业数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysStudentAcademic academic)
    {
        academic.setCreateBy(getUsername());
        return toAjax(academicService.insertAcademic(academic));
    }

    /**
     * 修改学业数据
     */
    @PreAuthorize("@ss.hasPermi('system:academic:edit')")
    @Log(title = "学业数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysStudentAcademic academic)
    {
        academic.setUpdateBy(getUsername());
        return toAjax(academicService.updateAcademic(academic));
    }

    /**
     * 删除学业数据
     */
    @PreAuthorize("@ss.hasPermi('system:academic:remove')")
    @Log(title = "学业数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{academicIds}")
    public AjaxResult remove(@PathVariable Long[] academicIds)
    {
        return toAjax(academicService.deleteAcademicByIds(academicIds));
    }

    /**
     * 核算学生总学分
     */
    @PreAuthorize("@ss.hasPermi('system:academic:calculate')")
    @GetMapping("/calculate/{studentId}")
    public AjaxResult calculateCredits(@PathVariable String studentId)
    {
        Double totalCredits = academicService.calculateTotalCredits(studentId);
        return success("核算成功，总学分为：" + totalCredits);
    }

    /**
     * 更新学生学业状态
     */
    @PreAuthorize("@ss.hasPermi('system:academic:updateStatus')")
    @PutMapping("/updateStatus/{studentId}")
    public AjaxResult updateStatus(@PathVariable String studentId)
    {
        int result = academicService.updateAcademicStatus(studentId);
        return toAjax(result);
    }
}
