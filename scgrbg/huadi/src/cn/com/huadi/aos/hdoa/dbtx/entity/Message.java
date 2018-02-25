package cn.com.huadi.aos.hdoa.dbtx.entity;

import java.util.Date;

public class Message {


	private String  sender;
	private Integer sender_id;
	private String  send_unit;
	private Integer send_unit_id;
	
	private String  receiver;
	private Integer receiver_id;
	private String  receive_unit;
	private Integer receive_unit_id;
	
	private Date    send_time;
	
	private String  open_url;
	private String  open_type;
	private String  subsystem;
	private String  function;
	private String  title;
	private String  number_of_doc;
	private String  security;
	private String  message;
	private String  emergency_degree;
	private String  list_type;     // 个人消息  部门消息
	private String  state;         // 已读   未读
		
	private String  main_table;			  // 业务主表
	private String  primary_key_value;    // 业务主键值
	
	
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Integer getSender_id() {
		return sender_id;
	}
	public void setSender_id(Integer sender_id) {
		this.sender_id = sender_id;
	}
	public String getSend_unit() {
		return send_unit;
	}
	public void setSend_unit(String send_unit) {
		this.send_unit = send_unit;
	}
	public Integer getSend_unit_id() {
		return send_unit_id;
	}
	public void setSend_unit_id(Integer send_unit_id) {
		this.send_unit_id = send_unit_id;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Integer getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(Integer receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getReceive_unit() {
		return receive_unit;
	}
	public void setReceive_unit(String receive_unit) {
		this.receive_unit = receive_unit;
	}
	public Integer getReceive_unit_id() {
		return receive_unit_id;
	}
	public void setReceive_unit_id(Integer receive_unit_id) {
		this.receive_unit_id = receive_unit_id;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public String getOpen_url() {
		return open_url;
	}
	public void setOpen_url(String open_url) {
		this.open_url = open_url;
	}
	public String getOpen_type() {
		return open_type;
	}
	public void setOpen_type(String open_type) {
		this.open_type = open_type;
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNumber_of_doc() {
		return number_of_doc;
	}
	public void setNumber_of_doc(String number_of_doc) {
		this.number_of_doc = number_of_doc;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmergency_degree() {
		return emergency_degree;
	}
	public void setEmergency_degree(String emergency_degree) {
		this.emergency_degree = emergency_degree;
	}
	public String getList_type() {
		return list_type;
	}
	public void setList_type(String list_type) {
		this.list_type = list_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMain_table() {
		return main_table;
	}
	public void setMain_table(String main_table) {
		this.main_table = main_table;
	}
	public String getPrimary_key_value() {
		return primary_key_value;
	}
	public void setPrimary_key_value(String primary_key_value) {
		this.primary_key_value = primary_key_value;
	}
	
	
	
}
