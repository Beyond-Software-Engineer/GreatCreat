<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="入学年份" prop="enrollmentYear">
        <el-input
          v-model="queryParams.enrollmentYear"
          placeholder="请输入入学年份"
          clearable
          style="width: 120px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生源省份" prop="sourceProvince">
        <el-input
          v-model="queryParams.sourceProvince"
          placeholder="请输入生源省份"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生源城市" prop="sourceCity">
        <el-input
          v-model="queryParams.sourceCity"
          placeholder="请输入生源城市"
          clearable
          style="width: 180px"
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
          v-hasPermi="['system:student:source:add']"
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
          v-hasPermi="['system:student:source:edit']"
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
          v-hasPermi="['system:student:source:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:student:source:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="sourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生源ID" align="center" prop="sourceId" />
      <el-table-column label="入学年份" align="center" prop="enrollmentYear" />
      <el-table-column label="生源省份" align="center" prop="sourceProvince" />
      <el-table-column label="生源城市" align="center" prop="sourceCity" />
      <el-table-column label="毕业中学" align="center" prop="graduationSchool" />
      <el-table-column label="学生人数" align="center" prop="studentCount" />
      <el-table-column label="平均入学分数" align="center" prop="avgEntranceScore" />
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
            v-hasPermi="['system:student:source:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:student:source:remove']"
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

    <!-- 添加或修改生源数据对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="入学年份" prop="enrollmentYear">
          <el-input v-model="form.enrollmentYear" placeholder="请输入入学年份" />
        </el-form-item>
        <el-form-item label="生源省份" prop="sourceProvince">
          <el-input v-model="form.sourceProvince" placeholder="请输入生源省份" />
        </el-form-item>
        <el-form-item label="生源城市" prop="sourceCity">
          <el-input v-model="form.sourceCity" placeholder="请输入生源城市" />
        </el-form-item>
        <el-form-item label="毕业中学" prop="graduationSchool">
          <el-input v-model="form.graduationSchool" placeholder="请输入毕业中学" />
        </el-form-item>
        <el-form-item label="学生人数" prop="studentCount">
          <el-input v-model="form.studentCount" type="number" placeholder="请输入学生人数" />
        </el-form-item>
        <el-form-item label="平均入学分数" prop="avgEntranceScore">
          <el-input v-model="form.avgEntranceScore" type="number" placeholder="请输入平均入学分数" />
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
import { listSource, getSource, delSource, addSource, updateSource, exportSource } from "@/api/system/student/source";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "StudentSource",
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
      // 生源数据表格数据
      sourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enrollmentYear: null,
        sourceProvince: null,
        sourceCity: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        enrollmentYear: [
          { required: true, message: "入学年份不能为空", trigger: "blur" }
        ],
        sourceProvince: [
          { required: true, message: "生源省份不能为空", trigger: "blur" }
        ],
        sourceCity: [
          { required: true, message: "生源城市不能为空", trigger: "blur" }
        ],
        studentCount: [
          { required: true, message: "学生人数不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生源数据列表 */
    getList() {
      this.loading = true;
      listSource(this.queryParams).then(response => {
        this.sourceList = response.rows;
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
      this.ids = selection.map(item => item.sourceId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生源数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sourceId = row.sourceId || this.ids[0];
      getSource(sourceId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生源数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sourceId != null) {
            updateSource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSource(this.form).then(response => {
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
        sourceId: null,
        enrollmentYear: null,
        sourceProvince: null,
        sourceCity: null,
        graduationSchool: null,
        studentCount: null,
        avgEntranceScore: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const sourceIds = row.sourceId || this.ids;
      this.$modal.confirm('是否确认删除生源数据编号为"' + sourceIds + '"的数据项？').then(function() {
        return delSource(sourceIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student/source/export', {
        ...this.queryParams
      }, `source_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
