package cn.com.huadi.aos.hdoa.dispose.message;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("公文封装包")
public class MessageDto {
	public MessageDto(){
		
	}
	@XStreamImplicit(itemFieldName = "封装实体")
	private List<EntityDto> EntityList;
	public List<EntityDto> getEntityList() {
		return EntityList;
	}
	public void setEntityList(List<EntityDto> entityList) {
		EntityList = entityList;
	}

	
}
