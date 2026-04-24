<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentNo">
        <el-input
          v-model="queryParams.studentNo"
          placeholder="请输入学号"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入姓名"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业" prop="majorName">
        <el-input
          v-model="queryParams.majorName"
          placeholder="请输入专业"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入班级"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学籍状态" prop="studentStatus">
        <el-select
          v-model="queryParams.studentStatus"
          placeholder="请选择学籍状态"
          clearable
          style="width: 120px"
        >
          <el-option label="在读" value="0" />
          <el-option label="休学" value="1" />
          <el-option label="复学" value="2" />
          <el-option label="毕业" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['system:student:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="small"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:student:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="small"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:student:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:student:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学生ID" align="center" prop="studentId" />
      <el-table-column label="学号" align="center" prop="studentNo" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="性别" align="center" prop="gender">
        <template slot-scope="scope">
          <span>{{ genderMap[scope.row.gender] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="专业" align="center" prop="majorName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="入学年份" align="center" prop="enrollmentYear" />
      <el-table-column label="生源城市" align="center" prop="sourceCity" />
      <el-table-column label="学籍状态" align="center" prop="studentStatus">
        <template slot-scope="scope">
          <el-tag :type="statusType[scope.row.studentStatus]" size="small">
            {{ statusMap[scope.row.studentStatus] }}
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
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:student:info:edit']"
          >修改</el-button>
          <el-button
            size="small"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:student:info:remove']"
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

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="0" />
            <el-option label="女" value="1" />
            <el-option label="未知" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="所属专业" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入所属专业" />
        </el-form-item>
        <el-form-item label="所属班级" prop="className">
          <el-input v-model="form.className" placeholder="请输入所属班级" />
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentYear">
          <el-input v-model="form.enrollmentYear" placeholder="请输入入学年份" />
        </el-form-item>
        <el-form-item label="生源城市" prop="sourceCity">
          <el-input v-model="form.sourceCity" placeholder="请输入生源城市" />
        </el-form-item>
        <el-form-item label="毕业中学" prop="graduationSchool">
          <el-input v-model="form.graduationSchool" placeholder="请输入毕业中学" />
        </el-form-item>
        <el-form-item label="入学分数" prop="entranceScore">
          <el-input v-model="form.entranceScore" type="number" placeholder="请输入入学分数" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactInfo">
          <el-input v-model="form.contactInfo" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="学籍状态" prop="studentStatus">
          <el-select v-model="form.studentStatus" placeholder="请选择学籍状态">
            <el-option label="在读" value="0" />
            <el-option label="休学" value="1" />
            <el-option label="复学" value="2" />
            <el-option label="毕业" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="form.studentId">
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
import { listStudent, getStudent, delStudent, addStudent, updateStudent, exportStudent } from "@/api/system/student/info";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "StudentInfo",
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
      // 学生信息表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentNo: null,
        studentName: null,
        majorName: null,
        className: null,
        studentStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentNo: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        majorName: [
          { required: true, message: "所属专业不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "所属班级不能为空", trigger: "blur" }
        ],
        enrollmentYear: [
          { required: true, message: "入学年份不能为空", trigger: "blur" }
        ]
      },
      // 性别字典
      genderMap: {
        0: "男",
        1: "女",
        2: "未知"
      },
      // 学籍状态字典
      statusMap: {
        0: "在读",
        1: "休学",
        2: "复学",
        3: "毕业"
      },
      statusType: {
        0: "success",
        1: "warning",
        2: "info",
        3: "danger"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学生信息列表 */
    getList() {
      this.loading = true;
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
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
      this.ids = selection.map(item => item.studentId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学生信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const studentId = row.studentId || this.ids[0];
      getStudent(studentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.studentId != null) {
            updateStudent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudent(this.form).then(response => {
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
        studentId: null,
        studentNo: null,
        studentName: null,
        gender: "0",
        majorName: null,
        className: null,
        enrollmentYear: null,
        sourceCity: null,
        graduationSchool: null,
        entranceScore: null,
        contactInfo: null,
        studentStatus: "0",
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const studentIds = row.studentId || this.ids;
      this.$modal.confirm('是否确认删除学生编号为"' + studentIds + '"的数据项？').then(function() {
        return delStudent(studentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student/info/export', {
        ...this.queryParams
      }, `student_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
