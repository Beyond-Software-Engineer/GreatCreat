package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * 科研论文对象 sys_research_paper
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public class SysResearchPaper extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 论文ID */
    private Long paperId;

    /** 论文标题 */
    @NotBlank(message = "论文标题不能为空")
    @Size(min = 0, max = 200, message = "论文标题长度不能超过200个字符")
    private String paperTitle;

    /** 作者 */
    @NotBlank(message = "作者不能为空")
    @Size(min = 0, max = 100, message = "作者长度不能超过100个字符")
    private String author;

    /** 发表期刊/会议 */
    @Size(min = 0, max = 100, message = "发表期刊/会议长度不能超过100个字符")
    private String publishJournal;

    /** 收录类型（EI/SCI/其他） */
    @Size(min = 0, max = 20, message = "收录类型长度不能超过20个字符")
    private String indexType;

    /** 论文分区（A/B/C/无） */
    @Size(min = 0, max = 10, message = "论文分区长度不能超过10个字符")
    private String paperZone;

    /** 发表时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 附件URL（录用通知等） */
    @Size(min = 0, max = 200, message = "附件URL长度不能超过200个字符")
    private String attachmentUrl;

    /** 审核状态（0待审核 1已通过 2已驳回） */
    private String status;

    public void setPaperId(Long paperId)
    {
        this.paperId = paperId;
    }

    public Long getPaperId()
    {
        return paperId;
    }
    public void setPaperTitle(String paperTitle)
    {
        this.paperTitle = paperTitle;
    }

    public String getPaperTitle()
    {
        return paperTitle;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }
    public void setPublishJournal(String publishJournal)
    {
        this.publishJournal = publishJournal;
    }

    public String getPublishJournal()
    {
        return publishJournal;
    }
    public void setIndexType(String indexType)
    {
        this.indexType = indexType;
    }

    public String getIndexType()
    {
        return indexType;
    }
    public void setPaperZone(String paperZone)
    {
        this.paperZone = paperZone;
    }

    public String getPaperZone()
    {
        return paperZone;
    }
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
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
            .append("paperId", getPaperId())
            .append("paperTitle", getPaperTitle())
            .append("author", getAuthor())
            .append("publishJournal", getPublishJournal())
            .append("indexType", getIndexType())
            .append("paperZone", getPaperZone())
            .append("publishTime", getPublishTime())
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
