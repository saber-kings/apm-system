<template>
    <div>
        <div class="goodsList">
            <h1 class="goodsTitle">{{listName}}</h1>
            <el-divider></el-divider>
            <el-row :gutter="20">
                <el-col :span="6" v-for="(i,index) in listGoods" class="goodsImgCol">
                    <div @click="toPage(index)">
                        <div class="grid-content bg-purple">
                            <div class="imgbox">
                                <img :src="i.image" class="goodsImg">
                            </div>
                            <div class="goodsDescription">
                                <span>{{i.storeName}}</span>
                                <p>{{i.storeInfo}}</p>
                            </div>
                            <div class="box">
                                    <span class="goodsPrice">
                                        <span class="activityPrice"><small>￥</small>{{i.price}}</span>
                                        <span class="originalPrice"><small>￥</small>{{i.otPrice}}</span>
                                    </span>
                                <span class="goods-btn">
                                        <button class="buy">购买</button>
                                    </span>
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <div style="text-align: right">
                <el-pagination
                        background
                        @current-change="handleCurrentChange"
                        layout="prev, pager, next"
                        :page-count="page_count"
                       >

                </el-pagination>
            </div>

        </div>
    </div>
</template>

<script>
    import service from "@/utils/request";

    export default {
        name: "ListofGoods",
        data() {
            return {
                listGoods: [],
                listName:'',
                page: 1,
                page_count: 0,
            }
        }, created() {
            this.getListGoods();
        },methods:{
            handleCurrentChange(val) {
                this.page = val;
                this.getListGoods();
            },
            getListGoods(){
                var this_ = this;
                // 获取分类商品
                service.get('/api/product/list', {
                    params: {
                        cateId: window.localStorage.getItem("cateId"),
                        pageNum: this.page,
                        pageSize: 20
                    }
                }).then( resp =>{
                    this_.listGoods = resp.data.data.list;
                    this_.listName= window.localStorage.getItem("listName");
                    this_.page_count= resp.data.data.totalPages;
                }).catch( error => {
                    console.log(error)
                })
            }, toPage(index){
                window.localStorage.setItem("goodsid",this.listGoods[index].id);
                this.$router.push("/GoodsDetails");
            }
        }
    }
</script>

<style scoped>

    .imgbox {
        position: relative;
        padding-bottom: 100%; /*重要属性*/
        outline: 1px solid green;
    }

    .imgbox img {
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
    }

    .goodsImg {
        width: 100%;
    }

    .el-row {
        margin-bottom: 20px;

    &
    :last-child {
        margin-bottom: 0;
    }

    }
    .el-col {
        border-radius: 4px;
    }

    .bg-purple-dark {
        background: #99a9bf;
    }

    .bg-purple {
        background: #d3dce6;
    }

    .bg-purple-light {
        background: #e5e9f2;
    }

    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }

    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }

    /*.goodsImgCol {*/
    /*    margin-top: 30px;*/
    /*}*/

    .goodsList {
        width: 80%;
        margin-left: 10%;
    }

    .goodsDescription {
        width: 100%;
        padding-right: 5px;
        padding-right: 5px;
        color: rgb(126, 126, 126);
        font-family: 微软雅黑;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 12px;
    }

    .bg-purple {
        background-color: white;
        border: 2px solid #DCDFE6;
        margin-top: 20px;
    }

    .bg-purple:hover {
        /*background-color: */
        border: 2px solid #FF0044;;
    }

    .goodsTitle {
        margin-top: 10px;
        color: #f60;
    }

    a {
        text-decoration: none;
    }

    .router-link-active {
        text-decoration: none;
    }

    button {
        border: 1px solid crimson;
        color: crimson;
        background-color: transparent;
        padding: 3px 10px 3px 10px;
        margin-bottom: 5px;
        margin-top: 5px;
    }

    button:hover {
        background-color: #FF0044;
        color: white;
    }

    .originalPrice {
        text-decoration: line-through;
        color: #C0C4CC;
        font-size: 12px;
        margin-left: 15px;
    }

    .activityPrice {
        color: #f60;
        font-size: 15px;
    }

    .goodsPrice {
        margin-top: 5px;
    }

    .box {
        display: flex;
        justify-content: space-around;
    }
</style>
