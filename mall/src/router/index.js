import Vue from 'vue'
import VueRouter from 'vue-router'
import Mall from "@/views/Mall";
import ListOfGoods from "@/views/ListOfGoods";
import GoodsDetails from "@/views/GoodsDetails";
import Order from "@/views/Order";
import ManagementAddress from "@/views/ManagementAddress"
import ShoppingCart from "@/views/ShoppingCart"
import ReleaseIdle from "@/views/ReleaseIdle"
import OrderInquiry from "@/views/OrderInquiry";
import LeavingMessage from "@/views/LeavingMessage"
import PersonalData from "@/views/PersonalData";
import Login from "@/views/Login"
import Register from "@/views/Register"

Vue.use(VueRouter)
const router = new VueRouter({
    mode: 'history',
    routes: [{
        name: 'Mall',
        path: '/',
        component: Mall
    }, {
        name: 'ListOfGoods',
        path: '/ListOfGoods',
        component: ListOfGoods
    }, {
        name: 'GoodsDetails',
        path: '/GoodsDetails',
        component: GoodsDetails
    },{
        name: 'Order',
        path: '/Order',
        component: Order
    },{
        name: 'ManagementAddress',
        path: '/ManagementAddress',
        component: ManagementAddress
    },{
        name: 'ShoppingCart',
        path: '/ShoppingCart',
        component: ShoppingCart
    },{
        name: 'ReleaseIdle',
        path: '/ReleaseIdle',
        component: ReleaseIdle
    },{
        name: 'OrderInquiry',
        path: '/OrderInquiry',
        component: OrderInquiry
    },{
        name: 'LeavingMessage',
        path: '/LeavingMessage',
        component: LeavingMessage
    },{
        name: 'PersonalData',
        path: '/PersonalData',
        component: PersonalData
    },{
        name: 'Login',
        path: '/Login',
        component: Login
    },{
        name: 'Register',
        path: '/Register',
        component: Register
    }
    ]
})

export default router
