<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="projectLeader">
        <el-input
          v-model="queryParams.projectLeader"
          placeholder="请输入项目负责人"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目类型" prop="projectType">
        <el-select
          v-model="queryParams.projectType"
          placeholder="请选择项目类型"
          clearable
          style="width: 120px"
        >
          <el-option label="纵向" value="纵向" />
          <el-option label="横向" value="横向" />
        </el-select>
      </el-form-item>
      <el-form-item label="到账状态" prop="arrivalStatus">
        <el-select
          v-model="queryParams.arrivalStatus"
          placeholder="请选择到账状态"
          clearable
          style="width: 120px"
        >
          <el-option label="未到账" value="0" />
          <el-option label="部分到账" value="1" />
          <el-option label="全部到账" value="2" />
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
          v-hasPermi="['system:research:project:add']"
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
          v-hasPermi="['system:research:project:edit']"
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
          v-hasPermi="['system:research:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:research:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目ID" align="center" prop="projectId" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目负责人" align="center" prop="projectLeader" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="获批时间" align="center" prop="approveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="经费金额" align="center" prop="fundingAmount">
        <template slot-scope="scope">
          <span>{{ scope.row.fundingAmount }} 元</span>
        </template>
      </el-table-column>
      <el-table-column label="到账状态" align="center" prop="arrivalStatus">
        <template slot-scope="scope">
          <el-tag :type="arrivalType[scope.row.arrivalStatus]" size="small">
            {{ arrivalMap[scope.row.arrivalStatus] }}
          </el-tag>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:research:project:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:research:project:remove']"
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

    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目负责人" prop="projectLeader">
          <el-input v-model="form.projectLeader" placeholder="请输入项目负责人" />
        </el-form-item>
        <el-form-item label="项目类型" prop="projectType">
          <el-select v-model="form.projectType" placeholder="请选择项目类型">
            <el-option label="纵向" value="纵向" />
            <el-option label="横向" value="横向" />
          </el-select>
        </el-form-item>
        <el-form-item label="获批时间" prop="approveTime">
          <el-date-picker
            v-model="form.approveTime"
            type="date"
            placeholder="选择获批时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="经费金额" prop="fundingAmount">
          <el-input v-model="form.fundingAmount" type="number" placeholder="请输入经费金额" />
        </el-form-item>
        <el-form-item label="到账状态" prop="arrivalStatus">
          <el-select v-model="form.arrivalStatus" placeholder="请选择到账状态">
            <el-option label="未到账" value="0" />
            <el-option label="部分到账" value="1" />
            <el-option label="全部到账" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="附件URL" prop="attachmentUrl">
          <el-input v-model="form.attachmentUrl" placeholder="请输入附件URL（立项通知书等）" />
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="form.projectId">
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
import { listProject, getProject, delProject, addProject, updateProject, exportProject } from "@/api/system/research/project";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "Project",
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
      // 项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
        projectLeader: null,
        projectType: null,
        arrivalStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        projectLeader: [
          { required: true, message: "项目负责人不能为空", trigger: "blur" }
        ],
        approveTime: [
          { required: true, message: "获批时间不能为空", trigger: "blur" }
        ],
        fundingAmount: [
          { required: true, message: "经费金额不能为空", trigger: "blur" }
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
      },
      // 到账状态字典
      arrivalMap: {
        0: "未到账",
        1: "部分到账",
        2: "全部到账"
      },
      arrivalType: {
        0: "danger",
        1: "warning",
        2: "success"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
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
      this.ids = selection.map(item => item.projectId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const projectId = row.projectId || this.ids[0];
      getProject(projectId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.projectId != null) {
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
        projectId: null,
        projectName: null,
        projectLeader: null,
        projectType: null,
        approveTime: null,
        fundingAmount: null,
        arrivalStatus: "0",
        attachmentUrl: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const projectIds = row.projectId || this.ids;
      this.$modal.confirm('是否确认删除项目编号为"' + projectIds + '"的数据项？').then(function() {
        return delProject(projectIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/research/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
