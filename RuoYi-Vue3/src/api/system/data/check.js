import request from '@/utils/request'

// 查询核对记录列表
export function listCheck(query) {
  return request({
    url: '/system/data/check/list',
    method: 'get',
    params: query
  })
}

// 查询核对记录详细
export function getCheck(checkId) {
  return request({
    url: '/system/data/check/' + checkId,
    method: 'get'
  })
}

// 新增核对记录
export function addCheck(data) {
  return request({
    url: '/system/data/check',
    method: 'post',
    data: data
  })
}

// 修改核对记录
export function updateCheck(data) {
  return request({
    url: '/system/data/check',
    method: 'put',
    data: data
  })
}

// 删除核对记录
export function delCheck(checkId) {
  return request({
    url: '/system/data/check/' + checkId,
    method: 'delete'
  })
}

// 导出核对记录
export function exportCheck(query) {
  return request({
    url: '/system/data/check/export',
    method: 'get',
    params: query
  })
}

// 自动核对
export function autoCheck() {
  return request({
    url: '/system/data/check/auto',
    method: 'post'
  })
}
