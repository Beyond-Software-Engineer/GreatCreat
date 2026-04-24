<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="论文标题" prop="paperTitle">
        <el-input
          v-model="queryParams.paperTitle"
          placeholder="请输入论文标题"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          style="width: 180px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发表期刊" prop="publishJournal">
        <el-input
          v-model="queryParams.publishJournal"
          placeholder="请输入发表期刊/会议"
          clearable
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收录类型" prop="indexType">
        <el-select
          v-model="queryParams.indexType"
          placeholder="请选择收录类型"
          clearable
          style="width: 120px"
        >
          <el-option label="EI" value="EI" />
          <el-option label="SCI" value="SCI" />
          <el-option label="其他" value="其他" />
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
          v-hasPermi="['system:research:paper:add']"
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
          v-hasPermi="['system:research:paper:edit']"
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
          v-hasPermi="['system:research:paper:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['system:research:paper:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="paperList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="论文ID" align="center" prop="paperId" />
      <el-table-column label="论文标题" align="center" prop="paperTitle" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="发表期刊/会议" align="center" prop="publishJournal" />
      <el-table-column label="收录类型" align="center" prop="indexType" />
      <el-table-column label="论文分区" align="center" prop="paperZone" />
      <el-table-column label="发表时间" align="center" prop="publishTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="statusType[scope.row.status]" size="small">
            {{ statusMap[scope.row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="small"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:research:paper:edit']"
          >修改</el-button>
          <el-button
            size="small"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:research:paper:remove']"
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

    <!-- 添加或修改论文对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="论文标题" prop="paperTitle">
          <el-input v-model="form.paperTitle" placeholder="请输入论文标题" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="发表期刊/会议" prop="publishJournal">
          <el-input v-model="form.publishJournal" placeholder="请输入发表期刊/会议" />
        </el-form-item>
        <el-form-item label="收录类型" prop="indexType">
          <el-select v-model="form.indexType" placeholder="请选择收录类型">
            <el-option label="EI" value="EI" />
            <el-option label="SCI" value="SCI" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="论文分区" prop="paperZone">
          <el-select v-model="form.paperZone" placeholder="请选择论文分区">
            <el-option label="A" value="A" />
            <el-option label="B" value="B" />
            <el-option label="C" value="C" />
            <el-option label="无" value="无" />
          </el-select>
        </el-form-item>
        <el-form-item label="发表时间" prop="publishTime">
          <el-date-picker
            v-model="form.publishTime"
            type="date"
            placeholder="选择发表时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="附件URL" prop="attachmentUrl">
          <el-input v-model="form.attachmentUrl" placeholder="请输入附件URL（录用通知等）" />
        </el-form-item>
        <el-form-item label="审核状态" prop="status" v-if="form.paperId">
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
import { listPaper, getPaper, delPaper, addPaper, updatePaper, exportPaper } from "@/api/system/research/paper";
import { parseTime, resetForm } from "@/utils/ruoyi";
import { download } from "@/utils/request";

export default {
  name: "Paper",
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
      // 论文表格数据
      paperList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paperTitle: null,
        author: null,
        publishJournal: null,
        indexType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paperTitle: [
          { required: true, message: "论文标题不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        publishTime: [
          { required: true, message: "发表时间不能为空", trigger: "blur" }
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
    /** 查询论文列表 */
    getList() {
      this.loading = true;
      listPaper(this.queryParams).then(response => {
        this.paperList = response.rows;
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
      this.ids = selection.map(item => item.paperId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加论文";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paperId = row.paperId || this.ids[0];
      getPaper(paperId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改论文";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paperId != null) {
            updatePaper(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPaper(this.form).then(response => {
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
        paperId: null,
        paperTitle: null,
        author: null,
        publishJournal: null,
        indexType: null,
        paperZone: null,
        publishTime: null,
        attachmentUrl: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const paperIds = row.paperId || this.ids;
      this.$modal.confirm('是否确认删除论文编号为"' + paperIds + '"的数据项？').then(function() {
        return delPaper(paperIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/research/paper/export', {
        ...this.queryParams
      }, `paper_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
