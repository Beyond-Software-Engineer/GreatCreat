import request from '@/utils/request'

// 查询经费列表
export function listFunding(query) {
  return request({
    url: '/system/research/funding/list',
    method: 'get',
    params: query
  })
}

// 查询经费详细
export function getFunding(fundingId) {
  return request({
    url: '/system/research/funding/' + fundingId,
    method: 'get'
  })
}

// 新增经费
export function addFunding(data) {
  return request({
    url: '/system/research/funding',
    method: 'post',
    data: data
  })
}

// 修改经费
export function updateFunding(data) {
  return request({
    url: '/system/research/funding',
    method: 'put',
    data: data
  })
}

// 删除经费
export function delFunding(fundingId) {
  return request({
    url: '/system/research/funding/' + fundingId,
    method: 'delete'
  })
}

// 导出经费
export function exportFunding(query) {
  return request({
    url: '/system/research/funding/export',
    method: 'get',
    params: query
  })
}
