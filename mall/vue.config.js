module.exports = {
  devServer: {
        open: true,
		// disableHostCheck: false,
		host: "localhost",
		port: 8081,
        https: false,
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // 接口的域名
                ws: true,
                changOrigin: true,//允许跨域
                pathRewrite: {
                    '^/api': ''
                }
            }
        },
  }
}
