$(function() {
	var myChart = echarts.init(document.getElementById('main'));
	option = {
		title: {
			text: '某站点用户访问来源',
			subtext: '纯属虚构',
			left: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: '{a} <br/>{b} : {c} ({d}%)'
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['用户介绍', '水滴信用', '微信广告', '记事本电话', '脉脉', '贴吧']
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: '80%',
			center: ['50%', '50%'],
			data: [{
					value: 335,
					name: '用户介绍'
				},
				{
					value: 310,
					name: '水滴信用'
				},
				{
					value: 234,
					name: '微信广告'
				},
				{
					value: 135,
					name: '记事本电话'
				},
				{
					value: 1548,
					name: '脉脉'
				},
				{
					value: 1548,
					name: '贴吧'
				}
			],
			emphasis: {
				itemStyle: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
	myChart.setOption(option);
})