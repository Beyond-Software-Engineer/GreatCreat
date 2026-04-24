<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="经费项目名称" prop="fundingName">
        <el-input
          v-model="queryParams.fundingName"
          placeholder="请输入经费项目名称"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="fundingLeader">
        <el-input
          v-model="queryParams.fundingLeader"
          placeholder="请输入负责人"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款单位" prop="payerUnit">
        <el-input
          v-model="queryParams.payerUnit"
          placeholder="请输入付款单位"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['system:research:funding:add']"
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
          v-hasPermi="['system:research:funding:edit']"
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
          v-hasPermi="['system:research:funding:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:research:funding:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="fundingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="经费ID" align="center" prop="fundingId" />
      <el-table-column label="经费项目名称" align="center" prop="fundingName" />
      <el-table-column label="负责人" align="center" prop="fundingLeader" />
      <el-table-column label="到账金额" align="center" prop="arrivalAmount">
        <template slot-scope="scope">
          <span>{{ scope.row.arrivalAmount }} 元</span>
        </template>
      </el-table-column>
      <el-table-column label="到账时间" align="center" prop="arrivalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="付款单位" align="center" prop="payerUnit" />
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
            v-hasPermi="['system:research:funding:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:research:funding:remove']"
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

    <!-- 添加或修改经费对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="经费项目名称" prop="fundingName">
          <el-input v-model="form.fundingName" placeholder="请输入经费项目名称" />
        </el-form-item>
        <el-form-item label="负责人" prop="fundingLeader">
          <el-input v-model="form.fundingLeader" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="到账金额" prop="arrivalAmount">
          <el-input v-model="form.arrivalAmount" type="number" placeholder="请输入到账金额" />
        </el-form-item>
        <el-form-item label="到账时间" prop="arrivalTime">
          <el-date-picker
            v-model="form.arrivalTime"
            type="date"
            placeholder="选择到账时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="付款单位" prop="payerUnit">
          <el-input v-model="form.payerUnit" placeholder="请输入付款单位" />
        </el-form-item>
        <el-form-item label="附件URL" prop="attachmentUrl">
          <el-input v-model="form.attachmentUrl" placeholder="请输入附件URL（到账凭证等）" />
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="form.fundingId">
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
import { listFunding, getFunding, delFunding, addFunding, updateFunding, exportFunding } from "@/api/system/research/funding";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "Funding",
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
      // 经费表格数据
      fundingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fundingName: null,
        fundingLeader: null,
        payerUnit: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fundingName: [
          { required: true, message: "经费项目名称不能为空", trigger: "blur" }
        ],
        fundingLeader: [
          { required: true, message: "负责人不能为空", trigger: "blur" }
        ],
        arrivalAmount: [
          { required: true, message: "到账金额不能为空", trigger: "blur" }
        ],
        arrivalTime: [
          { required: true, message: "到账时间不能为空", trigger: "blur" }
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
    /** 查询经费列表 */
    getList() {
      this.loading = true;
      listFunding(this.queryParams).then(response => {
        this.fundingList = response.rows;
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
      this.ids = selection.map(item => item.fundingId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加经费";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const fundingId = row.fundingId || this.ids[0];
      getFunding(fundingId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改经费";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fundingId != null) {
            updateFunding(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFunding(this.form).then(response => {
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
        fundingId: null,
        fundingName: null,
        fundingLeader: null,
        arrivalAmount: null,
        arrivalTime: null,
        payerUnit: null,
        attachmentUrl: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const fundingIds = row.fundingId || this.ids;
      this.$modal.confirm('是否确认删除经费编号为"' + fundingIds + '"的数据项？').then(function() {
        return delFunding(fundingIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/research/funding/export', {
        ...this.queryParams
      }, `funding_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
