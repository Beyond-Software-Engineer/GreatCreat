import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询学业数据列表
export function listAcademic(query) {
  return request({
    url: '/system/academic/list',
    method: 'get',
    params: query
  })
}

// 查询学业数据详细
export function getAcademic(academicId) {
  return request({
    url: '/system/academic/' + parseStrEmpty(academicId),
    method: 'get'
  })
}

// 新增学业数据
export function addAcademic(data) {
  return request({
    url: '/system/academic',
    method: 'post',
    data: data
  })
}

// 修改学业数据
export function updateAcademic(data) {
  return request({
    url: '/system/academic',
    method: 'put',
    data: data
  })
}

// 删除学业数据
export function delAcademic(academicId) {
  return request({
    url: '/system/academic/' + academicId,
    method: 'delete'
  })
}

// 导入学业数据
export function importAcademic(data) {
  return request({
    url: '/system/academic/importData',
    method: 'post',
    data: data
  })
}

// 导出学业数据
export function exportAcademic(query) {
  return request({
    url: '/system/academic/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 下载导入模板
export function importTemplate() {
  return request({
    url: '/system/academic/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}

// 核算学生总学分
export function calculateCredits(studentId) {
  return request({
    url: '/system/academic/calculate/' + studentId,
    method: 'get'
  })
}

// 更新学生学业状态
export function updateAcademicStatus(studentId) {
  return request({
    url: '/system/academic/updateStatus/' + studentId,
    method: 'put'
  })
}
