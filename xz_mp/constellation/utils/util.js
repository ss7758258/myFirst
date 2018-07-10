const Promise = require('./Promise')


const constellation = [{ id: 1, name: '白羊座', time: '3.21-4.19', img: '/assets/images/aries.png' }, { id: 2, name: '金牛座', time: '4.20-5.20', img: '/assets/images/taurus.png' }, { id: 3, name: '双子座', time: '5.21-6.21', img: '/assets/images/gemini.png' }, { id: 4, name: '巨蟹座', time: '6.22-7.22', img: '/assets/images/cancer.png' }, { id: 5, name: '狮子座', time: '7.23-8.22', img: '/assets/images/leo.png' }, { id: 6, name: '处女座', time: '8.23-9.22', img: '/assets/images/virgo.png' }, { id: 7, name: '天秤座', time: '9.23-10.23', img: '/assets/images/libra.png' }, { id: 8, name: '天蝎座', time: '10.24-11.22', img: '/assets/images/capricornus.png' }, { id: 9, name: '射手座', time: '11.23-12.21', img: '/assets/images/sagittarius.png' }, { id: 10, name: '摩羯座', time: '12.22-1.19', img: '/assets/images/scorpio.png' }, { id: 11, name: '水瓶座', time: '1.20-2.18', img: '/assets/images/aquarius.png' }, { id: 12, name: '双鱼座', time: '2.19-3.20', img: '/assets/images/pisces.png' }];



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
		if (lineWidth > canvasWidth - initX) {//减去initX,防止边界出现的问题
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
			lotTitleHint = '此签已完成拆签'
			helloText = '在这里谢谢大家'
		}
	} else {
		if (res.isMyQian == 1) {
			lotTitleHint = '下面是你的每日一签，快找好友帮你拆签吧~'
			helloText = '你好！'
		} else {
			lotTitleHint = '是否能够拆签成功，全都仰仗你们了！'
			helloText = '朋友们好，我是'
		}
	}
	if(res.buy === 0 || res.buy){
		helloText = '使用星星拆签完成'
		lotTitleHint = '使用星星拆签完成'
	}
	
	let dates = res.qianDate.split('-')
	let timer = {
		day : dates[2],
		time : `${dates[0]}.${dates[1]}`
	}
	console.log(timer)
	console.log(`-----------------------------`,res.alreadyOpen > 0 || res.status == 1)
	let myLot = {
		lotTitleHint: lotTitleHint,
		id: res.id,
		qianOpenSize: res.qianOpenSize,
		// 是否是购买的签 存在即购买的
		isOpen : res.buy,
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
		time : timer
	}

	console.log("=====lotDetail=====")
	console.log('加工后的数据：', myLot)
	console.log("=====lotDetail=====")
	return myLot
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
	parseLot
}
