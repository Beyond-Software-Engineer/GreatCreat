package com.ruoyi.common.core.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生学业数据对象 sys_student_academic
 * 
 * @author ruoyi
 */
public class SysStudentAcademic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学业数据ID */
    @Excel(name = "学业数据序号", cellType = ColumnType.NUMERIC, prompt = "学业数据编号")
    private Long academicId;

    /** 学号 */
    @Excel(name = "学号")
    @NotBlank(message = "学号不能为空")
    @Size(min = 0, max = 20, message = "学号长度不能超过20个字符")
    private String studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(min = 0, max = 30, message = "姓名长度不能超过30个字符")
    private String studentName;

    /** 学期 */
    @Excel(name = "学期")
    private String semester;

    /** 修读课程 */
    @Excel(name = "修读课程")
    @NotBlank(message = "修读课程不能为空")
    @Size(min = 0, max = 100, message = "课程名称长度不能超过100个字符")
    private String courseName;

    /** 课程成绩 */
    @Excel(name = "课程成绩")
    private Double courseScore;

    /** 获得学分 */
    @Excel(name = "获得学分")
    @NotBlank(message = "获得学分不能为空")
    private Double obtainedCredits;

    /** 总学分 */
    @Excel(name = "总学分")
    private Double totalCredits;

    /** 专业排名 */
    @Excel(name = "专业排名")
    private Integer majorRank;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String majorName;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String className;

    /** 学业状态（0正常 1预警） */
    @Excel(name = "学业状态", readConverterExp = "0=正常,1=预警")
    private String academicStatus;

    public Long getAcademicId()
    {
        return academicId;
    }

    public void setAcademicId(Long academicId)
    {
        this.academicId = academicId;
    }

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public Double getCourseScore()
    {
        return courseScore;
    }

    public void setCourseScore(Double courseScore)
    {
        this.courseScore = courseScore;
    }

    public Double getObtainedCredits()
    {
        return obtainedCredits;
    }

    public void setObtainedCredits(Double obtainedCredits)
    {
        this.obtainedCredits = obtainedCredits;
    }

    public Double getTotalCredits()
    {
        return totalCredits;
    }

    public void setTotalCredits(Double totalCredits)
    {
        this.totalCredits = totalCredits;
    }

    public Integer getMajorRank()
    {
        return majorRank;
    }

    public void setMajorRank(Integer majorRank)
    {
        this.majorRank = majorRank;
    }

    public String getMajorName()
    {
        return majorName;
    }

    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getAcademicStatus()
    {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus)
    {
        this.academicStatus = academicStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("academicId", getAcademicId())
            .append("studentId", getStudentId())
            .append("studentName", getStudentName())
            .append("semester", getSemester())
            .append("courseName", getCourseName())
            .append("courseScore", getCourseScore())
            .append("obtainedCredits", getObtainedCredits())
            .append("totalCredits", getTotalCredits())
            .append("majorRank", getMajorRank())
            .append("majorName", getMajorName())
            .append("className", getClassName())
            .append("academicStatus", getAcademicStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}