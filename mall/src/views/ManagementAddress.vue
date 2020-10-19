<template>
    <div style="width: 100%">
        <div style="width: 80%;margin-left: 10%;margin-top: 40px">
            <h3>新增收货地址</h3>
            <div style="width: 60%;margin-left: 20%;">
                <el-form :rules="rules" ref="addressForm" :model="addressForm"
                         style="margin-left: 25%">
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
                <el-divider></el-divider>
            </div>
            <div>
                <h3 style="margin: 10px 0;">管理已经存在的收货地址</h3>
                <div style="width: 80%;margin-left: 10%">
                    <table style="text-align: center;">
                        <tbody>
                        <tr>
                            <th>收货人</th>
                            <th>所在省</th>
                            <th>所在市</th>
                            <th>所在省</th>
                            <th>详细地址</th>
                            <th>邮编</th>
                            <th>电话</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="(i,index) in getAddressList">
                            <td>{{i.realName}}</td>
                            <td>{{i.province}}</td>
                            <td>{{i.city}}</td>
                            <td>{{i.district}}</td>
                            <td>{{i.detail}}</td>
                            <td>{{i.postCode}}</td>
                            <td>{{i.phone}}</td>
                            <td>
                                <div>
                                    <span style="padding: 0px;display: inline-block;color: #1D8CE0;cursor: pointer"
                                          @click="deladd(index)">删除</span>
                                    <span style="padding: 0px;display: inline-block;color: #1D8CE0;cursor: pointer;margin-left: 10px"
                                          @click="readd(index)">编辑</span>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="text-align: center;" v-show="showText">
                        没有数据
                    </div>
                </div>
                <el-drawer
                        title="修改当前收货地址"
                        :visible.sync="drawer"
                        direction="rtl">
                    <div style="width: 100%">
                        <div>
                            <el-form :rules="rules" ref="reAddressForm" :model="reAddressForm"
                                     style="margin-left: 25%">
                                <el-form-item label="地址信息:" prop="address" label-width="100px">
                                    <el-cascader style="width: 60%;" :options="options"
                                                 v-model="reAddressForm.address"
                                                 @change="handleChange">
                                    </el-cascader>
                                </el-form-item>
                                <el-form-item label="详细地址:" prop="detail"
                                              label-width="100px">
                                     <textarea placeholder="请输入详细地址" rows="2"
                                               v-model="reAddressForm.detail"
                                               style="color: #606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;resize: vertical"></textarea>
                                </el-form-item>
                                <el-form-item label="邮编:" label-position="left" label-width="100px">
                                    <input placeholder="请输入邮编" v-model="reAddressForm.postCode"
                                           style="color: #606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                                <el-form-item label="收货人姓名:" label-position="left"
                                              label-width="100px"
                                              prop="realName">
                                    <input placeholder="请输入收货人姓名" v-model="reAddressForm.realName"
                                           style="color:#606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                                <el-form-item label="手机号码:" label-position="left"
                                              label-width="100px"
                                              prop="phone">
                                    <input placeholder="请输入收货人联系方式" v-model="reAddressForm.phone"
                                           style="color:#606266;width: 60%;border: 1px solid rgb(220,223,230);border-radius: 4px;line-height: 35px;padding: 0;"/>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer btns">
                                <button class="btn1" type="button"
                                        @click="reAddress('reAddressForm')"
                                        style="border: 1px solid deeppink">保存修改
                                </button>
                            </div>
                        </div>
                    </div>
                </el-drawer>
            </div>
        </div>
    </div>
</template>

<script>
    import {regionData, CodeToText, TextToCode} from 'element-china-area-data'
    import service from "@/utils/request";

    export default {
        inject: ['reload'],
        name: "ManagementAddress",
        data() {
            return {
                addid: "",
                showText: true,
                dialogVisible: false,
                visible: false,
                options: regionData,
                ruleForm: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                address: [],
                getAddressList: {
                    province: '',
                    city: '',
                    district: '',
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                },
                addressForm: {
                    address: [],
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                },
                addressFormSub: {
                    province: '',
                    city: '',
                    district: '',
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                },
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
                drawer: false,
                reAddressForm: {
                    address: [],
                    province: '',
                    city: '',
                    district: '',
                    detail: '',
                    phone: '',
                    realName: '',
                    postCode: ''
                }
            }
        }, created() {
            this.getAddress()
        },
        methods: {
            handleChange(value) {
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
                        service.post('/api/address/', this.addressFormSub)
                            .then((response) => {
                                if (response.data.msg == "操作成功") {
                                    this.$message({
                                        message: "添加成功",
                                        type: "success"
                                    }, 500);
                                    this.dialogFormVisible = false;
                                    this.reload();
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
            getAddress() {
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                service.get('/api/address/list', {params: {userId: user.userId}})
                    .then((response) => {
                        console.log(response.data.data)
                        if (response.data.data.length != 0) {
                            this.showText = false;
                            this.getAddressList = response.data.data
                            for (let i = 0; i < this.getAddressList.length; i++) {
                                this.getAddressList[i].province = CodeToText[this.getAddressList[i].province]
                                this.getAddressList[i].city = CodeToText[this.getAddressList[i].city]
                                this.getAddressList[i].district = CodeToText[this.getAddressList[i].district]
                            }
                        }

                    }).catch(function (error) {
                    console.log(error);
                });
            },
            deladd(index) {
                service.delete('/api/address/' + this.getAddressList[index].id)
                    .then((response) => {
                        if (response.data.msg == "操作成功") {
                            this.$message({
                                message: '删除成功',
                                type: 'success',
                                offset: 200
                            });
                            this.reload();
                        } else {
                            this.$message({
                                message: '删除失败',
                                type: 'error',
                                offset: 200
                            });
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            readd(index) {
                this.drawer = true;
                let a = [];
                a.push(TextToCode[this.getAddressList[index].province].code);
                a.push(TextToCode[this.getAddressList[index].province][this.getAddressList[index].city].code);
                a.push(TextToCode[this.getAddressList[index].province][this.getAddressList[index].city][this.getAddressList[index].district].code)

                this.reAddressForm = this.getAddressList[index];
                this.reAddressForm.address=a;
            },
            reAddress(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.reAddressForm.province =this.reAddressForm.address[0];
                        this.reAddressForm.city =this.reAddressForm.address[1];
                        this.reAddressForm.district =this.reAddressForm.address[2];
                        service.put('/api/address/', this.reAddressForm)
                            .then((response) => {
                                if (response.data.msg == "操作成功") {
                                    this.$message({
                                        message: '修改成功',
                                        type: 'success',
                                        offset: 200
                                    });
                                    this.reload();
                                } else {
                                    this.$message({
                                        message: '修改失败',
                                        type: 'error',
                                        offset: 200
                                    });
                                }
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
        color: #C0C4CC;
    }

    input::-moz-placeholder, textarea::-moz-placeholder { /* Mozilla Firefox 19+ */
        color: #C0C4CC;
    }

    input:-moz-placeholder, textarea:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
        color: #C0C4CC;
    }

    input:-ms-input-placeholder, textarea:-ms-input-placeholder { /* Internet Explorer 10-11 */
        color: #C0C4CC;
    }

    .btns button {
        padding: 10px 20px 10px 20px;
        background-color: white;
        margin-left: 30px;
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

    table {
        border: 1px solid #99a9bf;
        border-right: none;
        border-top: none;
    }

    tr, td, th {
        border: 1px solid #99a9bf;
        border-left: none;
        border-bottom: none;
        text-align: center;
        padding: 5px 10px;
    }
</style>
