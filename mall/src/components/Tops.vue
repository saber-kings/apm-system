<template>
    <div class="content">
        <div class="tops">
            <div class="t-left" v-show="showText">
                您还没有登录请登录
            </div>
            <div class="t-right">
                <div v-show="showActive">
                    <router-link :to="{name:'Register'}" class="regist">
                        <span style="display: inline-block;cursor: pointer;margin-right: 10px">注册</span>
                    </router-link>
                    <span>|</span>
                    <router-link :to="{name:'Login'}" class="login">
                        <span style="display: inline-block;cursor: pointer;margin-left: 5px">登录</span>
                    </router-link>
                </div>
                <div v-show="showUser">
                    <span style="display: inline-block;">
                        欢迎您！
                    </span>
                    <router-link :to="{name: 'PersonalData',params:{uid:''}}">
                        <span style="display: inline-block;cursor: pointer">
                        {{nickname}}
                    </span>
                    </router-link>
                    <span @click="loginOut"
                          style="display: inline-block;margin-left: 15px;color: #FF0044;cursor: pointer">
                          退出
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Tops",
        data() {
            return {
                showText: true,
                showActive: true,
                showUser: false,
                nickname: '',
                isLogin: false
            }
        },
        created() {
            this.nickname = window.sessionStorage.getItem("nickname");
            this.isLogin = window.sessionStorage.getItem("isLogin");
            console.log(this.isLogin)
            if (this.isLogin) {
                this.showText = false;
                this.showActive = false;
                this.showUser = true
            } else {
                this.showText = true;
                this.showActive = true;
                this.showUser = false;
            }
        },
        methods: {
            loginOut() {
                this.isLogin = false;
                this.showText = true;
                this.showActive = true;
                this.showUser = false;
                window.sessionStorage.clear();
                this.$router.push("/")
                this.$message({
                    message: "您已退出",
                    type: "success"
                },500);
            }

        }
    }
</script>

<style scoped>
    .tops {
        background-color: #f5f5f5;
        height: 20px;
        padding: 5px 0 5px 0;
    }

    .t-left {
        padding-left: 80px;
        float: left;
        font-size: 12px;
    }

    .t-right {
        padding-right: 80px;
        float: right;
        font-size: 12px;
    }

    .login {
        margin-left: 10px;
    }

    a {
        text-decoration: none;
    }

    .router-link {
        text-decoration: none;
    }

</style>
