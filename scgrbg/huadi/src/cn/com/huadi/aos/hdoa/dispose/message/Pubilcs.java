/**
 * 
 */
package cn.com.huadi.aos.hdoa.dispose.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 公文
 * 
 * @author nmj
 * @E-mail bjahqj@163.com
 * @date 2016年4月26日上午9:31:34
 * @return
 * 
 */

@XStreamAlias("公文信息")
public class Pubilcs {

	public Pubilcs() {
	}
	@XStreamAlias("公文ID")
	private String wjbm;
	@XStreamAlias("公文标题")
	private String bt;
	@XStreamAlias("密级")
	private String mj;
	@XStreamAlias("紧急程度")
	private String jjcd;
	// 发文清单信息
	@XStreamAlias("发文清单ID")
	private String gwqd_id;
	@XStreamAlias("文件接收单位ID")
	private String jsdw_id;
	@XStreamAlias("接收单位名称")
	private String jsdw;
	@XStreamAlias("公文内容")
	private String gwnr;
	@XStreamAlias("发文机关")
	private String fwdw;

	public String getGwnr() {
		return gwnr;
	}
	public void setGwnr(String gwnr) {
		this.gwnr = gwnr;
	}
	public String getWjbm() {
		return wjbm;
	}
	public void setWjbm(String wjbm) {
		this.wjbm = wjbm;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getMj() {
		return mj;
	}
	public void setMj(String mj) {
		this.mj = mj;
	}
	public String getJjcd() {
		return jjcd;
	}
	public void setJjcd(String jjcd) {
		this.jjcd = jjcd;
	}
	public String getGwqd_id() {
		return gwqd_id;
	}
	public void setGwqd_id(String gwqd_id) {
		this.gwqd_id = gwqd_id;
	}
	public String getJsdw_id() {
		return jsdw_id;
	}
	public void setJsdw_id(String jsdw_id) {
		this.jsdw_id = jsdw_id;
	}
	public String getJsdw() {
		return jsdw;
	}
	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}
	public String getFwdw() {
		return fwdw;
	}
	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}
}
