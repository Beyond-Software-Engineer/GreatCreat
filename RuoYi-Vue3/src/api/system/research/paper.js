import request from '@/utils/request'

// 查询论文列表
export function listPaper(query) {
  return request({
    url: '/system/research/paper/list',
    method: 'get',
    params: query
  })
}

// 查询论文详细
export function getPaper(paperId) {
  return request({
    url: '/system/research/paper/' + paperId,
    method: 'get'
  })
}

// 新增论文
export function addPaper(data) {
  return request({
    url: '/system/research/paper',
    method: 'post',
    data: data
  })
}

// 修改论文
export function updatePaper(data) {
  return request({
    url: '/system/research/paper',
    method: 'put',
    data: data
  })
}

// 删除论文
export function delPaper(paperId) {
  return request({
    url: '/system/research/paper/' + paperId,
    method: 'delete'
  })
}

// 导出论文
export function exportPaper(query) {
  return request({
    url: '/system/research/paper/export',
    method: 'get',
    params: query
  })
}
