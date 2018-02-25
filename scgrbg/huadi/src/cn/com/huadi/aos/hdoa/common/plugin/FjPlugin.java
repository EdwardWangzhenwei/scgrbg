/**
 * 
 */
package cn.com.huadi.aos.hdoa.common.plugin;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.com.huadi.aos.hdoa.common.service.DefineJdid;
import cn.com.huadi.aos.hdoa.common.service.FjService;
import cn.com.huadi.aos.hdoa.common.upload.FileUploaderTool;
import cn.com.huadi.aos.hdoa.common.util.UuidUtil;
import com.aisino.platform.db.DbSvr;
import com.aisino.platform.exception.BusinessException;
import com.aisino.platform.util.SessUtil;
import com.aisino.platform.view.AbstractForm;
import com.aisino.platform.view.DataMsgBus;
import com.aisino.platform.view.listener.FormCreateListener;
import com.aisino.platform.view.listener.SubmitListener;

/**
 * @author qinyalin
 *
 * 2017年7月13日
 */
public class FjPlugin implements FormCreateListener, SubmitListener {
	@Override
	public void doSubmitAction(AbstractForm arg0, DataMsgBus arg1) {
		Map<String, File> l = (Map) arg1.getValue("f1");
		HttpSession session = SessUtil.getRequest().getSession();
		Map userInfo = (Map) session.getAttribute("USERINFO");
		if (userInfo != null) {
			try {
				Object userName = userInfo.get("user_name");
				Object unit_id = userInfo.get("unit_id");

				String union_table = arg1.getString("union_table");
				/*Integer union_table_id = arg1.getString("union_table_id");*/
				Object union_table_oid = arg1.get("union_table_id");
				Integer union_table_id = null;
				String llr = String.valueOf(userName);
				String unitId = String.valueOf(unit_id);
				if (union_table_oid != null&&union_table != null) {
					union_table_id = Integer.parseInt(union_table_oid.toString()) ;
				} else {
					union_table_id = DefineJdid.getMaxId();
					arg0.updateWidgetValue("union_table_id", union_table_id);
				}
				boolean falg = this.GetFileUploaderTool().uploade(l, llr,
						union_table, union_table_id);
				if (falg) {
					//arg0.setReturn("uploadSuccess");
					//arg1.put("dzwj_id", union_table_id);
				    //arg0.updateWidgetValue("dzwj_id", union_table_id);
				    arg0.setReturn("ok");
					arg0.executeJs("PT.closeModal("+union_table_id+")");
				} else {
					arg0.executeJs("PT.closeModal("+union_table_id+")");
					arg0.setReturn("delFail");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusinessException("执行操作错误,请联系系统管理员");
			}
		}
	}

	@Override
	public void onFormCreate(AbstractForm arg0, DataMsgBus arg1) {
		

	}

	public FileUploaderTool GetFileUploaderTool() {
		return new FileUploaderTool();
	}
	/*public JddjService getJddjService() {
		return new JddjService();
	}*/

}
