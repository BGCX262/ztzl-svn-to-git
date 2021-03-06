Ext.onReady(function() {
			var thistab = Ext.get('产品类别查询');
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
						header : '类别编码',
						dataIndex : 'id'
					}, {
						header : '功能类型',
						dataIndex : 'functype'
					}, {
						header : '类别名称',
						dataIndex : 'typename'
					}, {
						header : '产品类别信息',
						dataIndex : 'typeinfo'
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
											name : 'typename'
										}, {
											name : 'functype'
										}, {
											name : 'typeinfo'
										}, {
											name : 'inuse'
										}])
					});

			ds.load();

			var formFields = [new Ext.form.TextField({
								fieldLabel : '类型名',
								name : 'typename',
								allowBlank : false,
								blankText : '类型名不能为空！'
							}), new Ext.form.TextField({
								fieldLabel : '功能类型',
								name : 'functype'
							}), new Ext.form.TextField({
								fieldLabel : '类型描述',
								name : 'typeinfo'
							}), {
						xtype : 'radiogroup',
						fieldLabel : '是否有效',
						items : [{
									boxLabel : '是',
									name : 'inuse',
									inputValue : 1,
									checked : true
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
						id : 'tpqy',
						renderTo : thistab,
						cm : cm,
						ds : ds,
						sm : sm,
						width : thistab.getWidth(),
						autoHeight : true,
						cwidth : 300,
						cheight : 200,
						formFields : formFields,
						actionurl : '.\/json\/producttype.action',
						autoHeight : true,
						rowdblclick : function() {
						}

					}

			);

			Ext.get('tpqyadd').remove();
			Ext.get('tpqymodi').remove();
			Ext.get('tpqydel').remove();

		});