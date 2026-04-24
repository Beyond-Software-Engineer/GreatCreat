-- ----------------------------
-- 计算机科学技术学院数据中心管理系统 - 数据库SQL脚本
-- 根据需求规格说明书生成
-- ----------------------------

-- ----------------------------
-- 1、科研数据管理相关表
-- ----------------------------

-- 论文表
-- ----------------------------
drop table if exists sys_research_paper;
create table sys_research_paper (
  paper_id           bigint(20)      not null auto_increment    comment '论文ID',
  paper_title        varchar(200)    not null                   comment '论文标题',
  author             varchar(100)    not null                   comment '作者',
  publish_journal    varchar(100)    default null               comment '发表期刊/会议',
  index_type         varchar(20)     default null               comment '收录类型（EI/SCI/其他）',
  paper_zone         varchar(10)     default null               comment '论文分区（A/B/C/无）',
  publish_time       date            not null                   comment '发表时间',
  attachment_url     varchar(200)    default null               comment '附件URL（录用通知等）',
  status             char(1)         default '0'                comment '审核状态（0待审核 1已通过 2已驳回）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (paper_id)
) engine=innodb auto_increment=100 comment = '科研论文表';

-- 科研项目表
-- ----------------------------
drop table if exists sys_research_project;
create table sys_research_project (
  project_id         bigint(20)      not null auto_increment    comment '项目ID',
  project_name       varchar(200)    not null                   comment '项目名称',
  project_leader     varchar(50)     not null                   comment '项目负责人',
  project_type       varchar(20)     default null               comment '项目类型（纵向/横向）',
  approve_time       date            not null                   comment '获批时间',
  funding_amount     decimal(15,2)   not null                   comment '经费金额',
  arrival_status     char(1)         default '0'                comment '到账状态（0未到账 1部分到账 2全部到账）',
  attachment_url     varchar(200)    default null               comment '附件URL（立项通知书等）',
  status             char(1)         default '0'                comment '审核状态（0待审核 1已通过 2已驳回）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (project_id)
) engine=innodb auto_increment=100 comment = '科研项目表';

-- 横向项目经费表
-- ----------------------------
drop table if exists sys_research_funding;
create table sys_research_funding (
  funding_id         bigint(20)      not null auto_increment    comment '经费ID',
  funding_name       varchar(200)    not null                   comment '经费项目名称',
  funding_leader     varchar(50)     not null                   comment '负责人',
  arrival_amount     decimal(15,2)   not null                   comment '到账金额',
  arrival_time       date            not null                   comment '到账时间',
  payer_unit         varchar(100)    default null               comment '付款单位',
  attachment_url     varchar(200)    default null               comment '附件URL（到账凭证等）',
  status             char(1)         default '0'                comment '审核状态（0待审核 1已通过 2已驳回）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (funding_id)
) engine=innodb auto_increment=100 comment = '横向项目经费表';

-- ----------------------------
-- 2、教学数据管理相关表
-- ----------------------------

-- 开课信息表
-- ----------------------------
drop table if exists sys_teaching_course;
create table sys_teaching_course (
  course_id          bigint(20)      not null auto_increment    comment '开课ID',
  semester           varchar(20)     not null                   comment '学期',
  course_name        varchar(100)    not null                   comment '课程名称',
  course_code        varchar(50)     not null                   comment '课程编码',
  teacher_name       varchar(50)     not null                   comment '授课教师',
  major_name         varchar(50)     default null               comment '授课专业',
  class_name         varchar(50)     default null               comment '授课班级',
  course_hours       int(4)          not null                   comment '课时数',
  course_type        varchar(20)     default null               comment '课程类型',
  status             char(1)         default '0'                comment '审核状态（0待审核 1已通过 2已驳回）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (course_id),
  unique key uk_course_code (course_code)
) engine=innodb auto_increment=100 comment = '开课信息表';

-- 培养方案表
-- ----------------------------
drop table if exists sys_teaching_program;
create table sys_teaching_program (
  program_id         bigint(20)      not null auto_increment    comment '方案ID',
  major_name         varchar(50)     not null                   comment '专业名称',
  education_system   varchar(20)     default null               comment '学制',
  training_goal      text            default null               comment '培养目标',
  core_courses       text            not null                   comment '核心课程',
  course_credits     text            not null                   comment '课程学分',
  credit_requirement decimal(5,2)    default null               comment '学分要求',
  graduation_requirement text        default null               comment '毕业条件',
  version            varchar(20)     default '1.0'              comment '版本号',
  status             char(1)         default '0'                comment '状态（0草稿 1已发布）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (program_id)
) engine=innodb auto_increment=100 comment = '培养方案表';

-- ----------------------------
-- 3、学生管理数据相关表
-- ----------------------------

-- 学生信息表
-- ----------------------------
drop table if exists sys_student_info;
create table sys_student_info (
  student_id         bigint(20)      not null auto_increment    comment '学生ID',
  student_no         varchar(20)     not null                   comment '学号',
  student_name       varchar(30)     not null                   comment '姓名',
  gender             char(1)         default '0'                comment '性别（0男 1女 2未知）',
  major_name         varchar(50)     not null                   comment '所属专业',
  class_name         varchar(50)     not null                   comment '所属班级',
  enrollment_year    varchar(10)     not null                   comment '入学年份',
  source_city        varchar(50)     default null               comment '生源城市',
  graduation_school  varchar(100)    default null               comment '毕业中学',
  entrance_score     decimal(5,2)    default null               comment '入学分数',
  contact_info       varchar(50)     default null               comment '联系方式',
  student_status     char(1)         default '0'                comment '学籍状态（0在读 1休学 2复学 3毕业）',
  status             char(1)         default '0'                comment '审核状态（0待审核 1已通过 2已驳回）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (student_id),
  unique key uk_student_no (student_no)
) engine=innodb auto_increment=100 comment = '学生信息表';

-- 学生学业数据表（已存在，保留结构）
-- ----------------------------
drop table if exists sys_student_academic;
create table sys_student_academic (
  academic_id        bigint(20)      not null auto_increment    comment '学业数据ID',
  student_no         varchar(20)     not null                   comment '学号',
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

-- 生源数据表
-- ----------------------------
drop table if exists sys_student_source;
create table sys_student_source (
  source_id          bigint(20)      not null auto_increment    comment '生源ID',
  enrollment_year    varchar(10)     not null                   comment '入学年份',
  source_province    varchar(50)     not null                   comment '生源省份',
  source_city        varchar(50)     not null                   comment '生源城市',
  graduation_school  varchar(100)    default null               comment '毕业中学',
  student_count      int(4)          not null                   comment '学生人数',
  avg_entrance_score decimal(5,2)    default null               comment '平均入学分数',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (source_id)
) engine=innodb auto_increment=100 comment = '生源数据表';

-- ----------------------------
-- 4、数据核对记录表
-- ----------------------------
drop table if exists sys_data_check;
create table sys_data_check (
  check_id           bigint(20)      not null auto_increment    comment '核对ID',
  check_type         varchar(50)     not null                   comment '核对数据类型',
  check_data_id      bigint(20)      not null                   comment '核对数据ID',
  check_result       char(1)         default '0'                comment '核对结果（0无误 1错误）',
  check_reason       varchar(500)    default null               comment '核对原因',
  checker            varchar(64)     not null                   comment '核对人',
  check_time         datetime                                   comment '核对时间',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (check_id)
) engine=innodb auto_increment=100 comment = '数据核对记录表';

-- ----------------------------
-- 5、菜单权限配置 - 科研数据管理
-- ----------------------------
-- 科研数据管理菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('119', '科研数据管理', 1, 10, 'research', '', 1, 0, 'M', '0', '0', '', 'research', 'admin', sysdate(), '科研数据管理目录');

-- 科研数据管理子菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('120', '论文管理', 119, 1, 'paper', 'system/research/paper/index', 1, 0, 'C', '0', '0', 'system:research:paper:list', 'documentation', 'admin', sysdate(), '论文管理菜单'),
      ('121', '项目管理', 119, 2, 'project', 'system/research/project/index', 1, 0, 'C', '0', '0', 'system:research:project:list', 'project', 'admin', sysdate(), '项目管理菜单'),
      ('122', '经费管理', 119, 3, 'funding', 'system/research/funding/index', 1, 0, 'C', '0', '0', 'system:research:funding:list', 'money', 'admin', sysdate(), '经费管理菜单');

-- 论文管理按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1069', '论文查询', 120, 1, 1, 0, 'F', '0', '0', 'system:research:paper:query', '#', 'admin', sysdate()),
      ('1070', '论文新增', 120, 2, 1, 0, 'F', '0', '0', 'system:research:paper:add', '#', 'admin', sysdate()),
      ('1071', '论文修改', 120, 3, 1, 0, 'F', '0', '0', 'system:research:paper:edit', '#', 'admin', sysdate()),
      ('1072', '论文删除', 120, 4, 1, 0, 'F', '0', '0', 'system:research:paper:remove', '#', 'admin', sysdate()),
      ('1073', '论文导出', 120, 5, 1, 0, 'F', '0', '0', 'system:research:paper:export', '#', 'admin', sysdate()),
      ('1074', '论文导入', 120, 6, 1, 0, 'F', '0', '0', 'system:research:paper:import', '#', 'admin', sysdate());

-- 项目管理按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1075', '项目查询', 121, 1, 1, 0, 'F', '0', '0', 'system:research:project:query', '#', 'admin', sysdate()),
      ('1076', '项目新增', 121, 2, 1, 0, 'F', '0', '0', 'system:research:project:add', '#', 'admin', sysdate()),
      ('1077', '项目修改', 121, 3, 1, 0, 'F', '0', '0', 'system:research:project:edit', '#', 'admin', sysdate()),
      ('1078', '项目删除', 121, 4, 1, 0, 'F', '0', '0', 'system:research:project:remove', '#', 'admin', sysdate()),
      ('1079', '项目导出', 121, 5, 1, 0, 'F', '0', '0', 'system:research:project:export', '#', 'admin', sysdate()),
      ('1080', '项目导入', 121, 6, 1, 0, 'F', '0', '0', 'system:research:project:import', '#', 'admin', sysdate());

-- 经费管理按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1081', '经费查询', 122, 1, 1, 0, 'F', '0', '0', 'system:research:funding:query', '#', 'admin', sysdate()),
      ('1082', '经费新增', 122, 2, 1, 0, 'F', '0', '0', 'system:research:funding:add', '#', 'admin', sysdate()),
      ('1083', '经费修改', 122, 3, 1, 0, 'F', '0', '0', 'system:research:funding:edit', '#', 'admin', sysdate()),
      ('1084', '经费删除', 122, 4, 1, 0, 'F', '0', '0', 'system:research:funding:remove', '#', 'admin', sysdate()),
      ('1085', '经费导出', 122, 5, 1, 0, 'F', '0', '0', 'system:research:funding:export', '#', 'admin', sysdate()),
      ('1086', '经费导入', 122, 6, 1, 0, 'F', '0', '0', 'system:research:funding:import', '#', 'admin', sysdate());

-- ----------------------------
-- 6、菜单权限配置 - 教学数据管理
-- ----------------------------
-- 教学数据管理菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('123', '教学数据管理', 1, 11, 'teaching', '', 1, 0, 'M', '0', '0', '', 'education', 'admin', sysdate(), '教学数据管理目录');

-- 教学数据管理子菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('124', '开课管理', 123, 1, 'course', 'system/teaching/course/index', 1, 0, 'C', '0', '0', 'system:teaching:course:list', 'book', 'admin', sysdate(), '开课管理菜单'),
      ('125', '培养方案', 123, 2, 'program', 'system/teaching/program/index', 1, 0, 'C', '0', '0', 'system:teaching:program:list', 'plan', 'admin', sysdate(), '培养方案菜单');

-- 开课管理按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1087', '开课查询', 124, 1, 1, 0, 'F', '0', '0', 'system:teaching:course:query', '#', 'admin', sysdate()),
      ('1088', '开课新增', 124, 2, 1, 0, 'F', '0', '0', 'system:teaching:course:add', '#', 'admin', sysdate()),
      ('1089', '开课修改', 124, 3, 1, 0, 'F', '0', '0', 'system:teaching:course:edit', '#', 'admin', sysdate()),
      ('1090', '开课删除', 124, 4, 1, 0, 'F', '0', '0', 'system:teaching:course:remove', '#', 'admin', sysdate()),
      ('1091', '开课导出', 124, 5, 1, 0, 'F', '0', '0', 'system:teaching:course:export', '#', 'admin', sysdate()),
      ('1092', '开课导入', 124, 6, 1, 0, 'F', '0', '0', 'system:teaching:course:import', '#', 'admin', sysdate());

-- 培养方案按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1093', '方案查询', 125, 1, 1, 0, 'F', '0', '0', 'system:teaching:program:query', '#', 'admin', sysdate()),
      ('1094', '方案新增', 125, 2, 1, 0, 'F', '0', '0', 'system:teaching:program:add', '#', 'admin', sysdate()),
      ('1095', '方案修改', 125, 3, 1, 0, 'F', '0', '0', 'system:teaching:program:edit', '#', 'admin', sysdate()),
      ('1096', '方案删除', 125, 4, 1, 0, 'F', '0', '0', 'system:teaching:program:remove', '#', 'admin', sysdate()),
      ('1097', '方案导出', 125, 5, 1, 0, 'F', '0', '0', 'system:teaching:program:export', '#', 'admin', sysdate()),
      ('1098', '方案导入', 125, 6, 1, 0, 'F', '0', '0', 'system:teaching:program:import', '#', 'admin', sysdate());

-- ----------------------------
-- 7、菜单权限配置 - 学生管理数据
-- ----------------------------
-- 学生管理数据菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('126', '学生管理数据', 1, 12, 'student', '', 1, 0, 'M', '0', '0', '', 'user', 'admin', sysdate(), '学生管理数据目录');

-- 学生管理数据子菜单
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) 
values('127', '学生信息', 126, 1, 'info', 'system/student/info/index', 1, 0, 'C', '0', '0', 'system:student:info:list', 'peoples', 'admin', sysdate(), '学生信息菜单'),
      ('128', '学业数据', 126, 2, 'academic', 'system/student/academic/index', 1, 0, 'C', '0', '0', 'system:student:academic:list', 'education', 'admin', sysdate(), '学业数据菜单'),
      ('129', '生源数据', 126, 3, 'source', 'system/student/source/index', 1, 0, 'C', '0', '0', 'system:student:source:list', 'chart', 'admin', sysdate(), '生源数据菜单');

-- 学生信息按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1099', '学生查询', 127, 1, 1, 0, 'F', '0', '0', 'system:student:info:query', '#', 'admin', sysdate()),
      ('1100', '学生新增', 127, 2, 1, 0, 'F', '0', '0', 'system:student:info:add', '#', 'admin', sysdate()),
      ('1101', '学生修改', 127, 3, 1, 0, 'F', '0', '0', 'system:student:info:edit', '#', 'admin', sysdate()),
      ('1102', '学生删除', 127, 4, 1, 0, 'F', '0', '0', 'system:student:info:remove', '#', 'admin', sysdate()),
      ('1103', '学生导出', 127, 5, 1, 0, 'F', '0', '0', 'system:student:info:export', '#', 'admin', sysdate()),
      ('1104', '学生导入', 127, 6, 1, 0, 'F', '0', '0', 'system:student:info:import', '#', 'admin', sysdate());

-- 学业数据按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1105', '学业查询', 128, 1, 1, 0, 'F', '0', '0', 'system:student:academic:query', '#', 'admin', sysdate()),
      ('1106', '学业新增', 128, 2, 1, 0, 'F', '0', '0', 'system:student:academic:add', '#', 'admin', sysdate()),
      ('1107', '学业修改', 128, 3, 1, 0, 'F', '0', '0', 'system:student:academic:edit', '#', 'admin', sysdate()),
      ('1108', '学业删除', 128, 4, 1, 0, 'F', '0', '0', 'system:student:academic:remove', '#', 'admin', sysdate()),
      ('1109', '学业导出', 128, 5, 1, 0, 'F', '0', '0', 'system:student:academic:export', '#', 'admin', sysdate()),
      ('1110', '学业导入', 128, 6, 1, 0, 'F', '0', '0', 'system:student:academic:import', '#', 'admin', sysdate()),
      ('1111', '学分核算', 128, 7, 1, 0, 'F', '0', '0', 'system:student:academic:calculate', '#', 'admin', sysdate()),
      ('1112', '学业预警', 128, 8, 1, 0, 'F', '0', '0', 'system:student:academic:warning', '#', 'admin', sysdate());

-- 生源数据按钮权限
insert ignore into sys_menu (menu_id, menu_name, parent_id, order_num, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) 
values('1113', '生源查询', 129, 1, 1, 0, 'F', '0', '0', 'system:student:source:query', '#', 'admin', sysdate()),
      ('1114', '生源新增', 129, 2, 1, 0, 'F', '0', '0', 'system:student:source:add', '#', 'admin', sysdate()),
      ('1115', '生源修改', 129, 3, 1, 0, 'F', '0', '0', 'system:student:source:edit', '#', 'admin', sysdate()),
      ('1116', '生源删除', 129, 4, 1, 0, 'F', '0', '0', 'system:student:source:remove', '#', 'admin', sysdate()),
      ('1117', '生源导出', 129, 5, 1, 0, 'F', '0', '0', 'system:student:source:export', '#', 'admin', sysdate()),
      ('1118', '生源导入', 129, 6, 1, 0, 'F', '0', '0', 'system:student:source:import', '#', 'admin', sysdate());

-- ----------------------------
-- 8、角色菜单关联 - 普通角色关联新增菜单权限
-- ----------------------------
-- 关联科研数据管理菜单权限
insert ignore into sys_role_menu (role_id, menu_id) values
(2, 119), (2, 120), (2, 121), (2, 122),
(2, 1069), (2, 1070), (2, 1071), (2, 1072), (2, 1073), (2, 1074),
(2, 1075), (2, 1076), (2, 1077), (2, 1078), (2, 1079), (2, 1080),
(2, 1081), (2, 1082), (2, 1083), (2, 1084), (2, 1085), (2, 1086);

-- 关联教学数据管理菜单权限
insert ignore into sys_role_menu (role_id, menu_id) values
(2, 123), (2, 124), (2, 125),
(2, 1087), (2, 1088), (2, 1089), (2, 1090), (2, 1091), (2, 1092),
(2, 1093), (2, 1094), (2, 1095), (2, 1096), (2, 1097), (2, 1098);

-- 关联学生管理数据菜单权限
insert ignore into sys_role_menu (role_id, menu_id) values
(2, 126), (2, 127), (2, 128), (2, 129),
(2, 1099), (2, 1100), (2, 1101), (2, 1102), (2, 1103), (2, 1104),
(2, 1105), (2, 1106), (2, 1107), (2, 1108), (2, 1109), (2, 1110), (2, 1111), (2, 1112),
(2, 1113), (2, 1114), (2, 1115), (2, 1116), (2, 1117), (2, 1118);
