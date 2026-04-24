import request from '@/utils/request'

// 查询生源数据列表
export function listSource(query) {
  return request({
    url: '/system/student/source/list',
    method: 'get',
    params: query
  })
}

// 查询生源数据详细
export function getSource(sourceId) {
  return request({
    url: '/system/student/source/' + sourceId,
    method: 'get'
  })
}

// 新增生源数据
export function addSource(data) {
  return request({
    url: '/system/student/source',
    method: 'post',
    data: data
  })
}

// 修改生源数据
export function updateSource(data) {
  return request({
    url: '/system/student/source',
    method: 'put',
    data: data
  })
}

// 删除生源数据
export function delSource(sourceId) {
  return request({
    url: '/system/student/source/' + sourceId,
    method: 'delete'
  })
}

// 导出生源数据
export function exportSource(query) {
  return request({
    url: '/system/student/source/export',
    method: 'get',
    params: query
  })
}
