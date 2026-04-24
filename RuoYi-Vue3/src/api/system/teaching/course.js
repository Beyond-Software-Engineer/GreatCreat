import request from '@/utils/request'

// 查询开课列表
export function listCourse(query) {
  return request({
    url: '/system/teaching/course/list',
    method: 'get',
    params: query
  })
}

// 查询开课详细
export function getCourse(courseId) {
  return request({
    url: '/system/teaching/course/' + courseId,
    method: 'get'
  })
}

// 新增开课
export function addCourse(data) {
  return request({
    url: '/system/teaching/course',
    method: 'post',
    data: data
  })
}

// 修改开课
export function updateCourse(data) {
  return request({
    url: '/system/teaching/course',
    method: 'put',
    data: data
  })
}

// 删除开课
export function delCourse(courseId) {
  return request({
    url: '/system/teaching/course/' + courseId,
    method: 'delete'
  })
}

// 导出开课
export function exportCourse(query) {
  return request({
    url: '/system/teaching/course/export',
    method: 'get',
    params: query
  })
}
