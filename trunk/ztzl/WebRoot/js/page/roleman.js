Ext.onReady(function() {
			var thistab = Ext.get('角色管理');
			thistab.clean(true);

			var pageNo, pageCount; // 当前面和每页记录数
			var totalCount, totalPage; // 总记录数和总页数
			// var params;

			var sm = new Ext.grid.CheckboxSelectionModel();

			var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header : '序号',
								width : 30
							}),// 自动行号
					sm,// 添加的地方
					{
						header : '角色编码',
						dataIndex : 'id'
					}, {
						header : '角色名称',
						dataIndex : 'rolename'
					}, {
						header : '角色信息',
						dataIndex : 'roleinfo'
					}, {
						header : '角色备注',
						dataIndex : 'rolememo'
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
											name : 'rolename'
										}, {
											name : 'roleinfo'
										}, {
											name : 'rolememo'
										}])
					});

			ds.load();

			var formFields = [new Ext.form.TextField({
								fieldLabel : '角色名称',
								name : 'rolename',
								allowBlank : false,
								blankText : '角色名不能为空！'
							}), new Ext.form.TextField({
								fieldLabel : '用户信息',
								name : 'roleinfo'
							}), new Ext.form.TextField({
								fieldLabel : '其它信息',
								name : 'rolememo'
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
	//alert(Ext.get('maintab').getHeight()-20);
							
			genGrid = new GenGrid({
						renderTo : thistab,
						cm : cm,
						ds : ds,
						sm : sm,
						width : thistab.getWidth()-10,
						height:Ext.get('maintab').getHeight()-32,
						cwidth : 300,
						cheight : 200,
						formFields : formFields,
						actionurl : '.\/json\/roleman.action',
						autoHeight : true

					}

			);
			
			

		});