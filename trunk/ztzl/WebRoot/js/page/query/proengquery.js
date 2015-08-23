Ext.onReady(function() {
	var thistab = Ext.get('产品安装查询');
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
							Ext.MessageBox.alert('提示:', '取数据失败！');
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
				header : '安装日期',
				dataIndex : 'prodate',
				renderer : change
			}, {
				header : '安装地区',
				dataIndex : 'engarea'
			}, {
				header : '小区名称',
				dataIndex : 'areaname'
			}, {
				header : '安装地址',
				dataIndex : 'engaddr'
			}, {
				header : '负责人',
				dataIndex : 'principle'
			}, {
				header : '运行状态',
				dataIndex : 'runstatus',
				renderer : function(val) {
					if (val == '1')
						return '运行';
					else if (val == '2')
						return '调试';
					else
						return '拆除';
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
									name : 'principle'
								}, {
									name : 'proname'
								}, {
									name : 'prodate'/*
													 * , type : 'date',
													 * dateFormat : 'Y-m-d'
													 */
								}, {
									name : 'areaname'
								}, {
									name : 'engaddr'
								}, {
									name : 'engarea'
								}, {
									name : 'runstatus'
								}, {
									name : 'iuse'
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
						fieldLabel : '安装地区',
						name : 'engarea'
					}), new Ext.form.TextField({
						fieldLabel : '小区名称',
						name : 'areaname'
					}), new Ext.form.TextField({
						fieldLabel : '安装地址',
						name : 'engaddr'
					}), new Ext.form.DateField({
						fieldLabel : '安装日期',
						name : 'prodate',
						format : 'Y-m-dTh:i:s'
					}), new Ext.form.TextField({
						fieldLabel : '负责人',
						name : 'principle'
					}), new Ext.form.TextField({
						fieldLabel : '运行状态',
						name : 'runstatus'
					}), combo, new Ext.form.TextField({
						fieldLabel : 'ID',
						name : 'id',
						inputType : 'hidden'

					})];

	var id = 'peqy';

	genGrid = new GenGrid({
				id : id,
				renderTo : thistab,
				cm : cm,
				ds : ds,
				sm : sm,
				width : thistab.getWidth(),
				height : Ext.get('maintab').getHeight() - 81,
				cwidth : 300,
				cheight : 200,
				formFields : formFields,
				actionurl : '.\/json\/proeng.action',

				rowdblclick : function(grid, rowIndex, e) {
					var sele = genGrid.grid.getSelectionModel().getSelections();
					var id = sele[0].get('procode');
					show(id);

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
							text : '运行产品',
							handler : function() {
								genGrid.getData(1, 25,
										' and  runstatus= \'1\' ');
							}
						}, {
							text : '拆卸产品',
							handler : function() {
								genGrid.getData(1, 25,
										' and  runstatus<> \'1\' ');
							}
						}, {
							text : '本月安装',
							handler : function() {
								var dt = new Date();

								genGrid.getData(1, 25,
										' and  to_char(trunc(prodate,\'mm\'),\'yyyymm\')=\''
												+ dt.format('Ym') + '\'');
							}
						}, {
							text : '上月安装',
							handler : function() {
								var dt = new Date().add(Date.MONTH, -1);
								genGrid.getData(1, 25,
										' and  to_char(trunc(prodate,\'mm\'),\'yyyymm\')=\''
												+ dt.format('Ym') + '\'');
							}
						}]
			});

	function change(val) {
		var date = Date.parseDate(val, 'Y-m-dTh:i:s');
		if (date)
			return date.format('Y-m-d');
		else
			return null;
		// return Date.parseDate(val, 'Y-m-dTh:i:s').format('Y-m-d');
		// return val;
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

	var prodata;
	var procm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 30
					}),// 自动行号
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
				header : '产品条码',
				dataIndex : 'barcode'
			}, {
				header : '产品箱号',
				dataIndex : 'boxcode'
			}, {
				header : '产品批号',
				dataIndex : 'lotnum'
			}, {
				header : '生产日期',
				dataIndex : 'prodate',
				renderer : change
			}, {
				header : '产品信息',
				dataIndex : 'promemo'
			}, {
				header : '返修次数',
				dataIndex : 'repairnum'
			}, {
				header : '程序版本',
				dataIndex : 'proversion'
			}, {
				header : '产品状态',
				dataIndex : 'prostatus',
				renderer : function(val) {
					if (val == '1')
						return '入库';
					else
						return '新生产';
				}
			}, {
				header : '是否虚拟',
				dataIndex : 'virtualmark',
				renderer : function(val) {
					if (val == '1')
						return '虚拟';
					else
						return '实际';
				}
			}]);
	procm.defaultSortable = true;

	var prods = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(prodata),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'lotnum'
								}, {
									name : 'procode'
								}, {
									name : 'barcode'
								}, {
									name : 'boxcode'
								}, {
									name : 'prodate'/*
													 * , type : 'date',
													 * dateFormat : 'Y-m-d'
													 */
								}, {
									name : 'promemo'
								}, {
									name : 'prostatus'
								}, {
									name : 'proversion'
								}, {
									name : 'repairnum'
								}, {
									name : 'virtualmark'
								}, {
									name : 'typeid'
								}])
			});

	prods.load();

	var progrid = new Ext.grid.GridPanel({
				ds : prods,
				cm : procm,
				title : '产品信息',
				autoWidth : true,
				autoHeight : true
			});

	var flowdata;
	var flowcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 30
					}),// 自动行号
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
				header : '产品箱号',
				dataIndex : 'boxcode',
				hidden : true
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
				dataIndex : 'sourceoper',
				hidden : true
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
				dataIndex : 'sendinfo',
				hidden : true
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
				},
				hidden : true
			}, {
				header : '完成标志',
				dataIndex : 'returnmark',
				renderer : function(val) {
					if (val == '1')
						return '完成';
					else
						return '未完成';
				},
				hidden : true
			}]);
	flowcm.defaultSortable = true;

	var flowds = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(flowdata),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'procode'
								}, {
									name : 'boxcode'
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

	var flowgrid = new Ext.grid.GridPanel({
				ds : flowds,
				cm : flowcm,
				title : '产品流程',
				autoWidth : true,
				autoHeight : true
			});


			
  var repaircm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
						header : '序号',
						width : 30
					}),// 自动行号
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
				dataIndex : 'errorview'
			}, {
				header : '送修日期',
				dataIndex : 'senddate',
				renderer : change
			}, {
				header : '更换日期',
				dataIndex : 'changedate',
				renderer : change

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
	repaircm.defaultSortable = true;

	var repairdata;

	var repairds = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(repairdata),
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

	repairds.load();
	
	var repairgrid = new Ext.grid.GridPanel({
				ds : repairds,
				cm : repaircm,
				title : '产品返修',
				autoWidth : true,
				autoHeight : true
			});

	var tabpanel = new Ext.TabPanel({
				autoHeight : true,
				autoWidth : true,
				items : [progrid, flowgrid,repairgrid]
			});

	tabpanel.setActiveTab(progrid);

	if (Ext.getCmp('detailwin'))
		Ext.getCmp('detailwin').remove();

	var detailwin = new Ext.Window({
				id : 'detailwin',
				title : '产品详细信息',
				width : 600,
				height : 400,
				closable : true,
				constrainHeader : true,
				closeAction : 'hide',
				items : tabpanel
			});

	function show(id) {
		Ext.Ajax.request({
					url : '.\/json\/productquery.action',
					params : {
						pageNo : 1,
						pageCount : 100,
						Condi : ' and procode=\'' + id + '\''
					},
					success : function(action) {
						var myObject = Json.evaluate(action.responseText);
						if (myObject)
							if (myObject.notlog == true) {
								Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							}
						prods.loadData(myObject.pi);
					},
					failure : function(action) {
						Ext.MessageBox.alert('错误', '取数据失败！')
					}

				}

		);

		Ext.Ajax.request({
					url : '.\/json\/proflowquery.action',
					params : {
						pageNo : 1,
						pageCount : 100,
						Condi : ' and procode=\'' + id + '\''
					},
					success : function(action) {
						var myObject = Json.evaluate(action.responseText);
						if (myObject)
							if (myObject.notlog == true) {
								Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							}
						flowds.loadData(myObject.pi);
					},
					failure : function(action) {
						Ext.MessageBox.alert('错误', '取数据失败！')
					}

				}

		);

		
		
		Ext.Ajax.request({
					url : '.\/json\/repairquery.action',
					params : {
						pageNo : 1,
						pageCount : 100,
						Condi : ' and procode=\'' + id + '\''
					},
					success : function(action) {
						// alert(action.responseText);
						var engObject = Json.evaluate(action.responseText);

						if (engObject)
							if (engObject.notlog == true) {
								Ext.MessageBox.alert('登录已超时，请重新登录后操作！');
								window.location = 'login.jsp';
							}
						repairds.loadData(engObject.pi);
					},
					failure : function(action) {
						Ext.MessageBox.alert('错误', '取数据失败！')
					}

				}

		);

		detailwin.show();
	}

});