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
      <el-form-item label="学期" prop="semester">
        <el-input
          v-model="queryParams.semester"
          placeholder="请输入学期"
          clearable
          style="width: 120px"
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
      <el-form-item label="学业状态" prop="academicStatus">
        <el-select
          v-model="queryParams.academicStatus"
          placeholder="请选择学业状态"
          clearable
          style="width: 120px"
        >
          <el-option label="正常" value="0" />
          <el-option label="预警" value="1" />
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
          v-hasPermi="['system:student:academic:add']"
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
          v-hasPermi="['system:student:academic:edit']"
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
          v-hasPermi="['system:student:academic:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:student:academic:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-finance"
          size="mini"
          @click="handleCalculate"
          v-hasPermi="['system:student:academic:calculate']"
        >学分核算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-warning"
          size="mini"
          @click="handleWarning"
          v-hasPermi="['system:student:academic:warning']"
        >学业预警</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="academicList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学业数据ID" align="center" prop="academicId" />
      <el-table-column label="学号" align="center" prop="studentNo" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="学期" align="center" prop="semester" />
      <el-table-column label="修读课程" align="center" prop="courseName" />
      <el-table-column label="课程成绩" align="center" prop="courseScore" />
      <el-table-column label="获得学分" align="center" prop="obtainedCredits" />
      <el-table-column label="总学分" align="center" prop="totalCredits" />
      <el-table-column label="专业排名" align="center" prop="majorRank" />
      <el-table-column label="专业" align="center" prop="majorName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="学业状态" align="center" prop="academicStatus">
        <template slot-scope="scope">
          <el-tag :type="statusType[scope.row.academicStatus]" size="small">
            {{ statusMap[scope.row.academicStatus] }}
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
            v-hasPermi="['system:student:academic:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:student:academic:remove']"
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

    <!-- 添加或修改学业数据对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="请输入学期" />
        </el-form-item>
        <el-form-item label="修读课程" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入修读课程" />
        </el-form-item>
        <el-form-item label="课程成绩" prop="courseScore">
          <el-input v-model="form.courseScore" type="number" placeholder="请输入课程成绩" />
        </el-form-item>
        <el-form-item label="获得学分" prop="obtainedCredits">
          <el-input v-model="form.obtainedCredits" type="number" placeholder="请输入获得学分" />
        </el-form-item>
        <el-form-item label="总学分" prop="totalCredits">
          <el-input v-model="form.totalCredits" type="number" placeholder="请输入总学分" />
        </el-form-item>
        <el-form-item label="专业排名" prop="majorRank">
          <el-input v-model="form.majorRank" type="number" placeholder="请输入专业排名" />
        </el-form-item>
        <el-form-item label="专业" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="学业状态" prop="academicStatus">
          <el-select v-model="form.academicStatus" placeholder="请选择学业状态">
            <el-option label="正常" value="0" />
            <el-option label="预警" value="1" />
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
import { listAcademic, getAcademic, delAcademic, addAcademic, updateAcademic, exportAcademic, calculateCredits, updateWarning } from "@/api/system/student/academic";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "StudentAcademic",
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
      // 学业数据表格数据
      academicList: [],
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
        semester: null,
        majorName: null,
        academicStatus: null
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
        semester: [
          { required: true, message: "学期不能为空", trigger: "blur" }
        ],
        courseName: [
          { required: true, message: "修读课程不能为空", trigger: "blur" }
        ],
        obtainedCredits: [
          { required: true, message: "获得学分不能为空", trigger: "blur" }
        ],
        majorName: [
          { required: true, message: "专业不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "班级不能为空", trigger: "blur" }
        ]
      },
      // 学业状态字典
      statusMap: {
        0: "正常",
        1: "预警"
      },
      statusType: {
        0: "success",
        1: "danger"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询学业数据列表 */
    getList() {
      this.loading = true;
      listAcademic(this.queryParams).then(response => {
        this.academicList = response.rows;
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
      this.ids = selection.map(item => item.academicId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学业数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const academicId = row.academicId || this.ids[0];
      getAcademic(academicId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学业数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.academicId != null) {
            updateAcademic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAcademic(this.form).then(response => {
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
        academicId: null,
        studentNo: null,
        studentName: null,
        semester: null,
        courseName: null,
        courseScore: null,
        obtainedCredits: null,
        totalCredits: 0.00,
        majorRank: null,
        majorName: null,
        className: null,
        academicStatus: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const academicIds = row.academicId || this.ids;
      this.$modal.confirm('是否确认删除学业数据编号为"' + academicIds + '"的数据项？').then(function() {
        return delAcademic(academicIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student/academic/export', {
        ...this.queryParams
      }, `academic_${new Date().getTime()}.xlsx`);
    },
    /** 学分核算按钮操作 */
    handleCalculate() {
      this.$modal.confirm('是否确认进行学分核算？').then(function() {
        return calculateCredits();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("学分核算成功");
      }).catch(() => {});
    },
    /** 学业预警按钮操作 */
    handleWarning() {
      this.$modal.confirm('是否确认进行学业预警？').then(function() {
        return updateWarning();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("学业预警更新成功");
      }).catch(() => {});
    }
  }
};
</script>
