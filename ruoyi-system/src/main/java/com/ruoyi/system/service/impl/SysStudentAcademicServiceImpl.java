package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysStudentAcademic;
import com.ruoyi.system.mapper.SysStudentAcademicMapper;
import com.ruoyi.system.service.ISysStudentAcademicService;

/**
 * 学生学业数据服务实现
 * 
 * @author ruoyi
 */
@Service
public class SysStudentAcademicServiceImpl implements ISysStudentAcademicService
{
    @Autowired
    private SysStudentAcademicMapper academicMapper;

    @Override
    public List<SysStudentAcademic> selectAcademicList(SysStudentAcademic academic)
    {
        return academicMapper.selectAcademicList(academic);
    }

    @Override
    public SysStudentAcademic selectAcademicById(Long academicId)
    {
        return academicMapper.selectAcademicById(academicId);
    }

    @Override
    public List<SysStudentAcademic> selectAcademicByStudentId(String studentId)
    {
        return academicMapper.selectAcademicByStudentId(studentId);
    }

    @Override
    public int insertAcademic(SysStudentAcademic academic)
    {
        int result = academicMapper.insertAcademic(academic);
        // 插入后更新总学分和学业状态
        if (result > 0)
        {
            calculateTotalCredits(academic.getStudentId());
            updateAcademicStatus(academic.getStudentId());
        }
        return result;
    }

    @Override
    public int updateAcademic(SysStudentAcademic academic)
    {
        int result = academicMapper.updateAcademic(academic);
        // 更新后更新总学分和学业状态
        if (result > 0)
        {
            calculateTotalCredits(academic.getStudentId());
            updateAcademicStatus(academic.getStudentId());
        }
        return result;
    }

    @Override
    public int deleteAcademicById(Long academicId)
    {
        SysStudentAcademic academic = academicMapper.selectAcademicById(academicId);
        String studentId = academic.getStudentId();
        int result = academicMapper.deleteAcademicById(academicId);
        // 删除后更新总学分和学业状态
        if (result > 0)
        {
            calculateTotalCredits(studentId);
            updateAcademicStatus(studentId);
        }
        return result;
    }

    @Override
    public int deleteAcademicByIds(Long[] academicIds)
    {
        // 先获取所有要删除的学业数据的学号
        for (Long academicId : academicIds)
        {
            SysStudentAcademic academic = academicMapper.selectAcademicById(academicId);
            if (academic != null)
            {
                String studentId = academic.getStudentId();
                // 删除后更新总学分和学业状态
                calculateTotalCredits(studentId);
                updateAcademicStatus(studentId);
            }
        }
        return academicMapper.deleteAcademicByIds(academicIds);
    }

    @Override
    public Double calculateTotalCredits(String studentId)
    {
        List<SysStudentAcademic> academicList = academicMapper.selectAcademicByStudentId(studentId);
        double totalCredits = 0.0;
        for (SysStudentAcademic academic : academicList)
        {
            if (academic.getObtainedCredits() != null)
            {
                totalCredits += academic.getObtainedCredits();
            }
        }
        // 更新每个学业记录的总学分
        for (SysStudentAcademic academic : academicList)
        {
            academic.setTotalCredits(totalCredits);
            academicMapper.updateAcademic(academic);
        }
        return totalCredits;
    }

    @Override
    public int updateAcademicStatus(String studentId)
    {
        List<SysStudentAcademic> academicList = academicMapper.selectAcademicByStudentId(studentId);
        double totalCredits = calculateTotalCredits(studentId);
        // 简单的学业预警逻辑：总学分低于40分标记为预警
        String status = totalCredits < 40 ? "1" : "0";
        int count = 0;
        for (SysStudentAcademic academic : academicList)
        {
            academic.setAcademicStatus(status);
            count += academicMapper.updateAcademic(academic);
        }
        return count;
    }
}
