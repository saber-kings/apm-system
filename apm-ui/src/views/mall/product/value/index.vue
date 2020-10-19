<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="商品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入商品ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input
          v-model="queryParams.stock"
          placeholder="请输入库存"
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
          v-hasPermi="['mall:value:add']"
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
          v-hasPermi="['mall:value:edit']"
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
          v-hasPermi="['mall:value:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:value:export']"
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
      :data="valueList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form
            label-position="right"
            inline
            class="demo-table-expand"
            label-width="100px"
          >
            <el-form-item label="ID">
              <span>{{ scope.row.id }}</span>
            </el-form-item>
            <el-form-item label="商品ID">
              <span>{{ scope.row.productId }}</span>
            </el-form-item>
            <el-form-item label="库存">
              <span>{{ scope.row.stock }}</span>
            </el-form-item>
            <el-form-item label="销量">
              <span>{{ scope.row.sales }}</span>
            </el-form-item>
            <el-form-item label="成本价">
              <span>{{ scope.row.cost }}</span>
            </el-form-item>
            <el-form-item label="属性金额">
              <span>{{ scope.row.price }}</span>
            </el-form-item>
            <el-form-item label="唯一值">
              <span>{{ scope.row.unique }}</span>
            </el-form-item>
            <el-form-item label="图片">
              <a :href="scope.row.image" target="_blank">
                <el-image
                  :src="scope.row.image"
                  style="width: 30px; text-align: center"
                  title="点击查看"
                  fit="fill"
                >
                  <div slot="error" title="点击查看" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </a>
            </el-form-item>
            <el-form-item label="属性组合">
              <span>{{ scope.row.suk }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="库存" align="center" prop="stock" />
      <el-table-column label="销量" align="center" prop="sales" />
      <el-table-column label="成本价" align="center" prop="cost" />
      <el-table-column label="属性金额" align="center" prop="price" />
      <el-table-column label="图片" align="center" prop="image">
        <template slot-scope="scope">
          <a :href="scope.row.image" target="_blank">
            <el-image
              :src="scope.row.image"
              class="img-square"
              title="点击查看"
              style="width: 30px; text-align: center"
              fit="fill"
            >
              <div slot="error" title="点击查看" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </a>
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
            v-hasPermi="['mall:value:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:value:remove']"
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

    <!-- 添加或修改属性组合对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入商品ID" />
        </el-form-item>
        <el-form-item label="属性组合" prop="suk">
          <el-input v-model="form.suk" placeholder="请输入属性值组合" />
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" placeholder="请输入库存" />
        </el-form-item>
        <el-form-item label="属性金额" prop="price">
          <el-input v-model="form.price" placeholder="请输入属性金额" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入图片" />
        </el-form-item>
        <el-form-item label="成本价" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入成本价" />
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
  listValue,
  getValue,
  delValue,
  addValue,
  updateValue,
  exportValue,
} from "@/api/mall/value";

export default {
  name: "Value",
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
      // 属性组合表格数据
      valueList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        stock: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "商品ID不能为空", trigger: "blur" },
        ],
        suk: [
          { required: true, message: "属性值组合不能为空", trigger: "blur" },
        ],
        stock: [{ required: true, message: "库存不能为空", trigger: "blur" }],
        price: [
          { required: true, message: "属性金额不能为空", trigger: "blur" },
        ],
        image: [{ required: true, message: "图片不能为空", trigger: "blur" }],
        cost: [{ required: true, message: "成本价不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询属性组合列表 */
    getList() {
      this.loading = true;
      listValue(this.queryParams).then((response) => {
        this.valueList = response.rows;
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
        suk: null,
        stock: null,
        sales: null,
        price: null,
        image: null,
        unique: null,
        cost: null,
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
      this.title = "添加属性组合";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getValue(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改属性组合";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateValue(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addValue(this.form).then((response) => {
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
        '是否确认删除属性组合编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delValue(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有属性组合数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportValue(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
