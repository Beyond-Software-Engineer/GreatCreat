package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 学生信息对象 sys_student_info
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysStudentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生ID */
    private Long studentId;

    /** 学号 */
    @NotBlank(message = "学号不能为空")
    @Size(min = 0, max = 20, message = "学号长度不能超过20个字符")
    private String studentNo;

    /** 姓名 */
    @NotBlank(message = "姓名不能为空")
    @Size(min = 0, max = 30, message = "姓名长度不能超过30个字符")
    private String studentName;

    /** 性别（0男 1女 2未知） */
    private String gender;

    /** 所属专业 */
    @NotBlank(message = "所属专业不能为空")
    @Size(min = 0, max = 50, message = "所属专业长度不能超过50个字符")
    private String majorName;

    /** 所属班级 */
    @NotBlank(message = "所属班级不能为空")
    @Size(min = 0, max = 50, message = "所属班级长度不能超过50个字符")
    private String className;

    /** 入学年份 */
    @NotBlank(message = "入学年份不能为空")
    @Size(min = 0, max = 10, message = "入学年份长度不能超过10个字符")
    private String enrollmentYear;

    /** 生源城市 */
    @Size(min = 0, max = 50, message = "生源城市长度不能超过50个字符")
    private String sourceCity;

    /** 毕业中学 */
    @Size(min = 0, max = 100, message = "毕业中学长度不能超过100个字符")
    private String graduationSchool;

    /** 入学分数 */
    private Double entranceScore;

    /** 联系方式 */
    @Size(min = 0, max = 50, message = "联系方式长度不能超过50个字符")
    private String contactInfo;

    /** 学籍状态（0在读 1休学 2复学 3毕业） */
    private String studentStatus;

    /** 审核状态（0待审核 1已通过 2已驳回） */
    private String status;

    public void setStudentId(Long studentId)
    {
        this.studentId = studentId;
    }

    public Long getStudentId()
    {
        return studentId;
    }
    public void setStudentNo(String studentNo)
    {
        this.studentNo = studentNo;
    }

    public String getStudentNo()
    {
        return studentNo;
    }
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getStudentName()
    {
        return studentName;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
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
    public void setEnrollmentYear(String enrollmentYear)
    {
        this.enrollmentYear = enrollmentYear;
    }

    public String getEnrollmentYear()
    {
        return enrollmentYear;
    }
    public void setSourceCity(String sourceCity)
    {
        this.sourceCity = sourceCity;
    }

    public String getSourceCity()
    {
        return sourceCity;
    }
    public void setGraduationSchool(String graduationSchool)
    {
        this.graduationSchool = graduationSchool;
    }

    public String getGraduationSchool()
    {
        return graduationSchool;
    }
    public void setEntranceScore(Double entranceScore)
    {
        this.entranceScore = entranceScore;
    }

    public Double getEntranceScore()
    {
        return entranceScore;
    }
    public void setContactInfo(String contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }
    public void setStudentStatus(String studentStatus)
    {
        this.studentStatus = studentStatus;
    }

    public String getStudentStatus()
    {
        return studentStatus;
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
            .append("studentId", getStudentId())
            .append("studentNo", getStudentNo())
            .append("studentName", getStudentName())
            .append("gender", getGender())
            .append("majorName", getMajorName())
            .append("className", getClassName())
            .append("enrollmentYear", getEnrollmentYear())
            .append("sourceCity", getSourceCity())
            .append("graduationSchool", getGraduationSchool())
            .append("entranceScore", getEntranceScore())
            .append("contactInfo", getContactInfo())
            .append("studentStatus", getStudentStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
