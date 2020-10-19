<template>
    <div style="width: 100%">
        <div style="width: 80%;margin-left: 10%;">
            <div style="width: 60%;margin-left: 20%;padding-top: 50px">
                <el-steps :active="activeStep" finish-status="success">
                    <el-step title="拍下商品"></el-step>
                    <el-step title="完成支付"></el-step>
                    <!--                    <el-step title="收货评价"></el-step>-->
                </el-steps>
            </div>
            <div style="width: 90%;margin-left: 5%;margin-top: 20px">
                <h4>请选择收货地址</h4>
                <div style="margin-top: 15px">
                    <div v-for="(item, index) in address"
                         style="display: inline-block;position: relative;">
                        <div :class="{active:isActive == index}" @click="checked(index)"
                             style="cursor: pointer;padding: 5px;margin-left: 30px;border: 3px solid #1D8CE0;width: 200px;height: 80px;margin-top: 30px;overflow: hidden">
                            <span style="display: block;width: 200px;line-height: 18px;font-weight: 700;color: #666;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">{{item.province}}&nbsp;{{item.city}}&nbsp;({{item.realName}}收)</span>
                            <el-divider class="h"></el-divider>
                            <div style="font-size: 14px">
                                <span>{{item.district}}&nbsp;</span>
                                <span>{{item.detail}}&nbsp;</span>
                                <span>{{item.phone}}&nbsp;</span>
                            </div>
                            <img :class="{imgActive:ia ==index}" src="../assets/1.png"
                                 style="visibility: hidden;position: absolute;bottom: 0px;right: 0px">
                        </div>
                    </div>
                </div>
                <div style="display: flex;justify-content: space-between;margin-top: 30px">
                    <div>
                        <span @click="dialogFormVisible = true" style="color: #ff6600">
                              使用新地址
                        </span>
                        <el-dialog title="收货地址" :visible.sync="dialogFormVisible" center lock-scroll
                                   width="50%" style="margin-top: -70px">
                            <el-form :rules="rules" ref="addressForm" :model="addressForm" style="margin-left: 25%">
                                <el-form-item label="地址信息:" prop="address" label-width="100px">
                                    <el-cascader style="width: 60%;" :options="options"
                                                 v-model="addressForm.address"
                                                 @change="handleChange">
                                    </el-cascader>
                                </el-form-item>
                                <el-form-item label="详细地址:" prop="detail"
                                              label-width="100px">
                                     <textarea placeholder="请输入详细地址" rows="2"
                                               v-model="addressForm.detail"
                                               style="color: #606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;resize: vertical"></textarea>
                                </el-form-item>
                                <el-form-item label="邮编:" label-position="left" label-width="100px">
                                    <input placeholder="请输入邮编" v-model="addressForm.postCode"
                                           style="color: #606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                                <el-form-item label="收货人姓名:" label-position="left"
                                              label-width="100px"
                                              prop="realName">
                                    <input placeholder="请输入收货人姓名" v-model="addressForm.realName"
                                           style="color:#606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                                <el-form-item label="手机号码:" label-position="left"
                                              label-width="100px"
                                              prop="phone">
                                    <input placeholder="请输入收货人联系方式" v-model="addressForm.phone"
                                           style="color:#606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                                <!--                                <el-form-item label="是否设为默认地址：">-->
                                <!--                                    <el-checkbox></el-checkbox>-->
                                <!--                                </el-form-item>-->
                            </el-form>
                            <div slot="footer" class="dialog-footer btns">
                                <button class="btn1" type="button" @click="submitForm('addressForm')"
                                        style="border: 1px solid deeppink">立即创建
                                </button>
                            </div>
                        </el-dialog>
                    </div>
                    <div>
                        <router-link :to="{name:'ManagementAddress'}">
                                <span style="color: #ff6600">
                                    管理收货地址
                                </span>
                        </router-link>
                    </div>
                </div>
                <el-divider></el-divider>
                <h4>请选订单信息</h4>
                <div>
                    <el-table border :data="goodsTable" height="400px" default-expand-all>
                        <el-table-column type="expand">
                            <template slot-scope="props">
                                <el-form label-position="left" inline class="demo-table-expand">
                                    <el-form-item label="配送方式">
                                        <div style="width: 900px;display: flex;justify-content: space-between;margin-top: 30px;margin-left: 30px">
                                            <div>
                                                <span>{{props.row.goods.peisong.peisongfangshi }}</span>
                                            </div>
                                            <div>
                                                <span>邮费：</span>
                                                <span style="font-size: 18px;color: #FF0044"><strong>{{props.row.goods.peisong.peisongjiage }}</strong></span>
                                                <small>元</small>
                                            </div>
                                            <div style="float: right">
                                                <button class="placeOrder" style="margin-top: 60px"
                                                        @click="placeOrder">提交订单
                                                </button>
                                            </div>
                                        </div>
                                    </el-form-item>
                                </el-form>
                            </template>
                        </el-table-column>
                        <el-table-column prop="goods" label="店铺宝贝" width="200px">
                            <template scope="scope">
                                <img :src="scope.row.goods.Imglink" style="width: 80px;float: left">
                                <span style="float: left;color: #ff6600;display: inline-block;overflow: hidden;"><strong>{{scope.row.goods.name}}</strong></span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="goods.attribute" label="属性">
                            <template scope="scope">
                                <span v-for="(v) in scope.row.goods.attribute">
                                    <p v-for="(val,key) in v">
                                        {{key}}:{{val}}
                                    </p>
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="goods.price" label="单价">
                            <template scope="scope">
                                <small style="color:#909399;display: inline-block;margin-right: 5px">￥</small>
                                <span style="font-size: 18px;color: #FF0044"><strong>{{scope.row.goods.price}}</strong></span>
                                <small style="display: inline-block;margin-left: 5px;color: #99a9bf">元</small>
                            </template>
                        </el-table-column>
                        <el-table-column prop="count" label="数量">
                            <template scope="scope">
                                <div class="icon iconfont icon-icon_cut"
                                     style="padding: 5px 5px;height: 32px;color: #1D8CE0;display: inline-block;border: 1px solid #99a9bf;float: left;"
                                     @click="reducegood(scope.row)"></div>
                                <input type="text" :value="scope.row.count" disabled
                                       style="height: 30px;line-height: 30px;border-radius: 0px;padding: 0 !important;float: left;border:1px solid #99a9bf;background-color: transparent;width: 25px;text-align: center;display: inline-block">
                                <div class="icon iconfont icon-jia"
                                     style="padding: 5px 5px;height: 32px;float: left;color:#1D8CE0;;font-size: 16px;display: inline-block;border: 1px solid #99a9bf;"
                                     @click="addgood(scope.row)"></div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="subtotal" label="小计" fixed="right">
                            <template scope="scope">
                                <small style="color:#909399;display: inline-block;margin-right: 5px">￥</small>

                                <span style="font-size: 18px;color: #FF0044"><strong>
                                {{scope.row.goods.price*scope.row.count+scope.row.goods.peisong.peisongjiage}}</strong>
                                </span>
                                <small style="display: inline-block;margin-left: 5px;color: #909399">元</small>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    import {regionData, CodeToText,TextToCode} from 'element-china-area-data'
    import service from "@/utils/request";

    export default {
        name: "Order",
        data() {
            return {
                address: [],
                orderForm: {
                    userId: "",
                    realName: "",
                    userPhone: "",
                    userAddress: "",
                    cartId: "",
                    totalNum: "",
                    totalPrice: "",
                    totalPostage: "",
                    payPrice: "",
                },
                addressForm: {
                    address: [],
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                },
                addressFormSub: {
                    userId:'',
                    province: '',
                    city: '',
                    district: '',
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                },
                activeStep: 0,
                a: "",
                isActive: -1,
                ia: -1,
                options: regionData,
                dialogFormVisible: false,
                rules: {
                    address: [{required: true, message: '请选择地址信息', trigger: 'blur'}],
                    detail: [{required: true, message: '请填写详细地址信息', trigger: 'blur'}],
                    realName: [{required: true, message: '请填写收件人姓名', trigger: 'blur'}],
                    phone: [
                        {required: true, message: "请输入手机号", trigger: "blur"},
                        {
                            validator: function (rule, value, callback) {
                                if (/^1[34578]\d{9}$/.test(value) == false) {
                                    callback(new Error("请输入正确的手机号"));
                                } else {
                                    callback();
                                }
                            }, trigger: 'blur'
                        }
                    ]
                },
                goodsTable: [{
                    id: 1,
                    goods: {
                        name: '奥特曼手办',
                        price: '100',
                        peisong: {
                            peisongfangshi: '不包邮',
                            peisongjiage: 10
                        },
                        Imglink: 'static/images/atm.jpg',
                        attribute:
                            [{
                                '颜色': "红色",
                                '大小': '30 * 60'
                            }]
                    },
                    count: 1,
                }]


            };
        }, created() {
            this.getAddress();
        },
        methods: {
            next() {
                if (this.active < 2 & this.active >= 0) {
                    this.active++
                } else {
                }
            },
            placeOrder() {
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                this.orderForm.userId = user.userId;
                this.orderForm.realname = user.realName;
                // this.orderForm.totalNum = this.

                let order = this.orderForm
                var qs = require("querystring");
                service.post('/api/apmStoreUser/insertOrder', qs.stringify(order)).then((response) => {

                }).catch(function (error) {
                    console.log(error);
                });
            },
            checked(index) {
                this.isActive = index;
                this.ia = index;
                let p = TextToCode[this.address[index].province].code;
                let c = TextToCode[this.address[index].city].code;
                let d = TextToCode[this.address[index].district].code;
                let a =[];
                a.push(p);
                a.push(c);
                a.push(d);
                a.push(this.address[index].detail);
                this.orderForm.userAddress=a.toString();
                this.orderForm.userPhone= this.address[index].phone;
                this.orderForm.realName = this.address[index].realName;
                console.log(this.orderForm)
                },
            submitForm(formName) {
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                this.addressFormSub.userId = user.userId;
                this.addressFormSub.province = this.addressForm.address[0]
                this.addressFormSub.city = this.addressForm.address[1]
                this.addressFormSub.district = this.addressForm.address[2]
                this.addressFormSub.phone = this.addressForm.phone;
                this.addressFormSub.postCode = this.addressForm.postCode;
                this.addressFormSub.realName = this.addressForm.realName;
                this.addressFormSub.detail = this.addressForm.detail;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        service.post('/api/address/insertUserAddress', this.addressFormSub)
                            .then((response) => {
                                if (response.data == "success") {
                                    this.$message({
                                        message: "添加成功",
                                        type: "success"
                                    }, 500);
                                    this.getAddress();
                                    this.dialogFormVisible = false;
                                } else {
                                    this.$message({
                                        message: "添加失败",
                                        type: "error"
                                    }, 500);
                                }
                            }).catch(function (error) {
                            console.log(error);
                        });

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            handleChange(value) {
            },
            addgood(good) {
                let arr = this.goodsTable.filter(o => o.id == good.id)
                arr[0].count++;
            },
            reducegood(good) {
                let arr = this.goodsTable.filter(o => o.id == good.id)
                if (arr[0].count > 1) {
                    arr[0].count--;
                }
            },
            getAddress(){
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                service.get('/api/address/queryALlUserAddress', {params: {userId: user.userId}})
                    .then((response) => {
                        this.address = response.data;
                        console.log(this.address)
                        for (let i = 0; i < this.address.length; i++) {
                            this.address[i].province = CodeToText[this.address[i].province]
                            this.address[i].city = CodeToText[this.address[i].city]
                            this.address[i].district = CodeToText[this.address[i].district]
                        }
                    }).catch(function (error) {
                    console.log(error);
                });
            }
        }
    }
</script>

<style scoped>
    .placeOrder {
        background-color: #FF0044;
        color: #fff;
        font-size: 16px;
        padding: 10px 15px 10px 15px;
    }

    .active {
        outline: 5px solid deeppink;
        border: none !important;
    }

    .imgActive {
        visibility: visible !important;
        width: 100px;
        height: 100px;
    }

    .h {
        margin: 5px 0 !important;
    }

    .btns button {
        padding: 10px 20px 10px 20px;
        background-color: white;
    }

    .btns {
        width: 40%;
        margin-left: 30%;
    }

    .btn1:focus {
        border: 1px solid deeppink;
        outline: 2px solid deeppink;
    }

    button {
        background-color: transparent;
    }

</style>
