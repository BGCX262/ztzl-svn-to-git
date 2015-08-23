Ext.onReady(function() {
			var thistab = Ext.get('故障原因管理');
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
						header : '故障编码',
						dataIndex : 'id'
					}, {
						header : '故障类别',
						dataIndex : 'errotype'
					}, {
						header : '故障原因',
						dataIndex : 'errorview'
					}, {
						header : '故障信息',
						dataIndex : 'errorinfo'
					}, {
						header : '是否使用',
						dataIndex : 'inuse',
						renderer : function(val) {
							if (val == '1')
								return '有效';
							else
								return '无效';
						}
					}

			]);
			cm.defaultSortable = true;

			var data;

			var ds = new Ext.data.Store({
						proxy : new Ext.data.MemoryProxy(data),
						reader : new Ext.data.JsonReader({
									root : 'list'
								}, [{
											name : 'id'
										}, {
											name : 'errorinfo'
										}, {
											name : 'errotype'
										}, {
											name : 'errorview'
										}, {
											name : 'inuse'
										}])
					});

			ds.load();

			var formFields = [new Ext.form.TextField({
								fieldLabel : '故障原因',
								name : 'errorview',
								allowBlank : false,
								blankText : '故障原因不能为空！'
							}), new Ext.form.TextField({
								fieldLabel : '故障类别',
								name : 'errotype'
							}), new Ext.form.TextField({
								fieldLabel : '故障说明',
								name : 'errorinfo'
							}), {
						xtype : 'radiogroup',
						fieldLabel : '是否有效',
						items : [{
									boxLabel : '是',
									name : 'inuse',
									inputValue : 1
								}, {
									boxLabel : '否',
									name : 'inuse',
									inputValue : 0
								}]
					}, new Ext.form.TextField({
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
						actionurl : '.\/json\/errorforman.action',
						autoHeight : true

					}

			);

		});