package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * 横向项目经费对象 sys_research_funding
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysResearchFunding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 经费ID */
    private Long fundingId;

    /** 经费项目名称 */
    @NotBlank(message = "经费项目名称不能为空")
    @Size(min = 0, max = 200, message = "经费项目名称长度不能超过200个字符")
    private String fundingName;

    /** 负责人 */
    @NotBlank(message = "负责人不能为空")
    @Size(min = 0, max = 50, message = "负责人长度不能超过50个字符")
    private String fundingLeader;

    /** 到账金额 */
    private Double arrivalAmount;

    /** 到账时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arrivalTime;

    /** 付款单位 */
    @Size(min = 0, max = 100, message = "付款单位长度不能超过100个字符")
    private String payerUnit;

    /** 附件URL（到账凭证等） */
    @Size(min = 0, max = 200, message = "附件URL长度不能超过200个字符")
    private String attachmentUrl;

    /** 审核状态（0待审核 1已通过 2已驳回） */
    private String status;

    public void setFundingId(Long fundingId)
    {
        this.fundingId = fundingId;
    }

    public Long getFundingId()
    {
        return fundingId;
    }
    public void setFundingName(String fundingName)
    {
        this.fundingName = fundingName;
    }

    public String getFundingName()
    {
        return fundingName;
    }
    public void setFundingLeader(String fundingLeader)
    {
        this.fundingLeader = fundingLeader;
    }

    public String getFundingLeader()
    {
        return fundingLeader;
    }
    public void setArrivalAmount(Double arrivalAmount)
    {
        this.arrivalAmount = arrivalAmount;
    }

    public Double getArrivalAmount()
    {
        return arrivalAmount;
    }
    public void setArrivalTime(Date arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public Date getArrivalTime()
    {
        return arrivalTime;
    }
    public void setPayerUnit(String payerUnit)
    {
        this.payerUnit = payerUnit;
    }

    public String getPayerUnit()
    {
        return payerUnit;
    }
    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    public String getAttachmentUrl()
    {
        return attachmentUrl;
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
            .append("fundingId", getFundingId())
            .append("fundingName", getFundingName())
            .append("fundingLeader", getFundingLeader())
            .append("arrivalAmount", getArrivalAmount())
            .append("arrivalTime", getArrivalTime())
            .append("payerUnit", getPayerUnit())
            .append("attachmentUrl", getAttachmentUrl())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
