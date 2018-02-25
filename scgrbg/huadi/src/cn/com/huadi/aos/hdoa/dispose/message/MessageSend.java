package cn.com.huadi.aos.hdoa.dispose.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 发文机关
 * 
 * @author nmj
 * @E-mail bjahqj@163.com
 * @date 2016年4月26日上午9:01:56
 * @return
 * 
 */

@XStreamAlias("发文机关")
public class MessageSend {
	public MessageSend() {
	}

	@XStreamAlias("身份标识")
	private String sendcode;
	@XStreamAlias("身份名称")
	private String sendname;
	@XStreamAlias("身份描述")
	private String sendremark;
	/**
	 * @return the sendcode
	 */
	public String getSendcode() {
		return sendcode;
	}
	/**
	 * @param sendcode the sendcode to set
	 */
	public void setSendcode(String sendcode) {
		this.sendcode = sendcode;
	}
	/**
	 * @return the sendname
	 */
	public String getSendname() {
		return sendname;
	}
	/**
	 * @param sendname the sendname to set
	 */
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	/**
	 * @return the sendremark
	 */
	public String getSendremark() {
		return sendremark;
	}
	/**
	 * @param sendremark the sendremark to set
	 */
	public void setSendremark(String sendremark) {
		this.sendremark = sendremark;
	}

}
