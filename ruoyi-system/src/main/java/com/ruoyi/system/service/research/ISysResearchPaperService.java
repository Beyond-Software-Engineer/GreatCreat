package com.ruoyi.system.service.research;

import com.ruoyi.common.core.domain.entity.SysResearchPaper;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科研论文Service接口
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
public interface ISysResearchPaperService 
{
    /**
     * 查询科研论文
     * 
     * @param paperId 科研论文主键
     * @return 科研论文
     */
    public SysResearchPaper selectSysResearchPaperByPaperId(Long paperId);

    /**
     * 查询科研论文列表
     * 
     * @param sysResearchPaper 科研论文
     * @return 科研论文集合
     */
    public TableDataInfo selectSysResearchPaperList(SysResearchPaper sysResearchPaper);

    /**
     * 新增科研论文
     * 
     * @param sysResearchPaper 科研论文
     * @return 结果
     */
    public int insertSysResearchPaper(SysResearchPaper sysResearchPaper);

    /**
     * 修改科研论文
     * 
     * @param sysResearchPaper 科研论文
     * @return 结果
     */
    public int updateSysResearchPaper(SysResearchPaper sysResearchPaper);

    /**
     * 批量删除科研论文
     * 
     * @param paperIds 需要删除的科研论文主键集合
     * @return 结果
     */
    public int deleteSysResearchPaperByPaperIds(Long[] paperIds);

    /**
     * 删除科研论文信息
     * 
     * @param paperId 科研论文主键
     * @return 结果
     */
    public int deleteSysResearchPaperByPaperId(Long paperId);
}
