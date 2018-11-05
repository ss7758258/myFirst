const Promise = require('./Promise')

const constellation = [{
	id: 1,
	name: '白羊座',
	time: '3.21~4.19',
	startTime: '3月21日',
	endTime: '4月19日',
	bgcolor: '#FFF1D8',
	bgc: 'rgba(255,192,64,0.1)',
	color: '#F08000',
	img: '/assets/img/1.svg'
}, {
	id: 2,
	name: '金牛座',
	time: '4.20~5.20',
	startTime: '4月20日',
	endTime: '5月20日',
	bgcolor: '#FFF2CA',
	bgc: 'rgba(255,207,48,0.1)',
	color: '#FFC400',
	img: '/assets/img/2.svg'
}, {
	id: 3,
	name: '双子座',
	time: '5.21~6.21',
	startTime: '5月21日',
	endTime: '6月21日',
	bgcolor: '#FFE8F3',
	bgc: 'rgba(239,110,110,0.1)',
	color: '#FF678E',
	img: '/assets/img/3.svg'
}, {
	id: 4,
	name: '巨蟹座',
	time: '6.22~7.22',
	startTime: '6月22日',
	endTime: '7月22日',
	bgcolor: '#FFEBEB',
	bgc: 'rgba(232,81,81,0.1)',
	color: '#FF6D6D',
	img: '/assets/img/4.svg'
}, {
	id: 5,
	name: '狮子座',
	time: '7.23~8.22',
	startTime: '7月23日',
	endTime: '8月22日',
	bgcolor: '#FFEDDD',
	bgc: 'rgba(255,177,41,0.1)',
	color: '#FFA200',
	img: '/assets/img/5.svg'
}, {
	id: 6,
	name: '处女座',
	time: '8.23~9.22',
	startTime: '8月23日',
	endTime: '9月22日',
	bgcolor: '#FFE9E9',
	bgc: 'rgba(255,209,207,0.1)',
	color: '#FF9A95',
	img: '/assets/img/6.svg'
}, {
	id: 7,
	name: '天秤座',
	time: '9.23~10.23',
	startTime: '9月23日',
	endTime: '10月23日',
	bgcolor: '#E5F5FF',
	bgc: 'rgba(0,160,255,0.1)',
	color: '#5DB5FF',
	img: '/assets/img/7.svg'
}, {
	id: 8,
	name: '天蝎座',
	time: '10.24~11.22',
	startTime: '10月24日',
	endTime: '11月22日',
	bgcolor: '#FFEDE2',
	bgc: 'rgba(215,145,100,0.2)',
	color: '#9E420F',
	img: '/assets/img/8.svg'
}, {
	id: 9,
	name: '射手座',
	time: '11.23~12.21',
	startTime: '11月23日',
	endTime: '12月21日',
	bgcolor: '#FFE8E8',
	bgc: 'rgba(235,98,98,0.1)',
	color: '#FF7070',
	img: '/assets/img/9.svg'
}, {
	id: 10,
	name: '摩羯座',
	time: '12.22~1.19',
	startTime: '12月22日',
	endTime: '1月19日',
	bgcolor: '#FFECE7',
	bgc: 'rgba(255,195,173,0.1)',
	color: '#FF7F52',
	img: '/assets/img/10.svg'
}, {
	id: 11,
	name: '水瓶座',
	time: '1.20~2.18',
	startTime: '1月20日',
	endTime: '2月18日',
	bgcolor: '#EDEAFF',
	bgc: 'rgba(141,118,255,0.1)',
	color: '#836AFF',
	img: '/assets/img/11.svg'
}, {
	id: 12,
	name: '双鱼座',
	time: '2.19~3.20',
	startTime: '2月19日',
	endTime: '3月20日',
	bgcolor: '#E6F0FF',
	bgc: 'rgba(106,167,255,0.1)',
	color: '#62A2FF',
	img: '/assets/img/12.svg'
}]

const REGX_HTML_DECODE = /&\w{1,};|&#\d{1,};/g;
const HTML_DECODE = {
	"&lt;": "<",
	"&gt;": ">",
	"&amp;": "&",
	"&nbsp;": " ",
	"&quot;": "\"",
	"&copy;": "©"
}

/**
 * 将小程序的API封装成支持Promise的API
 * @params fn {Function} 小程序原始API，如wx.login
 */
function wxPromisify(fn) {
	return function (obj = {}) {
		return new Promise((resolve, reject) => {
			obj.success = function (res) {
				resolve(res)
			}

			obj.fail = function (res) {
				reject(res)
			}

			fn(obj)
		})
	}
}

function makeArray(num, val) {
	var arr = []
	for (var i = 0; i < num; i++) {
		arr.push(typeof val !== 'undefined' ? val : i)
	}
	return arr
}

function decodeHtml(str) {
	return (typeof str != "string") ? str :
		str.replace(REGX_HTML_DECODE, function ($0) {
			var c = HTML_DECODE[$0]
			if (c === undefined) {
				var m = $0.match(/\d{1,}/);
				if (m) {
					var cc = m[0];
					cc = (cc === 0xA0) ? 0x20 : cc;
					c = String.fromCharCode(cc);
				} else {
					c = $0;
				}
			}
			return c;
		})
}



function getDateString(time) {
	let dateTime = new Date(time)
	let year = dateTime.getFullYear()
	let month = dateTime.getMonth() + 1
	let day = dateTime.getDate()
	return year + '年' + month + '月' + day + '日 '
}

function login() {
	return new Promise((resolve, reject) => wx.login({
		success: resolve,
		fail: reject
	}))
}

function getUserInfo() {
	return new Promise((resolve, reject) =>
		wx.getUserInfo({
			success: resolve,
			fail: reject
		})
	)
}

function parseIndex(data) {
	var myLuck = []
	var colors = ['#9262FB', '#DA6AE4', '#B3B4FF', '#88BB74']
	for (var i = 0; i < 4; i++) {
		let countStr = data['luckyScore' + (i + 1)]
		let count = countStr ? countStr.split('%')[0] : 0
		count = count > 100 ? 100 : count
		var d = {
			id: i,
			name: data['luckyType' + (i + 1)],
			color: colors[i],
			count: count
		}
		myLuck.push(d)
	}
	return myLuck
}

function parseToady(data) {
	var dataList = []
	for (var i = 0; i < 4; i++) {
		var d = {
			name: data['luckyTypeMore' + (i + 1)],
			img: '/assets/images/icon_star' + (i + 1) + '.png',
			level: data['luckyScoreMore' + (i + 1)],
			content: data['luckyWords' + (i + 1)],
			width: 8
		}
		// if (i == 3) {
		//   d.width = null
		// }
		dataList.push(d)
	}
	dataList.push({
		name: '去做',
		img: '/assets/images/icon_star5.png',
		content: data.toDo,
		width: 8
	})
	dataList.push({
		name: '别做',
		img: '/assets/images/icon_star6.png',
		content: data.notDo,
		width: 0
	})

	return dataList
}

function canvasTextAutoLine(ctx, str, initX, initY, lineHeight, exWidth) {

	var lineWidth = 0;
	var canvasWidth = 750 - exWidth;
	var lastSubStrIndex = 0;
	for (let i = 0; i < str.length; i++) {
		lineWidth += ctx.measureText(str[i]).width;
		if (lineWidth > canvasWidth - initX) { //减去initX,防止边界出现的问题
			ctx.fillText(str.substring(lastSubStrIndex, i), initX, initY);
			initY += lineHeight;
			lineWidth = 0;
			lastSubStrIndex = i;
		}
		if (i == str.length - 1) {
			ctx.fillText(str.substring(lastSubStrIndex, i + 1), initX, initY);
		}
	}
}


function parseLot(res) {
	console.log('未加工的数据信息：', res)
	let troops = []
	for (var i = 1; i < res.qianOpenSize + 1; i++) {
		if (res['friendOpenId' + i]) {
			troops.push({
				openId: res['friendOpenId' + i] || '',
				name: res['friendOpenId' + i] || '',
				photo: res['friendHeadImage' + i] || '/assets/images/default_head.png',
			})
		}
	}

	let lotTitleHint = '下面是你的每日一签，快找好友帮你拆签吧~'
	let helloText = ''
	if (res.status == 1) {
		if (res.isMyQian == 1) {
			lotTitleHint = '你的好友已帮你完成拆签'
			helloText = '你好！'
		} else {
			lotTitleHint = ''
			helloText = '此签已拆开，我在这里谢谢大家'
		}
	} else {
		if (res.isMyQian == 1) {
			lotTitleHint = '下面是你的每日一签，快去拆签吧'
			helloText = ''
		} else {
			lotTitleHint = '是否能够拆签成功，全都仰仗你们了！'
			helloText = '朋友们好，'
		}
	}
	if (res.buy === 0 || res.buy) {
		if (res.isMyQian == 1) {
			helloText = ''
			lotTitleHint = '恭喜你已完成拆签'
		} else {
			helloText = ''
			lotTitleHint = '此签已拆开，同样感谢你能来'
		}
	}

	let dates = res.qianDate.split('-')
	let timer = {
		day: dates[2],
		time: `${dates[0]}.${dates[1]}`
	}
	console.log(timer)
	console.log(`-----------------------------`, res.alreadyOpen > 0 || res.status == 1)
	let myLot = {
		lotTitleHint: lotTitleHint,
		id: res.id,
		qianOpenSize: res.qianOpenSize,
		// 是否是购买的签 存在即购买的
		isOpen: res.buy,
		// 当前用户是否已经帮拆签 或者签已经被拆开
		hasChai: res.alreadyOpen > 0 || res.status == 1,
		// 是否是其它的签
		isOther: res.isMyQian === 0,
		// 是否已经帮忙拆签
		showChai: res.isMyQian === 0,
		// 签是否还未打开
		lotNotCompleted: res.status === 0,
		ownerHeadImage: res.ownerHeadImage || '/assets/images/default_head.png',
		ownerNickName: res.ownerNickName,
		qianContent: res.qianContent,
		qianDate: res.qianDate,
		qianName: res.qianName,
		// 问候语
		helloText,
		// 用户画像
		troops: troops,
		time: timer
	}

	console.log("=====lotDetail=====")
	console.log('加工后的数据：', myLot)
	console.log("=====lotDetail=====")
	return myLot
}

// 区间随机数
function random(m,n){
	return Math.round(Math.random() * (n - m) + m)
}

/**
 * 绘制圆角矩形
 * @param {*} x
 * @param {*} y
 * @param {*} w
 * @param {*} h
 * @param {*} r
 * @returns
 */
function roundRect(x, y, w, h, r,ctx) {
	if (w < 2 * r) r = w / 2;
	if (h < 2 * r) r = h / 2;
	ctx.beginPath();
	ctx.moveTo(x+r, y);
	ctx.arcTo(x+w, y, x+w, y+h, r);
	ctx.arcTo(x+w, y+h, x, y+h, r);
	ctx.arcTo(x, y+h, x, y, r);
	ctx.arcTo(x, y, x+w, y, r);
	ctx.closePath();
	return ctx;
}

module.exports = {
	constellation,
	Promise,
	wxPromisify,
	makeArray,
	decodeHtml,
	login,
	getUserInfo,
	getDateString,
	parseToady,
	parseIndex,
	canvasTextAutoLine,
	parseLot,
	random,
	roundRect
}