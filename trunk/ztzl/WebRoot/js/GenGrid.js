/*******************************************************************************
 * @author 张中伟 zhongweizhang@163.com
 * @class Ext.my.GenGrid 通用的Grid
 ******************************************************************************/

function objClone(obj)
// 增加了对网页元素克隆的判断
// 增加了对构造方法的克隆
{
	if (obj.cloneNode != null)
		return obj.cloneNode(true);
	result = new Object();
	result.constructor = obj.constructor
	for (var i in obj)
		result[i] = obj[i]
	return result;
}

// 实现中文拼音顺序排序
Ext.data.Store.prototype.applySort = function() {
	if (this.sortInfo && !this.remoteSort) {
		var s = this.sortInfo, f = s.field;
		var st = this.fields.get(f).sortType;
		var fn = function(r1, r2) {
			var v1 = st(r1.data[f]), v2 = st(r2.data[f]);
			if (typeof(v1) == "string") {
				return v1.localeCompare(v2);
			}
			return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
		};
		this.data.sort(s.direction, fn);
		if (this.snapshot && this.snapshot != this.data) {
			this.snapshot.sort(s.direction, fn);
		}
	}

}

// 通用的grid 对象
GenGrid = function(config) {
	Ext.QuickTips.init();
	this.config = config;

	// 参数配置 //对于可以省略的配置项使用 this.con=config.con||'';
	this.renderTo = this.config.renderTo;
	this.cm = this.config.cm;
	this.ds = this.config.ds;
	this.sm = new Ext.grid.CheckboxSelectionModel();

	this.width = this.config.width || Ext.get('maintab').getWidth() - 20;
	this.height = this.config.height || Ext.get('maintab').getHeight() - 51;
	this.cwidth = this.config.cwidth || 500;
	this.cheight = this.config.cheight || 500;
	this.actionurl = this.config.actionurl;
	this.win1 = '';
	this.params = '';
	this.id = this.config.id || 'ngd';

	this.formFields = this.config.formFields;

	/**
	 * @param {Object}
	 *            默认情况下有此显现,如果不需要可以为false 必须含有下列参数:<br>
	 *            loadingEL {String} 加载页面时显示的内容id<br>
	 *            loadingMaskEl {String} 渐显的内容id<br>
	 */
	// this.loadingMark = this.config.loadingMark || {
	// loadingEL : '正在载入数据……',
	// loadingMaskEL : '正在载入数据……'
	// };
	var comData = '[';
	for (var i = 0; i < this.formFields.length; i++) {
		// 如果是修改，对字段进行赋值
		if (this.formFields[i].name != 'command') {
			comData = comData + '[\'' + this.formFields[i].name + '\',\''
					+ this.formFields[i].fieldLabel + '\'],';
		}
	}

	comData = comData + '[\'\' , \'\' ]' + ']';

	var seleFields;
	eval('seleFields=' + comData);

	// var seleFields = [['username', '用户名'], ['userpass', '用户密码'],
	// ['userid', '用户ID'], ['memo', '描述'], ['id', 'ID'], ['','']];
	var store = new Ext.data.SimpleStore({
				fields : ['value', 'disp'],
				data : seleFields
			});

	var combo = new Ext.form.ComboBox({
				store : store,
				// id : 'comboField',
				displayField : 'disp',
				valueField : 'value',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				triggerAction : 'all',
				emptyText : '请选择字段...',
				selectOnFocus : true
			});

	// 为条件查询等使用
	this.combo = objClone(combo);

	var txtfind = new Ext.form.TextField({
				value : '输入查找值',
				id : this.id + 'txtFind',
				width : 80,
				listeners : {
					focus : function(t) {
						if (t.value == '输入查找值')
							t.setValue('');
					},
					change : function(t, n, o) {
						t.setValue(n);
					}
				}
			});
	this.tbar = new Ext.Toolbar([{
				id : this.id + 'add',
				iconCls : 'add',
				text : '增加',
				tooltip : '添加记录',
				handler : this.newInfo.createDelegate(this)
			}, {
				id : this.id + 'modi',
				iconCls : 'edit',
				text : '修改',
				tolltip : '修改记录',
				handler : this.modiRec.createDelegate(this)
			}, {
				id : this.id + 'del',
				iconCls : 'delete',
				text : '删除',
				tooltip : '删除记录',
				handler : this.deleRec.createDelegate(this)
			}, {
				iconCls : 'x-tbar-loading',
				text : '刷新',
				tooltip : '刷新',
				handler : this.refresh.createDelegate(this)
			}, {

				iconCls : 'filter',
				text : '条件查询',
				tooltip : '条件查询',
				handler : this.queryRec.createDelegate(this)
				// 用于条件查询
		}	, {
				iconCls : 'xls',
				text : '导出',
				tooltip : '导出到EXCEL',
				handler : this.expExcel.createDelegate(this)
			}, '-', combo, txtfind, {
				iconCls : 'find',
				text : '快速查找',
				tooltip : '在本页内查找',
				handler : this.findRec.createDelegate(this, [combo, txtfind])

			}
	// 添加自定义按钮使用程序中的 grid.getToptoolbar().add()

	]);

	this.bbar = new Ext.Toolbar([{
				iconCls : 'x-tbar-page-first',
				handler : this.firstPage.createDelegate(this)
			}, {
				iconCls : 'x-tbar-page-prev',
				handler : this.prePage.createDelegate(this)
			}, '第', new Ext.form.NumberField({
						value : '1',
						id : this.id + 'txtPage',
						width : '30',
						listeners : {
							change : this.chgPageNo.createDelegate(this, [],
									true)
						}
					}), '页', {
				iconCls : 'x-tbar-page-next',
				handler : this.nextPage.createDelegate(this)
			}, {
				iconCls : 'x-tbar-page-last',
				handler : this.lastPage.createDelegate(this)
			}, '-', {
				iconCls : 'x-tbar-loading',
				handler : this.refresh.createDelegate(this)
			}, '-', '每页显示', new Ext.form.NumberField({
						value : '25',
						id : this.id + 'txtCount',
						width : '25',
						listeners : {
							change : this.chgPageCount.createDelegate(this, [],
									true)
						}
					}), '条数据', /* '->', */' 共', new Ext.form.Field({
						value : '',
						width : 35,
						id : this.id + 'txtTotalPage'
					}), '页　', new Ext.form.Field({
						value : '',
						width : 40,
						id : this.id + 'txtTotalCount'
					}), '条数据', new Ext.form.Hidden({
						value : '',
						id : this.id + 'txtCondi'
					})]

	);

	// 通过参数构造grid

	this.grid = new Ext.grid.GridPanel({
				ds : this.ds,
				cm : this.cm,
				sm : this.sm,
				width : this.width,
				height : this.height,
				// bodyStyle : 'padding:10px 10px 0',
				// autoHeight : true,
				autoScroll : true,
				renderTo : this.renderTo,
				tbar : this.tbar,
				bbar : this.bbar,
				border : true,// 出现边框
				// loadMask : true,
				loadMask : {
					msg : '正在载入数据……'
				},
				viewConfig : {
					sortAscText : '升序',
					sortDescText : '降序',
					columnsText : '显示列',
					forceFit : true,
					autoFill : true,
					deferEmptyText : '请等待...',
					emptyText : '没有数据',
					enableRowBody : true
				}

			});

	this.grid.render();
	/**
	 * 双击数据的事件，弹出一个编辑此条数据的层
	 * 
	 * @param grid
	 *            此列表的对象
	 * @param rowIndex
	 *            在列表中的行数
	 * @param e
	 *            触发此事件的事件对象
	 */
	this.rowdblclick = this.config.rowdblclick || function(grid, rowIndex, e) {
		var record = this.grid.store.data.items[rowIndex];
		this.winform(record, '修改记录');

	};
	this.grid.on('rowdblclick', this.rowdblclick.createDelegate(this));

	Ext.Ajax.on('beforerequest', function() {
				Ext.MessageBox.wait('正在读取数据……', '请稍等', {
							interval : 500,
							text : '读取数据中……'
						});
			}, GenGrid);
	Ext.Ajax.on('requestcomplete', function() {
				Ext.MessageBox.hide();
			}, GenGrid);
	// 当js加载完毕后删除loading页面并渐显内容
	// if (this.loadingMark) {
	// setTimeout(function() {
	// Ext.get(this.loadingMark.loadingEL).remove();
	// Ext.get(this.loadingMark.loadingMaskEL).fadeOut({
	// remove : true
	// });
	// }.createDelegate(this), 250);
	// }
	this.getData(1, 25, '');

}

// 类方法
GenGrid.prototype = {

	// topbar 部分
	newInfo : function() {
		this.winform('', '增加新记录');
	},
	expExcel : function() {
		Ext.ux.Grid2Excel.Save2Excel(this.grid);
	},
	modiRec : function() {
		if (this.grid.getSelectionModel().getCount() != 1) {
			Ext.MessageBox.alert('提示', '请选择一行记录进行修改');
			return;
		}
		var record = this.grid.getSelectionModel().getSelected();
		// alert(record.get('id'));
		this.winform(record, '修改记录');
	},
	deleRec : function() {
		if (this.grid.getSelectionModel().getCount() < 1) {
			Ext.MessageBox.alert('提示', '没有选中行');
			return;
		}

		Ext.MessageBox.confirm('确认删除', '确定要删除选中的行吗?', this.del.createDelegate(
						this, [], true));
	},
	del : function(btn) {
		if (btn == 'no')
			return;

		var ids = '';
		// Ext.MessageBox.alert('',ids);
		var sele = this.grid.getSelectionModel().getSelections();
		for (var i = 0; i < sele.length; i++)
			ids += sele[i].get('id') + ',';

		// Ext.MessageBox.alert('',ids);
		// return;
		Ext.Ajax.request({
					url : this.actionurl,
					params : {
						pageNo : this.getPageNo(),
						pageCount : this.getPageCount(),
						Condi : this.getCondi(),
						command : 'dele',
						ids : ids
					},
					callback : function() {
						Ext.MessageBox.hide();
					},
					// 注意 createDelegate 的用法
					success : this.refresh.createDelegate(this),
					failure : function(action) {
						var myObject = Json.evaluate(action.responseText);
						if (myObject)
							if (myObject.notlog == true) {
								Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							} else
								Ext.MessageBox.alert('错误', '删除数据失败！');
					}

				}

		);

	},
	refresh : function() {
		this.grid.getSelectionModel().clearSelections();
		this.getData(this.getPageNo(), this.getPageCount(), this.getCondi());
	},
	// bbar 部分
	firstPage : function() {
		this.getData(1, this.getPageCount(), this.getCondi());
	},
	prePage : function() {
		this
				.getData(this.getPageNo() - 1, this.getPageCount(), this
								.getCondi());
	},
	nextPage : function() {
		this
				.getData(Number(this.getPageNo()) + 1, this.getPageCount(), this
								.getCondi());
	},
	lastPage : function() {
		this.getData(this.getTotalPage(), this.getPageCount(), this.getCondi());
	},
	chgPageNo : function(txt, n, o) {
		this.getData(n, this.getPageCount(), this.getCondi());
	},
	chgPageCount : function(txt, n, o) {
		this.getData(this.getPageNo(), n, this.getCondi());
	},
	// Ajax 取得数据
	getData : function(pageNo, pageCount, Condi) {

		Ext.Ajax.request({
					url : this.actionurl,
					params : {
						pageNo : pageNo,
						pageCount : pageCount,
						Condi : Condi
					},
					callback : function() {
						Ext.MessageBox.hide();
					},
					// 注意 createDelegate 的用法
					success : this.process.createDelegate(this, [], true),
					failure : function(action) {
						var myObject = Json.evaluate(action.responseText);
						if (myObject)
							if (myObject.notlog == true) {
								Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							} else
								Ext.MessageBox.alert('错误', '取数据失败！')
					}

				}

		);

	},

	// 处理返回数据
	process : function(action) {
		var myObject = Json.evaluate(action.responseText);

		if (myObject.notlog == true) {
			Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
			window.location = 'login.jsp';
		}
		var data = myObject.pi;
		pageNo = myObject.pi.pageNo;
		pageCount = myObject.pi.pageCount;
		totalPage = myObject.pi.totalPage;
		totalCount = myObject.pi.totalCount;
		condi = myObject.condi;

		// 刷新数据
		// Ext.MessageBox.alert('', data);
		this.ds.removeAll();
		this.ds.loadData(data);
		Ext.getCmp(this.id + 'txtPage').setValue(pageNo);// 设置工具条中页文本内容为1
		Ext.getCmp(this.id + 'txtCount').setValue(pageCount);// 设置工具条中个数文本内容为25
		Ext.getCmp(this.id + 'txtCondi').setValue(condi);

		Ext.getCmp(this.id + 'txtTotalCount').setValue(totalCount);
		Ext.getCmp(this.id + 'txtTotalPage').setValue(totalPage);

	},

	// 根据字段值查找行
	findRec : function(combo, txtFind) {
		var fields = combo.value;
		var txtFind = txtFind.value;
		var selectrow = '';

		// 取消原有选择
		this.grid.getSelectionModel().deselectRange(0,
				this.grid.getStore().getCount() - 1);
		// 查找行
		for (var i = 0; i < this.grid.getStore().getCount(); i++)
			if (this.grid.getStore().getAt(i).get(fields) == txtFind) {
				// this.grid.getSelectionModel().selectRow(i); // 选中找到的行 单选
				selectrow = selectrow + i + ',';
			}

		if (selectrow == '')
			Ext.MessageBox.alert('提示', '未找到符合条件的行');
		else {
			var selearr;
			eval('selearr=[' + selectrow + '9999];');
			this.grid.getSelectionModel().selectRows(selearr);
		}
	},

	// 构造增加和修改记录的窗口
	winform : function(id, title) {
		// 根据参数构造form
		// formFields=this.formFields; 直接使用会导致第二次打开窗口时看不到form
		// 克隆formFields 对象
		var formFields = new Array(this.formFields.length);
		for (var i = 0; i < formFields.length; i++) {
			formFields[i] = objClone(this.formFields[i]);
			// 如果是修改，对字段进行赋值

		}

		var form1 = new Ext.FormPanel({
					labelWidth : 75, // label settings here cascade unless
					// overridden
					url : this.actionurl,
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 350,
					defaults : {
						width : 230
					},
					defaultType : 'textfield',
					items : formFields
				});
		// form1.getForm();

		var win1 = new Ext.Window({// start Ext.Window
			title : title,
			id : 'edit',
			layout : 'fit',
			width : this.cwidth,
			height : this.cheight,
			bodyStyle : 'padding:5px',
			closeAction : 'close',
			constrain : true,
			plain : true,
			modal : true,
			items : form1,
			buttonAlign : 'center',
			buttons : [{ // 按钮
				text : '保存',
				handler : this.saveclick.createDelegate(this, [form1, win1])
			}, {
				text : '重置',
				handler : function() {// start function按钮处理函数
					form1.getForm().reset();
				}
			}, {
				text : '取消',
				handler : function() {// start function按钮处理函数
					win1.close();
				}
			}]
		});// end Ext.Window

		if (id) {
			// formFields[i].value = id.get(formFields[i].name);
			Ext.Ajax.request({
						url : this.actionurl,
						params : {
							pageNo : 1,
							pageCount : 100,
							command : 'qone',
							Condi : ' and id=\'' + id.get('id') + '\''
						},
						callback : function() {
							Ext.MessageBox.hide();
						},
						// 注意 createDelegate 的用法
						success : function(action, form) {
							var myObject = Json.evaluate(action.responseText);
							// Ext.MessageBox.alert('',action.responseText);
							if (myObject.notlog == true) {
								Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							}
							var data1 = myObject.pi.list[0];
							// Ext.MessageBox.alert(data1.id);

							form1.getForm().setValues(data1);
							form1.getForm().setValues({
										command : 'add'
									});

						},
						failure : function(action) {
							var myObject = Json.evaluate(action.responseText);
							if (myObject)
								if (myObject.notlog == true) {
									Ext.MessageBox.alert('提示',
											'登录已超时，请重新登录后操作！');
									window.location = 'login.jsp';
								} else
									Ext.MessageBox.alert('错误', '取数据失败！')
						}

					}

			);

		}

		this.win1 = win1;
		win1.show();

	},

	queryRec : function() {

		// var combo = objClone(this.combo);
		var fieldobj;
		var fielddata = {
			"list" : [{
						"fieldname" : "procode",
						"condition" : ">=",
						"fieldvalue" : "010809010000"
					}, {
						"fieldname" : "procode",
						"condition" : "<=",
						"fieldvalue" : "010809010000"
					}]
		};

		var afield = Ext.data.Record.create([{
					name : 'fieldname',
					mapping : 'fieldname'
				}, {
					name : 'condition',
					mapping : 'condition'
				}, {
					name : 'fieldvalue',
					mapping : 'fieldvalue'
				}]);
		//
		// var newField = new afield({
		// "fieldname" : "名称",
		// "fieldvalue" : "name",
		// "fieldtype" : "string"
		// });

		var fieldstore = new Ext.data.Store({
					proxy : new Ext.data.MemoryProxy(fielddata),
					reader : new Ext.data.JsonReader({
								root : 'list'
							}, [{
										name : 'fieldname'
									}, {
										name : 'condition'
									}, {
										name : 'fieldvalue'
									}])
				});

		fieldstore.loadData(fielddata);

		// 字段名使用原combo
		var newcombo = new Ext.form.ComboBox(this.combo);

		// 构造条件combo
		var confields;
		var constr = '[[\'=\',\'=\'],[\'>\',\'>\'],[\'<\',\'<\'],[\'>=\',\'>=\'],[\'<=\',\'<=\'],[\'in\',\'包括\'],[\'like\',\'包含\']]';

		eval('confields=' + constr);

		var constore = new Ext.data.SimpleStore({
					fields : ['value', 'disp'],
					data : confields
				});

		var concombo = new Ext.form.ComboBox({
					store : constore,
					// id : 'comboField',
					displayField : 'disp',
					valueField : 'value',
					typeAhead : true,
					editable : false,
					mode : 'local',
					forceSelection : true,
					width : 80,
					triggerAction : 'all',
					emptyText : '请选择条件...',
					selectOnFocus : true
				});

		var congrid = new Ext.grid.EditorGridPanel({

					width : 450,
					height : 300,
					store : fieldstore,
					frame : true,
					title : '',
					clicksToEdit : 1,
					columns : [{
								header : '字段名称',
								dataIndex : 'fieldname',
								editor : newcombo,
								allowBlank : false,
								blankText : '字段名不能为空！'
							}, {
								header : "字段条件",
								dataIndex : 'condition',
								editor : concombo,
								allowBlank : false,
								blankText : '字段条件不能为空！'
							}, {
								header : "条件值",

								dataIndex : 'fieldvalue',
								editor : new Ext.form.Field({}),
								allowBlank : false,
								blankText : '条件值不能为空！'
							}],
					tbar : [{
								text : '增加行',
								handler : function() {

									fieldstore.add(new afield({
												"fieldname" : "procode",
												"condition" : "=",
												"fieldvalue" : "010809010000"
											}));
								}
							}, {
								text : '删除末行',
								handler : function() {
									fieldstore.removeAt(fieldstore.getCount()
											- 1);
								}
							}, {
								text : '清除条件',
								handler : function() {
									fieldstore.removeAll();
								}
							}]
				});

		var win2 = new Ext.Window({
					title : '条件查询',
					id : 'edit',
					layout : 'fit',
					width : 400,
					height : 210,
					bodyStyle : 'padding:5px',
					closeAction : 'hide',
					plain : true,
					constrain : true,
					modal : true,
					items : congrid,
					buttons : [{
						text : '查询',
						handler : this.condiquery.createDelegate(this, [
										fieldstore, win2])
					}, {
						text : '关闭',
						handler : function() {
							win2.close()
						}
					}]

				});
		this.win2 = win2;

		win2.show();

	},
	condiquery : function(fieldstore, win2) {
		var con = '';
		for (var i = 0; i < fieldstore.getCount(); i++) {
			var rec = fieldstore.getAt(i);
			var fname = rec.get('fieldname');
			var cc = rec.get('condition');
			var fvalue = rec.get('fieldvalue')

			if (fvalue == '') {
				Ext.MessageBox.alert('提示', '第' + (i + 1) + '行条件值不能为空！');
				return;
			}

			// con += ' and ' + fname + ' ';
			if (fname.indexOf('date') > 0)
				con += ' and trunc(' + fname + ') ';
			else
				con += ' and ' + fname + ' ';

			con += cc + ' ';

			if (cc == 'like' && fname.indexOf('date') > 0) {
				Ext.MessageBox.alert('提示', '日期类型数据不能使用like条件！');
				return;
			}
			if (cc == 'in' && fname.indexOf('date') > 0) {
				Ext.MessageBox.alert('提示', '日期类型数据不能使用in条件！');
				return;
			}

			if (cc == 'like')
				fvalue = ' \'%' + fvalue + '%\' ';
			else if (cc == 'in') {
				// 拆分字符串并组合条件值
				var b = fvalue.split(",");
				var a = ' ';
				for (var i = 0; i < b.length; i++) {
					a += '\'' + b[i].trim() + '\',';
				}
				fvalue = ' (' + a + '\'0\' )';

			} else {
				if (fname.indexOf('date') > 0) {
					if (Date.parseDate(fvalue, "Y-m-d"))
						fvalue = ' to_date(\'' + fvalue + '\',\'yyyy-mm-dd\') ';
					else {
						Ext.MessageBox.alert('错误', '请使用　yyyy-mm-dd格式日期！');
						return;
					}
				} else
					fvalue = ' \'' + fvalue + '\' ';
			}
			con += fvalue;

		}

		Ext.getCmp(this.id + 'txtCondi').setValue(con);
		this.getData(this.getPageNo(), this.getPageCount(), this.getCondi());
		this.win2.close();
	},
	saveclick : function(form1, win1) {

		form1.getForm().submit({
					waitMsg : '正在保存数据...',
					success : this.savenew.createDelegate(this, [win1], true),
					failure : function(form, action) {
						var myObject = Json.evaluate(action.responseText);
						if (myObject.notlog == true) {
							Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
							window.location = 'login.jsp';
						}
						Ext.MessageBox.alert('有错误:', action.result.rtntxt);
					}
				});
	},
	savenew : function(form, action, win1) {
		Ext.MessageBox.alert('提示', '保存成功');
		// alert('before refresh');
		this.getData(1, this.getPageCount(), '');
		// form.reset();
		// form1.getForm().reset();
		//this.win1.close();
	},
	getPageNo : function() {
		return Ext.getCmp(this.id + 'txtPage').value;
	},
	getPageCount : function() {
		return Ext.getCmp(this.id + 'txtCount').value;
	},
	getTotalPage : function() {
		return Ext.getCmp(this.id + 'txtTotalPage').value;
	},
	getCondi : function() {
		return Ext.getCmp(this.id + 'txtCondi').value;;
	}

}
