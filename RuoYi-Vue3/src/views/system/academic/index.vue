<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-input v-model="queryParams.studentId" placeholder="请输入学号" clearable style="width: 240px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input v-model="queryParams.studentName" placeholder="请输入姓名" clearable style="width: 240px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="学期" prop="semester">
        <el-input v-model="queryParams.semester" placeholder="请输入学期" clearable style="width: 240px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="专业" prop="majorName">
        <el-input v-model="queryParams.majorName" placeholder="请输入专业" clearable style="width: 240px" @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="学业状态" prop="academicStatus">
        <el-select v-model="queryParams.academicStatus" placeholder="学业状态" clearable style="width: 240px">
          <el-option label="正常" value="0" />
          <el-option label="预警" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" style="width: 308px">
        <el-date-picker v-model="dateRange" value-format="YYYY-MM-DD" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:academic:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['system:academic:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:academic:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Upload" @click="handleImport" v-hasPermi="['system:academic:import']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:academic:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns" storageKey="academic-table-columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="academicList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" align="center" key="academicId" prop="academicId" v-if="columns.academicId.visible" width="80" />
      <el-table-column label="学号" align="center" key="studentId" prop="studentId" v-if="columns.studentId.visible" width="120" />
      <el-table-column label="姓名" align="center" key="studentName" prop="studentName" v-if="columns.studentName.visible" width="100" />
      <el-table-column label="学期" align="center" key="semester" prop="semester" v-if="columns.semester.visible" width="120" />
      <el-table-column label="修读课程" align="center" key="courseName" prop="courseName" v-if="columns.courseName.visible" :show-overflow-tooltip="true" />
      <el-table-column label="课程成绩" align="center" key="courseScore" prop="courseScore" v-if="columns.courseScore.visible" width="100" />
      <el-table-column label="获得学分" align="center" key="obtainedCredits" prop="obtainedCredits" v-if="columns.obtainedCredits.visible" width="100" />
      <el-table-column label="总学分" align="center" key="totalCredits" prop="totalCredits" v-if="columns.totalCredits.visible" width="100" />
      <el-table-column label="专业排名" align="center" key="majorRank" prop="majorRank" v-if="columns.majorRank.visible" width="100" />
      <el-table-column label="专业" align="center" key="majorName" prop="majorName" v-if="columns.majorName.visible" width="120" />
      <el-table-column label="班级" align="center" key="className" prop="className" v-if="columns.className.visible" width="120" />
      <el-table-column label="学业状态" align="center" key="academicStatus" v-if="columns.academicStatus.visible">
        <template #default="scope">
          <el-tag :type="scope.row.academicStatus === '0' ? 'success' : 'danger'">
            {{ scope.row.academicStatus === '0' ? '正常' : '预警' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns.createTime.visible" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:academic:edit']"></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:academic:remove']"></el-button>
          </el-tooltip>
          <el-tooltip content="核算学分" placement="top">
            <el-button link type="primary" icon="Calculator" @click="handleCalculateCredits(scope.row.studentId)" v-hasPermi="['system:academic:calculate']"></el-button>
          </el-tooltip>
          <el-tooltip content="更新状态" placement="top">
            <el-button link type="primary" icon="Refresh" @click="handleUpdateStatus(scope.row.studentId)" v-hasPermi="['system:academic:updateStatus']"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改学业数据对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form :model="form" :rules="rules" ref="academicRef" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入姓名" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学期" prop="semester">
              <el-input v-model="form.semester" placeholder="请输入学期" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="修读课程" prop="courseName">
              <el-input v-model="form.courseName" placeholder="请输入修读课程" maxlength="100" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="课程成绩" prop="courseScore">
              <el-input v-model.number="form.courseScore" type="number" placeholder="请输入课程成绩" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="获得学分" prop="obtainedCredits">
              <el-input v-model.number="form.obtainedCredits" type="number" placeholder="请输入获得学分" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总学分" prop="totalCredits">
              <el-input v-model.number="form.totalCredits" type="number" placeholder="请输入总学分" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业排名" prop="majorRank">
              <el-input v-model.number="form.majorRank" type="number" placeholder="请输入专业排名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="专业" prop="majorName">
              <el-input v-model="form.majorName" placeholder="请输入专业" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="form.className" placeholder="请输入班级" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学业状态">
              <el-select v-model="form.academicStatus" placeholder="请选择学业状态">
                <el-option label="正常" value="0" />
                <el-option label="预警" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 学业数据导入对话框 -->
    <excel-import-dialog ref="importAcademicRef" title="学业数据导入" action="/system/academic/importData" template-action="/system/academic/importTemplate" template-file-name="academic_template" update-support-label="是否更新已经存在的学业数据" @success="getList" />
  </div>
</template>

<script setup name="Academic">
import ExcelImportDialog from "@/components/ExcelImportDialog"
import { listAcademic, getAcademic, addAcademic, updateAcademic, delAcademic, exportAcademic, calculateCredits, updateAcademicStatus } from "@/api/system/academic"

const router = useRouter()
const { proxy } = getCurrentInstance()

const academicList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])

// 列显隐信息
const columns = ref({
  academicId: { label: '序号', visible: true },
  studentId: { label: '学号', visible: true },
  studentName: { label: '姓名', visible: true },
  semester: { label: '学期', visible: true },
  courseName: { label: '修读课程', visible: true },
  courseScore: { label: '课程成绩', visible: true },
  obtainedCredits: { label: '获得学分', visible: true },
  totalCredits: { label: '总学分', visible: true },
  majorRank: { label: '专业排名', visible: true },
  majorName: { label: '专业', visible: true },
  className: { label: '班级', visible: true },
  academicStatus: { label: '学业状态', visible: true },
  createTime: { label: '创建时间', visible: true }
})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    studentId: undefined,
    studentName: undefined,
    semester: undefined,
    majorName: undefined,
    academicStatus: undefined
  },
  rules: {
    studentId: [{ required: true, message: "学号不能为空", trigger: "blur" }],
    studentName: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
    courseName: [{ required: true, message: "修读课程不能为空", trigger: "blur" }],
    obtainedCredits: [{ required: true, message: "获得学分不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询学业数据列表 */
function getList() {
  loading.value = true
  listAcademic(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
    loading.value = false
    academicList.value = res.rows
    total.value = res.total
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = []
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 删除按钮操作 */
function handleDelete(row) {
  const academicIds = row.academicId || ids.value
  proxy.$modal.confirm('是否确认删除学业数据编号为"' + academicIds + '"的数据项？').then(function () {
    return delAcademic(academicIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/academic/export", {
    ...queryParams.value,
  },`academic_${new Date().getTime()}.xlsx`)
}

/** 选择条数  */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.academicId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 导入按钮操作 */
function handleImport() {
  proxy.$refs["importAcademicRef"].open()
}

/** 核算学分操作 */
function handleCalculateCredits(studentId) {
  proxy.$modal.confirm('是否确认核算学号为"' + studentId + '"的学生总学分？').then(function () {
    return calculateCredits(studentId)
  }).then((response) => {
    proxy.$modal.msgSuccess(response.msg)
    getList()
  }).catch(() => {})
}

/** 更新学业状态操作 */
function handleUpdateStatus(studentId) {
  proxy.$modal.confirm('是否确认更新学号为"' + studentId + '"的学生学业状态？').then(function () {
    return updateAcademicStatus(studentId)
  }).then(() => {
    proxy.$modal.msgSuccess("更新成功")
    getList()
  }).catch(() => {})
}

/** 重置操作表单 */
function reset() {
  form.value = {
    academicId: undefined,
    studentId: undefined,
    studentName: undefined,
    semester: undefined,
    courseName: undefined,
    courseScore: undefined,
    obtainedCredits: undefined,
    totalCredits: undefined,
    majorRank: undefined,
    majorName: undefined,
    className: undefined,
    academicStatus: "0",
    remark: undefined
  }
  proxy.resetForm("academicRef")
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加学业数据"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const academicId = row.academicId || ids.value
  getAcademic(academicId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改学业数据"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["academicRef"].validate(valid => {
    if (valid) {
      if (form.value.academicId != undefined) {
        updateAcademic(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAcademic(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

onMounted(() => {
  getList()
})
</script>
