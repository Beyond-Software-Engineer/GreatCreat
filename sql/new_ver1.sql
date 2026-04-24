-- ----------------------------
-- 学业数据管理相关SQL语句
-- ----------------------------

-- 1、学业数据表
-- ----------------------------
drop table if exists sys_student_academic;
create table sys_student_academic (
  academic_id        bigint(20)      not null auto_increment    comment '学业数据ID',
  student_id         varchar(20)     not null                   comment '学号',
  student_name       varchar(30)     not null                   comment '姓名',
  semester           varchar(20)     not null                   comment '学期',
  course_name        varchar(100)    not null                   comment '修读课程',
  course_score       decimal(5,2)    default null               comment '课程成绩',
  obtained_credits   decimal(5,2)    not null                   comment '获得学分',
  total_credits      decimal(5,2)    default 0.00               comment '总学分',
  major_rank         int(4)          default null               comment '专业排名',
  major_name         varchar(50)     not null                   comment '专业',
  class_name         varchar(50)     not null                   comment '班级',
  academic_status    char(1)         default '0'                comment '学业状态（0正常 1预警）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (academic_id)
) engine=innodb auto_increment=100 comment = '学生学业数据表';

-- ----------------------------
-- 2、菜单权限表 - 学业数据管理菜单
-- ----------------------------
-- 插入学业数据管理菜单（如果不存在）
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('118', '学业数据管理', 1, 9, 'academic', 'system/academic/index', 1, 0, 'C', '0', '0', 'system:academic:list', 'education', 'admin', sysdate(), '学业数据管理菜单');

-- 设置menu_id变量
set @menu_id = 118;

-- ----------------------------
-- 3、菜单权限表 - 学业数据管理按钮
-- ----------------------------
-- 插入学业数据管理按钮（如果不存在）
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1061', '学业数据查询', @menu_id, 1, 1, 0, 'F', '0', '0', 'system:academic:query', '#', 'admin', sysdate()),
      ('1062', '学业数据新增', @menu_id, 2, 1, 0, 'F', '0', '0', 'system:academic:add', '#', 'admin', sysdate()),
      ('1063', '学业数据修改', @menu_id, 3, 1, 0, 'F', '0', '0', 'system:academic:edit', '#', 'admin', sysdate()),
      ('1064', '学业数据删除', @menu_id, 4, 1, 0, 'F', '0', '0', 'system:academic:remove', '#', 'admin', sysdate()),
      ('1065', '学业数据导出', @menu_id, 5, 1, 0, 'F', '0', '0', 'system:academic:export', '#', 'admin', sysdate()),
      ('1066', '学业数据导入', @menu_id, 6, 1, 0, 'F', '0', '0', 'system:academic:import', '#', 'admin', sysdate()),
      ('1067', '学分核算', @menu_id, 7, 1, 0, 'F', '0', '0', 'system:academic:calculate', '#', 'admin', sysdate()),
      ('1068', '学业状态更新', @menu_id, 8, 1, 0, 'F', '0', '0', 'system:academic:updateStatus', '#', 'admin', sysdate());

-- ----------------------------
-- 4、角色和菜单关联表 - 关联学业数据管理权限
-- ----------------------------
-- 关联学业数据管理菜单权限（如果不存在）
insert ignore into sys_role_menu (role_id, menu_id) 
select 2, @menu_id 
from dual 
where not exists (select 1 from sys_role_menu where role_id = 2 and menu_id = @menu_id);

-- 关联学业数据管理按钮权限（如果不存在）
insert ignore into sys_role_menu (role_id, menu_id) 
select 2, menu_id 
from sys_menu 
where parent_id = @menu_id 
and not exists (select 1 from sys_role_menu where role_id = 2 and sys_role_menu.menu_id = sys_menu.menu_id);

