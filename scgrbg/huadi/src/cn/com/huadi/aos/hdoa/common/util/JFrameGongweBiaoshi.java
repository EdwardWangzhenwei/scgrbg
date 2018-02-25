package cn.com.huadi.aos.hdoa.common.util;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class JFrameGongweBiaoshi extends JFrame {
	public static final String GONGWEN_TYPE= "1.2.156.10";

	private JPanel contentPane;
	private JTextField txtTypeOID;
	private JTextField txtOrganizationCode;
	private JTextField txtSubDeptCode;
	private JComboBox comboYear;
	private JComboBox comboDocumentType;
	private JTextField txtSerialNumber;
	private JTextField txtCheckCode;
	private JTextField txtDocumentID;
	private JTextField txtCheckResult;
	private JTextField txtSoftDescription;
	private JTextField txtCodeDescription;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameGongweBiaoshi frame = new JFrameGongweBiaoshi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameGongweBiaoshi() {
		setTitle("\u7535\u5B50\u516C\u6587\u6807\u8BC6\u751F\u6210&\u6821\u9A8C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTypeOID = new JLabel("类型标识.固定");
		lblTypeOID.setFont(new Font("宋体", Font.PLAIN, 12));
		lblTypeOID.setBounds(10, 54, 80, 24);
		contentPane.add(lblTypeOID);
		
		txtTypeOID = new JTextField();
		txtTypeOID.setEditable(false);
		txtTypeOID.setEnabled(false);
		txtTypeOID.setText(GONGWEN_TYPE);
		txtTypeOID.setBounds(100, 54, 140, 24);
		contentPane.add(txtTypeOID);
		txtTypeOID.setColumns(10);
		
		JLabel lblOrganizationCode = new JLabel("信用代码.18位");
		lblOrganizationCode.setFont(new Font("宋体", Font.PLAIN, 12));
		lblOrganizationCode.setBounds(10, 94, 80, 24);
		contentPane.add(lblOrganizationCode);
		
		txtOrganizationCode = new JTextField();
		txtOrganizationCode.setText("12100000400002195R");
		txtOrganizationCode.setColumns(10);
		txtOrganizationCode.setBounds(100, 94, 140, 24);
		contentPane.add(txtOrganizationCode);
		
		JLabel lblSubDeptCode = new JLabel("内设机构.03位");
		lblSubDeptCode.setFont(new Font("宋体", Font.PLAIN, 12));
		lblSubDeptCode.setBounds(10, 134, 80, 24);
		contentPane.add(lblSubDeptCode);
		
		txtSubDeptCode = new JTextField();
		txtSubDeptCode.setText("000");
		txtSubDeptCode.setColumns(10);
		txtSubDeptCode.setBounds(100, 134, 114, 24);
		contentPane.add(txtSubDeptCode);
		
		JLabel lblDocumentType = new JLabel("公文种类.02位");
		lblDocumentType.setFont(new Font("宋体", Font.PLAIN, 12));
		lblDocumentType.setBounds(224, 134, 87, 24);
		contentPane.add(lblDocumentType);
		
		comboDocumentType = new JComboBox();
		comboDocumentType.setFont(new Font("宋体", Font.PLAIN, 12));
		comboDocumentType.setEditable(true);
		comboDocumentType.setModel(new DefaultComboBoxModel(new String[] {"\u51B3\u8BAE", "\u51B3\u5B9A", "\u547D\u4EE4\uFF08\u4EE4\uFF09", "\u516C\u62A5", "\u516C\u544A", "\u901A\u544A", "\u610F\u89C1", "\u901A\u77E5", "\u901A\u62A5", "\u62A5\u544A", "\u8BF7\u793A", "\u6279\u590D", "\u8BAE\u6848", "\u51FD", "\u7EAA\u8981", "\u5176\u4ED6"}));
		comboDocumentType.setSelectedIndex(15);
		comboDocumentType.setBounds(311, 134, 113, 24);
		contentPane.add(comboDocumentType);
		
		JLabel lblYear = new JLabel("年份代码.04位");
		lblYear.setFont(new Font("宋体", Font.PLAIN, 12));
		lblYear.setBounds(10, 174, 80, 24);
		contentPane.add(lblYear);
		
		comboYear = new JComboBox();
		comboYear.setFont(new Font("宋体", Font.PLAIN, 12));
		comboYear.setEditable(true);
		comboYear.setModel(new DefaultComboBoxModel(new String[] {"2010", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"}));
		comboYear.setSelectedIndex(16);
		comboYear.setBounds(100, 174, 114, 24);
		contentPane.add(comboYear);
		
		JLabel lblSerialNumber = new JLabel("流水号  .05位");
		lblSerialNumber.setFont(new Font("宋体", Font.PLAIN, 12));
		lblSerialNumber.setBounds(224, 174, 87, 24);
		contentPane.add(lblSerialNumber);
		
		txtSerialNumber = new JTextField();
		txtSerialNumber.setText("00056");
		txtSerialNumber.setColumns(10);
		txtSerialNumber.setBounds(311, 174, 113, 24);
		contentPane.add(txtSerialNumber);
		
		txtCheckCode = new JTextField();
		txtCheckCode.setFont(new Font("宋体", Font.PLAIN, 12));
		txtCheckCode.setEditable(false);
		txtCheckCode.setText("X");
		txtCheckCode.setColumns(10);
		txtCheckCode.setBounds(100, 214, 114, 24);
		contentPane.add(txtCheckCode);
		
		JLabel lblCheckCode = new JLabel("校验码  .01位");
		lblCheckCode.setFont(new Font("宋体", Font.PLAIN, 12));
		lblCheckCode.setBounds(10, 214, 80, 24);
		contentPane.add(lblCheckCode);
		
		txtCheckResult = new JTextField("");
		txtCheckResult.setFont(new Font("宋体", Font.PLAIN, 12));
		txtCheckResult.setEditable(false);
		txtCheckResult.setForeground(Color.RED);
		txtCheckResult.setBounds(224, 214, 200, 24);
		contentPane.add(txtCheckResult);
		
		JButton btnGenerate = new JButton("\u751F\u6210");
		btnGenerate.setFont(new Font("宋体", Font.PLAIN, 12));
		btnGenerate.setToolTipText("根据上述各项输入值计算生成公文标识");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generate();
			}
		});
		btnGenerate.setBounds(108, 267, 93, 23);
		contentPane.add(btnGenerate);
		
		JButton btnVerificate = new JButton("\u6821\u9A8C");
		btnVerificate.setFont(new Font("宋体", Font.PLAIN, 12));
		btnVerificate.setToolTipText("根据公文标识输入值自动分析得到各组成部分内容并计算对比校验位");
		btnVerificate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificate();
			}
		});
		btnVerificate.setBounds(253, 267, 93, 23);
		contentPane.add(btnVerificate);
		
		JLabel lblDocumentID = new JLabel("公文标识.49位");
		lblDocumentID.setFont(new Font("宋体", Font.PLAIN, 12));
		lblDocumentID.setBounds(10, 300, 80, 24);
		contentPane.add(lblDocumentID);
		
		txtDocumentID = new JTextField();
		txtDocumentID.setText("1.2.156.10.12100000400002195R-C01-2004-0A-10001-J");
		txtDocumentID.setColumns(10);
		txtDocumentID.setBounds(100, 300, 324, 24);
		contentPane.add(txtDocumentID);
		
		txtSoftDescription = new JTextField("本程序演示标识校验码算法，适用于v1.8（9位代码）和v1.9（18位代码）");
		txtSoftDescription.setFont(new Font("宋体", Font.PLAIN, 12));
		txtSoftDescription.setForeground(Color.BLUE);
		txtSoftDescription.setEditable(false);
		txtSoftDescription.setBounds(10, 15, 414, 24);
		contentPane.add(txtSoftDescription);
		
		txtCodeDescription = new JTextField("");
		txtCodeDescription.setFont(new Font("宋体", Font.PLAIN, 12));
		txtCodeDescription.setForeground(Color.BLUE);
		txtCodeDescription.setEditable(false);
		txtCodeDescription.setBounds(250, 94, 174, 24);
		contentPane.add(txtCodeDescription);
	}

	protected void verificate() {
		String documentId = this.txtDocumentID.getText();
		if(documentId==null||documentId.trim().length()<=GONGWEN_TYPE.length()){
			JOptionPane.showMessageDialog(this, "输入的公文标识字符串长度应大于"+GONGWEN_TYPE.length());
			this.txtDocumentID.selectAll();
			return;
		}
		if(!documentId.startsWith(GONGWEN_TYPE)){
			JOptionPane.showMessageDialog(this, "输入的公文标识字符串应以"+GONGWEN_TYPE+"开头");
			this.txtDocumentID.selectAll();
			return;
		}
		
		String subDocumentId = documentId.substring(GONGWEN_TYPE.length()+1);
		String[] subs = subDocumentId.split("-");
		if(subs.length<6){
			JOptionPane.showMessageDialog(this, "输入的公文标识字符串包含5个'-'分隔符");
			this.txtDocumentID.selectAll();
			return;
		}
		
		String rawDocumentId = subDocumentId.replace(".", "").replace("-", "");
		if(!isAllNumberOrLetter(rawDocumentId)){
			JOptionPane.showMessageDialog(this, "输入的公文标识字符串除分隔外不应包含数字和字母以外的字符");
			this.txtDocumentID.selectAll();
			return;
		}
		this.txtOrganizationCode.setText(subs[0]);
		if(subs[0].length()==18){
			this.txtCodeDescription.setText("当前使用18位社会信用代码");
		}
		else if(subs[0].length()==9){
			this.txtCodeDescription.setText("当前使用9位组织机构代码");
		}
		else{
			this.txtCheckResult.setText("组织结构或社会信用代码输入有误！");
		}
		this.txtSubDeptCode.setText(subs[1]);
		if(subs[1].length()!=3){
			this.txtCheckResult.setText("内设机构代码输入有误,应为3位！");
		}
		this.comboYear.setSelectedItem(subs[2]);
		this.comboDocumentType.setSelectedItem(getDocumentTypeNameFromCode(subs[3]));
		this.txtSerialNumber.setText(subs[4]);
		if(subs[4].length()!=5){
			this.txtCheckResult.setText("文件流水号输入有误,应为5位！");
		}
		this.txtCheckCode.setText(subs[5]);
		String code = computeCheckCode(rawDocumentId);
		if(code.equals(txtCheckCode.getText())){
			this.txtCheckResult.setText("校验成功！");
		}
		else{
			this.txtCheckResult.setText("校验失败！预期为"+code);
		}
		
	}
	private static String computeCheckCode(String rawText){
		int M = 36;
		int r = 2;
		int n= rawText.length();
		int P = M;
		int S = 0;
		int j = 0;
		
		System.out.println("M = "+M+" , n = "+n);
		
		System.out.println("     P[j],   S[j]=P[j]+a[n-j+1],   P[j+1]=S[j]‖M×2,   P[j+1]a=P[j+1]‖(M+1)");
		
		while(j++<n-1){
			int pj=getIntegerFromString(rawText.toUpperCase(),j-1);
			S = P+pj;
			S = S%M;
			if(S==0)S=M;
			P = S*r;
			System.out.print("j="+j+"       P["+j+"]="+pj+",       S["+j+"]="+S+",         P["+(j+1)+"]="+P);
			P = P%(M+1);
			if(P==0)P=M;
			System.out.println(",        P["+(j+1)+"]a="+P);
		}
		int result = M+1-P;
		System.out.println("---------------- required a[1] = "+result);
		if(P==1){
			if(M==11)return "X";
			if(M==36)return "*";
		}
		
		if(result<10)return result+"";
			
		char c = (char)((result-10)+'A');
		return c+"";
	}
	private static int getIntegerFromString(String rawText,int index){
		char a = rawText.charAt(index);
		if(a>='0'&&a<='9')return Integer.valueOf(a+"");
		if(a>='A'&&a<='Z')return a-'A'+10;
		return -1;
	}
	private static  boolean isAllNumberOrLetter(String text){
		if(text==null||text.length()<1)return false;
		for(int i =0;i<text.length();i++){
			char c= text.charAt(i);
			if(c>='0'&&c<='9'||c>='a'&&c<='z'||c>='A'&&c<='Z'){
				
			}
			else
				return false;
		}
		return true;
	}
	
	private static String getDocumentTypeCodeFromName(String text) {
		if ("决议".equals(text))
			return "01";
		if ("决定".equals(text))
			return "02";
		if ("命令（令）".equals(text))
			return "03";
		if ("公报".equals(text))
			return "04";
		if ("公告".equals(text))
			return "05";
		if ("通告".equals(text))
			return "06";
		if ("意见".equals(text))
			return "07";
		if ("通知".equals(text))
			return "08";
		if ("通报".equals(text))
			return "09";
		if ("报告".equals(text))
			return "0A";
		if ("请示".equals(text))
			return "0B";
		if ("批复".equals(text))
			return "0C";
		if ("议案".equals(text))
			return "0D";
		if ("函".equals(text))
			return "0E";
		if ("纪要".equals(text))
			return "0F";
		return "0Z";
	}
	
	private static  String getDocumentTypeNameFromCode(String text){
		if ("01".equals(text)) return "决议";
		if ("02".equals(text)) return "决定";
		if ("03".equals(text)) return "命令（令）";
		if ("04".equals(text)) return "公报";
		if ("05".equals(text)) return "公告";
		if ("06".equals(text)) return "通告";
		if ("07".equals(text)) return "意见";
		if ("08".equals(text)) return "通知";
		if ("09".equals(text)) return "通报";
		if ("0A".equals(text)) return "报告";
		if ("0B".equals(text)) return "请示";
		if ("0C".equals(text)) return "批复";
		if ("0D".equals(text)) return "议案";
		if ("0E".equals(text)) return "函";
		if ("0F".equals(text)) return "纪要";
		return "其他";
	}


	protected void generate() {
		String OID  = this.txtTypeOID.getText();
		String OrganizationCode = this.txtOrganizationCode.getText().trim();
		String SubDeptCode  =  this.txtSubDeptCode.getText();
		String Year =  (String)this.comboYear.getSelectedItem();
		String DocumentType =  (String)this.comboDocumentType.getSelectedItem();
		DocumentType = getDocumentTypeCodeFromName(DocumentType);
		String SerialNumber =  this.txtSerialNumber.getText();
		String rawDocumentId = OrganizationCode+"-"+SubDeptCode+"-"+Year+"-"+DocumentType+"-"+SerialNumber+"-X";
		rawDocumentId = rawDocumentId.replace(".", "").replace("-", "");
		String code = computeCheckCode(rawDocumentId);
		this.txtCheckCode.setText(code); 
		this.txtDocumentID.setText(OID+"."+OrganizationCode+"-"+SubDeptCode+"-"+Year+"-"+DocumentType+"-"+SerialNumber+"-"+code);
		this.txtCheckResult.setText("生成成功！");
		if(OrganizationCode.length()==18){
			this.txtCodeDescription.setText("当前使用18位社会信用代码");
		}
		else if(OrganizationCode.length()==9){
			this.txtCodeDescription.setText("当前使用9位组织机构代码");
		}
		else{
			this.txtCheckResult.setText("组织结构或社会信用代码输入有误！");
		}
	}
}
