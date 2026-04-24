import request from '@/utils/request'

// 查询学业数据列表
export function listAcademic(query) {
  return request({
    url: '/system/student/academic/list',
    method: 'get',
    params: query
  })
}

// 查询学业数据详细
export function getAcademic(academicId) {
  return request({
    url: '/system/student/academic/' + academicId,
    method: 'get'
  })
}

// 新增学业数据
export function addAcademic(data) {
  return request({
    url: '/system/student/academic',
    method: 'post',
    data: data
  })
}

// 修改学业数据
export function updateAcademic(data) {
  return request({
    url: '/system/student/academic',
    method: 'put',
    data: data
  })
}

// 删除学业数据
export function delAcademic(academicId) {
  return request({
    url: '/system/student/academic/' + academicId,
    method: 'delete'
  })
}

// 导出学业数据
export function exportAcademic(query) {
  return request({
    url: '/system/student/academic/export',
    method: 'get',
    params: query
  })
}

// 学分核算
export function calculateCredits() {
  return request({
    url: '/system/student/academic/calculate',
    method: 'post'
  })
}

// 学业预警
export function updateWarning() {
  return request({
    url: '/system/student/academic/warning',
    method: 'post'
  })
}
