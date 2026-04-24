package com.ruoyi.system.service.research.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysResearchPaper;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.research.SysResearchPaperMapper;
import com.ruoyi.system.service.research.ISysResearchPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 科研论文Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysResearchPaperServiceImpl implements ISysResearchPaperService 
{
    @Autowired
    private SysResearchPaperMapper sysResearchPaperMapper;

    /**
     * 查询科研论文
     * 
     * @param paperId 科研论文主键
     * @return 科研论文
     */
    @Override
    public SysResearchPaper selectSysResearchPaperByPaperId(Long paperId)
    {
        return sysResearchPaperMapper.selectSysResearchPaperByPaperId(paperId);
    }

    /**
     * 查询科研论文列表
     * 
     * @param sysResearchPaper 科研论文
     * @return 科研论文
     */
    @Override
    public TableDataInfo selectSysResearchPaperList(SysResearchPaper sysResearchPaper)
    {
        startPage();
        List<SysResearchPaper> list = sysResearchPaperMapper.selectSysResearchPaperList(sysResearchPaper);
        return getDataTable(list);
    }

    /**
     * 新增科研论文
     * 
     * @param sysResearchPaper 科研论文
     * @return 结果
     */
    @Override
    public int insertSysResearchPaper(SysResearchPaper sysResearchPaper)
    {
        return sysResearchPaperMapper.insertSysResearchPaper(sysResearchPaper);
    }

    /**
     * 修改科研论文
     * 
     * @param sysResearchPaper 科研论文
     * @return 结果
     */
    @Override
    public int updateSysResearchPaper(SysResearchPaper sysResearchPaper)
    {
        return sysResearchPaperMapper.updateSysResearchPaper(sysResearchPaper);
    }

    /**
     * 批量删除科研论文
     * 
     * @param paperIds 需要删除的科研论文主键集合
     * @return 结果
     */
    @Override
    public int deleteSysResearchPaperByPaperIds(Long[] paperIds)
    {
        return sysResearchPaperMapper.deleteSysResearchPaperByPaperIds(paperIds);
    }

    /**
     * 删除科研论文信息
     * 
     * @param paperId 科研论文主键
     * @return 结果
     */
    @Override
    public int deleteSysResearchPaperByPaperId(Long paperId)
    {
        return sysResearchPaperMapper.deleteSysResearchPaperByPaperId(paperId);
    }

    /**
     * 分页查询
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 封装分页数据
     */
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new com.github.pagehelper.PageInfo(list).getTotal());
        return rspData;
    }
}
