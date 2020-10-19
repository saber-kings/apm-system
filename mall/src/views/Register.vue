<template>
    <div class="login_container">
        <div class="login_box">
            <!--头像区域  -->
            <h3 class="title">动漫管理系统</h3>
            <!-- 登录表单 -->
            <el-form
                    ref="registerFormRef"
                    :model="registerForm"
                    :rules="registerFormRules"
                    label-width="0px"
                    class="login_form"
            >
                <!-- 用户名 -->
                <el-form-item prop="phoneNumber">
                    <el-input v-model="registerForm.phoneNumber" placeholder="请输入11位手机号"
                              prefix-icon="el-icon-date"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="registerForm.password" prefix-icon="el-icon-date" placeholder="请输入密码"
                              type="password"></el-input>
                </el-form-item>
                <el-form-item prop="repassword">
                    <el-input v-model="registerForm.repassword" prefix-icon="el-icon-date"  placeholder="请确认您的密码"
                              type="password"></el-input>
                </el-form-item>
                <el-form-item class="btns">
                    <button type="button" @click="register">注册</button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'))
                } else if (value !== this.registerForm.password) {
                    callback(new Error('两次输入密码不一致!'))
                } else {
                    callback()
                }
            }
            return {
                registerForm: {
                    phoneNumber: '',
                    password: ''
                },
                repassword: '',
                //表单的验证规则对象
                registerFormRules: {
                    //验证用户名是否合法
                    phonenNmber: [
                        {required: true, message: "请输入用户名", trigger: "blur"},
                        {
                            validator: function (rule, value, callback) {
                                if (/^1[34578]\d{9}$/.test(value) == false) {
                                    callback(new Error("请输入正确的手机号"));
                                } else {
                                    callback();
                                }
                            }, trigger: 'blur'
                        }
                    ],
                    //验证密码是否合法
                    password: [
                        {required: true, message: "请输入密码", trigger: "blur"},
                        {min: 6, max: 15, message: "长度在6到 15个字符", trigger: "blur"}
                    ],
                    repassword: [
                        {required: true, validator: validatePass2, trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            register() {
                var th = this;
                //在 then的内部不能使用Vue的实例化的this, 因为在内部 this 没有被绑定。
                //获取引用对象:this.$refs.registerFormRef
                //表单预验证
                this.$refs.registerFormRef.validate(async validate => {
                    //console.log(validate)
                    if (!validate) return;
                    const result = await this.$http
                        .post("/api/user/registerByPhone",
                            this.registerForm)
                        .then(function (response) {
                            if (response.data.msg == "操作成功") {
                                th.$message({
                                    message: "注册成功",
                                    type: "success"
                                }, 1000);

                                //注册成功跳转登录页面
                                th.$router.push("/Login");
                            } else {
                                th.$message({
                                    message: "注册失败！",
                                    type: "error"
                                });
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                });
            }
        }
    };
</script>

<style>
    .title {
        margin: 20px auto 30px auto;
        text-align: center;
        color: #707070;
    }

    .login_container {
        /* background-color: #2b4b6b; */
        background-image: url("../assets/bg.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        height: 100%;
        width: 100%;
    }

    .login_box {
        height: 400px;
        width: 400px;
        background-color: rgba(0, 0, 0, 0.6);
        border-radius: 3px;
        position: absolute;
        top: 50%;
        left: 50%;
        box-shadow: 0 0 10px #ddd;
        transform: translate(-50%, -50%);
    }

    .avatar_box {
        height: 130px;
        width: 130px;
        padding: 10px;
        border: 1px solid #eee;
        border-radius: 50%;
        position: absolute;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #fff;
    }

    .btns {
        display: flex;
        justify-content: flex-end;
    }

    .login_form {
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0 20px;
        top: 20%;
        box-sizing: border-box;
    }

    .login-code {
        width: 33%;
        height: 38px;
        float: right;

    }

    .login-code img {
        cursor: pointer;
        vertical-align: middle;
    }

    .login-code-img {

    }

    html, body, #app {
        height: 100%;
        margin: 0;
        padding: 0;
        min-width: 1366px;
    }

    .el-breadcrumb {
        margin-bottom: 15px;
        font-size: 12px;
    }

    .el-card {
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
    }

    .el-table {
        margin-top: 15px;
    }

    .el-pagination {
        margin-top: 15px;
    }

    button {
        padding: 10px 20px;
        margin-left: 30px;
        border-radius: 4px;
        background-color: #32CCBC;
    }
</style>
