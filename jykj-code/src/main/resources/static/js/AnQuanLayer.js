// 基于准备好的dom，初始化echarts实例
$(function() {
	//------------------------------------------------------->登录页面<---------------------------------------------
	layui.use(['carousel', 'form'], function() {
		var carousel = layui.carousel,
			form = layui.form;

		//自定义验证规则
		form.verify({
			userName: function(value) {
				if(value.length < 5) {
					return '账号至少得5个字符';
				}
			},
			pass: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
			vercodes: function(value) {
				//获取验证码
				var zylVerCode = $(".zylVerCode").html();
				if(value != zylVerCode) {
					return '验证码错误（区分大小写）';
				}
			},
			content: function(value) {
				layedit.sync(editIndex);
			}
		});

		//监听提交
		form.on('submit(demo1)', function(data) {
			var index = layer.load(2, {
				time: 20 * 1000
			}); //又换了种风格，并且设定最长等待10秒
			//关闭
			layer.close(index);
			$.ajax({
				url: "http://localhost:8081/Userlogin",
				data: JSON.stringify(data.field),
				type: "post",
				dataType: "text",
				contentType: "application/json",
				success: function(res) {
					if(res == "success") {
						layer.msg('登录成功！', {
							icon: 1,
							title: "欢迎回来！",
							time: 1000
						}, function() {
							parent.location.href = "/index";
						});

					}
					if(res == "error") {
						$("[name='userName']").val("");
						$("[name='nuse']").val("");
						$("[name='vercode']").val("");
						layer.msg('账户或密码错误！', {
							icon: 2,
							title: '登录异常！'
						});
					}
					if(res == "Ban") {
						layer.msg('您的账号已被封禁，请稍后再试！', {
							icon: 2,
							title: '登录异常！'
						});
					}
					if(res=="frozen"){
						layer.msg('您的账号由于您的账号密码错误超过5次，已被临时封禁，请十分钟后再试！错一次又需要等十分钟，请慎重输入！！！！', {
							icon: 2,
							title: '账户异常！',
							time:10000
						});
					}
				},
				error: function(result) {
					layer.msg('错误，请联系管理员!', {
						icon: 2,
						title: '系统异常！'
					});
				}
			});
			return false;
		});

		//设置轮播主体高度
		var zyl_login_height = $(window).height() / 1.3;
		var zyl_car_height = $(".zyl_login_height").css("cssText", "height:" + zyl_login_height + "px!important");

		//Login轮播主体
		carousel.render({
			elem: '#zyllogin' //指向容器选择器
			,
			width: '100%' //设置容器宽度
			,
			height: 'zyl_car_height',
			arrow: 'always' //始终显示箭头
			,
			anim: 'fade' //切换动画方式
			,
			autoplay: true //是否自动切换false true
			,
			arrow: 'hover' //切换箭头默认显示状态||不显示：none||悬停显示：hover||始终显示：always
			,
			indicator: 'none' //指示器位置||外部：outside||内部：inside||不显示：none
			,
			interval: '5000' //自动切换时间:单位：ms（毫秒）
		});

		//监听轮播--案例暂未使用
		carousel.on('change(zyllogin)', function(obj) {
			var loginCarousel = obj.index;
		});

		//粒子线条
		$(".zyl_login_cont").jParticle({
			background: "rgba(0,0,0,0)", //背景颜色
			color: "#fff", //粒子和连线的颜色
			particlesNumber: 100, //粒子数量
			//disableLinks:true,//禁止粒子间连线
			//disableMouse:true,//禁止粒子间连线(鼠标)
			particle: {
				minSize: 1, //最小粒子
				maxSize: 3, //最大粒子
				speed: 30, //粒子的动画速度
			}
		});
	});
	//------------------------------------------------------->主页面<---------------------------------------------
	$("#OutLogin").click(function() {
		layer.confirm('将清除本地的Cookie,下次将无法免密登录！', {
			icon: 2,
			title: '提示'
		}, function(index) {
			//do something
			layer.close(index);
			layer.msg('退出成功！再见', {
				icon: 1
			}, function() {
				parent.location.href = "./login.html";
			});
		});
	})
	//基本信息弹窗 
	$("#UserInfo").click(function() {
		var uid = $("#uid").text();
		layer.open({
			type: 2,
			title: '基本信息',
			area: ['70%', '80%'],
			shade: 0,
			maxmin: true,
			/*skin:'layui-layer-moly',*/
			skin: 'layui-layer-rim', //加上边框
			content: "create.html",
			success: function(layero, index) {
				// 获取子页面的iframe
				var iframe = window['layui-layer-iframe' + index];
				// 向子页面的全局函数child传参
				iframe.child(uid);
			}
		})
	})
	//基本信息弹窗 
	$("#SecuritySetting").click(function() {
		var uid = $("#uid").text();
		layer.open({
			type: 2,
			title: '账户安全设置',
			area: ['70%', '80%'],
			shade: 0,
			maxmin: true,
			/*skin:'layui-layer-moly',*/
			skin: 'layui-layer-rim', //加上边框
			content: "create.html",
			success: function(layero, index) {
				// 获取子页面的iframe
				var iframe = window['layui-layer-iframe' + index];
				// 向子页面的全局函数child传参
				iframe.child(uid);
			}
		})
	})
	layui.use('element', function() {
		var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

		//触发事件
		var active = {
			//在这里给active绑定几项事件，后面可通过active调用这些事件
			tabAdd: function(url, id, name) {
				//新增一个Tab项 传入三个参数，分别是tab页面的地址，还有一个规定的id，对应其标题，是标签中data-id的属性值
				//关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
				element.tabAdd('home-tabs', {
					title: name,
					content: '<iframe id="aaa" data-frameid="' + id + '" scrolling="auto" frameborder="0" src="' + url +
						'" style="width:100%;height:1000px;"></iframe>',
					id: id, //规定好的id
					success: function(layero, index) {
						alert();
						// 获取子页面的iframe
						var iframe = window['layui-layer-iframe' + index];
						// 向子页面的全局函数child传参
						iframe.child(uid);
					}
				});
				//通过鼠标mouseover事件  动态将新增的tab下的li标签绑定鼠标右键功能的菜单
				//下面的json.id是动态新增Tab的id，一定要传入这个id,避免重复加载mouseover数据
				$(".layui-tab-title li[lay-id=" + id + "]").mouseover(function() {
					var tabId = $(this).attr("lay-id");
					CustomRightClick(tabId); //给tab绑定右击事件
					FrameWH(); //计算ifram层的大小
				});
			},
			tabChange: function(id) {
				//切换到指定Tab项
				element.tabChange('home-tabs', id); //根据传入的id传入到指定的tab项
			},
			tabDelete: function(id) {
				element.tabDelete('home-tabs', id); //删除
			},
			tabRefresh: function(id) { //刷新页面
				$("iframe[data-frameid='" + id + "']").attr("src", $("iframe[data-frameid='" + id + "']").attr("src")) //刷新框架
			}
		};

		//当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
		$('.site-demo-active').on('click', function() {
			var dataid = $(this);

			//这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
			if($(".layui-tab-title li[lay-id]").length <= 0) {
				//如果比零小，则直接打开新的tab项
				active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
			} else {
				//否则判断该tab项是否以及存在

				var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
				$.each($(".layui-tab-title li[lay-id]"), function() {
					//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
					if($(this).attr("lay-id") == dataid.attr("data-id")) {
						isData = true;
					}
				});
				if(!isData) {
					//标志为false 新增一个tab项
					active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
				}
			}
			//最后不管是否新增tab，最后都转到要打开的选项页面上
			active.tabChange(dataid.attr("data-id"));
		});

		function CustomRightClick(id) {
			//取消右键  rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
			$('.layui-tab-title li').on('contextmenu', function() {
				return false;
			});
			$('.layui-tab-title,.layui-tab-title li').click(function() {
				$('.rightmenu').hide();
			});
			//单击取消右键菜单
			$('body,#aaa').click(function() {
				$('.rightmenu').hide();
			});
			//tab点击右键
			$('.layui-tab-title li').on('contextmenu', function(e) {
				var popupmenu = $(".rightmenu");
				popupmenu.find("li").attr("data-id", id); //在右键菜单中的标签绑定id属性

				//判断右侧菜单的位置
				l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
				t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
				popupmenu.css({
					left: l,
					top: t
				}).show(); //进行绝对定位
				return false;
			});
		}

		$(".rightmenu li").click(function() {
			//当前的tabId
			var currentTabId = $(this).attr("data-id");

			if($(this).attr("data-type") == "closeOthers") { //关闭其他
				var tabtitle = $(".layui-tab-title li");
				$.each(tabtitle, function(i) {
					if($(this).attr("lay-id") != currentTabId) {
						active.tabDelete($(this).attr("lay-id"))
					}
				});
			} else if($(this).attr("data-type") == "closeAll") { //关闭全部
				var tabtitle = $(".layui-tab-title li");
				$.each(tabtitle, function(i) {
					active.tabDelete($(this).attr("lay-id"))
				})

			} else if($(this).attr("data-type") == "refresh") { //刷新页面
				active.tabRefresh($(this).attr("data-id"));

			} else if($(this).attr("data-type") == "closeRight") { //关闭右边所有
				//找到当前聚焦的li之后的所有li标签 然后遍历
				var tabtitle = $(".layui-tab-title li[lay-id=" + currentTabId + "]~li");
				$.each(tabtitle, function(i) {
					active.tabDelete($(this).attr("lay-id"))
				})
			}

			$('.rightmenu').hide();
		});

		function FrameWH() {
			var h = $(window).height() - 41 - 10 - 60 - 10 - 44 - 10;
			$("iframe").css("height", h + "px");
		}

		$(window).resize(function() {
			FrameWH();
		});

		//打开默认页面
		active.tabAdd("childhtml/system/console.html?id=12", 11, "控制台");
		active.tabChange(11);
	});
});