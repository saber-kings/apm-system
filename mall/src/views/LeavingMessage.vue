<template>
    <div style="width: 100%;">
        <div style="width: 80%;margin-left: 10%;">
            <h4 style="margin: 15px 0"></h4>
            <div style="width: 60%;outline: 2px solid #D939CD;margin-left: 20%">
                <div style="width: 100%;height: 30px;background-color: #D939CD;padding-top: 10px;padding-bottom: 5px">
                    <h3 style="margin-left: 20px;">我要留言</h3>
                </div>
                <div style="display: flex;margin-top: 30px;justify-content: center;">
                    <div>
                        <img src="" style="width: 130px;height: 130px">
                        <p>
                            商品名称
                        </p>
                        <p>
                            <span>属性</span>
                            <span>值</span>
                        </p>
                    </div>
                    <div>
                        <div style="background-color: #D939CD;padding: 10px 5px 30px 5px;margin-left: 30px">
                            <textarea rows="6"
                                      style="border: 1px solid #99a9bf;width: 400px;resize: vertical;font-size: 20px"></textarea>
                            <el-upload
                                    class=""
                                    action="#"
                                    :on-preview="handlePreview"
                                    :on-remove="handleRemove"
                                    list-type="picture">
                                <button type="button"
                                        style="padding: 5px 15px;background-color: #32CCBC;color: white">
                                    点击上传
                                </button>
                                <div style="display: inline-block;color: #fff;margin-left: 30px"
                                     slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb
                                </div>
                            </el-upload>
                        </div>
                        <div style="margin-top: 30px;margin-bottom: 20px;margin-left: 180px">
                            <button type="button"
                                    style="padding: 5px 15px;background-color: #3C8CE7;color: white">
                                发布留言
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "LeavingMessage",
        methods: {
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            }
        }
    }
</script>

<style scoped>

</style>
