package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 培养方案对象 sys_teaching_program
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysTeachingProgram extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 方案ID */
    private Long programId;

    /** 专业名称 */
    @NotBlank(message = "专业名称不能为空")
    @Size(min = 0, max = 50, message = "专业名称长度不能超过50个字符")
    private String majorName;

    /** 学制 */
    @Size(min = 0, max = 20, message = "学制长度不能超过20个字符")
    private String educationSystem;

    /** 培养目标 */
    private String trainingGoal;

    /** 核心课程 */
    @NotBlank(message = "核心课程不能为空")
    private String coreCourses;

    /** 课程学分 */
    @NotBlank(message = "课程学分不能为空")
    private String courseCredits;

    /** 学分要求 */
    private Double creditRequirement;

    /** 毕业条件 */
    private String graduationRequirement;

    /** 版本号 */
    @Size(min = 0, max = 20, message = "版本号长度不能超过20个字符")
    private String version;

    /** 状态（0草稿 1已发布） */
    private String status;

    public void setProgramId(Long programId)
    {
        this.programId = programId;
    }

    public Long getProgramId()
    {
        return programId;
    }
    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMajorName()
    {
        return majorName;
    }
    public void setEducationSystem(String educationSystem)
    {
        this.educationSystem = educationSystem;
    }

    public String getEducationSystem()
    {
        return educationSystem;
    }
    public void setTrainingGoal(String trainingGoal)
    {
        this.trainingGoal = trainingGoal;
    }

    public String getTrainingGoal()
    {
        return trainingGoal;
    }
    public void setCoreCourses(String coreCourses)
    {
        this.coreCourses = coreCourses;
    }

    public String getCoreCourses()
    {
        return coreCourses;
    }
    public void setCourseCredits(String courseCredits)
    {
        this.courseCredits = courseCredits;
    }

    public String getCourseCredits()
    {
        return courseCredits;
    }
    public void setCreditRequirement(Double creditRequirement)
    {
        this.creditRequirement = creditRequirement;
    }

    public Double getCreditRequirement()
    {
        return creditRequirement;
    }
    public void setGraduationRequirement(String graduationRequirement)
    {
        this.graduationRequirement = graduationRequirement;
    }

    public String getGraduationRequirement()
    {
        return graduationRequirement;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("programId", getProgramId())
            .append("majorName", getMajorName())
            .append("educationSystem", getEducationSystem())
            .append("trainingGoal", getTrainingGoal())
            .append("coreCourses", getCoreCourses())
            .append("courseCredits", getCourseCredits())
            .append("creditRequirement", getCreditRequirement())
            .append("graduationRequirement", getGraduationRequirement())
            .append("version", getVersion())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
