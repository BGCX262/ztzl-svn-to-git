Ext.onReady(function() {
	var thistab = Ext.get('地市返修产品');
	thistab.clean(true);

	var pageNo, pageCount; // 当前面和每页记录数
	var totalCount, totalPage; // 总记录数和总页数
	// var params;

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

	// 故障类别combo
	var repairobj;
	Ext.Ajax.request({
				url : '.\/json\/errortypeman.action',
				params : {
					pageNo : 1,
					pageCount : 100,
					Condi : ''
				},
				success : getError
				// alert(this.myObject.pi.list[1].id);
				,
				failure : function(action) {
					Ext.MessageBox.alert('错误', '取数据失败！')
				}

			}

	);

	function getError(action) {
		repairobj = Json.evaluate(action.responseText);
		errorstore.loadData(repairobj.pi);
	}

	// var store = new Ext.data.SimpleStore({
	// fields : ['id', 'typename']
	// });
	//					
	var errortype;
	var errorstore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(errortype),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'errorview'
								}, {
									name : 'errortype'
								}, {
									name : 'errorinfo'
								}, {
									name : 'inuse'
								}])
			});

	errorstore.load();

	var errorcombo = new Ext.form.ComboBox({
				store : errorstore,
				// id : 'comboField',
				name : 'errorview',
				fieldLabel : '故障现象',
				displayField : 'errorview',
				valueField : 'id',
				hiddenName : 'errorview',
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
	
	
	var repairobj1;
	Ext.Ajax.request({
				url : '.\/json\/errorforman.action',
				params : {
					pageNo : 1,
					pageCount : 100,
					Condi : ''
				},
				success : getErrorfor
				// alert(this.myObject.pi.list[1].id);
				,
				failure : function(action) {
					Ext.MessageBox.alert('错误', '取数据失败！')
				}

			}

	);

	function getErrorfor(action) {
		repairobj1 = Json.evaluate(action.responseText);
		errorforstore.loadData(repairobj1.pi);
	}
	
	var errorfor;
	var errorforstore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(errorfor),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'errorview'
								}, {
									name : 'errortype'
								}, {
									name : 'errorinfo'
								}, {
									name : 'inuse'
								}])
			});

	errorforstore.load();

	var errorforcombo = new Ext.form.ComboBox({
				store : errorforstore,
				// id : 'comboField',
				name : 'errorview',
				fieldLabel : '故障原因',
				displayField : 'errorview',
				valueField : 'id',
				hiddenName : 'errorfor',
				typeAhead : true,
				editable : false,
				mode : 'local',
				forceSelection : true,
				width : 80,
				triggerAction : 'all',
				emptyText : '请选择字段...',
				selectOnFocus : true
			});
	

	function change(val) {
		if (val)
			return Date.parseDate(val, 'Y-m-dTh:i:s').format('Y-m-d');
		else
			return null;
	}

	function listType(val) {
		var tp = store.getAt(store.find('id', val));
		if (tp)
			return tp.get('typename');
		else
			return null;
	}

	function listError(val) {
		var ev = errorstore.getAt(errorstore.find('id', val));
		if (ev)
			return ev.get('errorview');
		else
			return null;
	}
	
	function listErrorfor(val) {
		var ev = errorforstore.getAt(errorforstore.find('id', val));
		if (ev)
			return ev.get('errorview');
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
				header : '故障现象',
				dataIndex : 'errorview',
				renderer : listError
			}, {
				header : '送修日期',
				dataIndex : 'senddate',
				renderer : change
			}, {
				header : '更换日期',
				dataIndex : 'changedate',
				renderer : change

			}, {
				header : '故障原因',
				dataIndex : 'errorfor',
				renderer : listErrorfor
			}, {
				header : '更换产品',
				dataIndex : 'changecode'
			}, {
				header : '备注信息',
				dataIndex : 'memo'
			}, {
				header : '返修状态',
				dataIndex : 'waremark',
				renderer : function(val) {
					if (val == '0')
						return '新录入';
					else if (val == '1')
						return '已送检';
					else if (val == '2')
						return '已入库';
						else if (val == '3')
						return '维修中';
					else
						return '已修理';
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
									name : 'errorview'
								}, {
									name : 'typeid'
								}, {
									name : 'senddate'/*
														 * , type : 'date',
														 * dateFormat : 'Y-m-d'
														 */
								}, {
									name : 'changecode'
								}, {
									name : 'changedate'
								}, {
									name : 'memo'
								}, {
									name : 'waremark'
								}, {
									name : 'errorfor'
								}, {
									name : 'inuse'
								}])
			});

	ds.load();

	function getEngAddress(txt, n, o) {

		if (n != null)
			Ext.Ajax.request({
				url : '.\/json\/proeng.action',
				params : {
					Condi : ' and procode =\'' + n + '\''
				},
				success : function(action, form) {
					var myObject = Json.evaluate(action.responseText);
					if (myObject.notlog == true) {
						alert('登录已超时，请重新登录后操作！');
						window.location = 'login.jsp';
					}
					var data = myObject.pi.list[0];
					if (data)
						Ext.MessageBox.alert('提示', data.engarea + data.engaddr);
					else
						Ext.MessageBox.alert('提示', '无安装记录！');
				}
				// alert(this.myObject.pi.list[1].id);
				,
				failure : function(action) {
					Ext.MessageBox.alert('错误', '取安装记录失败！')
				}
			});
	}

	var formFields = [new Ext.form.TextField({
						fieldLabel : '产品编码',
						name : 'procode',
						allowBlank : false,
						blankText : '产品编码不能为空！',
						listeners : {
							change : getEngAddress

						}
					}), new Ext.form.DateField({
						fieldLabel : '发送日期',
						name : 'senddate',
						format : 'Y-m-dTh:i:s'
					}), new Ext.form.DateField({
						fieldLabel : '更换日期',
						name : 'changedate',
						format : 'Y-m-dTh:i:s'
					}), new Ext.form.TextField({
						fieldLabel : '更换产品',
						name : 'changecode'
					}), combo, errorcombo,errorforcombo, new Ext.form.TextField({
						fieldLabel : '备注信息',
						name : 'memo'
					}), new Ext.form.TextField({
						fieldLabel : 'ID',
						name : 'id',
						inputType : 'hidden'

					}), new Ext.form.TextField({
						fieldLabel : '命令',
						name : 'command',
						inputType : 'hidden',
						value : 'add'

					})];
	var id = 'rpqy';
	genGrid = new GenGrid({
				id : id,
				renderTo : thistab,
				cm : cm,
				ds : ds,
				sm : sm,
				width : Ext.get('maintab').getWidth() - 20,
				height : Ext.get('maintab').getHeight() - 81,
				cwidth : 300,
				cheight : 200,
				formFields : formFields,
				actionurl : '.\/json\/arearepairquery.action',
				rowdblclick : function() {
				}

			}

	);

	Ext.get(id + 'add').remove();
	Ext.get(id + 'modi').remove();
	Ext.get(id + 'del').remove();

	var qpn = new Ext.Toolbar({
				renderTo : thistab,
				width : Ext.get('maintab').getWidth() - 20,
				items : [{
							text : '快速查询:'
						}, {
							text : '全部产品',
							handler : function() {
								genGrid.getData(1, 25, '');
							}
						}, {
							text : '未送检',
							handler : function() {
								genGrid
										.getData(1, 25,
												' and  waremark= \'0\' ');
							}
						}, {
							text : '已送检',
							handler : function() {
								genGrid
										.getData(1, 25,
												' and  waremark= \'1\' ');
							}
						}, {
							text : '维修中',
							handler : function() {
								genGrid
										.getData(1, 25,
												' and  waremark= \'2\' ');
							}
						}, {
							text : '已修理',
							handler : function() {
								genGrid
										.getData(1, 25,
												' and  waremark= \'3\' ');
							}
						}, {
							text : '本月返修',
							handler : function() {
								var dt = new Date();

								genGrid.getData(1, 25,
										' and  to_char(trunc(senddate,\'mm\'),\'yyyymm\')=\''
												+ dt.format('Ym') + '\'');
							}
						}, {
							text : '上月返修',
							handler : function() {
								var dt = new Date().add(Date.MONTH, -1);
								genGrid.getData(1, 25,
										' and  to_char(trunc(senddate,\'mm\'),\'yyyymm\')=\''
												+ dt.format('Ym') + '\'');
							}
						}]
			});

});