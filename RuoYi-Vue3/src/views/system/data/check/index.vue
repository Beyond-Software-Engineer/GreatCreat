<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="核对类型" prop="checkType">
        <el-select
          v-model="queryParams.checkType"
          placeholder="请选择核对类型"
          clearable
          style="width: 180px"
        >
          <el-option label="科研数据" value="科研数据" />
          <el-option label="教学数据" value="教学数据" />
          <el-option label="学生数据" value="学生数据" />
        </el-select>
      </el-form-item>
      <el-form-item label="核对结果" prop="checkResult">
        <el-select
          v-model="queryParams.checkResult"
          placeholder="请选择核对结果"
          clearable
          style="width: 120px"
        >
          <el-option label="无误" value="0" />
          <el-option label="错误" value="1" />
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
          icon="el-icon-refresh"
          size="mini"
          @click="handleAutoCheck"
          v-hasPermi="['system:data:check:auto']"
        >自动核对</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleManualCheck"
          v-hasPermi="['system:data:check:manual']"
        >手动核对</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:data:check:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:data:check:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="核对ID" align="center" prop="checkId" />
      <el-table-column label="核对类型" align="center" prop="checkType" />
      <el-table-column label="核对数据ID" align="center" prop="checkDataId" />
      <el-table-column label="核对结果" align="center" prop="checkResult">
        <template slot-scope="scope">
          <el-tag :type="resultType[scope.row.checkResult]" size="small">
            {{ resultMap[scope.row.checkResult] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="核对原因" align="center" prop="checkReason" />
      <el-table-column label="核对人" align="center" prop="checker" />
      <el-table-column label="核对时间" align="center" prop="checkTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleManualCheck(scope.row)"
            v-hasPermi="['system:data:check:manual']"
          >手动核对</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:data:check:remove']"
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

    <!-- 手动核对对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="核对类型" prop="checkType">
          <el-select v-model="form.checkType" placeholder="请选择核对类型">
            <el-option label="科研数据" value="科研数据" />
            <el-option label="教学数据" value="教学数据" />
            <el-option label="学生数据" value="学生数据" />
          </el-select>
        </el-form-item>
        <el-form-item label="核对数据ID" prop="checkDataId">
          <el-input v-model="form.checkDataId" placeholder="请输入核对数据ID" />
        </el-form-item>
        <el-form-item label="核对结果" prop="checkResult">
          <el-select v-model="form.checkResult" placeholder="请选择核对结果">
            <el-option label="无误" value="0" />
            <el-option label="错误" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="核对原因" prop="checkReason">
          <el-input v-model="form.checkReason" type="textarea" placeholder="请输入核对原因" />
        </el-form-item>
        <el-form-item label="核对人" prop="checker">
          <el-input v-model="form.checker" placeholder="请输入核对人" />
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
import { listCheck, getCheck, delCheck, addCheck, updateCheck, exportCheck, autoCheck } from "@/api/system/data/check";

export default {
  name: "DataCheck",
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
      // 核对记录表格数据
      checkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        checkType: null,
        checkResult: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        checkType: [
          { required: true, message: "核对类型不能为空", trigger: "blur" }
        ],
        checkDataId: [
          { required: true, message: "核对数据ID不能为空", trigger: "blur" }
        ],
        checkResult: [
          { required: true, message: "核对结果不能为空", trigger: "blur" }
        ],
        checker: [
          { required: true, message: "核对人不能为空", trigger: "blur" }
        ]
      },
      // 核对结果字典
      resultMap: {
        0: "无误",
        1: "错误"
      },
      resultType: {
        0: "success",
        1: "danger"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询核对记录列表 */
    getList() {
      this.loading = true;
      listCheck(this.queryParams).then(response => {
        this.checkList = response.rows;
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
      this.ids = selection.map(item => item.checkId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 自动核对按钮操作 */
    handleAutoCheck() {
      this.$modal.confirm('是否确认进行自动数据核对？').then(function() {
        return autoCheck();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("自动核对完成");
      }).catch(() => {});
    },
    /** 手动核对按钮操作 */
    handleManualCheck(row) {
      this.reset();
      if (row) {
        const checkId = row.checkId || this.ids[0];
        getCheck(checkId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改核对记录";
        });
      } else {
        this.open = true;
        this.title = "添加核对记录";
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.checkId != null) {
            updateCheck(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheck(this.form).then(response => {
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
        checkId: null,
        checkType: null,
        checkDataId: null,
        checkResult: "0",
        checkReason: null,
        checker: null,
        checkTime: new Date()
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const checkIds = row.checkId || this.ids;
      this.$modal.confirm('是否确认删除核对记录编号为"' + checkIds + '"的数据项？').then(function() {
        return delCheck(checkIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/data/check/export', {
        ...this.queryParams
      }, `check_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
