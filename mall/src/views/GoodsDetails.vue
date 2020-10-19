<template>
    <div class="boxAll">
        <div class="goodsContent">
            <div class="imgcount">
                <div class="goodsImg">
                    <img :src="goods.image" class="Image">
                </div>
                <div class="goodsImgList">
                    <tr class="goodsImgTr">
                        <td v-for="i in goods.result.values">
                            <div class="listContent">
                                <img :src="i.pic" class="imgList">
                            </div>
                        </td>
                    </tr>
                </div>
            </div>
            <div class="oneGoodsRight">
                <div class="detailsBox">
                    <h3 class="oneGoodsTitle">{{goods.storeName}}</h3>
                    <span class="price-bck">
                        <span>
                            <small>价格：</small>
                            <span class="price">￥{{goods.price}}</span>
                            <small>元</small>
                            <small>原价：</small>
                            <span class="oldPrice">￥{{goods.otPrice}}</span>
                            <small>元</small>
                        </span>
                        <span>
                            <small>交易成功数：</small>
                            <span class="count">{{goods.sales}}</span>
                            <small>留言条数：</small>
                            <span class="count">1</span>
                        </span>
                    </span>
                    <el-divider></el-divider>
                    <div class="goodsAttribute">
                        <tr class="attribute" v-for="(i,index) in goods.result.attrs">
                            <td class="attributeName">{{i.value}}</td>
                            <td class="abkey">
                                <el-radio-group v-model="i.checked" size="small"
                                                @change="changeAttr">
                                    <el-radio-button v-for="j in i.detail"
                                                     :label="j"></el-radio-button>
                                </el-radio-group>
                            </td>
                        </tr>
                        <tr class="goodsCount">
                            <td class="goodsCountAttribute">数量</td>
                            <td class="countValue">
                                <div class="icon iconfont icon-icon_cut" @click="reduceCount"></div>
                                <input v-model="count" class="countInput"/>
                                <div class="icon iconfont icon-jia" @click="addCount"></div>
                            </td>
                        </tr>
                    </div>
                </div>
                <el-divider></el-divider>
                <div class="goodsBtn">
                    <router-link :to="{name: 'Order',params:{key :1}}">
                        <button class="btn-buy">立即购买</button>
                    </router-link>
                    <router-link :to="{name: 'ShoppingCart',params:{key :1}}">
                        <button class="btn-add"><i class="icon iconfont icon-truck"></i>添加到购物车
                        </button>
                    </router-link>
                </div>
            </div>
        </div>
        <div class="more">
            <el-tabs type="border-card">
                <el-tab-pane label="商品详情">
                    <img src="../assets/logo.jpg" alt="">
                </el-tab-pane>
                <el-tab-pane label="留言">
                    <div class="messageBoard">
                        <el-main>
                            <div class="message" v-for="i in 2">
                                <div class="m-left">
                                    <img src="../assets/logo.jpg" alt="">
                                </div>
                                <div class="m-right">
                                    <p>
                                        留言内容留言内容留言内容留言内容留言内容留言内容留言内容留言内容留言内容
                                    </p>
                                    <span>
                                    <img src="../assets/top-bg.jpg" alt="">
                                </span>
                                    <p>
                                        <span>2020年10月2日15:01</span>
                                        <span>|属性</span>
                                    </p>
                                </div>
                                <el-divider></el-divider>
                            </div>
                        </el-main>
                    </div>
                    <el-footer class="message-footer">
                        <el-pagination
                                background
                                layout="prev, pager, next"
                                :total="1000">
                        </el-pagination>
                    </el-footer>
                </el-tab-pane>
                <el-tab-pane label="专享服务">
                    <div>
                        <h1>价格说明</h1>
                        <el-divider></el-divider>
                        <h3>
                            划线价格
                        </h3>
                        <p>
                            指商品的专柜价、吊牌价、正品零售价、厂商指导价或该商品的曾经展示过的销售价等，并非原价，仅供参考。
                        </p>
                        <h3>
                            未划线价格
                        </h3>
                        <p>
                            指商品的实时标价，不因表述的差异改变性质。具体成交价格根据商品参加活动，或会员使用优惠券、积分等发生变化，最终以订单结算页价格为准。
                        </p>
                        <p>
                            商家详情页（含主图）以图片或文字形式标注的一口价、促销价、优惠价等价格可能是在使用优惠券、满减或特定优惠活动和时段等情形下的价格，具体请以结算页面的标价、优惠条件或活动规则为准。
                        </p>
                        <p>
                            此说明仅当出现价格比较时有效。若商家单独对划线价格进行说明的，以商家的表述为准。
                        </p>
                        <h1>安全提示</h1>
                        <el-divider></el-divider>
                        <p>
                            交易中请勿使用第三方的聊天工具沟通，不要接收可疑文件和不要点击不明来源的链接，支付前核实好域名和支付详情。
                            本站不会以订单有问题，让您提供任何银行卡、密码、手机验证码！遇到可疑情况可在钱盾“诈骗举报”中进行举报, 安全推荐
                        </p>
                        推荐安全软件：钱盾
                        <h1>内容申明</h1>
                        <el-divider></el-divider>
                        <p>
                            本站为第三方交易平台及互联网信息服务提供者，本站所展示的商品/服务的标题、价格、详情等信息内容系由店铺经营者发布，其真实性、准确性和合法性均由店铺经营者负责。提醒用户购买商品/服务前注意谨慎核实。如用户对商品/服务的标题、价格、详情等任何信息有任何疑问的，请在购买前与店铺经营者沟通确认；如用户发现店铺内有任何违法/侵权信息，请立即举报并提供有效线索</p>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import service from "@/utils/request";

    export default {
        name: "GoodsDetails",
        data() {
            return {
                count: 1,
                goods: []
            }
        }, created() {
            service.get('api/product/web/' + localStorage.getItem("goodsid")).then((response) => {
                this.goods = response.data.data;
                if (response.data.data.result != null) {
                    this.goods.result = JSON.parse(response.data.data.result);
                }else{
                    console.log("null")
                }
                console.log(this.goods)
            })
                .catch(function (error) {
                    console.log(error);
                });

        },
        methods: {
            addCount() {
                this.count++
            },
            reduceCount() {
                if (this.count <= 1) {

                } else {
                    this.count--
                }
            }, changeAttr() {
                let attrlist=[]
                this.goods.result.attrs.forEach(attr=>{
                    attrlist.push(attr.checked)
                    console.log(attrlist);
                })
                // for (let i =0;i<this.goods.result.values.length;i++){
                //     this.goods.result.values[i].detail
                // }
            }
        }
    }
</script>

<style scoped>
    .message-footer {
        float: right;
    }

    .m-left {
        float: left;
    }

    .m-left > img {
        width: 50px;
        height: 50px;
    }

    .m-right > p {
        text-indent: 0em;
    }

    .m-right > span > img {
        width: 50px;
        margin-top: 10px;
        height: 50px;
    }

    .m-right > p > span {
        color: #C0C4CC;
    }

    .m-right {
        float: left;
        margin-left: 80px;
    }

    .message {
        clear: both;
    }

    h3 {
        color: #FF0044;
    }

    h1 {
        font-size: 20px;
        margin-top: 30px;
    }

    p {
        text-indent: 2em;
    }

    .evaluate {
        width: 100%;
        background-color: rgb(240, 240, 240);
        padding: 30px 15px 30px 15px;
    }

    .el-tab-pane {
        width: 80%;
        margin-left: 10%;
    }

    .boxAll {
        width: 80%;
        margin-left: 10%;
    }

    .goodsContent {
        border: 1px solid #99a9bf;
        width: 100%;
        min-height: 700px;
        margin-top: 60px;
    }

    .imgcount {
        float: left;
        width: 40%;
    }

    .goodsImg {
        border: 1px solid #99a9bf;
        width: 100%;
        margin-top: 30px;
        margin-left: 30px;
        padding-bottom: 100%;
        float: left;
        position: relative;
    }

    .Image {
        height: 100%;
        width: 100%;
        position: absolute;
        left: 0;
        top: 0;
    }

    .abkey {
        padding-top: 10px;
        padding-left: 15px;
    }

    .oneGoodsRight {
        /*border: 1px solid #99a9bf;*/
        width: 45%;
        float: left;
        margin-top: 30px;
        margin-right: 50px;
        float: right;
    }

    .detailsBox {
        width: 100%;
        /*padding-bottom: 100%;*/
        padding-left: 15px;
    }

    .oneGoodsTitle {
        margin-top: 20px;
        width: 90%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .price-bck {
        display: inline-block;
        width: 90%;
        margin-top: 30px;
        background-color: #fff2e8;
        padding: 15px 15px 15px 0;
    }

    small {
        color: #909399;
    }

    .price {
        color: #FF0044;
        font-size: 26px;
        margin-left: 3%;
        margin-right: 5%;
    }

    .oldPrice {
        text-decoration: line-through;
        color: #909399;
        margin-right: 5%;
    }

    .count {
        margin-right: 3%;
        color: #909399;
    }

    .el-divider {
        width: 90%;
    }

    .attribute {
        color: #000;
    }

    button {
        border: 1px solid #909399;
        font-size: 15px;
        padding: 5px;
        background-color: #fff;
        margin-left: 10px;
    }

    .attributeName, .goodsCountAttribute {
        color: #909399;
        font-size: 12px;
    }

    .goodsCount {
        margin-left: 80px;
    }

    .countInput {
        border: 1px solid #909399;
        border-radius: 0;
        width: 30px;
        text-align: center;
        height: 25px;
        line-height: 25px;
        float: left;
        padding: 0 0 0 0;
    }

    .icon-icon_cut, .icon-jia {
        border: 1px solid #909399;
        float: left;
        background-color: rgb(204, 204, 204);
        color: black;
        height: 25px;
        line-height: 25px;
        padding: 0 5px 0 5px;
    }

    .countValue {
        padding-top: 10px;
        padding-left: 15px;
    }

    .goodsImgList {
        /*border: 1px solid #99a9bf;*/
        /*width: 100%;*/
        float: left;
        margin-top: 20px;
        margin-left: 30px;
    }

    .goodsImgTr {
        /*width: 100%;*/
        border: 1px solid #99a9bf;
    }

    .goodsImgTr > td {
        width: 80px;
        /*width: 30%;*/
        padding-left: 10px;
    }

    .listContent {
        position: relative;
        padding-bottom: 100%;
        outline: 1px solid #99a9bf;

    }

    .imgList {
        width: 80px;
        position: absolute;
        top: 0;
        left: 0;
    }

    .goodsBtn {
        float: right;
        margin-right: 10%;
        margin-bottom: 5%;
    }

    .btn-buy {
        border: 1px solid red;
        padding: 10px 15px 10px 15px;
        color: red;
        background-color: transparent;
    }

    .btn-add {
        padding: 10px 15px 10px 15px;
        background-color: #FF0044;
        color: white;
    }

    .thisGoods {
        width: 15%;
        display: block;
    }
</style>
