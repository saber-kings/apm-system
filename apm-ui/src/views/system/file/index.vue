<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文件名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="大小" prop="size">
        <el-input
          v-model="queryParams.size"
          placeholder="请输入大小"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人" prop="operate">
        <el-input
          v-model="queryParams.operate"
          placeholder="请输入操作人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建日期" prop="createTime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:file:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:file:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:file:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:file:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="fileList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item label="文件ID">
              <span>{{ scope.row.id }}</span>
            </el-form-item>
            <el-form-item label="标题">
              <span>{{ scope.row.title }}</span>
            </el-form-item>
            <el-form-item label="文件名">
              <span>{{ scope.row.name }}</span>
            </el-form-item>
            <el-form-item label="真实名称">
              <span>{{ scope.row.realName }}</span>
            </el-form-item>
            <el-form-item label="网络地址">
              <a class="my-a-hover-deepskyblue"
                :href="scope.row.url"
                target="_blank">
                {{ scope.row.url }}
              </a>
            </el-form-item>
            <el-form-item label="磁盘路径">
              <span>{{ scope.row.realUrl }}</span>
            </el-form-item>
            <el-form-item label="后缀">
              <span>{{ scope.row.suffix }}</span>
            </el-form-item>
            <el-form-item label="虚拟路径">
              <span>{{ scope.row.path }}</span>
            </el-form-item>
            <el-form-item label="类型">
              <el-tag
                :type="typeClass(scope.row, scope.row.type)"
                disable-transitions>{{typeFormat(scope.row, scope.row.type)}}</el-tag>
            </el-form-item>
            <el-form-item label="大小">
              <span>{{ scope.row.size }}</span>
            </el-form-item>
            <el-form-item label="操作人">
              <span>{{ scope.row.operate }}</span>
            </el-form-item>
            <el-form-item label="创建日期">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.createTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="备注/描述">
              <span>{{ scope.row.remark }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="文件ID" :show-overflow-tooltip="true" align="center" prop="id" />
      <el-table-column label="标题" :show-overflow-tooltip="true" align="center" prop="title" />
      <el-table-column label="文件名" width="160" :show-overflow-tooltip="true" align="center" prop="name" />
      <el-table-column label="后缀" width="80" align="center" prop="suffix" />
      <el-table-column
        label="类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
        width="60"
      />
      <el-table-column label="大小" width="160" align="center" prop="size" />
      <el-table-column label="操作人" align="center" prop="operate" />
      <el-table-column
        label="创建日期"
        align="center"
        prop="createTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:file:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:file:remove']"
            >删除</el-button
          >
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

    <!-- 添加或修改文件管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select
            v-model="form.type"
            placeholder="请选择类型"
          >
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注/描述" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listFile,
  getFile,
  delFile,
  addFile,
  updateFile,
  exportFile,
} from "@/api/system/file";

export default {
  name: "File",
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
      // 文件管理表格数据
      fileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型(0图片 1音频 2视频 3文档 4其他)字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        name: null,
        type: null,
        size: null,
        operate: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          {
            required: true,
            message: "类型(0图片 1音频 2视频 3文档 4其他)不能为空",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_upload_file_type").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询文件管理列表 */
    getList() {
      this.loading = true;
      listFile(this.queryParams).then((response) => {
        this.fileList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类型(0图片 1音频 2视频 3文档 4其他)字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
     // 类型字典回显样式
    typeClass(row, column) {
      return this.selectDictListClass(this.typeOptions, row.type);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        name: null,
        realName: null,
        url: null,
        realUrl: null,
        suffix: null,
        path: null,
        type: null,
        size: null,
        operate: null,
        createTime: null,
        remark: null,
      };
      this.resetForm("form");
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加文件管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFile(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文件管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateFile(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFile(this.form).then((response) => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除文件管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delFile(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有文件管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportFile(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
