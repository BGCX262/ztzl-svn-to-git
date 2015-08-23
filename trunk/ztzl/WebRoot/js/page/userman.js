Ext.onReady(function() {
			var thistab = Ext.get('用户管理');
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
						header : '用户编码',
						dataIndex : 'id'
					}, {
						header : '用户名称',
						dataIndex : 'username'
					}, {
						header : '用户信息',
						dataIndex : 'memo'
					}, {
						header : '用户地区',
						dataIndex : 'userarea'
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
											name : 'username'
										}, {
											name : 'userpass'
										}, {
											name : 'memo'
										}, {
											name : 'userarea'
										}])
					});

			ds.load();

			var formFields = [new Ext.form.TextField({
								fieldLabel : '用户名称',
								name : 'username',
								allowBlank : false,
								blankText : '用户名不能为空！'
							}), new Ext.form.TextField({
								fieldLabel : '用户密码',
								name : 'userpass',
								inputType : 'password'
							}), new Ext.form.TextField({
								fieldLabel : '重复密码',
								name : 'repass',
								inputType : 'password'
							}), new Ext.form.TextField({
								fieldLabel : '用户地区',
								name : 'userarea'
							}), new Ext.form.TextField({
								fieldLabel : '用户信息',
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
	//alert(Ext.get('maintab').getHeight()-20);
							
			genGrid = new GenGrid({
						renderTo : thistab,
						cm : cm,
						ds : ds,
						sm : sm,
						width : thistab.getWidth()-10,
						height:Ext.get('maintab').getHeight()-52,
						cwidth : 300,
						cheight : 200,
						formFields : formFields,
						actionurl : '.\/json\/userman.action',
						autoHeight : true

					}

			);
			
			
			//用户角色维护　
			
			

		});