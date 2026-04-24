package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 生源数据对象 sys_student_source
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysStudentSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 生源ID */
    private Long sourceId;

    /** 入学年份 */
    @NotBlank(message = "入学年份不能为空")
    @Size(min = 0, max = 10, message = "入学年份长度不能超过10个字符")
    private String enrollmentYear;

    /** 生源省份 */
    @NotBlank(message = "生源省份不能为空")
    @Size(min = 0, max = 50, message = "生源省份长度不能超过50个字符")
    private String sourceProvince;

    /** 生源城市 */
    @NotBlank(message = "生源城市不能为空")
    @Size(min = 0, max = 50, message = "生源城市长度不能超过50个字符")
    private String sourceCity;

    /** 毕业中学 */
    @Size(min = 0, max = 100, message = "毕业中学长度不能超过100个字符")
    private String graduationSchool;

    /** 学生人数 */
    private Integer studentCount;

    /** 平均入学分数 */
    private Double avgEntranceScore;

    public void setSourceId(Long sourceId)
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId()
    {
        return sourceId;
    }
    public void setEnrollmentYear(String enrollmentYear)
    {
        this.enrollmentYear = enrollmentYear;
    }

    public String getEnrollmentYear()
    {
        return enrollmentYear;
    }
    public void setSourceProvince(String sourceProvince)
    {
        this.sourceProvince = sourceProvince;
    }

    public String getSourceProvince()
    {
        return sourceProvince;
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
    public void setStudentCount(Integer studentCount)
    {
        this.studentCount = studentCount;
    }

    public Integer getStudentCount()
    {
        return studentCount;
    }
    public void setAvgEntranceScore(Double avgEntranceScore)
    {
        this.avgEntranceScore = avgEntranceScore;
    }

    public Double getAvgEntranceScore()
    {
        return avgEntranceScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sourceId", getSourceId())
            .append("enrollmentYear", getEnrollmentYear())
            .append("sourceProvince", getSourceProvince())
            .append("sourceCity", getSourceCity())
            .append("graduationSchool", getGraduationSchool())
            .append("studentCount", getStudentCount())
            .append("avgEntranceScore", getAvgEntranceScore())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
