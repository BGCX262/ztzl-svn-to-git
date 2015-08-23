function randomColor() {
	var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
	if (rand.length == 6) {
		return rand;
	} else {
		return randomColor();
	}
}

function getBody(name, value, color) {
	return "<set name='" + name + "' value='" + value + "' color='" + color
			+ "' />";
}

function getHead(caption, xa, ya) {
	return "<graph baseFontSize='12' caption='" + caption + "' xAisName='" + xa + "' yAxisName='"
			+ ya + "' decimalPrecision='2' formatNumberScale='0'>";
}

function getTail() {
	return "</graph>";
}

Ext.onReady( function() {

	var thistab = Ext.get('月产量走势图');
	thistab.clean(true);

	var data = [ [ 'Charts/Area2D.swf', '二维区域图' ],
			[ 'Charts/Bar2D.swf', '二维条图' ], [ 'Charts/Column2D.swf', '二维柱状图' ],
			[ 'Charts/Column3D.swf', '三维柱状图' ],
			[ 'Charts/Doughnut2D.swf', '二维环图' ],
			[ 'Charts/Doughnut3D.swf', '三维环图' ],
			[ 'Charts/Pie2D.swf', '二维饼图' ], [ 'Charts/Pie3D.swf', '三维饼图' ] ];

	var store = new Ext.data.SimpleStore( {
		fields : [ 'value', 'text' ],
		data :data
	});

	var combo = new Ext.form.ComboBox( {
		store :store,
		// id : 'comboField',
		displayField :'text',
		valueField :'value',
		typeAhead :true,
		editable :false,
		mode :'local',
		forceSelection :true,
		width :80,
		triggerAction :'all',
		emptyText :'请选择图形...',
		selectOnFocus :true
	});
	combo.setValue('Charts/Column3D.swf');

	var start = new Ext.form.DateField( {
		format :'Y-m',
		value :new Date().add(Date.MONTH, -6).format('Y-m'),
		readOnly :true
	});
	var end = new Ext.form.DateField( {
		format :'Y-m',
		value :new Date().format('Y-m'),
		readOnly :true,
		maxValue :new Date().format('Y-m')
	});

	var tbar = new Ext.Toolbar( {
		// renderTo : thistab,
			items : [
					{
						text :'开始月份:'
					},
					start,
					{
						text :'结束月份:'
					},
					end,
					combo,
					{
						text :'  查询  ',
						handler : function() {

							var condi = " and prodate between to_date('"
									+ start.value
									+ "','yyyy-mm') and to_date('" + end.value
									+ "','yyyy-mm') ";
							getData(condi);

						}
					}

			]
		});

	var p = new Ext.Panel( {

		autoWidth :true,
		autoHeigt :true
	});
	var t = new Ext.Panel( {
		tbar :tbar,
		renderTo :thistab,
		autoWidth :true,
		autoHeigt :true,
		items :p
	});
	var data;

	getData(null);

	

	function getData(condi) {
		Ext.Ajax.request( {
			url :'.\/json\/monthyield.action',
			params : {
				pageNo :1,
				pageCount :100,
				Condi :condi
			},
			callback : function() {
				// Ext.MessageBox.hide();
		},
		// 注意 createDelegate 的用法
			success :Success,
			failure : function(action) {
				var myObject = Json.evaluate(action.responseText);
				if (myObject)
					if (myObject.notlog == true) {
						Ext.MessageBox.alert('提示', '登录已超时,请重新登录后操作！');
						window.location = 'login.jsp';
					} else
						Ext.MessageBox.alert('错误', '取数据失败！');
			}

		}

		);
	}

	function Success(action) {
		var myObject = Json.evaluate(action.responseText);

		if (myObject.notlog == true) {
			Ext.MessageBox.alert('提示', '登录已超时，请重新登录后操作！');
			window.location = 'login.jsp';
		}
		data = myObject.pi.list;
		process(data);
	}

	function process(list) {
		var myChart = new FusionCharts(combo.getValue(), "myChartId", "600",
				"400");
		var xmlData = getHead('月产量统计图', '月份', '产量');
		;

		for ( var i = 0; i < list.length; i++) {
			xmlData += getBody(list[i][0], list[i][1], randomColor());
		}
		xmlData += getTail();
		myChart.setDataXML(xmlData);
		myChart.setTransparent(true);
		myChart.render(p.getId());

	}

	// var xmlData = "<graph caption='月销售量报表Monthly Unit Sales'
		// xAxisName='Month' yAxisName='Units' showNames='1'
		// decimalPrecision='0'
		// formatNumberScale='0'>" +
		//
		// "<set name='Jan' value='462' color='"+ randomColor()+"' />" +
		//
		// "<set name='Feb' value='857' color='"+ randomColor()+"' />" +
		//
		// "<set name='Mar' value='671' color='"+ randomColor()+"' />" +
		//
		// "<set name='Apr' value='494' color='"+ randomColor()+"' />" +
		//
		// "<set name='May' value='761' color='"+ randomColor()+"' />" +
		//
		// "<set name='Jun' value='960' color='"+ randomColor()+"' />" +
		//
		// "<set name='Jul' value='629' color='"+ randomColor()+"' />" +
		//
		// "<set name='Aug' value='622' color='588526' />" +
		//
		// "<set name='Sep' value='376' color='B3AA00' />" +
		//
		// "<set name='Oct' value='494' color='008ED6' />" +
		//
		// "<set name='Nov' value='761' color='"+ randomColor()+"' />" +
		//
		// "<set name='Dec' value='960' color='A186BE' />" +
		//
		// "</graph>";
		// var myChart = new FusionCharts("Charts/Column3D.swf", "myChartId",
		// "600",
		// "400");
		// // myChart.setDataURL("Data.xml");
		// myChart.setDataXML(xmlData);
		// // alert('test');
		// myChart.render('tab');

		// var tab1= new Ext.Panel({
		// id : 'tab1',
		// autoWidth : true,
		// autoHeight : true,
		// renderTo : thistab
		// });
		// var pieChart=new
		// FusionCharts("Charts/Doughnut2D.swf","Doughnut","600","400");
		// pieChart.setDataXML(xmlData);
		// pieChart.render("tab1");
		//
		//	
		// var tab2= new Ext.Panel({
		// id : 'tab2',
		// autoWidth : true,
		// autoHeight : true,
		// renderTo : thistab
		// });
		// var pieChart2=new
		// FusionCharts("Charts/Pie3D.swf","pie23","600","400");
		// pieChart2.setDataXML(xmlData);
		// pieChart2.render('tab2');
		//	
		//	
		// var tab3= new Ext.Panel({
		// id : 'tab3',
		// autoWidth : true,
		// autoHeight : true,
		// renderTo : thistab
		// });
		// var Chart3=new FusionCharts("Charts/Area2D.swf","sssss","600","400");
		// Chart3.setDataXML(xmlData);
		// Chart3.render('tab3');
		//	
		// rt(randomColor());
		// alert('test2');
		// }
		// grid.on("afteredit", afterE, this);
		// grid.render(document.body);
	});
