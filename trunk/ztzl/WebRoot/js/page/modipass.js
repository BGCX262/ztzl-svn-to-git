Ext.onReady(function() {

	var thistab = Ext.get('修改密码');
	thistab.clean(true);


	var np = new Ext.form.TextField({
				fieldLabel : '新密码',
				allowBlank : false,
				inputType : 'password',
				name : 'newpass',
				blankText : '新密码不能为空！'
			})
	var fp = new Ext.form.FormPanel({
				renderTo : thistab,
				labelAlign : 'right',
				title : '密码修改',
				labelWidth : 60,
				frame : true,
				url : '.\/json\/chpass.action',
				width : 300,
				height : 200,

				items : [new Ext.form.TextField({
									fieldLabel : '原始密码',
									allowBlank : false,
									inputType : 'password',
									name : 'userpass',
									blankText : '旧密码不能为空！'
								}), np, new Ext.form.TextField({
									fieldLabel : '重复输入',
									allowBlank : false,
									inputType : 'password',
									name : 'repass',
									blankText : '重复密码不能为空！',
									 invalidText :'两次输入密码不一致!',
									validator : function(val) {
										if (val == np.getValue())
											return true;
										else
											return false;
									}
								})],

				buttons : [{
					text : '提交',
					handler : function() {
						if (fp.getForm().isValid())
							fp.getForm().submit({
										waitMsg : '登录中，请稍等...',
										success : function(form, action) {
											form.reset();
											Ext.Msg.alert('信息', '密码修改成功');
										},
										failure : function(form, action) {
											Ext.Msg.alert('错误',
													action.result.msg);
										}
									});
					}
				}, {
					text : '重置',
					handler : function() {
						form.getForm().reset();
					}
				}],
				keys : [{
					key : [13], // 回车键
					fn : function() {
						if (fp.getForm().isValid())
							fp.getForm().submit({
								waitMsg : '登录中，请稍等...',
								success : function(form, action) {
									Ext.Msg.alert('信息', '密码修改成功');
									form.reset();
								},
								failure : function(form, action) {
									if (action.result.msg)
										Ext.MessageBox.alert('错误',
												action.result.msg);
									else
										Ext.Msg.alert('错误', '未知原因，密码修改失败！');
								}
							});
					}
				}]
			});
		// }
		// grid.on("afteredit", afterE, this);
		// grid.render(document.body);
	});
