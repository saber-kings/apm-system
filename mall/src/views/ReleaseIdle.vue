<template>
    <div style="width: 100%">
        <div style="width: 80%;margin-left: 10%">
            <div>
                <div style="padding: 20px">
                    <h1 style="font-size: 20px;font-family: 幼圆;color: hotpink;">
                        <div style="display: inline-block;font-size:80px;color: hotpink"
                             class="icon iconfont icon-ershoushebei">
                        </div>
                        发布闲置
                    </h1>
                </div>
                <div style="padding: 30px 20% 30px 20%">
                    <div style="clear: both">
                        <h4>闲置商品名称</h4>
                        <input type="text"
                               style="width: 50%;margin-left: 20%;padding: 0;border: 1px solid #99a9bf"/>
                    </div>
                    <div style="clear: both">
                        <h4>闲置商品介绍</h4>
                        <textarea rows="5" type="text"
                                  style="resize: vertical;width: 50%;margin-left: 20%;padding: 0;border: 1px solid #99a9bf"/>
                    </div>
                    <div>
                        <h4 style="margin-bottom: 30px;margin-top: 15px">上传闲置商品照片</h4>
                        <!--            action	必选参数，上传的地址-->
                        <!--            on-preview	点击文件列表中已上传的文件时的钩子-->
                        <!--            on-remove	文件列表移除文件时的钩子-->
                        <!--            list-type	文件列表的类型-->
                        <div style="margin-left: 20%">
                            <el-upload
                                    action="#"
                                    list-type="picture-card"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                                <img width="100%" :src="dialogImageUrl" alt="">
                            </el-dialog>
                        </div>
                    </div>
                    <div style="position: relative">
                        <h4>选择分类</h4>
                        <el-select :popper-append-to-body="false" placeholder="请选择闲置商品分类" style="width: 50%;margin-left: 20%">
                            <el-option
                                    v-for="item in 6">{{item}}
                            </el-option>
                        </el-select>
                    </div>
                    <div>
                        <h4>请选择商品属性</h4>
                        <div style="margin-left: 20%">
                            <span v-for="i in 6">属性名{{i}}
                                <el-checkbox v-for="i in 3"
                                             :label="'属性值'+i"
                                             border>

                                </el-checkbox>
                                <el-divider></el-divider>
                            </span>
                        </div>
                    </div>
                    <div style="width: 100%;text-align: center">
                        <button type="button">立即发布</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ReleaseIdle",
        data() {
            return {
                dialogImageUrl: '',
                dialogVisible: false
            };
        },
        methods: {
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            }
        }
    }
</script>

<style>
    .el-icon-arrow-up:before {
       display: none;
    }

    .el-divider--horizontal{
        margin: 15px 0 !important;
    }

    .el-select-dropdown{
        position: absolute !important;
        top: 35px !important;
        left: 0px !important;
    }
</style>
