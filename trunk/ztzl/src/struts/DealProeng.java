package struts;

import hibernate.pojo.Products;
import hibernate.pojo.Proeng;
import hibernate.pojo.Proflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DealProeng extends BaseAction {

	public Date getDate() {
		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd", java.util.Locale.CHINA);
			java.util.Date d = sdf.parse("2006-01-01");
			return d;

		} catch (Exception e) {
			return new Date();
		}

	}

	public int dealEng(BaseAction bs, List<Proeng> list) {
		List allobj = new ArrayList();
		for (Proeng o : list) {

			// 判断产品编码是否符合要求，不符合要求的编号为1111111并且不生成虚拟记录，仅生成安装记录
			if (!Regular.match(o.getProcode()))
				o.setProcode("11111111");
			else {
				// 判断系统中是否存在
				List products = bs
						.findAll((" from Products where 1=1 and procode ='"
								+ o.getProcode() + "' "));

				// 系统中不存在的记录建立虚拟记录
				if (products.size() > 0) {
					o.setTypeid(((Products) products.get(0)).getTypeid());
				} else {
					if (!o.getProcode().trim().equals("")) {
						List virtalist = dealVirtual(o);
						allobj.addAll(virtalist);
					}
				}

				// 判断是否有安装记录 有安装记录的进行更新
				List pro = bs
						.findAll(" from Proeng where 1=1 and runstatus='1' and procode ='"
								+ o.getProcode() + "' ");
				if (pro.size() > 0)
					o.setId(((Proeng) pro.get(0)).getId());
			}
		}
		// 添加到要保存的列表
		allobj.addAll(list);
		// 全部保存
		bs.uporsave(allobj);

		return 0;
	}

	public List dealVirtual(Proeng proeng) {

		if (proeng.getProcode().equals("1111111111"))
			return null;

		List list = new ArrayList();

		Products products = new Products();
		products.setProcode(proeng.getProcode());
		products.setBarcode(proeng.getProcode());
		products.setTypeid(proeng.getTypeid());
		products.setLotnum("11111111");
		products.setVirtualmark("1");
		products.setProstatus("1");
		products.setTypeid(proeng.getTypeid());
		products.setProdate(proeng.getProdate());

		list.add(products);

		Proflow pf1 = new Proflow();
		Proflow pf2 = new Proflow();
		// 入库记录
		pf1.setProcode(proeng.getProcode());
		pf1.setCurware("2");
		pf1.setOvermark("1");
		pf1.setProsource("1");
		pf1.setSendto("2");
		pf1.setSourcedate(proeng.getProdate());
		pf1.setReceivedate(proeng.getProdate());
		pf1.setReceiver("virtual");
		pf1.setSourceoper("virtual");
		// 出库记录
		pf2.setProcode(proeng.getProcode());
		pf2.setCurware("3");
		pf2.setOvermark("1");
		pf2.setProsource("2");
		pf2.setSendto("3");
		pf2.setSourcedate(proeng.getProdate());
		pf2.setReceivedate(proeng.getProdate());
		pf2.setReceiver("virtual");
		pf2.setSourceoper("virtual");

		list.add(pf1);
		list.add(pf2);

		return list;
	}

	public static void main(String[] args) {
		// 测试正则表达式
		System.out.println(Regular.match("三经书苑总表－2196"));
	}

}

class Regular {
	public static boolean match(String procode) {
		//Pattern pattern = Pattern.compile("(\\d|-){4,15}");
		Pattern pattern = Pattern.compile("(\\d|-)\\d{4,15}");
		Matcher matcher = pattern.matcher(procode);
//		for(int i=0;i<procode.length();i++)
//		{
//			String s=procode.substring(i,i+1);
//		 if (Pattern.matches("[\u4e00-\u9fa5]",s )){
//		     return false;
//		    }
//		}
		if (matcher.find())
			return true;
		else
			return false;
	}

}
