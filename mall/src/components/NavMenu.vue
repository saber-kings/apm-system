<template>
    <div class="tabs">
        <el-menu
                :default-active="activeIndex2"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                background-color="#404145"
                text-color="#fff"
                active-text-color="#ffd04b">
            <el-menu-item index="1">
                <router-link to="/">
                    <span>
                        热门推荐
                    </span>
                </router-link>
            </el-menu-item>
            <el-submenu index="2">
                <template slot="title"><i class="icon iconfont icon-caidan"></i>全部分类</template>
                <template v-for="(item,i) in list">
                    <el-submenu :index="String(item.id)">
                        <template slot="title">{{item.cateName}}</template>
                        <template v-if="item.subCategoryList!=null"
                                  v-for="(child,j) in item.subCategoryList">
                            <template>
                                <router-link :to="{name:'ListOfGoods',params:{listId:child.id}}">
                                    <el-menu-item :index="String(child.id)">
                                        {{child.cateName}}
                                    </el-menu-item>
                                </router-link>
                            </template>
                        </template>
                    </el-submenu>
                </template>

            </el-submenu>
            <el-menu-item index="4">
                <router-link :to="{name: 'ReleaseIdle'}">发布闲置</router-link>
            </el-menu-item>
            <el-menu-item style="float: right;margin-right: 20%">
                <input placeholder="请输入内容"/>
                <el-button style="padding-right: 15px !important;" type="text" id="search"><i class="icon iconfont icon-iconsmallsearch"></i>搜索
                </el-button>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    import service from "@/utils/request";

    export default {
        data() {
            return {
                activeIndex: '1',
                activeIndex2: '1',
                list: []
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
        }, created() {
            service.get('/api/category/web/list').then((response) => {
                this.list = response.data.data;
            })
                .catch(function (error) {
                    console.log(error);
                });
        }
    }
</script>

<style>
    .el-menu {
        padding: 0px 0px 0px 0px;
    }

    .el-menu, .el-menu-item, .el-submenu, .el-submenu__title {
        height: 35px !important;
        line-height: 35px !important;
    }

    .tabs > .el-menu {
        padding-left: 10% !important;
    }

    input {
        padding-left: 10px;
        line-height: 35px;
        height: 35px;
        float: left;
        background-color: transparent;
        border-radius: 5px;
    }

    .el-button {
        color: #fff !important;
        padding: 6px 0 6px 15px !important;
        height: 35px;
    }

    i {
        margin-right: 3px;
    }

    .router-link, a {
        text-decoration: none;
    }

    .el-menu, .el-submenu {
        background-color: pink;
    }

    .el-menu-item input:focus {
        outline: rgba(255, 255, 255, 0) auto 0px;
        color: #fff;
    }
    #search:active,#search:focus{
        border: none;
        outline: none;
    }
</style>
<style scoped>
    .router-link-active {
        text-decoration: none;
        color: #ffd04b;
    }

    a {
        text-decoration: none;
        color: #fff;
    }
</style>
