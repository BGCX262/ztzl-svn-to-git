Ext.onReady(function() {

			// NOTE: This is an example showing simple state management. During
			// development,
			// it is generally best to disable state management as
			// dynamically-generated
			// ids
			// can change across page loads, leading to unpredictable results.
			// The
			// developer
			// should ensure that stable state ids are set for stateful
			// components in
			// real apps.

			Ext.BLANK_IMAGE_URL = './ext/resources/images/default/s.gif';
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			// 构造border的north,用作logo
			var north = new Ext.BoxComponent({ // raw
				region : 'north',
				el : 'north',
				height : 100
			});

			// 构造border的soutth,准备用作版权
			var south = {
				region : 'south',
				contentEl : 'south',
				split : true,
				height : 50,
				minSize : 50,
				maxSize : 50,
				collapsible : true,
				title : '版权',
				margins : '0 0 0 0'
			};

			// 构造border的west
			var root = new Ext.tree.AsyncTreeNode({
						// 声明一个根节点
						id : '0',// id为0
						text : '模块列表',// 显示文字为0
						children : [{// 子节点
							text : 'loading',// 显示文字为loading
							iconCls : 'loading',// 使用图标为loading 在index.html
							// style 中定义
							leaf : true
								// 是叶子节点,不在包含子节点
						}]
					});
			var treeLoader = new Ext.tree.TreeLoader();// 指定一个空的TreeLoader
			var tree = new Ext.tree.TreePanel({// 声明一个TreePanel显示tree
				id : 'tree',// id为tree
				iconCls : 'nav',
				region : 'north',// 设定显示区域为东边,停靠在容器左边
				split : true,// 出现拖动条
				collapseMode : 'mini',// 拖动条显示类型为mini,可出现拖动条中间的尖头
				width : 210,// 初始宽度
				minSize : 210,// 拖动最小宽度
				maxSize : 300,// 拖动最大宽度
				collapsible : true,// 允许隐藏
				title : "模块列表",// 显示标题为树
				loader : treeLoader,// 指定数据载入的loader对象,现在定义为空
				lines : true,// 出现节点间虚线
				autoScroll : true,// 自动出现滚动条
				root : root
					// 根节点为 root 对象
			});

			root.on('expand', gettree);// 当根节点展开后触发gettree事件

			function gettree(node) {// 此方法使用mootools框架的AJAX实现对子节点的异步读取,没使用EXT的是为了表达
				// EXT的灵活性和可扩展性
				if (node.firstChild.text == 'loading') {// 如果当前展开节点的第一个子节点为loading,则调用异步方法取得子节点数据,否则直接展开
					// //可修改为每次都取得
					var url = 'json\/getTree.action';// 请求的地址,因为使用的 struts2
					// 所以请求为.action
					var params = node.id;// 请求数据

					Ext.Ajax.request({
								url : url,
								params : {
									id : params
								},
								success : function(action) {
									var myObject = Json
											.evaluate(action.responseText);

									if (myObject.notlog == true) {
										Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
										window.location = 'login.jsp';
									}

									var tl = myObject.tl;
									// Ext.MessageBox.alert('do it youself');
									for (var i = 0; i < tl.length; i++) {// 循环数组,添加子节点
										var cnode = new Ext.tree.AsyncTreeNode(
												{
													id : tl[i].id,// +
													// node.id//
													// 为服务器返回id+父节点id
													text : tl[i].name, // +//
													// node.id
													leaf : tl[i].leaf,// 是否为叶子节点,根据服务器返回内容决定是否为叶子节点
													children : [{// 添加子节点,如果服务器返回tl[i].leaf为true则孩子节点将无法显示
														text : 'loading',
														iconCls : 'x-tbar-loading',
														leaf : false
													}]
												});
										cnode.on('expand', gettree);// 定义当前节点展开时调用gettree方法,再次异步读取子节点
										node.appendChild(cnode);// 将当前节点添加为待展开节点子节点
									}
									node.firstChild.remove();// 删除当前节点第一个孩子节点(loading节点)
								},
								failure : function(action) {
									var myObject = Json
											.evaluate(action.responseText);
									if (myObject)
										if (myObject.notlog == true) {
											Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
											window.location = 'login.jsp';
										}
									Ext.MessageBox.alert('错误', '取数据失败！')
								}
							});
				}
			}

			var west = {
				region : 'west',
				id : 'west-panel',
				title : '权限',
				split : true,
				width : 200,
				minSize : 175,
				maxSize : 400,
				collapsible : true,
				margins : '0 0 0 5',
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},
				items : [tree, {
							title : 'Settings',
							html : '<p>Some settings in here.</p>',
							border : false,
							iconCls : 'settings'
						}]
			};

			// 构造border的center
			var center = new Ext.TabPanel({
						region : 'center',
						id : 'maintab',
						enableTabScroll : true,
						deferredRender : false,
						autoScroll : true,
						activeTab : 0,
						items : [{
									autoLoad : './page/main.jsp',
									title : 'Close Me',
									closable : true,
									autoScroll : true
								}]
					});

			// 构造border视图
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [north, south, west, center]
					});

			// 点击模块列表项打开新tab页
			tree.on('click', function(node) {// 当树节点被点击时触发
						if (node.isLeaf()) {// 如果不是叶子节点则不处理
							// 请求的地址
							selectNode = node;// 设置选择节点为当前节点
							var url = 'json\/getWindow.action';// 请求位置
							var params = 'id=' + node.id;// 请求数据

							Ext.Ajax.request({
										url : url,
										params : {
											id : node.id
										},
										// 注意 createDelegate 的用法
										success : function(action, form) {// 异步响应完成时的回调方法
											var myObject = Json
													.evaluate(action.responseText);
											var priid = myObject.priid;
											var pris = myObject.pris;
											var priname = pris.priname;
											var priurl = pris.priurl;

											// var tab =
											// center.getComponent(priid);
											var tab = center.getItem(priname);

											if (tab) {
												center.setActiveTab(tab);
											} else {

												center.add(new Ext.Panel({
															id : priname,
															iconCls : 'x-tbar-loading',
															autoLoad : {
																url : priurl,
																nocache : true,
																scripts : true,
																autoWidth : true,
																autoHeight : true,
																autoScroll : true

															},
															title : priname,
															closable : true,
															autoScroll : true,
															autoHeight : true,
															autoWidth : true,
															// width:center.width,
															// heigth:center.height,
															layout : 'fit'

														})).show();
												// Ext.MessageBox.alert('this is
												// a
												// test');
											}

										},
										failure : function(action) {
											Ext.MessageBox.alert('错误',
													'请求服务器数据失败');
										}

									}

							);

							// // 创建Ajax.Request对象，对应于发送请求
							// var myAjax = new Ajax(url, {
							// method : 'post',
							// data : params,
							// onComplete : function() {// 异步响应完成时的回调方法
							// var myObject = Json
							// .evaluate(this.response.text);
							// var priid = myObject.priid;
							// var pris = myObject.pris;
							// var priname = pris.priname;
							// var priurl = pris.priurl;
							//
							// // var tab =
							// // center.getComponent(priid);
							// var tab = center.getItem(priname);
							//
							// if (tab) {
							// center.setActiveTab(tab);
							// } else
							// center.add({
							// id : priname,
							// iconCls : 'x-tbar-loading',
							// autoLoad : {
							// url : priurl,
							// nocache : true,
							// scripts : true
							//
							// },
							// title : priname,
							// closable : true,
							// autoScroll : true,
							// autoHeight : true,
							// autoWidth : true
							//
							// }).show();
							//
							// },
							// onFailure : function() {
							// Ext.MessageBox.alert('提示',
							// '请求服务器失败！');
							// }
							// });
							// myAjax.request();// 发送请求
							//		
						}
					});

		});