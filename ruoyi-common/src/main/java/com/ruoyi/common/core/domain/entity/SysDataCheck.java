package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * 数据核对对象 sys_data_check
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysDataCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 核对ID */
    private Long checkId;

    /** 核对类型 */
    @NotBlank(message = "核对类型不能为空")
    @Size(min = 0, max = 50, message = "核对类型长度不能超过50个字符")
    private String checkType;

    /** 核对数据ID */
    @NotBlank(message = "核对数据ID不能为空")
    @Size(min = 0, max = 50, message = "核对数据ID长度不能超过50个字符")
    private String checkDataId;

    /** 核对结果（0无误 1错误） */
    private String checkResult;

    /** 核对原因 */
    private String checkReason;

    /** 核对人 */
    @NotBlank(message = "核对人不能为空")
    @Size(min = 0, max = 50, message = "核对人长度不能超过50个字符")
    private String checker;

    /** 核对时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    public void setCheckId(Long checkId)
    {
        this.checkId = checkId;
    }

    public Long getCheckId()
    {
        return checkId;
    }
    public void setCheckType(String checkType)
    {
        this.checkType = checkType;
    }

    public String getCheckType()
    {
        return checkType;
    }
    public void setCheckDataId(String checkDataId)
    {
        this.checkDataId = checkDataId;
    }

    public String getCheckDataId()
    {
        return checkDataId;
    }
    public void setCheckResult(String checkResult)
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult()
    {
        return checkResult;
    }
    public void setCheckReason(String checkReason)
    {
        this.checkReason = checkReason;
    }

    public String getCheckReason()
    {
        return checkReason;
    }
    public void setChecker(String checker)
    {
        this.checker = checker;
    }

    public String getChecker()
    {
        return checker;
    }
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime()
    {
        return checkTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkId", getCheckId())
            .append("checkType", getCheckType())
            .append("checkDataId", getCheckDataId())
            .append("checkResult", getCheckResult())
            .append("checkReason", getCheckReason())
            .append("checker", getChecker())
            .append("checkTime", getCheckTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
