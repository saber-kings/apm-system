<template>
    <div style="width: 100%;">
        <div style="width: 80%;margin-left: 10%">
            <div>
                <div style="padding: 20px;">
                    <h1 style="font-size: 20px;font-family: 幼圆;color: royalblue;">
                        <div style="display: inline-block;font-size:80px;color: royalblue"
                             class="icon iconfont icon-dingdan">
                        </div>
                        查看订单
                    </h1>
                </div>
                <div style="margin-top: 30px">
                    <el-tabs type="border-card">
                        <el-tab-pane label="全部订单">
                            <template>
                                <div style="width: 100%">
                                    <div style="border: 1px solid #99a9bf;display: flex;background-color: #e5e9f2">
                                        <span style="display: inline-block;width: 25%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">商品</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">属性</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">单价</span>
                                        <span style="display: inline-block;width: 5%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">数量</span>
                                        <span style="display: inline-block;width: 15%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">实付款</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">状态</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">操作</span>
                                    </div>
                                    <div v-for="(i,index) in allorder">
                                        <div style="width: 98%;background-color: #ABDCFF;padding: 1%;margin-top: 15px">
                                            <span style="display: inline-block;font-size: 12px;color: #999;">创建时间:</span>
                                            <span style="display: inline-block;font-size: 16px;margin:0 15px;color: #666;font-weight: bold">{{i.addTime|formatDate}}</span>
                                            <span style="display: inline-block;font-size: 12px;color: #999">订单编号:</span>
                                            <span style="display: inline-block;font-size: 16px;margin-left: 15px;color: #666;font-weight: bold">{{i.orderId}}</span>
                                            <span class="del"
                                                  style="float: right;margin-right: 15px;cursor: pointer"><i
                                                    class="el-icon-delete" @click="remOrder(i)"></i></span>
                                        </div>
                                        <div style="border: 1px solid #ABDCFF;display:flex;" @click="toPageGoods(index)">
                                            <div style="padding: 15px;width: 25%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;">
                                                    <img :src="i.cartInfo.product.pic"
                                                         style="width: 100px;height: 100px;">
                                                    <span><p>{{i.cartInfo.product.name}}</p></span>
                                                </div>
                                            </div>
                                            <div style="padding: 15px;width: 10%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;justify-content: space-evenly">
                                                    <span v-for="j in i.cartInfo.product.detail">{{j}}</span>
                                                </div>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.cartInfo.product.price}}</span>
                                                <span>元</span>
                                            </div>
                                            <div style="width: 5%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>{{i.cartInfo.product.totalNum}}</span>
                                                <span>件</span>
                                            </div>
                                            <div style="width: 15%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.totalPrice}}</span>
                                                <span>元</span>
                                                <p>含运费（{{i.totalPostage}}元）</p>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>{{i.status}}</span>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span v-show="i.status=='待发货'||i.status=='待收货'"
                                                      style="outline: 3px solid #81FBB8;padding: 5px 10px;color: #0396FF;cursor: pointer;display: block"><i
                                                        class="icon iconfont icon-icon-receive"></i>确认收货</span>
                                                <span v-show="i.status=='未支付'"
                                                      style="outline: 3px solid #EE9AE5;padding: 5px 15px;color: #E80505;cursor: pointer;display: block "><i
                                                        class="icon iconfont icon-fukuan"></i>去付款</span>
                                                <span v-show="i.status=='已收货'||i.status=='待评价'"
                                                      style="outline: 3px solid #90F7EC;padding: 5px 15px;color: #C32BAC;display: block;cursor: pointer "><i
                                                        class="icon iconfont icon-liuyan"></i>去留言</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-tab-pane>
                        <el-tab-pane label="待付款">
                            <template>
                                <div style="width: 100%">
                                    <div style="border: 1px solid #99a9bf;display: flex;background-color: #e5e9f2">
                                        <span style="display: inline-block;width: 25%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">商品</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">属性</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">单价</span>
                                        <span style="display: inline-block;width: 5%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">数量</span>
                                        <span style="display: inline-block;width: 15%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">实付款</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">状态</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">操作</span>
                                    </div>
                                    <div v-for="(i,index) in nopayorder">
                                        <div style="width: 98%;background-color: #ABDCFF;padding: 1%;margin-top: 15px">
                                            <span style="display: inline-block;font-size: 12px;color: #999;">创建时间:</span>
                                            <span style="display: inline-block;font-size: 16px;margin:0 15px;color: #666;font-weight: bold">{{i.addTime|formatDate}}</span>
                                            <span style="display: inline-block;font-size: 12px;color: #999">订单编号:</span>
                                            <span style="display: inline-block;font-size: 16px;margin-left: 15px;color: #666;font-weight: bold">{{i.orderId}}</span>
                                            <span class="del"
                                                  style="float: right;margin-right: 15px;cursor: pointer"><i
                                                    class="el-icon-delete" @click="remOrder(i)"></i></span>
                                        </div>
                                        <div style="border: 1px solid #ABDCFF;display:flex;">
                                            <div style="padding: 15px;width: 25%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;">
                                                    <img :src="i.cartInfo.product.pic"
                                                         style="width: 100px;height: 100px;">
                                                    <span><p>{{i.cartInfo.product.name}}</p></span>
                                                </div>
                                            </div>
                                            <div style="padding: 15px;width: 10%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;justify-content: space-evenly">
                                                    <span v-for="j in i.cartInfo.product.detail">{{j}}</span>
                                                </div>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.cartInfo.product.price}}</span>
                                                <span>元</span>
                                            </div>
                                            <div style="width: 5%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>{{i.cartInfo.product.totalNum}}</span>
                                                <span>件</span>
                                            </div>
                                            <div style="width: 15%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.totalPrice}}</span>
                                                <span>元</span>
                                                <p>含运费（{{i.totalPostage}}元）</p>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>待付款</span>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span style="outline: 3px solid #EE9AE5;padding: 5px 15px;color: #E80505;cursor: pointer;display: block "><i
                                                    class="icon iconfont icon-fukuan"></i>去付款</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-tab-pane>
                        <el-tab-pane label="进行中">
                            <template>
                                <div style="width: 100%">
                                    <div style="border: 1px solid #99a9bf;display: flex;background-color: #e5e9f2">
                                        <span style="display: inline-block;width: 25%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">商品</span>
                                        <span style="display: inline-block;width: 20%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">属性</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">单价</span>
                                        <span style="display: inline-block;width: 5%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">数量</span>
                                        <span style="display: inline-block;width: 15%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">实付款</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">状态</span>
                                    </div>
                                    <div v-for="(i,index) in ingorder">
                                        <div style="width: 98%;background-color: #ABDCFF;padding: 1%;margin-top: 15px">
                                            <span style="display: inline-block;font-size: 12px;color: #999;">创建时间:</span>
                                            <span style="display: inline-block;font-size: 16px;margin:0 15px;color: #666;font-weight: bold">{{i.addTime|formatDate}}</span>
                                            <span style="display: inline-block;font-size: 12px;color: #999">订单编号:</span>
                                            <span style="display: inline-block;font-size: 16px;margin-left: 15px;color: #666;font-weight: bold">{{i.orderId}}</span>
                                            <span class="del"
                                                  style="float: right;margin-right: 15px;cursor: pointer"><i
                                                    class="el-icon-delete" @click="remOrder(i)"></i></span>
                                        </div>
                                        <div style="border: 1px solid #ABDCFF;display:flex;">
                                            <div style="padding: 15px;width: 25%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;">
                                                    <img :src="i.cartInfo.product.pic"
                                                         style="width: 100px;height: 100px;">
                                                    <span><p>{{i.cartInfo.product.name}}</p></span>
                                                </div>
                                            </div>
                                            <div style="padding: 15px;width: 20%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;justify-content: space-evenly">
                                                    <span v-for="j in i.cartInfo.product.detail">{{j}}</span>
                                                </div>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.cartInfo.product.price}}</span>
                                                <span>元</span>
                                            </div>
                                            <div style="width: 5%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>{{i.cartInfo.product.totalNum}}</span>
                                                <span>件</span>
                                            </div>
                                            <div style="width: 15%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.totalPrice}}</span>
                                                <span>元</span>
                                                <p>含运费（{{i.totalPostage}}元）</p>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span style="outline: 3px solid #81FBB8;padding: 5px 10px;color: #0396FF;cursor: pointer;display: block"><i
                                                    class="icon iconfont icon-icon-receive"></i>确认收货</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-tab-pane>
                        <el-tab-pane label="已完成">
                            <template>
                                <div style="width: 100%" v-for="(i,index) in overorder">
                                    <div style="border: 1px solid #99a9bf;display: flex;background-color: #e5e9f2">
                                        <span style="display: inline-block;width: 25%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">商品</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">属性</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">单价</span>
                                        <span style="display: inline-block;width: 5%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">数量</span>
                                        <span style="display: inline-block;width: 15%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">实付款</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">状态</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">操作</span>
                                    </div>
                                    <div style="width: 98%;background-color: #ABDCFF;padding: 1%;margin-top: 15px">
                                        <span style="display: inline-block;font-size: 12px;color: #999;">创建时间:</span>
                                        <span style="display: inline-block;font-size: 16px;margin:0 15px;color: #666;font-weight: bold">{{i.addTime|formatDate}}</span>
                                        <span style="display: inline-block;font-size: 12px;color: #999">订单编号:</span>
                                        <span style="display: inline-block;font-size: 16px;margin-left: 15px;color: #666;font-weight: bold">{{i.orderId}}</span>
                                        <span class="del"
                                              style="float: right;margin-right: 15px;cursor: pointer"><i
                                                class="el-icon-delete"
                                                @click="remOrder(i)"></i></span>
                                    </div>
                                    <div style="border: 1px solid #ABDCFF;display:flex;">
                                        <div style="padding: 15px;width: 25%;border-right: 1px dashed #ABDCFF;">
                                            <div style="display: flex;">
                                                <img :src="i.cartInfo.product.pic"
                                                     style="width: 100px;height: 100px;">
                                                <span><p>{{i.cartInfo.product.name}}</p></span>
                                            </div>
                                        </div>
                                        <div style="padding: 15px;width: 10%;border-right: 1px dashed #ABDCFF;">
                                            <div style="display: flex;justify-content: space-evenly">
                                                <span v-for="j in i.cartInfo.product.detail">{{j}}</span>
                                            </div>
                                        </div>
                                        <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span>￥</span>
                                            <span>{{i.cartInfo.product.price}}</span>
                                            <span>元</span>
                                        </div>
                                        <div style="width: 5%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span>{{i.cartInfo.product.totalNum}}</span>
                                            <span>件</span>
                                        </div>
                                        <div style="width: 15%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span>￥</span>
                                            <span>{{i.totalPrice}}</span>
                                            <span>元</span>
                                            <p>含运费（{{i.totalPostage}}元）</p>
                                        </div>
                                        <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <span>已完成</span>
                                        </div>
                                        <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                            <router-link
                                                    :to="{name:'LeavingMessage',params:{key:1}}">
                                                <span style="outline: 3px solid #90F7EC;padding: 5px 15px;color: #C32BAC;display: block;cursor: pointer "><i
                                                        class="icon iconfont icon-liuyan"></i>去留言</span>
                                            </router-link>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-tab-pane>
                        <el-tab-pane label="退款订单">
                            <template>
                                <div style="width: 100%">
                                    <div style="border: 1px solid #99a9bf;display: flex;background-color: #e5e9f2">
                                        <span style="display: inline-block;width: 25%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">商品</span>
                                        <span style="display: inline-block;width: 20%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">属性</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">单价</span>
                                        <span style="display: inline-block;width: 5%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">数量</span>
                                        <span style="display: inline-block;width: 15%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">实付款</span>
                                        <span style="display: inline-block;width: 10%;text-align: center;border-right:1px solid #99a9bf;padding: 15px">状态</span>
                                    </div>
                                    <div v-for="(i,index) in refundorder">
                                        <div style="width: 98%;background-color: #ABDCFF;padding: 1%;margin-top: 15px">
                                            <span style="display: inline-block;font-size: 12px;color: #999;">创建时间:</span>
                                            <span style="display: inline-block;font-size: 16px;margin:0 15px;color: #666;font-weight: bold">{{i.addTime|formatDate}}</span>
                                            <span style="display: inline-block;font-size: 12px;color: #999">订单编号:</span>
                                            <span style="display: inline-block;font-size: 16px;margin-left: 15px;color: #666;font-weight: bold">{{i.orderId}}</span>
                                            <span class="del"
                                                  style="float: right;margin-right: 15px;cursor: pointer"><i
                                                    class="el-icon-delete"
                                                    @click="remOrder(i)"></i></span>
                                        </div>

                                        <div style="border: 1px solid #ABDCFF;display:flex;">
                                            <div style="padding: 15px;width: 25%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;">
                                                    <img :src="i.cartInfo.product.pic"
                                                         style="width: 100px;height: 100px;">
                                                    <span><p>{{i.cartInfo.product.name}}</p></span>
                                                </div>
                                            </div>
                                            <div style="padding: 15px;width: 20%;border-right: 1px dashed #ABDCFF;">
                                                <div style="display: flex;justify-content: space-evenly">
                                                    <span v-for="j in i.cartInfo.product.detail">{{j}}</span>
                                                </div>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.cartInfo.product.price}}</span>
                                                <span>元</span>
                                            </div>
                                            <div style="width: 5%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>{{i.cartInfo.product.totalNum}}</span>
                                                <span>件</span>
                                            </div>
                                            <div style="width: 15%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>￥</span>
                                                <span>{{i.totalPrice}}</span>
                                                <span>元</span>
                                                <p>含运费（{{i.totalPostage}}元）</p>
                                            </div>
                                            <div style="width: 10%;border-right: 1px dashed #ABDCFF;padding: 15px;text-align: center">
                                                <span>已完成</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import service from "@/utils/request";
    import {formatDate} from '@/utils/date';

    export default {
        inject: ['reload'],
        name: "OrderInquiry",
        data() {
            return {
                allorder: [],
                nopayorder: [],
                ingorder: [],
                overorder: [],
                refundorder: []
            }
        },
        created() {
            let user = window.sessionStorage.getItem("user");
            user = JSON.parse(user);
            // console.log(user.userId)
            service.get('/api/order/' + user.userId).then((response) => {
                console.log(response.data.data)
                this.allorder = response.data.data;
                for (let i = 0; i < this.allorder.length; i++) {
                    this.allorder[i].cartInfo = JSON.parse(this.allorder[i].cartInfo);
                }
                // this.allorder = response.data.object.orderList;
                //
                for (let i = 0; i < this.allorder.length; i++) {
                    if (this.allorder[i].paid == 1) {
                        this.allorder[i].status = "未支付";
                    } else if (this.allorder[i].status == -1) {
                        this.allorder[i].status = "申请退款";
                    } else if (this.allorder[i].status == -2) {
                        this.allorder[i].status = "退款成功";
                    } else if (this.allorder[i].status == 0) {
                        this.allorder[i].status = "待发货";
                    } else if (this.allorder[i].status == 1) {
                        this.allorder[i].status = "待收货";
                    } else if (this.allorder[i].status == 3) {
                        this.allorder[i].status = "待评价";
                    } else if (this.allorder[i].status == 2) {
                        this.allorder[i].status = "已收货";
                    }
                }
                //
                for (let i = 0; i < this.allorder.length; i++) {
                    if (this.allorder[i].paid == 1) {
                        this.nopayorder.push(this.allorder[i]);
                        // this.nopaygoods.push(this.allgoods[i]);
                    } else {
                        if (this.allorder[i].status == "待发货" || this.allorder[i].status == "申请退款") {
                            this.ingorder.push(this.allorder[i]);
                            // this.inggoods.push(this.allgoods[i]);
                        } else if (this.allorder[i].status == "待评价") {
                            this.overorder.push(this.allorder[i]);
                            // this.overgoods.push(this.allgoods[i]);
                        } else if (this.allorder[i].status == "申请退款" || this.allorder[i].status == "退款成功") {
                            this.refundorder.push(this.allorder[i]);
                            // this.refundgoods.push(this.allgoods[i]);
                        }
                    }
                }
                //
                // console.log(this.nopaygoods)
            }).catch(function (error) {
                console.log(error);
            });
        },
        methods: {
            remOrder(item) {
                // console.log(item.orderId)
                var qs = require("querystring")
                let user = window.sessionStorage.getItem("user");
                user = JSON.parse(user);
                service.put('/api/order/cancel/',  {orderId: item.orderId,userId:user.userId})
                    .then((response) => {
                        if (response.data.msg == "操作成功") {
                            this.$message({
                                message: "删除成功！",
                                type: "success"
                            });
                            this.reload()
                        } else {
                            this.$message({
                                message: "删除失败！",
                                type: "error"
                            });
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            toPageGoods(index){
                localStorage.setItem("goodsid",this.allorder[index].cartInfo.product.productId);
                this.$router.push("/GoodsDetails");
            }
        },
        filters: {
            formatDate(time) {
                var date = new Date(time);
                return formatDate(date, 'yyyy-MM-dd hh:mm');
            },

        }
    }
</script>

<style scoped>
    .del:hover {
        transform: scale(1.5);
    }
</style>
