const fs = require('fs')
const path = require('path')
const argv  = require('optimist').argv;

const env = argv.env || 'prod'

const createAppVar = (read,env,writ) => {
  let tmp = path.join(__dirname,`${writ}/wxProcessConf.js`)
  console.log('--------------------读取文件')
  // let app = fs.readFileSync(tmp,{encoding:'utf-8'})
  let app = `module.exports = {env : '${env}'}`
  fs.writeFileSync(tmp,app)
  console.log('--------------------写入文件成功')
}

module.exports = () => {
  createAppVar('src',env,'dist')
}
 