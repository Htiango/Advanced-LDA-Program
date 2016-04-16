package preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLWriter {
	
	public void writeXml(String filePath,	Map<Integer, Double> annotationMapTest)
	{
		Element root = new Element("测试集信息");
		root.setAttribute("userAccuracy", annotationMapTest.get(0) + "");
		Document doc = new Document(root);
		int id = 1;
		String childNode = "问答信息";
		
		for(Map.Entry<Integer, Double> entry : annotationMapTest.entrySet()){
			if(entry.getKey() == 0){
				continue;
			}
			Element quesAnsInfo = new Element(childNode);
			quesAnsInfo.setAttribute("id", id+"");
			root.addContent(quesAnsInfo);
			Element element = new Element("推荐答案是否相关");
			if(entry.getValue() == 1.0){
				element.setText("True");
			}
			else{
				element.setText("False");
			}
			quesAnsInfo.addContent(element);		
			id += 1;
		}
		
		Format format=Format.getCompactFormat();
		
        //设置换行
        format.setIndent("");
        //设置编码
        format.setEncoding("UTF-8");
        //4.创建XMLoutputter对象
        XMLOutputter outputter=new XMLOutputter(format);
        //5.利用outputter对象将document转换为xml文档
        try {
            outputter.output(doc, new FileOutputStream(new File(filePath +"/annotated file.xml")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}
	
}

