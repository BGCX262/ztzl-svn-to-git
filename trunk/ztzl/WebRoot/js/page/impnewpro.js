Ext.onReady(function() {

	var thistab = Ext.get('产品信息导入');
	thistab.clean(true);
	Ext.QuickTips.init();

	new Ext.Panel({
		renderTo : thistab,
		html : '<p align="center">模板下载 <a href="mods/newProducts.xls">产品信息模板</a></p><br>',
		width : '800',
		// autoScroll : true,
		bodyStyle : 'padding:10px 10px 10px 10px'
	});

	var fp = new Ext.FormPanel({
				renderTo : thistab,
				fileUpload : true,
				// frame : true,
				title : '请选择要导入的文件',
				url : '.\/json\/impnewpro.action',
				autoHeight : true,
				width : 800,
				bodyStyle : 'padding: 10px 10px 0 10px;',
				labelWidth : 100,
				defaults : {
					anchor : '95%',
					allowBlank : false,
					msgTarget : 'side'
				},
				items : [{
							xtype : 'fileuploadfield',
							// id : 'upload',
							emptyText : '选择要导入的文件',
							fieldLabel : '选择文件',
							name : 'upload',
							blankText : '文件不能为空!'/*
													 * , buttonCfg : { text :
													 * '选择' }
													 */
						}],
				buttons : [{
					text : '导入',
					handler : function() {
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								url : '.\/json\/impnewpro.action',
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
										}
									// Ext.MessageBox.alert('提示:',
									// action.responseText);
									Ext.MessageBox.alert('提示',action.result.rtntxt);

									// Ext.decode(action.responseText);
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
