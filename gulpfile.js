const gulp = require('gulp')
const tsc = require('gulp-typescript')
const babel = require('gulp-babel')
const sass = require('gulp-sass')
const cssnano = require('gulp-cssnano')
const uglify = require('gulp-uglify')
const clean = require('gulp-clean')
const htmlmin = require('gulp-htmlmin')
const sequence = require('run-sequence')
const assets = require('./assets')
const createC = require('./createConf')
const rename = require("gulp-rename")
sass.compiler = require('node-sass')

// tsc的配置信息
const tsConfig = tsc.createProject('tsconfig.json')

// 编译ts
gulp.task('compile:ts', function () {
  return gulp.src(['src/**/*.ts'])
    .pipe(tsConfig())
    .pipe(gulp.dest('dist/'))
});

// 编译es
gulp.task('compile:es', function () {
  return gulp.src(['src/**/*.js'])
    .pipe(babel())
    // .pipe(uglify())
    .pipe(gulp.dest('dist/'))
});

// 编译scss
gulp.task("compile:scss", function () {
  return gulp.src(['src/**/*.{scss,sass}'])
    .pipe(sass({
      outputStyle: 'expanded'
    }).on('error', sass.logError))
    .pipe(cssnano({autoprefixer:false}))
    .pipe(rename({
      extname: ".wxss"
    }))
    .pipe(gulp.dest("dist/"));
});

// 编译wxss
gulp.task("compile:wxss", function () {
  return gulp.src(['src/**/*.wxss'])
    // .pipe(cssnano())
    .pipe(gulp.dest("dist/"));
});

// 编译wxml
gulp.task("compile:wxml", function () {
  return gulp.src(['src/**/*.wxml'])
    // .pipe(htmlmin())
    .pipe(gulp.dest("dist/"));
});

// 复制文件
gulp.task("copy:all", function () {
  return gulp.src(['src/**/*.json'])
    .pipe(gulp.dest("dist/"));
});

// 复制所有的静态资源文件
gulp.task("copy:assets", function () {
  return gulp.src(assets)
    .pipe(gulp.dest("dist/"));
});

// 清除文件夹
gulp.task("clean", function () {
  console.log('清空 dist 目录下的资源')
  return gulp.src('dist/*', {
      read: false
    })
    .pipe(clean({
      force: true
    }));
  console.log('清空 dist 目录 SUCCESS')
})

// 默认构建任务
gulp.task('default',gulp.series('clean' ,'compile:es', 'compile:ts', 'compile:wxml', 'compile:scss', 'compile:wxss', 'copy:all', 'copy:assets',() => {
  console.log('任务完成')
  console.log('----------------------文件监听注册------------------------')
  // 实时刷新
  gulp.watch('src/**/*.js', gulp.series('compile:es'))
  gulp.watch('src/**/*.ts', gulp.series('compile:ts'))
  gulp.watch('src/**/*.wxml', gulp.series('compile:wxml'))
  gulp.watch('src/**/*.{scss,sass}', gulp.series('compile:scss'))
  gulp.watch('src/**/*.wxss', gulp.series('compile:wxss'))
  gulp.watch(assets, gulp.series('copy:assets'))
  console.log('----------------------等待-----------------------------')
  createC()
  console.log('----------------------编译完成-------------------------')
}))
