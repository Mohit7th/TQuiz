/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFile {

    private final DocumentBuilderFactory factory;
    private final DocumentBuilder builder;
    private final Document doc;

   public ReadXMLFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        //Setting all the objects that are necessary to read XML file
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.parse(new File(fileName));
    }

    //To get all the elements of a parent based on child node's tag name
    private NodeList getElementsList(Element parent, String tagName) {        
        NodeList list = parent.getElementsByTagName(tagName);
        return list;
    }

    private String[] createOptions(NodeList options) {
        String[] ops = new String[4];
        for (int i = 0; i < options.getLength(); i++) {
            ops[i] = ((Element) options.item(i)).getTextContent();
        }
        return ops;
    }

    public Question createQuestion(Node question) {
        Question que = new Question();
        Element questionEle = (Element) question;

        //setting options
        Element options = (Element) questionEle.getElementsByTagName("Options").item(0); //to get the <Options> tag
        String[] ops = createOptions(getElementsList(options, "Option"));
        que.setOptions(ops);

        //setting other values
        que.setQuestion(questionEle.getElementsByTagName("Qes").item(0).getTextContent());
        que.setDescription(questionEle.getElementsByTagName("Description").item(0).getTextContent());
        que.setAns(options.getElementsByTagName("Answer").item(0).getTextContent());
        return que;
    }

    private Element findRoot(String root) {
        //To find the root element based on given tag name
        return ((Element) doc.getElementsByTagName(root).item(0));
    }
    
    //Create a list of all questions exist in XML file
    public ArrayList<Question> getQuestionList() {
        Element root = findRoot("Test");
        NodeList questions = getElementsList(root, "Question");

        //creating array list for all question exist in file
        ArrayList<Question> questionList = new ArrayList<>();

        for (int i = 0; i < questions.getLength(); i++) {
            //filling the list with questions
            questionList.add(createQuestion(questions.item(i)));
        }
        return questionList;
    }

}
