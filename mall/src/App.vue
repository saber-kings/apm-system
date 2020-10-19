<template>
    <div id="app">
        <Tops v-show="$route.path !=='/Register'&&$route.path !=='/Login'" :key="$route.fullPath"></Tops>
        <HeadNav v-show="$route.path !=='/Register'&&$route.path !=='/Login'"></HeadNav>
        <NavMenu v-show="$route.path !=='/Register'&&$route.path !=='/Login'"></NavMenu>
        <router-view v-if="isRouterAlive"/>
        <Footer v-show="$route.path !=='/Register'&&$route.path !=='/Login'"></Footer>
    </div>
</template>

<script>
    import HeadNav from "@/components/HeadNav";
    import Tops from "@/components/Tops";
    import NavMenu from "@/components/NavMenu";
    import Footer from "@/components/Footer";

    export default {
        name:'app',
        provide(){
          return{
              reload: this.reload
          }
        },
        data(){
          return{
              isRouterAlive:true
          }
        },
        components: {
            HeadNav, Tops, NavMenu, Footer
        },
        methods:{
            reload(){
                this.isRouterAlive = false;
                this.$nextTick(function () {
                    this.isRouterAlive = true;
                })
            }
        }
    }
</script>

<style>

</style>
