<template>
    <div class="login_container">
        <div class="login_box">
            <h3 class="title">动漫管理系统</h3>
            <!-- 登录表单 -->
            <el-form
                    ref="loginFormRef"
                    :model="LoginForm"
                    :rules="LoginFormRules"
                    label-width="0px"
                    class="login_form"
            >
                <!-- 用户名 -->
                <el-form-item prop="phonenumber">
                    <el-input v-model="LoginForm.phonenumber" placeholder="请输入注册手机号" prefix-icon="el-icon-date"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="LoginForm.password" prefix-icon="el-icon-date" placeholder="请输入密码"
                              type="password"></el-input>
                </el-form-item>
                <el-form-item class="btns">
                    <button style="background: #1D8CE0" type="button" id="btn-login" @click="login">
                        登录
                    </button>
                    <router-link :to="{name:'Register'}">
                        <button type="button">注册</button>
                    </router-link>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                //登录表单的数据绑定对象
                LoginForm: {
                    phonenumber: "",
                    password: ""
                },
                //表单的验证规则对象
                LoginFormRules: {
                    //验证用户名是否合法
                    phonenumber: [
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
                    ],
                    //验证密码是否合法
                    password: [
                        {required: true, message: "请输入密码", trigger: "blur"},
                        {min: 6, max: 15, message: "长度在6到 15个字符", trigger: "blur"}
                    ]
                }
            };
        },
        created() {
            document.onkeydown = function (e) {   //按下回车提交
                let key = window.event.keyCode;
                //事件中keycode=13为回车事件
                if (key == 13) {
                    document.getElementById("btn-login").click();
                }
            }
        },
        methods: {
            login() {
                //在 then的内部不能使用Vue的实例化的this, 因为在内部 this 没有被绑定。
                var th = this;

                //获取引用对象:this.$refs.loginFormRef
                //表单预验证
                this.$refs.loginFormRef.validate(async validate => {
                    //console.log(validate)
                    if (!validate) return;
                    window.sessionStorage.setItem("isLogin", true);
                    const result = await this.$http
                        .get("/api/user/login", {
                            params: {
                                phoneNumber: this.LoginForm.phonenumber,
                                password: this.LoginForm.password
                            }
                        })
                        .then(function (response) {
                            if (response.data.msg == "操作成功") {
                                th.$message({
                                    message: "登录成功",
                                    type: "success"
                                }, 500);
                                //数据存储到会话内存中,正常存储token
                                window.sessionStorage.setItem('nickname', response.data.data.nickName);
                                window.sessionStorage.setItem('user',JSON.stringify(response.data.data));
                                window.sessionStorage.setItem("token", response.data.data.token);
                                window.sessionStorage.setItem("isLogin", true);
                                //登录成功之后跳转页面
                                th.$router.push("/");
                            } else {
                                th.$message({
                                    message: "帐号密码错误,登录失败！！！",
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

<style scoped>
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
        height: 280px !important;
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
    }
</style>
