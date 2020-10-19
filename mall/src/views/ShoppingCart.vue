<template>
    <div style="width: 100%">
        <div style="width: 80%;margin-left: 10%">
            <div style="height: 100px;">
                <div style="float: left">
                    <h1 style="font-size: 20px;font-family: 幼圆;color: purple;">
                        <div style="display: inline-block;font-size:80px;color: purple"
                             class="icon iconfont icon-truck">
                        </div>
                        <span>
                        购物车
                    </span>
                    </h1>
                </div>
                <div style="float: right;width: 800px">
                    <el-steps :active="activeStep" finish-status="success" style="margin-top: 50px">
                        <el-step title="查看购物车"></el-step>
                        <el-step title="拍下商品"></el-step>
                        <el-step title="完成支付"></el-step>
                        <el-step title="收货评价"></el-step>
                    </el-steps>
                </div>
            </div>
            <!--            /***********************************购物车***************************************************/-->
            <div v-show="showList">
                <h4 style="margin-left: 15%;color: #ff6600">全部商品</h4>
                <el-divider></el-divider>
                <div>
                    <el-table border :data="goodsTable" @selection-change="handleSelectionChange">
                        <el-table-column type="selection"></el-table-column>
                        <el-table-column prop="goods" label="店铺宝贝" width="250px">
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
                                <input type="number"
                                       oninput="if(value>99)value=99;if(value<1)value=1"
                                       v-model="scope.row.count"
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
                                {{scope.row.goods.price*scope.row.count}}</strong>
                                </span>
                                <small style="display: inline-block;margin-left: 5px;color: #909399">元</small>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作"  fixed="right">
                            <template scope="scope">
                                <button type="button"
                                        style="border: none;background-color: transparent">移除
                                </button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-divider></el-divider>
                    <div style="padding: 0;height: 40px;background-color: rgb(229,229,229);width: 100%;display: flex;justify-content: space-between"
                         id="fixedDiv">
                        <div>
                            <button type="button"
                                    style="margin: 0!important;display: inline-block;padding: 11px 20px;background-color: #F60;color: white">
                                清空购物车
                            </button>
                        </div>
                        <div>
                            <small>当前选中</small>
                            <span style="font-size: 24px;color: #ff6600;display: inline-block;margin: 0 10px">{{totalCount}}</span>
                            <small>件商品</small>
                            <small>合计</small>
                            <span style="font-size: 24px;color: #ff6600;display: inline-block;margin: 0 10px">{{totalPrice}}</span>
                            <small>元(不含邮费)</small>
                            <div style="display: inline-block;margin-top: 0;margin-left: 20px">
                                <button type="button" @click="settlement"
                                        style="display: inline-block;padding: 11px 20px;background-color: #FF0044;color: white">
                                    结算
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--            /***********************************购物车***************************************************/-->
            <!--            /***********************************购物车结算***************************************************/-->
            <div v-show="showSettlement">
                <div>
                    <h4 style="margin-left: 15%;color: #ff6600">确认订单</h4>
                    <el-divider></el-divider>
                    <el-table border :data="checkedGoods" @selection-change="handleSelectionChange">
                        <el-table-column type="expand">
                            <template slot-scope="props">
                                <el-form label-position="left" inline class="demo-table-expand">
                                    <el-form-item label="配送方式">
                                        <div style="width: 200px;display: flex;justify-content: space-between;margin: 30px">
                                            <div>
                                                <span>{{props.row.goods.peisong.peisongfangshi }}</span>
                                            </div>
                                            <div>
                                                <span>邮费：</span>
                                                <span style="font-size: 18px;color: #FF0044"><strong>{{props.row.goods.peisong.peisongjiage }}</strong></span>
                                                <small>元</small>
                                            </div>
                                        </div>
                                    </el-form-item>
                                </el-form>
                            </template>
                        </el-table-column>
                        <el-table-column prop="goods" label="店铺宝贝" width="250px">
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
                                <span>{{scope.row.count}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="subtotal" label="小计" fixed="right" show-summary>
                            <template scope="scope">
                                <small style="color:#909399;display: inline-block;margin-right: 5px">￥</small>
                                <span style="font-size: 18px;color: #FF0044"><strong>
                                {{scope.row.goods.price*scope.row.count + scope.row.goods.peisong.peisongjiage}}</strong>
                                </span>
                                <small style="display: inline-block;margin-left: 5px;color: #909399">元</small>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="height: 80px">
                        <div style="float: right;margin-top: 30px">
                            <span>合计：</span>
                            <span style="color: #ff6600;font-size: 22px">{{settlementPrice}}</span>
                            <span>元</span>
                            <button style="padding: 10px 20px;background-color: #FF0044;color: white;margin-left: 30px">
                                提交订单
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!--            /***********************************购物车结算***************************************************/-->

        </div>
    </div>
</template>

<script>
    window.onload = function () {
        var p = document.getElementById('fixedDiv');
        //需要固定的元素距离顶部的距离
        var pTop = p.offsetTop
        window.onscroll = function () {
            //获取被卷去高度
            var sT = document.documentElement.scrollTop || document.body.scrollsToTop;
            // console.log(sT,pTop)
            if (sT <= pTop) {
                p.style.width = '80%'
                p.style.position = 'fixed';
                p.style.bottom = '0';
                p.style.zIndex = '999';
            } else {
                p.style.position = 'static';
                p.style.width = '100%'
            }
        }

    }
    export default {
        name: "ShoppingCart",
        computed: {},
        data() {
            return {
                activeStep: 0,
                showList: true,
                showSettlement: false,
                rowPrice: 0,
                settlementPrice: 0,
                checkedGoods: [],
                totalCount: 0,
                totalPrice: 0,
                goodsTable: [{
                    id: 1,
                    goods: {
                        name: '奥特曼手办',
                        price: 100,
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
                }, {
                    id: 2,
                    goods: {
                        name: '奥特曼手办',
                        price: 100,
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
                },
                    {
                        id: 3,
                        goods: {
                            name: '奥特曼手办',
                            price: 100,
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
                    }
                ]
            }
        }, methods: {
            claculatePrice() {
                this.totalPrice = 0;
                for (let i = 0; i < this.checkedGoods.length; i++) {
                    this.totalPrice += (this.checkedGoods[i].goods.price * this.checkedGoods[i].count);
                }
            },
            addgood(good) {
                let arr = this.goodsTable.filter(o => o.id == good.id)
                arr[0].count++;
                this.claculatePrice();
            },
            reducegood(good) {
                let arr = this.goodsTable.filter(o => o.id == good.id)
                if (arr[0].count > 1) {
                    arr[0].count--;
                    this.claculatePrice();
                }
            },
            handleSelectionChange(val) {
                this.checkedGoods = val
                console.log(this.checkedGoods)
                this.totalCount = this.checkedGoods.length;
                this.totalPrice = 0;
                for (let i = 0; i < this.checkedGoods.length; i++) {
                    this.totalPrice += (this.checkedGoods[i].goods.price * this.checkedGoods[i].count);
                }
            },
            settlement() {
                if (this.checkedGoods.length > 0) {
                    this.activeStep++;
                    this.showList = false;
                    this.showSettlement = true;

                    for (let i = 0; i < this.checkedGoods.length; i++) {
                        this.settlementPrice += (this.checkedGoods[i].goods.price * this.checkedGoods[i].count) + this.checkedGoods[i].goods.peisong.peisongjiage;
                    }
                } else {
                    this.$message({
                        message: '您还没有选中商品哦',
                        type: 'warning',
                        offset: 200
                    });
                }
            }
        }
    }
</script>

<style scoped>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }

    input[type="number"] {
        -moz-appearance: textfield;
    }
</style>
