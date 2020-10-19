<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="分类名称" prop="cateName">
        <el-input
          v-model="queryParams.cateName"
          placeholder="请输入分类名称"
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
          v-hasPermi="['mall:category:add']"
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="categoryList"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="父分类" align="center" prop="parentName" />
      <el-table-column label="分类名称" align="center" prop="cateName" />
      <el-table-column
        label="是否推荐"
        align="center"
        prop="isShow"
        :formatter="isShowFormat"
      />
      <el-table-column label="创建时间" align="center" prop="addTime" sortable width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.addTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="删除状态" align="center" prop="isDel">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isDel" :active-value="parseInt(0)" :inactive-value="parseInt(1)" disabled />
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
            v-hasPermi="['mall:category:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:category:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改商品分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父分类" prop="pid">
          <treeselect
            v-model="form.pid"
            :options="categoryOptions"
            :show-count="true"
            :normalizer="normalizer"
            placeholder="请选择父分类"
          />
        </el-form-item>
        <el-form-item label="分类名称" prop="cateName">
          <el-input v-model="form.cateName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="是否推荐" prop="isShow">
          <el-select v-model="form.isShow" placeholder="请选择是否推荐">
            <el-option
              v-for="dict in isShowOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
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
  listCategory,
  getCategory,
  delCategory,
  addCategory,
  updateCategory,
  exportCategory,
} from "@/api/mall/category";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Editor from "@/components/Editor";

export default {
  name: "Category",
  components: {
    Treeselect,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 商品分类表格数据
      categoryList: [],
      // 商品分类树选项
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否推荐字典
      isShowOptions: [],
      // 删除状态字典
      isDelOptions: [],
      // 查询参数
      queryParams: {
        cateName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pid: [{ required: true, message: "父id不能为空", trigger: "change" }],
        cateName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("mall_product_category_is_show").then((response) => {
      this.isShowOptions = response.data;
    });
    this.getDicts("mall_product_category_is_del").then((response) => {
      this.isDelOptions = response.data;
    });
  },
  methods: {
    /** 查询商品分类列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then((response) => {
        this.categoryList = this.handleTree(response.data, "id", "pid");
        this.loading = false;
      });
    },
    /** 转换商品分类数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.cateName,
        children: node.children,
      };
    },
    /** 查询分类下拉树结构 */
    getTreeselect() {
      listCategory().then((response) => {
        this.categoryOptions = [];
        const data = { id: 0, cateName: "顶级分类", children: [] };
        data.children = this.handleTree(response.data, "id", "pid");
        this.categoryOptions.push(data);
      });
    },
    // 是否推荐字典翻译
    isShowFormat(row, column) {
      return this.selectDictLabel(this.isShowOptions, row.isShow);
    },
    // 删除状态字典翻译
    isDelFormat(row, column) {
      return this.selectDictLabel(this.isDelOptions, row.isDel);
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
        pid: null,
        cateName: null,
        sort: null,
        pic: null,
        isShow: null,
        addTime: null,
        isDel: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.title = "添加商品分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.pid = row.id;
      }
      getCategory(row.id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateCategory(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addCategory(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm(
        '是否确认删除商品分类编号为"' + row.id + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(function () {
          return delCategory(row.id);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
