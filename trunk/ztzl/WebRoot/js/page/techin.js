Ext.onReady(function() {
	var thistab = Ext.get('生技部接收');
	thistab.clean(true);

	var pageNo, pageCount; // 当前面和每页记录数
	var totalCount, totalPage; // 总记录数和总页数
	// var params;

	// 取得类型二级代码
	var sm = new Ext.grid.CheckboxSelectionModel();

	var myObject;
	Ext.Ajax.request({
				url : '.\/json\/producttype.action',
				params : {
					pageNo : 1,
					pageCount : 100,
					Condi : ''
				},
				success : getList
				// alert(this.myObject.pi.list[1].id);
				,
				failure : function(action) {
					var myObject = Json.evaluate(action.responseText);
					if (myObject)
						if (myObject.notlog == true) {
							Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
							window.location = 'login.jsp';
						} else
							Ext.MessageBox.alert('错误', '取数据失败！')
				}

			}

	);

	function getList(action) {
		var myObject = Json.evaluate(action.responseText);
		if (myObject)
			if (myObject.notlog == true) {
				Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
				window.location = 'login.jsp';
			}
		store.loadData(myObject.pi);
	}

	// var store = new Ext.data.SimpleStore({
	// fields : ['id', 'typename']
	// });
	//					
	var data1;
	var store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data1),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'typename'
								}, {
									name : 'functype'
								}, {
									name : 'typeinfo'
								}, {
									name : 'inuse'
								}])
			});

	store.load();

	var combo = new Ext.form.ComboBox({
				store : store,
				// id : 'comboField',
				name : 'typeid',
				fieldLabel : '产品类别',
				displayField : 'typename',
				valueField : 'id',
				hiddenName : 'typeid',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				triggerAction : 'all',
				emptyText : '请选择字段...',
				selectOnFocus : true
			});

	// combo.on('select', function(combo1) {
	// alert(combo1.getValue())
	// });

	function change(val) {
		var date = Date.parseDate(val, 'Y-m-dTh:i:s');
		if (date)
			return date.format('Y-m-d');
		else
			return null;
		// return Date.parseDate(val, 'Y-m-dTh:i:s').format('Y-m-d');
		// return val;
	}

	function listType(val) {
		var al = store.getAt(store.find('id', val));
		if (al)
			return al.get('typename');
		else
			return null;
	}

	function listware(val) {
		if (val == '1')
			return '生产车间';
		else if (val == '2')
			return '仓库';
		else if (val == '3')
			return '工程部';
		else if (val == '4')
			return '生技部';
		else
			return null;
	}

	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 30
					}),// 自动行号
			sm,// 添加的地方
			{
				header : '编码',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '产品编码',
				dataIndex : 'procode',
				width : 100
			}, {
				header : '类别名称',
				dataIndex : 'typeid',
				renderer : listType
			}, {
				header : '当前位置',
				dataIndex : 'curware',
				renderer : listware
			}, {
				header : '产品来源',
				dataIndex : 'prosource',
				renderer : listware
			}, {
				header : '发送人',
				dataIndex : 'sourceoper'
			}, {
				header : '发送日期',
				dataIndex : 'sourcedate',
				renderer : change
			}, {
				header : '发送到',
				dataIndex : 'sendto',
				renderer : listware
			}, {
				header : '发送说明',
				dataIndex : 'sendinfo'
			}, {
				header : '发送地区',
				dataIndex : 'sendarea'
			}, {
				header : '接收人',
				dataIndex : 'receiver',
				hidden : true
			}, {
				header : '接收日期',
				dataIndex : 'receivedate',
				renderer : change,
				hidden : true
			}, {
				header : '接收说明',
				dataIndex : 'receiveinfo',
				hidden : true
			}, {
				header : '退回标志',
				dataIndex : 'returnmark',
				renderer : function(val) {
					if (val == '1')
						return '退回';
					else
						return '正常';
				}
			}, {
				header : '完成标志',
				dataIndex : 'returnmark',
				renderer : function(val) {
					if (val == '1')
						return '完成';
					else
						return '未完成';
				}
			}]);
	cm.defaultSortable = true;

	var data;

	var ds = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'procode'
								}, {
									name : 'curware'
								}, {
									name : 'prosource'/*
														 * , type : 'date',
														 * dateFormat : 'Y-m-d'
														 */
								}, {
									name : 'sourceoper'
								}, {
									name : 'sourcedate'
								}, {
									name : 'sendarea'
								}, {
									name : 'sendinfo'
								}, {
									name : 'sendto'
								}, {
									name : 'receiver'
								}, {
									name : 'receivedate'
								}, {
									name : 'receiveinfo'
								}, {
									name : 'returnmark'
								}, {
									name : 'overmark'
								}, {
									name : 'typeid'
								}])
			});

	ds.load();

	var formFields = [new Ext.form.TextField({
						fieldLabel : '产品编码',
						name : 'procode',
						allowBlank : false,
						blankText : '产品编码不能为空！'
					}), new Ext.form.TextField({
						fieldLabel : '当前所在',
						name : 'curware'
					}), new Ext.form.TextField({
						fieldLabel : '产品类别',
						name : 'typeid'
					}), new Ext.form.TextField({
						fieldLabel : '产品来源',
						name : 'prosource'
					}), new Ext.form.TextField({
						fieldLabel : '发送到',
						name : 'sendto'
					}), new Ext.form.TextField({
						fieldLabel : '发送日期',
						name : 'sourcedate'
					}), new Ext.form.TextField({
						fieldLabel : '发送人',
						name : 'sourceoper'
					}), new Ext.form.TextField({
						fieldLabel : '发送地区',
						name : 'sendarea'
					}), new Ext.form.TextField({
						fieldLabel : '接收人',
						name : 'reciver'
					}), new Ext.form.TextField({
						fieldLabel : '接收日期',
						name : 'receivedate'
					}), new Ext.form.TextField({
						fieldLabel : '接收信息',
						name : 'receivedate'
					})];

	genGrid = new GenGrid({
				id : 'techin',
				renderTo : thistab,
				cm : cm,
				ds : ds,
				sm : sm,
				width : thistab.getWidth(),
				autoHeight : true,
				cwidth : 300,
				cheight : 200,
				formFields : formFields,
				actionurl : '.\/json\/techin.action',
				autoHeight : true,
				rowdblclick : function() {
				}

			}

	);

	genGrid.tbar.addButton({
				text : '接收',
				handler : function() {

					if (genGrid.grid.getSelectionModel().getCount() < 1) {
						Ext.MessageBox.alert('提示', '没有选中行');
						return;
					}
					var ids = '';
					// Ext.MessageBox.alert('',ids);
					var sele = genGrid.grid.getSelectionModel().getSelections();
					for (var i = 0; i < sele.length; i++)
						ids += sele[i].get('id') + ',';

					// Ext.MessageBox.alert('', ids);
					// return;
					Ext.Ajax.request({
								url : genGrid.actionurl,
								params : {
									pageNo : genGrid.getPageNo(),
									pageCount : genGrid.getPageCount(),
									Condi : genGrid.getCondi(),
									command : 'ware',
									ids : ids
								},
								// 注意 createDelegate 的用法
								success : genGrid.refresh
										.createDelegate(genGrid),
								failure : function(action) {
									Ext.MessageBox.alert('错误', '接收失败！');
								}

							}

					);
				}
			});

	genGrid.tbar.addButton({
				text : '退回',
				handler : function() {

					if (genGrid.grid.getSelectionModel().getCount() < 1) {
						Ext.MessageBox.alert('提示', '没有选中行');
						return;
					}
					var ids = '';
					// Ext.MessageBox.alert('',ids);
					var sele = genGrid.grid.getSelectionModel().getSelections();
					for (var i = 0; i < sele.length; i++)
						ids += sele[i].get('id') + ',';

					// Ext.MessageBox.alert('', ids);
					// return;
					Ext.Ajax.request({
								url : genGrid.actionurl,
								params : {
									pageNo : genGrid.getPageNo(),
									pageCount : genGrid.getPageCount(),
									Condi : genGrid.getCondi(),
									command : 'back',
									ids : ids
								},
								// 注意 createDelegate 的用法
								success : genGrid.refresh
										.createDelegate(genGrid),
								failure : function(action) {
									Ext.MessageBox.alert('错误', '退回失败！');
								}

							}

					);
				}
			});

	Ext.get('techinadd').remove();
	Ext.get('techinmodi').remove();
	Ext.get('techindel').remove();
	genGrid.grid.on('rowdblclick', function() {
			});

});
