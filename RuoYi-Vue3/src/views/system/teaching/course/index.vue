<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="授课教师" prop="teacherName">
        <el-input
          v-model="queryParams.teacherName"
          placeholder="请输入授课教师"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学期" prop="semester">
        <el-input
          v-model="queryParams.semester"
          placeholder="请输入学期"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程类型" prop="courseType">
        <el-select
          v-model="queryParams.courseType"
          placeholder="请选择课程类型"
          clearable
          style="width: 120px"
        >
          <el-option label="必修课" value="必修课" />
          <el-option label="选修课" value="选修课" />
          <el-option label="实践课" value="实践课" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:teaching:course:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:teaching:course:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:teaching:course:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:teaching:course:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="开课ID" align="center" prop="courseId" />
      <el-table-column label="学期" align="center" prop="semester" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="课程编码" align="center" prop="courseCode" />
      <el-table-column label="授课教师" align="center" prop="teacherName" />
      <el-table-column label="授课专业" align="center" prop="majorName" />
      <el-table-column label="授课班级" align="center" prop="className" />
      <el-table-column label="课时数" align="center" prop="courseHours" />
      <el-table-column label="课程类型" align="center" prop="courseType" />
      <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="statusType[scope.row.status]" size="small">
            {{ statusMap[scope.row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:teaching:course:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:teaching:course:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改开课对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="请输入学期" />
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程编码" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入课程编码" />
        </el-form-item>
        <el-form-item label="授课教师" prop="teacherName">
          <el-input v-model="form.teacherName" placeholder="请输入授课教师" />
        </el-form-item>
        <el-form-item label="授课专业" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入授课专业" />
        </el-form-item>
        <el-form-item label="授课班级" prop="className">
          <el-input v-model="form.className" placeholder="请输入授课班级" />
        </el-form-item>
        <el-form-item label="课时数" prop="courseHours">
          <el-input v-model="form.courseHours" type="number" placeholder="请输入课时数" />
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="form.courseType" placeholder="请选择课程类型">
            <el-option label="必修课" value="必修课" />
            <el-option label="选修课" value="选修课" />
            <el-option label="实践课" value="实践课" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="form.courseId">
          <el-select v-model="form.status" placeholder="请选择审核状态">
            <el-option label="待审核" value="0" />
            <el-option label="已通过" value="1" />
            <el-option label="已驳回" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCourse, getCourse, delCourse, addCourse, updateCourse, exportCourse } from "@/api/system/teaching/course";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "Course",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 开课表格数据
      courseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: null,
        teacherName: null,
        semester: null,
        courseType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        semester: [
          { required: true, message: "学期不能为空", trigger: "blur" }
        ],
        courseName: [
          { required: true, message: "课程名称不能为空", trigger: "blur" }
        ],
        courseCode: [
          { required: true, message: "课程编码不能为空", trigger: "blur" }
        ],
        teacherName: [
          { required: true, message: "授课教师不能为空", trigger: "blur" }
        ],
        courseHours: [
          { required: true, message: "课时数不能为空", trigger: "blur" }
        ]
      },
      // 审核状态字典
      statusMap: {
        0: "待审核",
        1: "已通过",
        2: "已驳回"
      },
      statusType: {
        0: "warning",
        1: "success",
        2: "danger"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询开课列表 */
    getList() {
      this.loading = true;
      listCourse(this.queryParams).then(response => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.courseId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加开课"; 
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const courseId = row.courseId || this.ids[0];
      getCourse(courseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改开课";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.courseId != null) {
            updateCourse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCourse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 重置表单 */
    reset() {
      this.form = {
        courseId: null,
        semester: null,
        courseName: null,
        courseCode: null,
        teacherName: null,
        majorName: null,
        className: null,
        courseHours: null,
        courseType: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const courseIds = row.courseId || this.ids;
      this.$modal.confirm('是否确认删除开课编号为"' + courseIds + '"的数据项？').then(function() {
        return delCourse(courseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/teaching/course/export', {
        ...this.queryParams
      }, `course_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
