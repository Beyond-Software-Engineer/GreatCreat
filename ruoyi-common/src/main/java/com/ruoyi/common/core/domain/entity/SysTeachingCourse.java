package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 开课信息对象 sys_teaching_course
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysTeachingCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 开课ID */
    private Long courseId;

    /** 学期 */
    @NotBlank(message = "学期不能为空")
    @Size(min = 0, max = 20, message = "学期长度不能超过20个字符")
    private String semester;

    /** 课程名称 */
    @NotBlank(message = "课程名称不能为空")
    @Size(min = 0, max = 100, message = "课程名称长度不能超过100个字符")
    private String courseName;

    /** 课程编码 */
    @NotBlank(message = "课程编码不能为空")
    @Size(min = 0, max = 50, message = "课程编码长度不能超过50个字符")
    private String courseCode;

    /** 授课教师 */
    @NotBlank(message = "授课教师不能为空")
    @Size(min = 0, max = 50, message = "授课教师长度不能超过50个字符")
    private String teacherName;

    /** 授课专业 */
    @Size(min = 0, max = 50, message = "授课专业长度不能超过50个字符")
    private String majorName;

    /** 授课班级 */
    @Size(min = 0, max = 50, message = "授课班级长度不能超过50个字符")
    private String className;

    /** 课时数 */
    private Integer courseHours;

    /** 课程类型 */
    @Size(min = 0, max = 20, message = "课程类型长度不能超过20个字符")
    private String courseType;

    /** 审核状态（0待审核 1已通过 2已驳回） */
    private String status;

    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }

    public Long getCourseId()
    {
        return courseId;
    }
    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getSemester()
    {
        return semester;
    }
    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getCourseName()
    {
        return courseName;
    }
    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public String getCourseCode()
    {
        return courseCode;
    }
    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getTeacherName()
    {
        return teacherName;
    }
    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMajorName()
    {
        return majorName;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setCourseHours(Integer courseHours)
    {
        this.courseHours = courseHours;
    }

    public Integer getCourseHours()
    {
        return courseHours;
    }
    public void setCourseType(String courseType)
    {
        this.courseType = courseType;
    }

    public String getCourseType()
    {
        return courseType;
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
            .append("courseId", getCourseId())
            .append("semester", getSemester())
            .append("courseName", getCourseName())
            .append("courseCode", getCourseCode())
            .append("teacherName", getTeacherName())
            .append("majorName", getMajorName())
            .append("className", getClassName())
            .append("courseHours", getCourseHours())
            .append("courseType", getCourseType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
