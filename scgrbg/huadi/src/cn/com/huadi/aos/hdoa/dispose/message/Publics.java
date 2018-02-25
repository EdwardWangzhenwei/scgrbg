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
public class Publics {

	public Publics() {
	}

	@XStreamAlias("密级")
	private String mj;
	@XStreamAlias("紧急程度")
	private String jjcd;
	@XStreamAlias("字号")
	private String zh;
	@XStreamAlias("年号")
	private String nh;
	@XStreamAlias("序号")
	private String xh;
	@XStreamAlias("后缀")
	private String hz;
	@XStreamAlias("发文机关")
	private String ngdw;
	@XStreamAlias("收文机关")
	private String zs;
	@XStreamAlias("抄送部门")
	private String cs;
	@XStreamAlias("公文标题")
	private String bt;
	@XStreamAlias("公文内容")
	private String gwnr;

	// 暂定在交换系统操作转处理的人员信息，就是转到处理系统里面承办人的信息，即GZL_NODE.N_EXECUTOR,GZL_NODE.N_EXECUTORNAME
	@XStreamAlias("操作人姓名")
	private String czrxm;
	@XStreamAlias("操作人id")
	private String czrid;
	@XStreamAlias("成文日期")
	private String cwrq;
	@XStreamAlias("签发人")
	private String qfr;
	@XStreamAlias("签发日期")
	private String qfrq;
	@XStreamAlias("经办人")
	private String jbr;

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

	public String getZh() {
		return zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

	public String getNh() {
		return nh;
	}

	public void setNh(String nh) {
		this.nh = nh;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public String getNgdw() {
		return ngdw;
	}

	public void setNgdw(String ngdw) {
		this.ngdw = ngdw;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}

	public String getGwnr() {
		return gwnr;
	}

	public void setGwnr(String gwnr) {
		this.gwnr = gwnr;
	}

	public String getCzrxm() {
		return czrxm;
	}

	public void setCzrxm(String czrxm) {
		this.czrxm = czrxm;
	}

	public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid;
	}

	public String getCwrq() {
		return cwrq;
	}

	public void setCwrq(String cwrq) {
		this.cwrq = cwrq;
	}

	public String getQfr() {
		return qfr;
	}

	public void setQfr(String qfr) {
		this.qfr = qfr;
	}

	public String getQfrq() {
		return qfrq;
	}

	public void setQfrq(String qfrq) {
		this.qfrq = qfrq;
	}

	public String getJbr() {
		return jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}
}
