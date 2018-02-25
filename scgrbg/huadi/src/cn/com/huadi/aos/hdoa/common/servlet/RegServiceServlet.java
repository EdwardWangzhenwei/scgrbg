package cn.com.huadi.aos.hdoa.common.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RegServiceServlet extends HttpServlet {

	public static final Hashtable SYSCON = new Hashtable();

	public RegServiceServlet() {
	}

	@Override
	public void init() throws ServletException {
		try {

			regSystemParam();
			// regFunction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest httpservletrequest1,
			HttpServletResponse httpservletresponse1) throws IOException {
	}

	@Override
	public void doGet(HttpServletRequest httpservletrequest1,
			HttpServletResponse httpservletresponse1) throws IOException {
	}

	public void regSystemParam() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			URL url = this.getClass().getClassLoader()
					.getResource("SysParameter.xml");
			String path = url.getPath();
			File tempfile = new File(path);
			Document document = builder.parse(tempfile);
			Element element = document.getDocumentElement();
			NodeList nodelist = element.getElementsByTagName("Parameter");
			if (nodelist.getLength() == 0)
				return;
			NodeList subNodeList = null;
			Node cnode = null;
			Node chieldnode = null;
			String temp[] = new String[2];
			for (int i = 0; i < nodelist.getLength(); i++) {
				subNodeList = nodelist.item(i).getChildNodes();
				for (int j = 0; j < subNodeList.getLength(); j++) {
					chieldnode = subNodeList.item(j);
					if (chieldnode.getNodeName().equals("ParameterName")) {
						temp[0] = chieldnode.getChildNodes().item(0)
								.getNodeValue();
						continue;
					}
					if (chieldnode.getNodeName().equals("ParameterValue")) {
						temp[1] = chieldnode.getChildNodes().item(0)
								.getNodeValue();
					}

				}
				SYSCON.put(temp[0], temp[1]);
				temp = new String[2];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
