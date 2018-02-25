package cn.com.huadi.aos.hdoa.dispose.message;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("封装实体")
public class EntityDto{
	public EntityDto(){
		
	}
	@XStreamAlias("发文机关")
	private MessageSend MessageSend;
	//@XStreamImplicit(itemFieldName = "收文机关")
	//private List<MessageAccept> messageAcceptlist;
	//现采用“,”分割的方式，故不用List类型	
	@XStreamAlias("收文机关")
	private MessageAccept messageAccept;
	@XStreamAlias("公文信息")
	private Publics Publics;
	@XStreamAlias("附件集")
	private Accessories Accessories;
	@XStreamAlias("备注")
	private String bz;
	
	
	
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public MessageSend getMessageSend() {
		return MessageSend;
	}
	public void setMessageSend(MessageSend messageSend) {
		MessageSend = messageSend;
	}
	/*public List<MessageAccept> getMessageAcceptlist() {
		return messageAcceptlist;
	}
	public void setMessageAcceptlist(List<MessageAccept> messageAcceptlist) {
		this.messageAcceptlist = messageAcceptlist;
	}*/
	public Publics getPublics() {
		return Publics;
	}
	public MessageAccept getMessageAccept() {
		return messageAccept;
	}
	public void setMessageAccept(MessageAccept messageAccept) {
		this.messageAccept = messageAccept;
	}
	public void setPublics(Publics publics) {
		Publics = publics;
	}
	public Accessories getAccessories() {
		return Accessories;
	}
	public void setAccessories(Accessories accessories) {
		Accessories = accessories;
	}
	
	
	
}
