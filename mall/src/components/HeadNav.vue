<template>
    <div class="headNav">
        <div class="nav-cont">
            <div class="nav-left">
                <img class="siteLogo" src="../assets/logo.png">
<!--                <div class="siteName">-->
<!--                    <p>APM</p>-->
<!--                </div>-->
            </div>
            <div></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HeadNav"
    }
</script>

<style scoped>
    .headNav{
        width: 100%;
        background-image: url("../assets/bg2.jpeg");
        background-size:100% 100%;
        background-repeat: no-repeat;
    }
    .nav-cont{
        width: 80%;
        margin-left: 10%;
        height: 210px;
    }

    .siteLogo{
        width: 170px;
        /*height: 100px;*/
        float: left;
    }

    /*@font-face {*/
    /*    font-family: RuslanDisplay;*/
    /*    src: url(../assets/RuslanDisplay.ttf);*/
    /*}*/

    /*.siteName{*/
    /*    font-size: 40px;*/
    /*    font-weight: bold;*/
    /*    font-family: "RuslanDisplay";*/
    /*    color: deeppink;*/
    /*    float: right;*/
    /*    text-align: center;*/
    /*    margin-top: 30px;*/
    /*}*/
    .nav-left{
        margin-left: 3%;
    }
</style>
