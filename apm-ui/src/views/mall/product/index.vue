<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="商品类型" prop="category">
        <el-select
          v-model="queryParams.category"
          placeholder="请选择商品类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="dict in categoryOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品分类" prop="cateId">
         <el-select
          v-model="queryParams.cateId"
          placeholder="请选择商品分类"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        >
          <el-option
            v-for="cate in classOptions"
            :key="cate.id"
            :label="cate.cateName"
            :value="cate.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品名称" prop="storeName">
        <el-input
          v-model="queryParams.storeName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品价格" prop="price">
        <el-input 
          placeholder="请输入数值范围以'-'分割"
          v-model="numRange" 
          style="width: 190px" 
          clearable
          size="small"
          onkeyup="this.value=this.value.replace(/[^\d\-]/g,'')"
          @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="添加时间" prop="addTime">
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
          v-hasPermi="['mall:product:add']"
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
          v-hasPermi="['mall:product:edit']"
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
          v-hasPermi="['mall:product:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:product:export']"
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
      :data="productList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="展开" type="expand" width="55">
        <template slot-scope="scope">
          <el-form label-position="right" inline class="demo-table-expand" label-width="100px">
            <el-form-item label="商品ID">
              <span>{{ scope.row.id }}</span>
            </el-form-item>
            <el-form-item label="商品类型">
              <el-tag
                size="small"
                :type="categoryClass(scope.row, scope.row.category)"
                disable-transitions>{{categoryFormat(scope.row, scope.row.category)}}</el-tag>
            </el-form-item>
            <el-form-item label="商品分类">
              <span>{{ scope.row.cateName }}</span>
            </el-form-item>
            <el-form-item label="商品图片">
              <el-image :src="scope.row.image"
                style="width: 30px;text-align: center;"
                title="点击预览该商品所有图片"
                fit="fill"
                :preview-src-list="previewSrc(scope.row)">
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
            <el-form-item label="轮播图">
              <el-image :src="scope.row.sliderImage"
                style="width: 30px;text-align: center;"
                title="点击预览该商品所有图片"
                fit="fill"
                :preview-src-list="previewSrc(scope.row)">
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
            <el-form-item label="二维码">
              <el-image :src="scope.row.codePath"
                style="width: 30px;text-align: center;"
                title="点击预览该商品所有图片"
                fit="fill"
                :preview-src-list="previewSrc(scope.row)">
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
            <el-form-item label="商品名称">
              <span>{{ scope.row.storeName }}</span>
            </el-form-item>
            <el-form-item label="商品简介">
              <span>{{ scope.row.storeInfo }}</span>
            </el-form-item>
            <el-form-item label="关键字">
              <span>{{ scope.row.keyword }}</span>
            </el-form-item>
            <el-form-item label="产品条码">
              <span>{{ scope.row.barCode }}</span>
            </el-form-item>
            <el-form-item label="商品价格">
              <span>{{ scope.row.price }}</span>
            </el-form-item>
            <el-form-item label="市场价">
              <span>{{ scope.row.otPrice }}</span>
            </el-form-item>
            <el-form-item label="成本价">
              <span>{{ scope.row.cost }}</span>
            </el-form-item>
            <el-form-item label="邮费">
              <span>{{ scope.row.postage }}</span>
            </el-form-item>
            <el-form-item label="销量">
              <span>{{ scope.row.sales }}</span>
            </el-form-item>
            <el-form-item label="虚拟销量">
              <span>{{ scope.row.ficti }}</span>
            </el-form-item>
            <el-form-item label="库存">
              <span>{{ scope.row.stock }}</span>
            </el-form-item>
            <el-form-item label="状态">
              <el-tag
                size="small"
                :type="isShowClass(scope.row, scope.row.isShow)"
                disable-transitions>{{isShowFormat(scope.row, scope.row.isShow)}}</el-tag>
            </el-form-item>
            <el-form-item label="是否新品">
              <el-tag
                size="small"
                :type="isNewClass(scope.row, scope.row.isNew)"
                disable-transitions>{{isNewFormat(scope.row, scope.row.isNew)}}</el-tag>
            </el-form-item>
            <el-form-item label="是否包邮">
              <el-tag
                size="small"
                :type="isPostageClass(scope.row, scope.row.isPostage)"
                disable-transitions>{{isPostageFormat(scope.row, scope.row.isPostage)}}</el-tag>
            </el-form-item>
            <el-form-item label="是否代理">
              <el-tag
                size="small"
                :type="merUseClass(scope.row, scope.row.merUse)"
                disable-transitions>{{merUseFormat(scope.row, scope.row.merUse)}}</el-tag>
            </el-form-item>
            <el-form-item label="添加时间">
              <el-date-picker 
                class="my-date-shower"
                type="datetime"
                v-model="scope.row.addTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                readonly/>
            </el-form-item>
            <el-form-item label="产品描述">
              <div v-html="scope.row.description"></div>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="商品ID" align="center" prop="id" />
      <el-table-column
        label="商品类型"
        align="center"
        prop="category"
        width="100"
        :formatter="categoryFormat"
      />
      <el-table-column label="商品分类" align="center" width="120" :show-overflow-tooltip="true" prop="cateName" />
      <el-table-column label="商品名称" align="center" width="120" :show-overflow-tooltip="true" prop="storeName" />
      <el-table-column label="商品图片" align="center" prop="image">
        <template slot-scope="scope">
          <a :href="scope.row.image" target="_blank">
            <el-image :src="scope.row.image"
              class="img-square"
              style="width: 30px;text-align: center;"
              fit="fill">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </a>
        </template> 
      </el-table-column>
      <el-table-column label="轮播图" align="center" prop="sliderImage">
        <template slot-scope="scope">
          <a :href="scope.row.sliderImage" target="_blank">
            <el-image :src="scope.row.sliderImage"
              class="img-square"
              style="width: 30px;text-align: center;"
              fit="fill">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </a>
        </template>
      </el-table-column>
      <el-table-column label="浏览量" align="center" sortable prop="browse" />
      <el-table-column
        label="添加时间"
        align="center"
        prop="addTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.addTime) }}</span>
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
            v-hasPermi="['mall:product:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:product:remove']"
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="商品类型" prop="category">
              <el-select v-model="form.category" placeholder="请选择商品类型">
                <el-option
                  v-for="dict in categoryOptions"
                  :key="dict.dictCode"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品分类" prop="cateId">
              <el-select
                v-model="form.cateId"
                placeholder="请选择商品分类"
                clearable
                @keyup.enter.native="handleQuery"
              >
                <el-option
                  v-for="cate in classOptions"
                  :key="cate.id"
                  :label="cate.cateName"
                  :value="cate.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商户" prop="merId">
              <el-select v-model="form.merId" placeholder="请选择商户" class="full-select">
                <el-option
                  v-for="dict in merIdOptions"
                  :key="dict.dictCode"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品名称" prop="storeName">
              <el-input v-model="form.storeName" placeholder="请输入商品名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品条码" prop="barCode">
              <el-input v-model="form.barCode" placeholder="请输入产品条码" />
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="商品图片" prop="image">
              <el-input v-model="form.image"
                type="url"
                title="请输入URL"
                placeholder="请输入商品图片地址" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="上传预览" prop="image">
              <el-image :src="form.image"
                @click="editCropper(form.image, 0)"
                title="点击上传图片" 
                class="img-square"
                style="width: 40px;text-align: center;"
                fit="fill">
                <div slot="error" title="点击上传图片" @click="editCropper(form.image, 0)" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="轮播图" prop="sliderImage">
              <el-input v-model="form.sliderImage"
                type="url"
                title="请输入URL"
                placeholder="请输入轮播图地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="上传预览" prop="sliderImage">
              <el-image :src="form.sliderImage"
                @click="editCropper(form.sliderImage, 1)"
                title="点击上传图片" 
                class="img-square"
                style="width: 40px;text-align: center;"
                fit="fill">
                <div slot="error" title="点击上传图片" @click="editCropper(form.sliderImage, 1)" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="二维码" prop="codePath">
              <el-input
                v-model="form.codePath"
                type="url"
                title="请输入URL"
                placeholder="请输入产品二维码地址"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="上传预览" prop="codePath">
              <el-image :src="form.codePath"
                @click="editCropper(form.codePath, 2)"
                title="点击上传图片" 
                class="img-square"
                style="width: 40px;text-align: center;"
                fit="fill">
                <div slot="error" title="点击上传图片" @click="editCropper(form.codePath, 2)" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="商品简介" prop="storeInfo">
              <el-input v-model="form.storeInfo" type="textarea" placeholder="请输入商品简介" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关键字" prop="keyword">
              <el-input v-model="form.keyword" placeholder="请输入关键字" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品价格" prop="price">
              <el-input-number v-model="form.price" :precision="4" :step="0.1" placeholder="请输入商品价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="市场价" prop="otPrice">
              <el-input-number v-model="form.otPrice" :precision="4" :step="0.1" placeholder="请输入市场价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮费" prop="postage">
              <el-input-number v-model="form.postage" :precision="4" :step="0.1" :max="100" placeholder="请输入邮费" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" placeholder="请输入排序" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存" prop="stock">
              <el-input-number v-model="form.stock" placeholder="请输入库存" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.isShow">
                <el-radio
                  v-for="dict in isShowOptions"
                  :key="dict.dictCode"
                  :label="parseInt(dict.dictValue)"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否新品">
              <el-radio-group v-model="form.isNew">
                <el-radio
                  v-for="dict in isNewOptions"
                  :key="dict.dictCode"
                  :label="parseInt(dict.dictValue)"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产品描述">
              <editor v-model="form.description" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否包邮">
              <el-radio-group v-model="form.isPostage">
                <el-radio
                  v-for="dict in isPostageOptions"
                  :key="dict.dictCode"
                  :label="parseInt(dict.dictValue)"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否代理">
              <el-radio-group v-model="form.merUse">
                <el-radio
                  v-for="dict in merUseOptions"
                  :key="dict.dictCode"
                  :label="parseInt(dict.dictValue)"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成本价" prop="cost">
              <el-input-number :precision="4" :step="0.1" v-model="form.cost" placeholder="请输入成本价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="虚拟销量" prop="ficti">
              <el-input-number v-model="form.ficti" placeholder="请输入虚拟销量" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="openUpload" width="800px" append-to-body @opened="modalOpened">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '360px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '360px'}">
          <div class="image-upload-preview img-square">
            <img :src="previews.url" class="img-bg" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              上传
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="openSliderUpload" width="400px" append-to-body>
      <el-row>
        <el-col :lg="18" :md="24">
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="requestUpload"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :before-upload="beforeUpload"
            multiple
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList">
              <el-button size="small" type="primary">
                点击选择图片
                <i class="el-icon-upload el-icon--right"></i>
              </el-button>
              <div slot="tip" class="el-upload__tip">只能上传图片文件，且不超过50M</div>
          </el-upload>
         </el-col>
         <el-col :lg="{span: 2, offset: 2}" :md="24">
          <el-button type="primary" size="small" @click="uploadSliderImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProduct,
  getProduct,
  delProduct,
  addProduct,
  updateProduct,
  exportProduct,
  imageUpload
} from "@/api/mall/product";
import Editor from '@/components/Editor';
import { secondList } from "@/api/mall/category";
import { VueCropper } from "vue-cropper";

export default {
  name: "Product",
  components: {
    Editor,
    VueCropper,
  },
  data() {
    var validateUrl = (rule, value, callback) => {
      if (value === '') {
        callback();
      } else if(!(/^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/.test(value))) {
        callback(new Error('请输入正确的URL'))
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
      // 商品表格数据
      productList: [],
      // 日期范围
      dateRange: [],
      //数值范围
      numRange: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示上传图片弹出层
      openUpload: false,
      // 轮播图文件列表
      fileList: [],
      // 是否显示上传轮播图的弹出层
      openSliderUpload: false,
      // 是否显示cropper
      visible: false,
      //上传图片类型(0:商品简介图，1:轮播图)
      imageType: 0,
      // 弹出层标题
      options: {
        img: null, //裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 400, // 默认生成截图框宽度
        autoCropHeight: 400, // 默认生成截图框高度
        fixedBox: false // 固定截图框大小 不允许改变
      },
      previews: {},
      // 商户Id字典
      merIdOptions: [],
      // 商品类型字典
      categoryOptions: [],
      // 商品分类列表
      classOptions: [],
      // 状态字典
      isShowOptions: [],
      // 是否新品字典
      isNewOptions: [],
      // 是否包邮字典
      isPostageOptions: [],
      // 是否删除字典
      isDelOptions: [],
      // 是否代理(0不可代理 1可代理)字典
      merUseOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        category: null,
        storeName: null,
        cateId: null,
        price: null,
        sales: null,
        stock: null,
        addTime: null
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        merId: [
          { required: true, message: "商户不能为空", trigger: "change" },
        ],
        category: [
          { required: true, message: "商品类型不能为空", trigger: "change" },
        ],
        barCode: [
          { required: true, message: "产品条码不能为空", trigger: "blur" },
        ],
        image: [
          { required: true, validator: validateUrl, message: "商品图片不能为空", trigger: "blur" },
        ],
        storeName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" },
        ],
        cateId: [
          { required: true, message: "商品分类不能为空", trigger: "blur" },
        ],
        price: [
          { required: true, message: "商品价格不能为空", trigger: "blur" },
        ],
        postage: [{ required: true, message: "邮费不能为空", trigger: "blur" }],
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        stock: [{ required: true, message: "库存不能为空", trigger: "blur" }],
        merUse: [
          {
            required: true,
            message: "是否代理不能为空",
            trigger: "blur",
          },
        ],
        cost: [{ required: true, message: "成本价不能为空", trigger: "blur" }],
        sliderImage: [
          {  validator: validateUrl, trigger: "blur" }
        ],
        codePath: [
          {  validator: validateUrl, trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getSecondList();
    this.getDicts("apm_store_product_mer_id").then((response) => {
      this.merIdOptions = response.data;
    });
    this.getDicts("apm_store_product_category").then((response) => {
      this.categoryOptions = response.data;
    });
    this.getDicts("apm_store_is_show").then((response) => {
      this.isShowOptions = response.data;
    });
    this.getDicts("apm_store_product_is _new").then((response) => {
      this.isNewOptions = response.data;
    });
    this.getDicts("apm_store_product_is_postage").then((response) => {
      this.isPostageOptions = response.data;
    });
    this.getDicts("sys_user_del_flag").then((response) => {
      this.isDelOptions = response.data;
    });
    this.getDicts("apm_store_product_mer_use").then((response) => {
      this.merUseOptions = response.data;
    });
  },
  methods: {
    /** 查询商品列表 */
    getList() {
      this.loading = true;
      listProduct(this.addDateAndNumRange(this.queryParams, this.dateRange, this.numRange)).then((response) => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询商品分类二级列表 */
    getSecondList() {
      secondList().then((response) => {
        this.classOptions = response.data;
      });
    },
    // 商户Id字典翻译
    merIdFormat(row, column) {
      return this.selectDictLabel(this.merIdOptions, row.merId);
    },
    // 商品类型字典翻译
    categoryFormat(row, column) {
      return this.selectDictLabel(this.categoryOptions, row.category);
    },
    // 商品类型字典回显样式
    categoryClass(row, column) {
      return this.selectDictListClass(this.categoryOptions, row.category);
    },
    // 状态字典翻译
    isShowFormat(row, column) {
      return this.selectDictLabel(this.isShowOptions, row.isShow);
    },
    // 状态字典回显样式
    isShowClass(row, column) {
      return this.selectDictListClass(this.isShowOptions, row.isShow);
    },
    // 是否新品字典翻译
    isNewFormat(row, column) {
      return this.selectDictLabel(this.isNewOptions, row.isNew);
    },
    // 是否新品字典回显样式
    isNewClass(row, column) {
      return this.selectDictListClass(this.isNewOptions, row.isNew);
    },
    // 是否包邮字典翻译
    isPostageFormat(row, column) {
      return this.selectDictLabel(this.isPostageOptions, row.isPostage);
    },
    // 是否包邮字典回显样式
    isPostageClass(row, column) {
      return this.selectDictListClass(this.isPostageOptions, row.isPostage);
    },
    // 是否删除字典翻译
    isDelFormat(row, column) {
      return this.selectDictLabel(this.isDelOptions, row.isDel);
    },
    // 是否删除字典回显样式
    isDelClass(row, column) {
      return this.selectDictListClass(this.isDelOptions, row.isDel);
    },
    // 是否代理(0不可代理 1可代理)字典翻译
    merUseFormat(row, column) {
      return this.selectDictLabel(this.merUseOptions, row.merUse);
    },
    // 是否代理字典回显样式
    merUseClass(row, column) {
      return this.selectDictListClass(this.merUseOptions, row.merUse);
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
        merId: null,
        category: null,
        image: null,
        sliderImage: null,
        storeName: null,
        storeInfo: null,
        keyword: null,
        barCode: null,
        cateId: null,
        price: null,
        vipPrice: null,
        otPrice: null,
        postage: null,
        unitName: null,
        sort: null,
        sales: null,
        stock: null,
        isShow: 0,
        isHot: null,
        isBenefit: null,
        isBest: null,
        isNew: 0,
        description: null,
        addTime: null,
        isPostage: 0,
        isDel: 0,
        merUse: 0,
        giveIntegral: null,
        cost: null,
        isGood: null,
        ficti: null,
        browse: null,
        codePath: null,
        soureLink: null,
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
      this.title = "添加商品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getProduct(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addProduct(this.form).then((response) => {
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
      this.$confirm('是否确认删除商品编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delProduct(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有商品数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportProduct(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    previewSrc(row) {
      let srcList = [];
      srcList.push(row.image);
      if (row.sliderImage != null && row.sliderImage != "") {
        srcList.push(row.sliderImage);
      }
      if (row.codePath != null && row.codePath != "") {
        srcList.push(row.codePath);
      }
      return srcList;
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    handleRemove(file) {
      console.log(file);
    },
    handleExceed(files) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件`);
    },
    //打开上传框之前的预处理
    beforeEdit(image, type, callback) {
      this.imageType = type;
      if (image != null && image != "") {
        this.options.img = image;
      } else {
        this.options.img = "/api/profile/default/default.png"
      }
      this.title = "上传图片";
      callback();
    },
    // 上传轮播图的弹出层
    editUpload(image, type) {
      this.beforeEdit(image, type, () => {
        this.openSliderUpload = true;
      });
    },
    // 编辑商品图片
    editCropper(image, type) {
      this.beforeEdit(image, type, () => {
        this.openUpload = true;
      });
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
        this.fileList = [];
      } else {
        if (this.imageType === 1) {
          this.fileList.push(file);
          console.log(this.fileList);
        }
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    appendForm(data) {
      let formData = new FormData();
      let oldUrl = null;
      if (this.imageType === 0) {
        oldUrl = this.form.image;
      } else if (this.imageType === 1) {
        oldUrl = this.form.sliderImage;
      } else if (this.imageType === 2) {
        oldUrl = this.form.codePath;
      }
      formData.append("oldUrl", oldUrl );
      formData.append("image", data);
      return formData;
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        let formData = this.appendForm(data);
        imageUpload(formData).then(response => {
          if (response.code === 200) {
            this.openUpload = false;
            this.options.img = response.imgUrl;
            if (this.imageType === 0) {
              this.form.image = this.options.img;
            } else if (this.imageType === 2) {
              this.form.codePath = this.options.img;
            }
            this.msgSuccess("上传成功");
          }
          this.visible = false;
        });
      });
    },
    uploadSliderImg() {
      let formData = this.appendForm(this.fileList[0]);
      imageUpload(formData).then(response => {
        if (response.code === 200) {
          this.openSliderUpload = false;
          this.fileList = [];
          this.options.img = response.imgUrl;
          if (this.imageType === 1) {
            this.form.sliderImage = this.options.img;
          }
          this.msgSuccess("上传成功");
        }
      });
    },
    // 实时预览
    realTime(data) {
      this.previews = data;
    }
  },
};
</script>

<style scoped>
  .el-select.full-select.el-select--medium {
    width: 100% !important;
  }
  .my-upload-btn.el-button--small {
    padding: 10px 5px !important;
    font-size: 10px !important;
  }
  .upload-demo {
    margin-bottom: 10px;
  }
</style>