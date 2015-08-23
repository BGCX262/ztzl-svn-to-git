Ext.onReady(function() {
	
	
	
	var thistab = Ext.get('产品信息管理');
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
					Ext.MessageBox.alert('错误', '取数据失败！')
				}

			}

	);

	function getList(action) {
		var myObject = Json
											.evaluate(action.responseText);
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
				width:100
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
	cm.defaultSortable = true;

	var data;

	var ds = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
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

	ds.load();

	var formFields = [new Ext.form.TextField({
						fieldLabel : '产品编码',
						name : 'procode',
						allowBlank : false,
						blankText : '产品编码不能为空！'
					}), new Ext.form.TextField({
						fieldLabel : '产品条码',
						name : 'barcode'
					}), new Ext.form.TextField({
						fieldLabel : '产品批号',
						name : 'lotnum'
					}), new Ext.form.TextField({
						fieldLabel : '产品箱号',
						name : 'boxcode'
					}), new Ext.form.DateField({
						fieldLabel : '生产日期',
						name : 'prodate',
						format : 'Y-m-dTh:i:s'
					}), new Ext.form.TextField({
						fieldLabel : '产品备注',
						name : 'promemo'
					}), new Ext.form.RadioGroup({
						fieldLabel : '产品状态',
						name : 'prostatus',
						items : [{
									boxLabel : '已入库',
									name : 'prostatus',
									inputValue : 1
								}, {
									boxLabel : '待入库',
									name : 'prostatus',
									inputValue : 0,
									checked:true
								}]
					}), new Ext.form.TextField({
						fieldLabel : '程序版本',
						name : 'proversion'
					}), combo, new Ext.form.TextField({
						fieldLabel : 'ID',
						name : 'id',
						inputType : 'hidden'

					}), new Ext.form.TextField({
						fieldLabel : '命令',
						name : 'command',
						inputType : 'hidden',
						value : 'add'

					})];

	genGrid = new GenGrid({
				renderTo : thistab,
				cm : cm,
				ds : ds,
				sm : sm,
				width : thistab.getWidth(),
				autoHeight:true,
				cwidth : 300,
				cheight : 200,
				formFields : formFields,
				actionurl : '.\/json\/productman.action',
				autoHeight : true

			}

	);

	genGrid.tbar.addButton({
				text : '入 库 ',
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
									Ext.MessageBox.alert('错误', '入库失败！'
													+ action.result.rtntxt);
								}

							}

					);
				}
			});
			
			genGrid.tbar.addButton({
				text : '报废 ',
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
									command : 'reject',
									ids : ids
								},
								// 注意 createDelegate 的用法
								success : genGrid.refresh
										.createDelegate(genGrid),
								failure : function(action) {
									Ext.MessageBox.alert('错误', '入库失败！'
													+ action.result.rtntxt);
								}

							}

					);
				}
			});

});