package xml;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateXml_demo1 {

	public static void main(String[] args) throws Exception {

		// 入力ファイル
//		System.out.println("入力ファイルパスを入力してください。");
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String inputXml = br.readLine();
		String inputXml = "C:\\tmp\\xml\\sample.xml";
		
		// 出力ファイル
//		System.out.println("出力ファイルパスを入力してください。");
//		String outputXml = br.readLine();
		String outputXml = "C:\\tmp\\xml\\out.xml";

		// XMLファイル読み込み
		System.out.println("開始");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(Paths.get(inputXml).toFile());

		// rootノード取得
		Element root = document.getDocumentElement();
		System.out.println("ノード名：" + root.getNodeName());

		// recordノード取得
		NodeList recordList = root.getElementsByTagName("record");
		int cnt = 0;

		for (int i = 0; i < recordList.getLength(); ++i) {
			Node record = recordList.item(i);
			System.out.println("ノード名：" + record.getNodeName());
			System.out.println("ノードタイプ：" + record.getNodeType());
			if (record.getNodeType() == Node.ELEMENT_NODE) {

				NamedNodeMap attr = record.getAttributes();
				Node nodeAttr = attr.getNamedItem("id");
				System.out.println("ノード名：" + nodeAttr.getNodeName());

				if ("record".equals(record.getNodeName())) {
					nodeAttr.setTextContent(Integer.toString(++cnt));
				}
			}
		}
	
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(outputXml));
		transformer.transform(source, result);
		System.out.println("終了");

	}
}
