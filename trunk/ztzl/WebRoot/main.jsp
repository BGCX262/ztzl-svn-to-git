<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>

<script language="JavaScript" src="Charts/JSClass/FusionCharts.js"></script>
		
	
	</head>
	<body>
		

		<div id="chartdiv" align="center">
			
		</div>
		<script type="text/javascript">　　
　　　　　　　 var myChart = new FusionCharts("Charts/Column3D.swf", "myChartId", "600", "500");　　　
　　　　　　　 myChart.setDataURL("Data.xml");　　　
　　　　　　　 myChart.render("chartdiv");　　　
　　　 </script>
<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			width="600" height="500" id="Column3D">
			<param name="movie" value="./Charts/Column3D.swf" />
			<param name="FlashVars"
				value="&dataURL=Data.xml&chartWidth=600&chartHeight=500">
			<param name="quality" value="high" />
			<embed src="./Charts/Column3D.swf"
				flashVars="&dataURL=Data.xml&chartWidth=600&chartHeight=500"
				quality="high" width="600" height="500" name="Column3D"
				type="application/x-shockwave-flash"
				pluginspage="http://www.macromedia.com/go/getflashplayer" />
		</object>
		

	</body>
</html>