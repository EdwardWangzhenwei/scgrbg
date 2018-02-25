/**
 * 
 */
package cn.com.huadi.aos.hdoa.dispose.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 附件
 * 
 * @author nmj
 * @E-mail bjahqj@163.com
 * @date 2016年4月26日上午9:26:46
 * @return
 * 
 */

@XStreamAlias("附件")
public class AccessoryType {
	public AccessoryType() {
	}
	@XStreamAlias("附件id")
	private String dzwj_id;
	@XStreamAlias("附件名称")
	private String dzwjm;
	@XStreamAlias("附件相对下载地址")
	private String file_catalog;
	@XStreamAlias("附件真实名称")
	private String file_name;
	@XStreamAlias("附件内容")
	private String accessoryconten;
	@XStreamAlias("附件后缀名")
	private String dzwjlx;
	@XStreamAlias("附件类型")
	private String dzwjsx;
	@XStreamAlias("附件大小")
	private String dzwj_size;
	@XStreamAlias("创建人")
	private String lrr;
	@XStreamAlias("创建时间")
	private String lrsj;
	@XStreamAlias("密级")
	private String mj;
	@XStreamAlias("关联表")
	private String con_table;
	@XStreamAlias("关联表ID")
	private String con_table_id;
	public String getDzwj_id() {
		return dzwj_id;
	}
	public void setDzwj_id(String dzwj_id) {
		this.dzwj_id = dzwj_id;
	}
	public String getDzwjm() {
		return dzwjm;
	}
	public void setDzwjm(String dzwjm) {
		this.dzwjm = dzwjm;
	}
	public String getFile_catalog() {
		return file_catalog;
	}
	public void setFile_catalog(String file_catalog) {
		this.file_catalog = file_catalog;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getAccessoryconten() {
		return accessoryconten;
	}
	public void setAccessoryconten(String accessoryconten) {
		this.accessoryconten = accessoryconten;
	}
	public String getDzwjlx() {
		return dzwjlx;
	}
	public void setDzwjlx(String dzwjlx) {
		this.dzwjlx = dzwjlx;
	}
	public String getDzwjsx() {
		return dzwjsx;
	}
	public void setDzwjsx(String dzwjsx) {
		this.dzwjsx = dzwjsx;
	}
	public String getDzwj_size() {
		return dzwj_size;
	}
	public void setDzwj_size(String dzwj_size) {
		this.dzwj_size = dzwj_size;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getLrsj() {
		return lrsj;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	public String getMj() {
		return mj;
	}
	public void setMj(String mj) {
		this.mj = mj;
	}
	public String getCon_table() {
		return con_table;
	}
	public void setCon_table(String con_table) {
		this.con_table = con_table;
	}
	public String getCon_table_id() {
		return con_table_id;
	}
	public void setCon_table_id(String con_table_id) {
		this.con_table_id = con_table_id;
	}
}
