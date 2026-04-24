package com.ruoyi.system.service.student.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysStudentInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.system.mapper.student.SysStudentInfoMapper;
import com.ruoyi.system.service.student.ISysStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 学生信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-24
 */
@Service
public class SysStudentInfoServiceImpl implements ISysStudentInfoService 
{
    @Autowired
    private SysStudentInfoMapper sysStudentInfoMapper;

    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    @Override
    public SysStudentInfo selectSysStudentInfoByStudentId(Long studentId)
    {
        return sysStudentInfoMapper.selectSysStudentInfoByStudentId(studentId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param sysStudentInfo 学生信息
     * @return 学生信息
     */
    @Override
    public TableDataInfo selectSysStudentInfoList(SysStudentInfo sysStudentInfo)
    {
        startPage();
        List<SysStudentInfo> list = sysStudentInfoMapper.selectSysStudentInfoList(sysStudentInfo);
        return getDataTable(list);
    }

    /**
     * 新增学生信息
     * 
     * @param sysStudentInfo 学生信息
     * @return 结果
     */
    @Override
    public int insertSysStudentInfo(SysStudentInfo sysStudentInfo)
    {
        return sysStudentInfoMapper.insertSysStudentInfo(sysStudentInfo);
    }

    /**
     * 修改学生信息
     * 
     * @param sysStudentInfo 学生信息
     * @return 结果
     */
    @Override
    public int updateSysStudentInfo(SysStudentInfo sysStudentInfo)
    {
        return sysStudentInfoMapper.updateSysStudentInfo(sysStudentInfo);
    }

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键集合
     * @return 结果
     */
    @Override
    public int deleteSysStudentInfoByStudentIds(Long[] studentIds)
    {
        return sysStudentInfoMapper.deleteSysStudentInfoByStudentIds(studentIds);
    }

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentInfoByStudentId(Long studentId)
    {
        return sysStudentInfoMapper.deleteSysStudentInfoByStudentId(studentId);
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
