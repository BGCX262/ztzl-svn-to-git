Ext.onReady(function() {
			var thistab = Ext.get('发送地区管理');
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
						header : '发送地区编码',
						dataIndex : 'id'
					}, {
						header : '发送地区',
						dataIndex : 'sendarea'
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
											name : 'sendarea'
										}])
					});

			ds.load();

			var formFields = [new Ext.form.TextField({
								fieldLabel : '发送地区',
								name : 'sendarea',
								allowBlank : false,
								blankText : '发送地区不能为空！'
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

			genGrid = new GenGrid({
						renderTo : thistab,
						cm : cm,
						ds : ds,
						sm : sm,
						width : thistab.getWidth(),
						autoHeight : true,
						cwidth : 300,
						cheight : 200,
						formFields : formFields,
						actionurl : '.\/json\/sendarea.action',
						autoHeight : true

					}

			);

		});