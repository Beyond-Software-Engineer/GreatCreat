package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * 科研项目对象 sys_research_project
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysResearchProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    private Long projectId;

    /** 项目名称 */
    @NotBlank(message = "项目名称不能为空")
    @Size(min = 0, max = 200, message = "项目名称长度不能超过200个字符")
    private String projectName;

    /** 项目负责人 */
    @NotBlank(message = "项目负责人不能为空")
    @Size(min = 0, max = 50, message = "项目负责人长度不能超过50个字符")
    private String projectLeader;

    /** 项目类型（纵向/横向） */
    @Size(min = 0, max = 20, message = "项目类型长度不能超过20个字符")
    private String projectType;

    /** 获批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approveTime;

    /** 经费金额 */
    private Double fundingAmount;

    /** 到账状态（0未到账 1部分到账 2全部到账） */
    private String arrivalStatus;

    /** 附件URL（立项通知书等） */
    @Size(min = 0, max = 200, message = "附件URL长度不能超过200个字符")
    private String attachmentUrl;

    /** 审核状态（0待审核 1已通过 2已驳回） */
    private String status;

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setProjectLeader(String projectLeader)
    {
        this.projectLeader = projectLeader;
    }

    public String getProjectLeader()
    {
        return projectLeader;
    }
    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }

    public String getProjectType()
    {
        return projectType;
    }
    public void setApproveTime(Date approveTime)
    {
        this.approveTime = approveTime;
    }

    public Date getApproveTime()
    {
        return approveTime;
    }
    public void setFundingAmount(Double fundingAmount)
    {
        this.fundingAmount = fundingAmount;
    }

    public Double getFundingAmount()
    {
        return fundingAmount;
    }
    public void setArrivalStatus(String arrivalStatus)
    {
        this.arrivalStatus = arrivalStatus;
    }

    public String getArrivalStatus()
    {
        return arrivalStatus;
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
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("projectLeader", getProjectLeader())
            .append("projectType", getProjectType())
            .append("approveTime", getApproveTime())
            .append("fundingAmount", getFundingAmount())
            .append("arrivalStatus", getArrivalStatus())
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
