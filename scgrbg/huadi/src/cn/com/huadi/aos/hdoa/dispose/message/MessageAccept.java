package cn.com.huadi.aos.hdoa.dispose.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 收文机关
 * 
 * @author nmj
 * @E-mail bjahqj@163.com
 * @date 2016年4月26日上午8:53:15
 * @return
 * 
 */

@XStreamAlias("收文机关")
public class MessageAccept {

	public MessageAccept() {
	}

	@XStreamAlias("身份标识")
	private String acceptcode;
	@XStreamAlias("身份名称")
	private String acceptname;
	@XStreamAlias("身份描述")
	private String acceptremark;

	/**
	 * @return the acceptcode
	 */
	public String getAcceptcode() {
		return acceptcode;
	}

	/**
	 * @param acceptcode
	 *            the acceptcode to set
	 */
	public void setAcceptcode(String acceptcode) {
		this.acceptcode = acceptcode;
	}

	/**
	 * @return the acceptname
	 */
	public String getAcceptname() {
		return acceptname;
	}

	/**
	 * @param acceptname
	 *            the acceptname to set
	 */
	public void setAcceptname(String acceptname) {
		this.acceptname = acceptname;
	}

	/**
	 * @return the acceptremark
	 */
	public String getAcceptremark() {
		return acceptremark;
	}

	/**
	 * @param acceptremark
	 *            the acceptremark to set
	 */
	public void setAcceptremark(String acceptremark) {
		this.acceptremark = acceptremark;
	}

}
