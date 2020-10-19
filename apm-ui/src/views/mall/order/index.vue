<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="订单号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mall:order:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mall:order:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mall:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="filterTable" v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item label="订单号">
              <span>{{ scope.row.orderId }}</span>
            </el-form-item>
            <!-- <el-form-item label="额外订单号">
              <span>{{ scope.row.extendOrderId }}</span>
            </el-form-item> -->
            <el-form-item label="用户id">
              <span>{{ scope.row.userId }}</span>
            </el-form-item>
            <el-form-item label="用户姓名">
              <span>{{ scope.row.realName }}</span>
            </el-form-item>
            <el-form-item label="用户电话">
              <span>{{ scope.row.userPhone }}</span>
            </el-form-item>
            <el-form-item label="详细地址">
              <span>{{ scope.row.userAddress }}</span>
            </el-form-item>
            <el-form-item label="购物车id">
              <span>{{ scope.row.cartId }}</span>
            </el-form-item>
            <el-form-item label="运费金额">
              <span>{{ scope.row.freightPrice }}</span>
            </el-form-item>
            <el-form-item label="订单商品总数">
              <span>{{ scope.row.totalNum }}</span>
            </el-form-item>
            <el-form-item label="订单总价">
              <span>{{ scope.row.totalPrice }}</span>
            </el-form-item>
            <el-form-item label="邮费">
              <span>{{ scope.row.totalPostage }}</span>
            </el-form-item>
            <el-form-item label="实际支付金额">
              <span>{{ scope.row.payPrice }}</span>
            </el-form-item>
            <el-form-item label="支付邮费">
              <span>{{ scope.row.payPostage }}</span>
            </el-form-item>
            <el-form-item label="支付时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.payTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.addTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="退款图片">
              <span>{{ scope.row.refundReasonWapImg }}</span>
            </el-form-item>
            <el-form-item label="退款时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.refundReasonTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="退款原因">
              <span>{{ scope.row.refundReasonWap }}</span>
            </el-form-item>
            <el-form-item label="退款金额">
              <span>{{ scope.row.refundPrice }}</span>
            </el-form-item>
            <el-form-item label="快递公司编号">
              <span>{{ scope.row.deliverySn }}</span>
            </el-form-item>
            <el-form-item label="快递单号">
              <span>{{ scope.row.deliveryId }}</span>
            </el-form-item>
            <el-form-item label="备注" :show-overflow-tooltip="true">
              <span>{{ scope.row.mark }}</span>
            </el-form-item>
            <el-form-item label="管理员备注" :show-overflow-tooltip="true">
              <span>{{ scope.row.remark }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="订单ID" align="center" prop="id" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="订单商品总数" align="center" prop="totalNum" />
      <el-table-column label="订单总价" align="center" width="100" prop="totalPrice" />
      <el-table-column
        label="支付状态"
        align="center"
        prop="paid"
        :filters="[{ text: '已支付', value: 0 }, { text: '未支付', value: 1 }]"
        :filter-method="filterPaid"
        width="100"
        filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
            :type="paidTagClass(scope.row, scope.row.paid)"
            disable-transitions>{{paidFormat(scope.row, scope.row.paid)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="订单状态"
        align="center"
        prop="status"
        :filters="[{ text: '申请退款', value: -1 }, { text: '退货成功', value: -2 }, { text: '待发货', value: 0 }, { text: '待收货', value: 1 }, { text: '已收货', value: 2 }, { text: '待评价', value: 3 }]"
        :filter-method="filterStatus"
        width="100"
        filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
            :type="statusTagClass(scope.row, scope.row.status)"
            disable-transitions>{{statusFormat(scope.row, scope.row.status)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="退货状态"
        align="center"
        prop="refundStatus"
        :filters="[{ text: '未退款', value: 0 }, { text: '申请中', value: 1 }, { text: '已退款', value: 2 }]"
        :filter-method="filterRefundStatus"
        width="100"
        filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
            :type="refundStatusTagClass(scope.row, scope.row.refundStatus)"
            disable-transitions>{{refundStatusFormat(scope.row, scope.row.refundStatus)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" :formatter="dateFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mall:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:order:remove']"
          >删除</el-button>
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

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="订单号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单号" disabled />
        </el-form-item>
        <!-- <el-form-item label="额外订单号" prop="extendOrderId">
          <el-input v-model="form.extendOrderId" placeholder="请输入额外订单号" />
        </el-form-item> -->
        <el-form-item label="用户电话" prop="userPhone">
          <el-input v-model="form.userPhone" placeholder="请输入用户电话" />
        </el-form-item>
        <el-form-item label="详细地址" prop="userAddress">
          <el-input v-model="form.userAddress" placeholder="请输入详细地址" />
        </el-form-item>
        <!-- <el-form-item label="抵扣金额" prop="deductionPrice">
          <el-input v-model="form.deductionPrice" placeholder="请输入抵扣金额" />
        </el-form-item>-->
        <!-- <el-form-item label="优惠券id" prop="couponId">
          <el-input v-model="form.couponId" placeholder="请输入优惠券id" />
        </el-form-item>
        <el-form-item label="优惠券金额" prop="couponPrice">
          <el-input v-model="form.couponPrice" placeholder="请输入优惠券金额" />
        </el-form-item>-->
        <!-- <el-form-item label="支付方式" prop="payType">
          <el-select v-model="form.payType" placeholder="请选择支付方式">
            <el-option label="请选择字典生成" value />
          </el-select>
        </el-form-item>-->
        <el-form-item label="订单状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择订单状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="退货状态" prop="refundStatus">
            <el-select v-model="form.refundStatus" placeholder="请选择退货状态">
              <el-option
                v-for="dict in refundStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="退款图片" prop="refundReasonWapImg">
          <el-input v-model="form.refundReasonWapImg" placeholder="请输入退款图片" />
        </el-form-item>
        <el-form-item label="退款用户说明" prop="refundReasonWapExplain">
          <el-input v-model="form.refundReasonWapExplain" placeholder="请输入退款用户说明" />
        </el-form-item>
        <el-form-item label="不退款的理由" prop="refundReason">
          <el-input v-model="form.refundReason" placeholder="请输入不退款的理由" />
        </el-form-item>
        <el-form-item label="退款金额" prop="refundPrice">
          <el-input v-model="form.refundPrice" placeholder="请输入退款金额" />
        </el-form-item>
        <el-form-item label="快递公司编号" prop="deliverySn">
          <el-input v-model="form.deliverySn" placeholder="请输入快递公司编号" />
        </el-form-item>
        <el-form-item label="快递名称" prop="deliveryName">
          <el-input v-model="form.deliveryName" placeholder="请输入快递名称/送货人姓名" />
        </el-form-item>
        <!-- <el-form-item label="发货类型" prop="deliveryType">
          <el-select v-model="form.deliveryType" placeholder="请选择发货类型">
            <el-option label="请选择字典生成" value />
          </el-select>
        </el-form-item>-->
        <el-form-item label="快递单号" prop="deliveryId">
          <el-input v-model="form.deliveryId" placeholder="请输入快递单号/手机号" />
        </el-form-item>
        <!-- <el-form-item label="消费赚取积分" prop="gainIntegral">
          <el-input v-model="form.gainIntegral" placeholder="请输入消费赚取积分" />
        </el-form-item>
        <el-form-item label="使用积分" prop="useIntegral">
          <el-input v-model="form.useIntegral" placeholder="请输入使用积分" />
        </el-form-item>-->
        <!-- <el-form-item label="给用户退了多少积分" prop="backIntegral">
          <el-input v-model="form.backIntegral" placeholder="请输入给用户退了多少积分" />
        </el-form-item>-->
        <!-- <el-form-item label="唯一id(md5加密)类似id" prop="unique">
          <el-input v-model="form.unique" placeholder="请输入唯一id(md5加密)类似id" />
        </el-form-item>-->
        <el-form-item label="管理员备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="管理员备注" prop="isMerCheck">
          <el-input v-model="form.isMerCheck" placeholder="请输入管理员备注" />
        </el-form-item>-->
        <!-- <el-form-item label="成本价" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入成本价" />
        </el-form-item>
        <el-form-item label="核销码" prop="verifyCode">
          <el-input v-model="form.verifyCode" placeholder="请输入核销码" />
        </el-form-item>
        <el-form-item label="门店id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入门店id" />
        </el-form-item>-->
        <!-- <el-form-item label="配送方式 1=快递 ，2=门店自提" prop="shippingType">
          <el-select v-model="form.shippingType" placeholder="请选择配送方式 1=快递 ，2=门店自提">
            <el-option label="请选择字典生成" value />
          </el-select>
        </el-form-item>-->
        <!-- <el-form-item label="支付渠道(0微信公众号1微信小程序)" prop="isChannel">
          <el-input v-model="form.isChannel" placeholder="请输入支付渠道(0微信公众号1微信小程序)" />
        </el-form-item>
        <el-form-item label="支付渠道(0微信公众号1微信小程序)" prop="isRemind">
          <el-input v-model="form.isRemind" placeholder="请输入支付渠道(0微信公众号1微信小程序)" />
        </el-form-item>
        <el-form-item label="支付渠道(0微信公众号1微信小程序)" prop="isSystemDel">
          <el-input v-model="form.isSystemDel" placeholder="请输入支付渠道(0微信公众号1微信小程序)" />
        </el-form-item>-->
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
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
  exportOrder
} from "@/api/mall/order";

export default {
  name: "order",
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
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 订单支付状态字典
      paidOptions: [],
      // 订单状态字典
      statusOptions: [],
      // 订单状态字典
      refundStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null
      },
      // 表单参数
      form: {},

      formorder: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("pay_status").then(response => {
      this.paidOptions = response.data;
    });
    this.getDicts("order_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("order_refund_status").then(response => {
      this.refundStatusOptions = response.data;
    });
  },
  methods: {
    //时间格式化
    dateFormat(row, column) {
      let data = row[column.property];
      console.log(data);
      let dt = new Date(data);
      return (
        dt.getFullYear() +
        "-" +
        (dt.getMonth() + 1) +
        "-" +
        dt.getDate() +
        " " +
        dt.getHours() +
        ":" +
        dt.getMinutes() +
        ":" +
        dt.getSeconds()
      );
    },

    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //根据订单状态筛选数据
    filterStatus(value, row) {
      return row.status === value;
    },
    // 订单状态字典回显样式
    statusTagClass(row, column) {
      return this.selectDictListClass(this.statusOptions, row.status);
    },
    // 订单状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 根据支付状态筛选数据
    filterPaid(value, row) {
      return row.paid === value;
    },
    // 支付状态字典回显样式
    paidTagClass(row, column) {
      return this.selectDictListClass(this.paidOptions, row.paid);
    },
    // 支付状态字典翻译
    paidFormat(row, column) {
      return this.selectDictLabel(this.paidOptions, row.paid);
    },
    // 根据退货状态筛选数据
    filterRefundStatus(value, row) {
      return row.refundStatus === value;
    },
    // 订单状态字典回显样式
    refundStatusTagClass(row, column) {
      return this.selectDictListClass(this.refundStatusOptions, row.refundStatus);
    },
    // 订单状态字典翻译
    refundStatusFormat(row, column) {
      return this.selectDictLabel(this.refundStatusOptions, row.refundStatus);
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
        orderId: null,
        extendOrderId: null,
        userId: null,
        realName: null,
        userPhone: null,
        userAddress: null,
        cartId: null,
        freightPrice: null,
        totalNum: null,
        totalPrice: null,
        totalPostage: null,
        payPrice: null,
        payPostage: null,
        deductionPrice: null,
        couponId: null,
        couponPrice: null,
        paid: null,
        payTime: null,
        payType: null,
        addTime: null,
        status: 0,
        refundStatus: 0,
        refundReasonWapImg: null,
        refundReasonWapExplain: null,
        refundReasonTime: null,
        refundReasonWap: null,
        refundReason: null,
        refundPrice: null,
        deliverySn: null,
        deliveryName: null,
        deliveryType: null,
        deliveryId: null,
        gainIntegral: null,
        useIntegral: null,
        backIntegral: null,
        mark: null,
        isDel: null,
        unique: null,
        remark: null,
        isMerCheck: null,
        cost: null,
        verifyCode: null,
        storeId: null,
        shippingType: null,
        isChannel: null,
        isRemind: null,
        isSystemDel: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOrder(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除订单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delOrder(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有订单数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportOrder(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>