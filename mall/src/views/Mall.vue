<template>
    <div class="Mall">
        <el-backtop></el-backtop>
        <div class="card">
            <el-carousel :interval="4000" type="card" height="300px">
                <el-carousel-item v-for="item in cards">
                    <img :src="item" class="carImg">
                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="goods-count">
            <div class="goods" v-for="(j,index1) in listgoods">
                <h1 class="goodsTitle">{{j.cateName}}
                    <span class="goodsLink" @click="goTopageList(index1)">
                        点击查看更多>>
                    </span>
                </h1>
                <el-divider></el-divider>
                <div class="goodsList">
                    <el-row :gutter="20">
                        <el-col :span="6" v-for="(i,index2) in j.productList" class="goodsImgCol">
                                <div @click="toPage(index1,index2)">
                                    <div class="grid-content bg-purple">
                                        <div class="imgbox">
                                            <img :src="i.image" class="goodsImg">
                                        </div>
                                        <div class="goodsDescription">
                                            <span>{{i.storeName}}</span>
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
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import service from "@/utils/request";
    import index from "@/store";

    export default {
        name: "Mall",
        data() {
            return {
                // cards:[],
                goods: [],
                listgoods: [],
                cards: ['https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1007520304,1774717201&fm=11&gp=0.jpg',
                    'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=339470934,3305429106&fm=11&gp=0.jpg',
                    'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=158453035,2696288894&fm=26&gp=0.jpg',
                    'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3556961323,780841707&fm=11&gp=0.jpg',
                    'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=272626357,1603769394&fm=26&gp=0.jpg',
                    'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=74295797,76919157&fm=11&gp=0.jpg',
                    'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3067295878,4286841015&fm=11&gp=0.jpg',
                    'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1459132732,1847096421&fm=26&gp=0.jpg',]
            }
        },
        created() {
            //获取轮播图
            // service.get('http://10.132.22.161:8080/api').then((response) => {
            //     console.log(response.data)
            //     this.cards = response.data;
            // })
            // .catch(function (error) {
            //         console.log(error);
            //     });
            //获取首页推荐商品
            service.get('api/product/web/list').then((response) => {
                this.listgoods = response.data.data;
                // console.log(this.listgoods)
            })
                .catch(function (error) {
                    console.log(error);
                });

        },
        methods : {
            toPage(index1,index2){
                localStorage.setItem("goodsid",this.listgoods[index1].productList[index2].id);
                console.log(localStorage.getItem("goodsid"))
                this.$router.push("/GoodsDetails");
            },
            goTopageList(index1){
                window.localStorage.setItem("cateId",this.listgoods[index1].id);
                window.localStorage.setItem("listName",this.listgoods[index1].cateName);
                this.$router.push("/ListOfGoods");
            }
        }
    }

</script>

<style scoped>
    .imgbox {
        padding-bottom: 100%; /*重要属性*/
        outline: 1px solid green;
        position: relative;
    }

    .imgbox img {
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
    }

    .carImg {
        width: 100%;
        height: 100%;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
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

    .goodsImgCol {
        margin-top: 30px;
    }

    .goods-count {
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
    }

    .bg-purple:hover {
        /*background-color: */
        border: 2px solid #FF0044;;
    }

    .goodsTitle {
        color: #f60;
    }

    .goodsLink {
        font-size: 15px;
        color: black;
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

    /*.buy:focus{*/
    /*    color: white;*/
    /*    background-color: #FF0044;*/
    /*}*/
</style>
