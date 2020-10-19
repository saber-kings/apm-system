<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性名" prop="attrName">
        <el-input
          v-model="queryParams.attrName"
          placeholder="请输入属性名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['mall:attr:add']"
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
          v-hasPermi="['mall:attr:edit']"
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
          v-hasPermi="['mall:attr:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:attr:export']"
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
      :data="attrList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item label="属性ID">
              <span>{{ scope.row.id }}</span>
            </el-form-item>
            <el-form-item label="商品ID">
              <span>{{ scope.row.productId }}</span>
            </el-form-item>
            <el-form-item label="商品名称">
              <span>{{ scope.row.productName }}</span>
            </el-form-item>
            <el-form-item label="属性名">
              <span>{{ scope.row.attrName }}</span>
            </el-form-item>
            <el-form-item label="属性值">
              <span>{{ scope.row.attrValues }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="属性ID" align="center" prop="id" />
      <el-table-column label="商品名称" :show-overflow-tooltip="true" align="center" prop="productName" />
      <el-table-column label="属性名" align="center" prop="attrName" />
      <el-table-column label="属性值" :show-overflow-tooltip="true" align="center" prop="attrValues" />
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
            v-hasPermi="['mall:attr:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:attr:remove']"
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

    <!-- 添加或修改商品属性对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属商品" prop="productId">
          <el-select v-model="form.productId" filterable  placeholder="请选择商品" class="full-select">
            <el-option
              v-for="product in productList"
              :key="product.id"
              :label="product.storeName"
              :value="product.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="属性名" prop="attrName">
          <el-input v-model="form.attrName" placeholder="请输入属性名" />
        </el-form-item>
        <el-form-item label="属性值" prop="attrValues">
          <el-input
            v-model="form.attrValues"
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
  listAttr,
  getAttr,
  delAttr,
  addAttr,
  updateAttr,
  exportAttr,
} from "@/api/mall/attr";
import { listProduct } from "@/api/mall/product";

export default {
  name: "Attr",
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
      // 商品列表数据
      productList: [],
      // 商品属性表格数据
      attrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 商品查询参数
      productParams: {
        pageNum: 1,
        pageSize: 100000000
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        attrName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "商品ID不能为空", trigger: "blur" },
        ],
        attrName: [
          { required: true, message: "属性名不能为空", trigger: "blur" },
        ],
        attrValues: [
          { required: true, message: "属性值不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getProductList();
    this.getList();
  },
  methods: {
     /** 查询商品列表 */
    getProductList() {
      this.loading = true;
      listProduct(this.productParams).then((response) => {
        this.productList = response.rows;
      });
    },
    /** 查询商品属性列表 */
    getList() {
      this.loading = true;
      listAttr(this.queryParams).then((response) => {
        this.attrList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        productId: null,
        attrName: null,
        attrValues: null,
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
      this.title = "添加商品属性";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getAttr(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品属性";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateAttr(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAttr(this.form).then((response) => {
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
        '是否确认删除商品属性编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delAttr(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有商品属性数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportAttr(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>

<style scoped>
.el-select.full-select.el-select--medium {
  width: 100% !important;
}
</style>