
/**
 * @author qinjinwei
 * modi by zhang   zzw0598@gmail.com
 * time: 2008年10月27日
 */
var idTmr = "";
Ext.ux.Grid2Excel = {

	Save2Excel : function(grid) {
		var cm = grid.getColumnModel();
		var store = grid.getStore();

		var it = store.data.items;
		var rows = it.length;

		var oXL = new ActiveXObject("Excel.application");
		var oWB = oXL.Workbooks.Add();
		var oSheet = oWB.ActiveSheet;
		// alert('true');

		for (var i = 0; i < cm.getColumnCount(); i++) {

			if (!cm.isHidden(i)) {
				oSheet.Cells(1, i + 1).value = cm.getColumnHeader(i);
			}

			for (var j = 0; j < rows; j++) {
				r = it[j].data;
				var v = r[cm.getDataIndex(i)];
				var fld = store.recordType.prototype.fields.get(cm
						.getDataIndex(i));
				if (fld&&fld.type == 'date') {
					v = v.format('Y-m-d');
				}
				//设置所有单元格格式为 字符 
				oSheet.Cells(2+j, i+1).NumberFormatLocal = "@"
				
				oSheet.Cells(2 + j, i + 1).value = v;
			}
		}
		oXL.DisplayAlerts = false;
		oXL.Visible = true;
		
		//oXL.Save();  //如果使用此行，可以自动保存

		oXL.DisplayAlerts = true;
		//oXL.Quit();　　//如果使用此行，可以自动退出　
		oXL = null;
		idTmr = window.setInterval("Cleanup();", 1);
	}
};
function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
};