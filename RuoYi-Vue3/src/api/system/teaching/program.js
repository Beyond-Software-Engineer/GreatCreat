import request from '@/utils/request'

// 查询培养方案列表
export function listProgram(query) {
  return request({
    url: '/system/teaching/program/list',
    method: 'get',
    params: query
  })
}

// 查询培养方案详细
export function getProgram(programId) {
  return request({
    url: '/system/teaching/program/' + programId,
    method: 'get'
  })
}

// 新增培养方案
export function addProgram(data) {
  return request({
    url: '/system/teaching/program',
    method: 'post',
    data: data
  })
}

// 修改培养方案
export function updateProgram(data) {
  return request({
    url: '/system/teaching/program',
    method: 'put',
    data: data
  })
}

// 删除培养方案
export function delProgram(programId) {
  return request({
    url: '/system/teaching/program/' + programId,
    method: 'delete'
  })
}

// 导出培养方案
export function exportProgram(query) {
  return request({
    url: '/system/teaching/program/export',
    method: 'get',
    params: query
  })
}
