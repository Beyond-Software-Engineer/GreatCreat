<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="专业名称" prop="majorName">
        <el-input
          v-model="queryParams.majorName"
          placeholder="请输入专业名称"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学制" prop="educationSystem">
        <el-input
          v-model="queryParams.educationSystem"
          placeholder="请输入学制"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          style="width: 120px"
        >
          <el-option label="草稿" value="0" />
          <el-option label="已发布" value="1" />
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
          v-hasPermi="['system:teaching:program:add']"
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
          v-hasPermi="['system:teaching:program:edit']"
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
          v-hasPermi="['system:teaching:program:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:teaching:program:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="programList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案ID" align="center" prop="programId" />
      <el-table-column label="专业名称" align="center" prop="majorName" />
      <el-table-column label="学制" align="center" prop="educationSystem" />
      <el-table-column label="学分要求" align="center" prop="creditRequirement">
        <template slot-scope="scope">
          <span>{{ scope.row.creditRequirement }} 学分</span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="状态" align="center" prop="status">
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
            v-hasPermi="['system:teaching:program:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:teaching:program:remove']"
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

    <!-- 添加或修改培养方案对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="专业名称" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="学制" prop="educationSystem">
          <el-input v-model="form.educationSystem" placeholder="请输入学制" />
        </el-form-item>
        <el-form-item label="培养目标" prop="trainingGoal">
          <el-input v-model="form.trainingGoal" type="textarea" placeholder="请输入培养目标" />
        </el-form-item>
        <el-form-item label="核心课程" prop="coreCourses">
          <el-input v-model="form.coreCourses" type="textarea" placeholder="请输入核心课程" />
        </el-form-item>
        <el-form-item label="课程学分" prop="courseCredits">
          <el-input v-model="form.courseCredits" type="textarea" placeholder="请输入课程学分" />
        </el-form-item>
        <el-form-item label="学分要求" prop="creditRequirement">
          <el-input v-model="form.creditRequirement" type="number" placeholder="请输入学分要求" />
        </el-form-item>
        <el-form-item label="毕业条件" prop="graduationRequirement">
          <el-input v-model="form.graduationRequirement" type="textarea" placeholder="请输入毕业条件" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="草稿" value="0" />
            <el-option label="已发布" value="1" />
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
import { listProgram, getProgram, delProgram, addProgram, updateProgram, exportProgram } from "@/api/system/teaching/program";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "Program",
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
      // 培养方案表格数据
      programList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        majorName: null,
        educationSystem: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        majorName: [
          { required: true, message: "专业名称不能为空", trigger: "blur" }
        ],
        coreCourses: [
          { required: true, message: "核心课程不能为空", trigger: "blur" }
        ],
        courseCredits: [
          { required: true, message: "课程学分不能为空", trigger: "blur" }
        ]
      },
      // 状态字典
      statusMap: {
        0: "草稿",
        1: "已发布"
      },
      statusType: {
        0: "warning",
        1: "success"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询培养方案列表 */
    getList() {
      this.loading = true;
      listProgram(this.queryParams).then(response => {
        this.programList = response.rows;
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
      this.ids = selection.map(item => item.programId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加培养方案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const programId = row.programId || this.ids[0];
      getProgram(programId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改培养方案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.programId != null) {
            updateProgram(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProgram(this.form).then(response => {
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
        programId: null,
        majorName: null,
        educationSystem: null,
        trainingGoal: null,
        coreCourses: null,
        courseCredits: null,
        creditRequirement: null,
        graduationRequirement: null,
        version: "1.0",
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const programIds = row.programId || this.ids;
      this.$modal.confirm('是否确认删除培养方案编号为"' + programIds + '"的数据项？').then(function() {
        return delProgram(programIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/teaching/program/export', {
        ...this.queryParams
      }, `program_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
