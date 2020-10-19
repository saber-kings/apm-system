<template>
    <div id="div01" style="width: 100%">
        <div style="width: 60%;margin-left: 20%;display: flex;">
            <div id="left">
                <ul>
                    <li @click="chang1"> 个人资料</li>
                    <li @click="chang2"> 个人交易信息</li>
                    <li @click="goToPageAdd"> 地址管理</li>
                    <li @click="goToPageOrder"> 查看订单</li>
                </ul>
            </div>
            <div id="right">
                <!--            个人资料-->
                <div id="personalData" v-show="show">
                    <div id="main-profile" class="parts">
                        <div>
                            <label>当前头像：</label>
                            <div class="user-header" style="text-align: center;width: 60%">
                                <el-upload
                                        class="avatar-uploader"
                                        action=""
                                        :show-file-list="false"
                                        :on-success="handleAvatarSuccess"
                                        :before-upload="beforeAvatarUpload">
                                    <!--                                    <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
                                    <img src="../assets/jpg.jpg" class="avatar">
                                </el-upload>
                            </div>
                        </div>

                        <div>
                            <label>昵称：<em style="color: #FF0044">*</em></label>
                            <input style="margin-left: 60px;width: 40%;margin-bottom: 10px"
                                   type="text" maxlength="25" v-model="nickname">
                        </div>
                        <div>
                            <label>真实姓名：</label>
                            <input style="margin-left: 60px;width: 40%;margin-bottom: 10px"
                                   type="text" maxlength="25" v-model="realname">
                        </div>
                        <div>
                            <label>生日：</label>
                            <div class="block" style="margin-left: 60px;margin-bottom: 10px">
                                <el-date-picker
                                        v-model="birthday"
                                        type="date"
                                        placeholder="选择日期">
                                </el-date-picker>
                            </div>
                        </div>
                        <div>
                            <label>性别：<em style="color: #FF0044">*</em></label>
                            <div style="margin-left: 60px">
                                <el-radio label="0" v-model="sex">男</el-radio>
                                <el-radio label="1" v-model="sex">女</el-radio>
                            </div>
                        </div>
                        <div>
                            <label>居住地：</label>
                            <template>
                                <div style="margin-left: 60px;width: 100%;">
                                    <el-cascader style="width: 42%"
                                                 size="large"
                                                 :options="options"
                                                 v-model="address"
                                                 @change="handleChange">
                                    </el-cascader>
                                </div>
                            </template>
                        </div>
                    </div>

                    <div class="act skin-blue">
                    <span class="btn n-btn" style="width: 60%;text-align: center">
                        <button type="button"
                                style="padding: 10px;background: transparent;border: 1px solid slateblue"
                                @click="saveInfo">保存</button>
                    </span>
                    </div>
                </div>
                <!--        个人交易信息-->
                <div id="transaction" v-show="show2">
                    <div class="main-wrap">
                        <div class="account-security profile-setting" id="main-content">
                            <form name="mainform" action="" method=post>
                                <input name='_tb_token_' type='hidden' value='eebee116851e4'>
                                <h2 class="h2-single">
                                <span class="entity">
                                    <span>个人交易信息</span>
                                </span>
                                </h2>
                                <div class="elem-form section security-profile trade:addressEditor"
                                     style="margin-left: 30px"
                                     id="ah:addressForm">
                                    <span style="margin-top: 30px">
                                        <span class="label-like">真实姓名：</span>
                                        <input type="text" v-model="realname"
                                               style="margin-left: 100px;margin-bottom: 10px;width: 40%"/>
                                    </span>
                                    <span style="margin-top: 30px">
                                        <span class="label-like">实名证件号：</span>
                                        <input type="text" v-model="cardId"
                                               style="margin-left: 100px;margin-bottom: 10px;width: 40%"/>
                                    </span>
                                    <span>
                                        <label>性别：<em style="color: #FF0044">*</em></label>
                                        <div style="margin-left: 100px">
                                            <el-radio v-model="sex" label="0">男</el-radio>
                                            <el-radio v-model="sex" label="1">女</el-radio>
                                        </div>
                                    </span>
                                    <span>
                                        <span class="label-like">生日:</span>
                                        <div class="block" style="margin-left: 100px">
                                            <el-date-picker
                                                    v-model="birthday"
                                                    style="width: 40%"
                                                    type="date"
                                                    placeholder="选择日期">
                                            </el-date-picker>
                                      </div>
                                    </span>
                                    <span>
                                        <span class="label-like">详细地址:</span>
                                        <el-cascader style="width: 42%;margin-left: 100px"
                                                     size="large"
                                                     :options="options"
                                                     v-model="address"
                                                     @change="handleChange">
                                        </el-cascader>
                                    </span>
                                    <span>
                                        <span class="label-like">邮箱:</span>
                                        <input type="text" v-model="email"
                                               style="margin-left: 100px;margin-bottom: 10px;width: 40%"/>
                                    </span>
                                    <span>
                                        <span class="label-like">电话:</span>
                                        <input type="text" v-model="phone"
                                               style="margin-left: 100px;margin-bottom: 10px;width: 40%"/>
                                    </span>
                                    <span class="container-btn">
                                    <span class="skin-gray" style="width: 80%;text-align: center">
                                        <button type="button"
                                                style="padding: 10px 20px;background: transparent;border: 1px solid slateblue;display: inline-block" @click="saveInfo">保存</button>
                                    </span>
                                    </span>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    import {provinceAndCityData} from 'element-china-area-data'
    import service from "@/utils/request";

    export default {
        name: "personalData",
        data() {
            return {
                imgStr: require('../assets/jpg.jpg'),
                errorStr: '',
                show: true,
                show2: false,
                options: provinceAndCityData,
                address: [],
                imageUrl: '',
                birthday: '',
                realname: '',
                nickname: '',
                phone: '',
                sex: '',
                email: '',
                apmUser: {
                    userId: '',
                    realName: '',
                    nickName: '',
                    sex: '',
                    birthday: '',
                    address: '',
                    phonenumber: '',
                    email: '',
                    cardId:''
                }
            }
        }, created() {
            let user = window.sessionStorage.getItem("user");
            user = JSON.parse(user);
            this.nickname = user.nickName;
            this.realname = user.realName;
            this.sex = user.sex;
            this.birthday = user.birthday;
            let addre=[];
            addre.push(user.address.split(",")[0])
            addre.push(user.address.split(",")[1])
            this.address = addre;
            this.phone = user.phoneNumber;
            this.email = user.email;
            this.cardId = user.cardId;
        },
        methods: {
            handleChange(value) {
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            chang1() {
                this.show = true;
                this.show2 = false;
            },
            chang2() {
                this.show = false;
                this.show2 = true;
            },
            goToPageAdd() {
                this.$router.push({name: 'ManagementAddress', params: {uid: 1001}})
            },
            goToPageOrder() {
                this.$router.push({name: 'OrderInquiry', params: {uid: 1001}})
            }, saveInfo() {
                var qs = require("querystring")
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                    this.apmUser.userId = user.userId,
                    this.apmUser.realName = this.realname,
                    this.apmUser.nickName = this.realname,
                    this.apmUser.sex = this.sex,
                    this.apmUser.birthday = this.birthday,
                    this.apmUser.address = this.address[0]+","+this.address[1],
                    this.apmUser.phonenumber = this.phone,
                    this.apmUser.email = this.email

                service.post('/api/user/updateUserInfo',this.apmUser)
                    .then((response) => {
                        let addre=[];
                        addre.push(response.data.address.split(",")[0])
                        addre.push(response.data.address.split(",")[1])
                        this.apmUser.userId = response.data.userId,
                            this.apmUser.realName = response.data.realname,
                            this.apmUser.nickName = response.data.realname,
                            this.apmUser.sex = response.data.sex,
                            this.apmUser.birthday = response.data.birthday,
                            this.apmUser.address = addre,
                            this.apmUser.phonenumber = response.data.phone,
                            this.apmUser.email = response.data.email
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        }
    }
</script>

<style scoped>
    input {
        border: 1px solid #99a9bf;
    }

    div, span, p {
        clear: both;
    }

    p, label, input, span {
        display: block;
    }

    #div01 {
        width: 100%;
        position: relative;
        margin: auto;
    }

    #left {
        text-align: left;
        width: 20%;
        background-color: #FF96F9;
    }

    li {
        list-style: none;
        display: block;
        padding: 10px;
        color: #fff;
        cursor: pointer;
    }

    li:hover, li:active {
        background-color: #C32BAC;
    }

    #right {
        border: 1px solid #C32BAC;
        height: 100%;
        width: 80%;
        padding-top: 30px;
        padding-bottom: 30px;
        padding-left: 50px;
    }

    #left label {
        width: 150px;
        margin: 10px 0 0 0;
        letter-spacing: 5px;
    }

    #left label:hover {
        color: red;
    }

    .user-header {
        position: relative;
        display: inline-block;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
