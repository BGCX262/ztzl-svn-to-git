// 由于后台进程比较长 ，提交等待时间设置为30分钟 
Ext.Ajax.timeout = 1800000; 

Ext.onReady(function() {

	var thistab = Ext.get('集抄数据导入');
	thistab.clean(true);
	Ext.QuickTips.init();

	// 添加发送地区和发送按钮
	var saobject;

	// var store = new Ext.data.SimpleStore({
	// fields : ['id', 'typename']
	// });
	//					
	var sendarea;
	var sastore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(sendarea),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'sendarea'
								}])
			});

	function getSendArea(action) {
		saObject = Json.evaluate(action.responseText);
		if (saObject.notlog == true) {
			Ext.MessageBox.alert('提示','登录已超时，请重新登录后操作！');
			window.location = 'login.jsp';
		}
		// alert(action.responseText);
		sastore.loadData(saObject.pi);
	}

	Ext.Ajax.request({
				url : '.\/json\/sendarea.action',
				params : {
					pageNo : 1,
					pageCount : 100,
					Condi : ''
				},
				success : getSendArea
				// alert(this.myObject.pi.list[1].id);
				,
				failure : function(action) {
					Ext.MessageBox.alert('错误', '取数据失败！')
				}

			}

	);

	sastore.load();

	var sacombo = new Ext.form.ComboBox({
				store : sastore,
				// id : 'sendarea',
				name : 'sendarea',
				fieldLabel : '安装地区',
				displayField : 'sendarea',
				valueField : 'sendarea',
				hiddenName : 'sendarea',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				allowBlank : false,
				blankText : '必须选择安装地区',
				triggerAction : 'all',
				emptyText : '请选择安装地区...',
				selectOnFocus : true
			});
	var sacombo2 = new Ext.form.ComboBox({
				store : sastore,
				// id : 'sendarea',
				name : 'sendarea',
				fieldLabel : '安装地区',
				displayField : 'sendarea',
				valueField : 'sendarea',
				hiddenName : 'sendarea',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				allowBlank : false,
				blankText : '必须选择安装地区',
				triggerAction : 'all',
				emptyText : '请选择安装地区...',
				selectOnFocus : true
			});

	var fp = new Ext.FormPanel({
				// renderTo : thistab,
				fileUpload : true,
				// frame : true,
				title : '导入485产品数据',
				url : '.\/json\/impnewpro.action',
				autoHeight : true,
				width : 600,
				labelWidth : 100,
				defaults : {
					anchor : '95%',
					allowBlank : false,
					msgTarget : 'side'
				},
				items : [sacombo, new Ext.form.TextField({
									fieldLabel : '安装负责人',
									name : 'principle',
									allowBlank : false,
									blankText : '安装负责人不能为空！'
								}), new Ext.form.DateField({
									fieldLabel : '安装时间',
									name : 'engdate',
									format : 'Y-m-d'
								}), {
							xtype : 'fileuploadfield',
							// id : 'upload',
							emptyText : '选择要导入的文件',
							fieldLabel : '选择文件',
							name : 'upload',
							blankText : '文件不能为空!',
							buttonCfg : {
								text : '选择文件'
							}

						}],
				buttons : [{
					text : '导入',
					handler : function() {
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								url : '.\/json\/imp485.action',
								waitMsg : '正在导入中……',
								success : function(form, action) {
									// form.reset();
									Ext.MessageBox.alert('提示', '导入成功');
								},
								failure : function(form, action) {

									var myObject = Json
											.evaluate(action.responseText);
									if (myObject)
										if (myObject.notlog == true) {
											Ext.MessageBox
													.alert('提示','登录已超时，请重新登录后操作！');
											window.location = 'login.jsp';
										}

									Ext.MessageBox.alert('提示:',
											action.result.rtntxt);
								}
							});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						fp.getForm().reset();
					}
				}]
			});

	var fp2 = new Ext.FormPanel({
				title : '导入400V集抄数据',
				url : '.\/json\/imp400.action',
				autoHeight : true,
				width : 600,
				labelWidth : 100,
				defaults : {
					anchor : '95%',
					msgTarget : 'side'
				},
				items : [sacombo2, new Ext.form.TextField({
									fieldLabel : '安装负责人',
									name : 'principle',
									allowBlank : false,
									value:'舒永锋',
									blankText : '安装负责人不能为空！'
								}), new Ext.form.DateField({
									fieldLabel : '安装时间',
									name : 'engdate',
									value:'2008-01-01',
									format : 'Y-m-d'
								}), new Ext.form.TextField({
									fieldLabel : '用户名',
									name : 'username',
									value:'sa',
									allowBlank : false,
									blankText : '用户名不能为空！'
								}), new Ext.form.TextField({
									fieldLabel : '用户密码',
									name : 'userpass',
									value:'sa',
									allowBlank : false,
									blankText : '用户密码不能为空！'
								}), new Ext.form.TextField({
									fieldLabel : '数据库地址',
									name : 'dataip',
									value:'192.168.1.1',
									allowBlank : false,
									blankText : '数据库地址不能为空！'
								}), new Ext.form.TextField({
									fieldLabel : '数据库名',
									name : 'dataname',
									allowBlank : false,
									blankText : '数据库名不能为空！'
								})],
				buttons : [{
					text : '导入',
					handler : function() {
						if (fp2.getForm().isValid()) {
							fp2.getForm().submit({
								url : '.\/json\/imp400.action',
								waitMsg : '正在导入中……',
								success : function(form, action) {
									// form.reset();
									Ext.MessageBox.alert('提示', '导入成功');
								},
								failure : function(form, action) {
									var myObject = Json
											.evaluate(action.responseText);
									if (myObject)
										if (myObject.notlog == true) {
											Ext.MessageBox
													.alert('登录已超时，请重新登录后操作！');
											window.location = 'login.jsp';
										} else
											Ext.MessageBox.alert('提示:',
													'导入失败！未知原因');
									// Ext.decode(action.responseText);
								}
							});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						fp2.getForm().reset();
					}
				}]

			}

	);

	var tab = new Ext.TabPanel({
				renderTo : thistab,
				enableTabScroll : true,
				deferredRender : false,
				// autoScroll : true,
				activeTab : 0,
				items : [fp2, fp]
			});

		// var fp1 = new Ext.form.FormPanel({
		// renderTo : thistab,
		// labelAlign : 'right',
		// title : '文件上传',
		// labelWidth : 60,
		// frame : true,
		// msgTarget : 'side',
		// url : '.\/json\/impnewpro.action',// fileUploadServlet
		// height : 100,
		// width : 400,
		// fileUpload : true,
		//
		// items : [{
		// xtype : 'textfield',
		// allowBlank : false,
		// fieldLabel : '文件名',
		// name : 'upload',
		// emptyText : '选择要导入的文件',
		// blankText : '文件不能为空!',
		// inputType : 'file'// 文件类型
		// }],
		//
		// buttons : [{
		// text : '导入',
		// handler : function() {
		// // if (form.getForm().isValid())
		// fp1.getForm().submit({
		// url : '.\/json\/impnewpro.action',
		// waitMsg : '正在导入中……',
		// success : function(form, action) {
		// Ext.Msg.alert('信息', '文件导入成功！');
		// },
		// failure : function() {
		// Ext.MessageBox.alert('导入出错:',
		// action.result.rtntxt);
		// }
		// });
		// }
		// }]
		// });

	});
