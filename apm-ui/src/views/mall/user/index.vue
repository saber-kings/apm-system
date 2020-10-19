<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="帐号状态" prop="status">
        <el-select 
          v-model="queryParams.status" 
          placeholder="请选择帐号状态" 
          clearable 
          size="small" 
          @keyup.enter.native="handleQuery">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker 
          v-model="dateRange" 
          size="small" 
          style="width: 200px" 
          value-format="yyyy-MM-dd" 
          type="daterange" 
          range-separator="-" 
          start-placeholder="开始日期" 
          end-placeholder="结束日期"
           @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入真实姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="购买次数" prop="payCount">
        <el-input 
          placeholder="请输入数值范围以'-'分割"
          v-model="numRange" 
          style="width: 190px" 
          clearable
          size="small"
          onkeyup="this.value=this.value.replace(/[^\d\-]/g,'')"
          @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mall:user:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mall:user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mall:user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:user:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="filterTable" v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" fixed="left" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item label="用户ID">
              <span>{{ scope.row.userId }}</span>
            </el-form-item>
             <el-form-item label="用户类型">
              <el-tag
                :type="userTypeTagClass(scope.row, scope.row.userType)"
                disable-transitions>{{userTypeFormat(scope.row, scope.row.userType)}}</el-tag>
            </el-form-item>
            <el-form-item label="用户账号">
              <span>{{ scope.row.userName }}</span>
            </el-form-item>
            <el-form-item label="用户昵称">
              <span>{{ scope.row.nickName }}</span>
            </el-form-item>
            <el-form-item label="手机号码">
              <span>{{ scope.row.phoneNumber }}</span>
            </el-form-item>
            <el-form-item label="用户邮箱">
              <span>{{ scope.row.email }}</span>
            </el-form-item>
            <el-form-item label="用户性别">
              <el-tag
                :type="sexTagClass(scope.row, scope.row.sex)"
                disable-transitions>{{sexFormat(scope.row, scope.row.sex)}}</el-tag>
            </el-form-item>
            <el-form-item label="头像地址">
              <span>{{ scope.row.avatar }}</span>
            </el-form-item>
            <el-form-item label="最后登录IP">
              <span>{{ scope.row.loginIp }}</span>
            </el-form-item>
            <el-form-item label="最后登录时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.loginDate"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.createTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="更新者">
              <span>{{ scope.row.updateBy }}</span>
            </el-form-item>
            <el-form-item label="更新时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.updateTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="真实姓名">
              <span>{{ scope.row.realName }}</span>
            </el-form-item>
            <el-form-item label="生日">
              <el-date-picker 
                class="my-date-shower"
                type="date"
                v-model="scope.row.birthday"
                value-format="yyyy-MM-dd"
                readonly/>
            </el-form-item>
            <el-form-item label="购买次数">
              <span>{{ scope.row.payCount }}</span>
            </el-form-item>
            <el-form-item label="登陆类型">
              <el-tag
                :type="loginTypeTagClass(scope.row, scope.row.loginType)"
                disable-transitions>{{loginTypeFormat(scope.row, scope.row.loginType)}}</el-tag>
            </el-form-item>
            <el-form-item label="备注">
              <span>{{ scope.row.remark }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户昵称" align="center" prop="nickName" :show-overflow-tooltip="true" />
      <el-table-column label="手机号码" align="center" prop="phoneNumber" width="120" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" sortable width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" align="center" prop="realName" />
      <el-table-column label="生日" align="center" prop="birthday" sortable width="140">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="购买次数" align="center" sortable width="120" prop="payCount" />
      <el-table-column
        label="登陆类型"
        align="center"
        prop="loginType"
        :filters="[{ text: '网站', value: 'web' }, { text: '移动端', value: 'app' }]"
        :filter-method="filterLoginType"
        width="100"
        filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.loginType === 'web' ? 'primary' : 'success'"
            disable-transitions>{{loginTypeFormat(scope.row, scope.row.loginType)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mall:user:edit']">
            修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:user:remove']">
            删除
          </el-button>
          <el-button 
            size="mini" 
            type="text" 
            icon="el-icon-key" 
            @click="handleResetPwd(scope.row)"
            v-hasPermi="['mall:user:resetPwd']">
            重置
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="form.userType" placeholder="请选择用户类型">
            <el-option
              v-for="dict in userTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入手机号码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" type="primary" @click="submitForm">确 定</el-button>
        <el-button size="mini" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, exportUser, resetUserPwd, changeUserStatus } from "@/api/mall/user";

export default {
  name: "User",
  data() {
    var validatePhone = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('手机号不能为空'));
      } else if(!(/^1[34578]\d{9}$/.test(value))) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback();
      }
    };
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
      // 用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户类型字典
      userTypeOptions: [],
      // 用户性别字典
      sexOptions: [],
      // 日期范围
      dateRange: [],
      //数值范围
      numRange: '',
      // 帐号状态字典
      statusOptions: [],
      // 删除标志字典
      delFlagOptions: [],
      // 登陆类型字典
      loginTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null,
        phoneNumber: null,
        status: null,
        createTime: null,
        realName: null,
        payCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户账号不能为空", trigger: "blur" }
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        userType: [
          { required: true, message: "用户类型不能为空", trigger: "change" }
        ],
        phoneNumber: [
          {  required: true, validator: validatePhone, trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "帐号状态不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("apm_user_type").then(response => {
      this.userTypeOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("sys_user_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_common_del_flag").then(response => {
      this.delFlagOptions = response.data;
    });
    this.getDicts("sys_user_login_type").then(response => {
      this.loginTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateAndNumRange(this.queryParams, this.dateRange, this.numRange)).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.nickName + '"用户吗?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 用户类型字典翻译
    userTypeFormat(row, column) {
      return this.selectDictLabel(this.userTypeOptions, row.userType);
    },
    // 用户类型字典回显样式
    userTypeTagClass(row, column) {
      return this.selectDictListClass(this.userTypeOptions, row.userType);
    },
    // 根据用户类型筛选数据
    filterUserType(value, row) {
      return row.userType === value;
    },
    // 用户性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 用户性别字典回显样式
    sexTagClass(row, column) {
      return this.selectDictListClass(this.sexOptions, row.sex);
    },
    // 帐号状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 删除标志字典翻译
    delFlagFormat(row, column) {
      return this.selectDictLabel(this.delFlagOptions, row.delFlag);
    },
    // 根据登陆类型筛选数据
    filterLoginType(value, row) {
      return row.loginType === value;
    },
    // 登陆类型字典翻译
    loginTypeFormat(row, column) {
      return this.selectDictLabel(this.loginTypeOptions, row.loginType);
    },
    // 登陆类型字典回显样式
    loginTypeTagClass(row, column) {
      return this.selectDictListClass(this.loginTypeOptions, row.loginType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: null,
        userName: null,
        nickName: null,
        userType: null,
        email: null,
        phoneNumber: null,
        sex: null,
        avatar: null,
        password: null,
        status: "0",
        delFlag: "0",
        loginIp: null,
        loginDate: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        realName: null,
        birthday: null,
        cardId: null,
        integral: null,
        signNum: null,
        level: null,
        payCount: null,
        address: null,
        loginType: null,
        remark: null
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
      this.$refs.filterTable.clearFilter();
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getUser(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户";
      });
    },
     /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.nickName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("修改成功，新密码是：" + value);
            }
          });
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateUser(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUser(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUser(userIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUser(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>