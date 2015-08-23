

Ext.onReady(function() {

	var thistab = Ext.get('产品安装统计表');
	thistab.clean(true);

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
				header : '安装地区',
				dataIndex : 'engarea',
				width : 100
			}, {
				header : '年月',
				dataIndex : 'ny',
				width : 100
			}, {
				header : '类别名称',
				dataIndex : 'typeid',
				renderer : listType
			}, {
				header : '数量',
				dataIndex : 'cn'
			}]);
	cm.defaultSortable = true;

	var data;

	var ds = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'engarea'
								}, {
									name : 'ny'
								}, {
									name : 'cn'
								}, {
									name : 'typeid'
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
				fieldLabel : '命令',
				name : 'command',
				inputType : 'hidden',
				value : 'add'

			})];
	var id = 'peqy';
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
				actionurl : '.\/json\/proengstatus.action',
				rowdblclick : function() {
				}

			}

	);

	Ext.get(id + 'add').remove();
	Ext.get(id + 'modi').remove();
	Ext.get(id + 'del').remove();

	var start = new Ext.form.DateField({
				value : new Date().add(Date.MONTH, -6).format('Y-m'),
				readOnly : true,
				format : 'Y-m'
			});
	var end = new Ext.form.DateField({
				value : new Date().format('Y-m'),
				readOnly : true,
				maxValue : new Date().format('Y-m'),
				format : 'Y-m'
			});

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
							text : '本年度安装',
							handler : function() {
								var dt = new Date();

								genGrid
										.getData(1, 25, ' and   a.id.ny >= \''
														+ dt.format('Y')
														+ '-01' + '\'');
							}
						}, {
							text : '上年度安装',
							handler : function() {
								var dt = new Date().add(Date.YEAR, -1);

								genGrid.getData(1, 25, ' and   a.id.ny >= \''
												+ dt.format('Y') + '-01'
												+ '\' and a.id.ny<=\''
												+ dt.format('Y') + '-12'
												+ '\' ');
							}
						}, '按时间段查询', start, '到', end, {
							text : '查询',
							handler : function() {
								
								genGrid.getData(1, 25, ' and   a.id.ny >= \''
												+ start.value
												+ '\' and a.id.ny<=\''
												+ end.value + '\' ');
							}
						}]
			});

});
