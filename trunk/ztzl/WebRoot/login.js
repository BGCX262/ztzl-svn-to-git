

Ext.onReady(

function() {

			Ext.QuickTips.init();// 支持tips提示
			Ext.form.Field.prototype.msgTarget = 'qtip';// 提示的方式，枚举值为"qtip","title","under","side",id(元素id)
			Ext.BLANK_IMAGE_URL = 'resources/images/default/s.gif';
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

			var win, form;
			// form action 地址
			submitUrl = "./json/login.action";

			var logoPanel = new Ext.Panel({
						baseCls : 'x-plain',
						id : 'login-logo',
						region : 'north',
						height : 200,
						contentEl : 'logo'

					});

			// 构建form
			var formPanel = new Ext.form.FormPanel({
						baseCls : 'x-plain',
						baseParams : {},
						bodyStyle : 'padding:5px 5px 0',
						defaults : {
							width : 200

						},
						defaultType : 'textfield',
						frame : false,
						height : 100,
						id : 'login-form',
						items : [{
									fieldLabel : '用户名称',
									allowBlank : false,
									name : 'username',
									invalidText : '用户名无效！',
									blankText : '用户名不能为空!'

								}, {
									fieldLabel : '用户密码',
									allowBlank : false,
									inputType : 'password',
									name : 'userpass',
									blankText : '用户密码不能为空!'
								}],
						labelWidth : 80,
						region : 'center',
						url : submitUrl
					});

			win = new Ext.Window({
						buttons : [{
									handler : function() {
										if (form.isValid())
											form.submit({
														waitMsg : '登录中，请稍等...',
														// reset : true,
														method : 'POST',
														success : Success,
														failure : Failure
													});

									},

									text : '登录'
								}],
						keys : {
							key : [13], // 回车键
							fn : function() {
								form.submit({
											waitMsg : '登录中，请稍等...',
											reset : true,
											method : 'POST',
											success : Success,
											falure : Failure

										});
							}
						},
						buttonAlign : 'center',
						height : 360,
						id : 'login-win',
						layout : 'border',
						minHeight : 200,
						minWidth : 300,
						plain : false,
						resizable : false,
						closable : false,
						draggable : false,
						items : [logoPanel, formPanel],
						title : '中天公司质量管理系统用户登录',
						width : 400

					});

			Failure = function(form, action) {
				// alert(action.result);
				Ext.MessageBox.alert('失败！', action.result.result);

			};

			Success = function(form, action) {
				// Ext.MessageBox.alert('警告', '成功');
				// window.location = 'index.jsp';

				window.location = 'index.jsp';
			};

			form = formPanel.getForm();

			win.show();

		});