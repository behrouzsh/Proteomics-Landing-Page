package eh3.uc.edu.utils;

//import org.json.JSONException;
//import org.json.JSONObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by chojnasm on 11/17/15.
 * Edited by Behrouzsh on 9/6/16.
 */
public class UtilsNetwork {

    private static final Logger log = LoggerFactory.getLogger(UtilsNetwork.class);
    private static UtilsNetwork instance;

    private UtilsNetwork() {
    }

    static {
        instance = new UtilsNetwork();
    }

    public static UtilsNetwork getInstance() {
System.out.println(instance);
        return instance;
    }

    public String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString().replace('\'', '\"');
        } finally {
            if (reader != null)
                reader.close();
        }
    }

//    public JSONObject loadJSONFromJSON(String urlString) throws Exception {
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(urlString);
//            reader = new BufferedReader(new InputStreamReader(url.openStream()));
//            StringBuffer buffer = new StringBuffer();
//            int read;
//            char[] chars = new char[1024];
//            while ((read = reader.read(chars)) != -1)
//                buffer.append(chars, 0, read);
//            return new JSONObject(buffer);
//        } finally {
//            if (reader != null)
//                reader.close();
//        }
//    }

    public String readUrlXml(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            System.out.println(buffer.toString());
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }


    public static Map loadJSONFromStringHarmonizomeGene(String json) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(json));
        Document doc = builder.parse(is);
        Map<String, Object> proteinToUniprotMap = new HashMap<String, Object>();
        //Map proteinToUniprotMap = new HashMap();

        // get the first element
        doc.getDocumentElement().normalize();
        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        //Element root = doc.getDocumentElement();
        NodeList uniprotNodeList = doc.getElementsByTagName("symbol");
        Node entryNode = uniprotNodeList.item(0);
        NodeList entryNodeList = entryNode.getChildNodes();
        int found = 0;
        if (entryNodeList != null && entryNodeList.getLength() > 0) {
            for (int i = 0; i < entryNodeList.getLength(); i++) {
                Node entryNodeListNode = entryNodeList.item(i);

                if (entryNodeListNode.getNodeName() == "gene") {

                    //if (found == 0) {
                    NodeList geneNodeList = entryNodeListNode.getChildNodes();
                    proteinToUniprotMap.put("gene_id", geneNodeList.item(1).getTextContent());
                    //   found = 1;
//                    log.info("========================");
//                    log.info(geneNodeList.item(1).getTextContent());
//                    log.info("++++++++++++++++++++");
                    // }

                }


                if (entryNodeListNode.getNodeName() == "sequence") {
                    Element sequenceElement = (Element) entryNodeListNode;

                    proteinToUniprotMap.put("length", sequenceElement.getAttribute("length"));
                    log.info(entryNodeList.item(i).getTextContent());
                    proteinToUniprotMap.put("sequence", entryNodeList.item(i).getTextContent().replace("\n", ""));
                }


            }
        }


        return proteinToUniprotMap;

    }


//    public static Map loadJSONFromString(String json) throws Exception
//    {
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        InputSource is = new InputSource(new StringReader(json));
//        Document doc = builder.parse(is);
//        Map<String, Object> proteinToUniprotMap = new HashMap<String, Object>();
//        //Map proteinToUniprotMap = new HashMap();
//
//        // get the first element
//        doc.getDocumentElement().normalize();
//        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//        //Element root = doc.getDocumentElement();
//        NodeList uniprotNodeList = doc.getElementsByTagName("entry");
//        Node entryNode = uniprotNodeList.item(0);
//        NodeList entryNodeList = entryNode.getChildNodes();
//        //int found = 0;
//        if (entryNodeList != null && entryNodeList.getLength() > 0) {
//            for (int i = 0; i < entryNodeList.getLength(); i++) {
//                Node entryNodeListNode = entryNodeList.item(i);
//
//                if(entryNodeListNode.getNodeName() == "gene")
//                {
//
//                    //if (found == 0) {
//                        NodeList geneNodeList = entryNodeListNode.getChildNodes();
//                        proteinToUniprotMap.put("gene_id", geneNodeList.item(1).getTextContent());
//                     //   found = 1;
////                    log.info("========================");
////                    log.info(geneNodeList.item(1).getTextContent());
////                    log.info("++++++++++++++++++++");
//                   // }
//
//                }
//
//
//                if(entryNodeListNode.getNodeName() == "sequence") {
//                    Element sequenceElement = (Element)entryNodeListNode;
//
//                    proteinToUniprotMap.put("length", sequenceElement.getAttribute("length"));
//                    log.info(entryNodeList.item(i).getTextContent());
//                    proteinToUniprotMap.put("sequence", entryNodeList.item(i).getTextContent().replace("\n", ""));
//                }
//
//
//
//            }
//        }
//
//
//        return proteinToUniprotMap;
//
//    }


    public static Map loadXMLFromString(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document doc = builder.parse(is);
        Map<String, Object> proteinToUniprotMap = new HashMap<String, Object>();
        ArrayList proteinToGeneList = new ArrayList();
        //Map proteinToUniprotMap = new HashMap();

        // get the first element
        doc.getDocumentElement().normalize();
        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        //Element root = doc.getDocumentElement();
        NodeList uniprotNodeList = doc.getElementsByTagName("entry");
        Node entryNode = uniprotNodeList.item(0);
        NodeList entryNodeList = entryNode.getChildNodes();
        int found = 0;
        if (entryNodeList != null && entryNodeList.getLength() > 0) {
            for (int i = 0; i < entryNodeList.getLength(); i++) {
                Node entryNodeListNode = entryNodeList.item(i);

                if (entryNodeListNode.getNodeName() == "gene") {

                    if (found == 0) {
                        NodeList geneNodeList = entryNodeListNode.getChildNodes();
                        proteinToGeneList.add(geneNodeList.item(1).getTextContent());
                        // found = 1;
                        System.out.println("========================");
                        System.out.println(geneNodeList.item(1).getTextContent());
                        System.out.println("++++++++++++++++++++");
                    }

                }


                if (entryNodeListNode.getNodeName() == "sequence") {
                    Element sequenceElement = (Element) entryNodeListNode;

                    proteinToUniprotMap.put("length", sequenceElement.getAttribute("length"));
                    log.info(entryNodeList.item(i).getTextContent());
                    proteinToUniprotMap.put("sequence", entryNodeList.item(i).getTextContent().replace("\n", ""));

                }


            }
            proteinToUniprotMap.put("gene_id", proteinToGeneList);
        }


        return proteinToUniprotMap;

    }


    public static JSONObject loadXMLFromStringPIR(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document doc = builder.parse(is);
        Map<String, Object> peptideToProteinMap = new HashMap<String, Object>();
        JSONObject peptideToProteinJson = new JSONObject();
        JSONArray matchList = new JSONArray();
        JSONObject match = new JSONObject();
        String sequence_ac;
        String sequence_id;
        //int start;
        int stop;
        String length;
        String numberOfMatchedProteinsInt;
        String start;
        String end;
        //Map proteinToUniprotMap = new HashMap();

        // get the first element
        doc.getDocumentElement().normalize();
        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        //Element root = doc.getDocumentElement();
        // <numberOfMatchedProteins>17</numberOfMatchedProteins>
        //Node numberOfMatchedProteins = doc.getElementsByTagName("numberOfMatchedProteins");
        //log.info("here--1");

        //Node numberOfMatchedProteins = (Node) doc.getElementsByTagName("numberOfMatchedProteins");
//        if (numberOfMatchedProteins) {
//        } else {
//            numberOfMatchedProteinsInt = numberOfMatchedProteins.getTagName();
//        }


        //umberOfMatchedProteins = numberOfMatchedProteins.item(0).getTextContent();
        //log.info("here0");
        NodeList matchPerPeptide = doc.getElementsByTagName("matchPerPeptide");
//        numberOfMatchedProteinsInt = (String) ((Element) bookslist.item(0)).getElementsByTagName("servername").
//                item(0).getChildNodes().item(0).getNodeValue();
        Node matchPerPeptideNode = matchPerPeptide.item(0);
        NodeList perPeptideNodeList = matchPerPeptideNode.getChildNodes();
        numberOfMatchedProteinsInt = "";
        //int found = 0;
        //log.info("here1");
        if (perPeptideNodeList != null && perPeptideNodeList.getLength() > 0) {
            //match = null;

            for (int i = 0; i < perPeptideNodeList.getLength(); i++) {
                //log.info("here2");

                Node perPeptideNodeListNode = perPeptideNodeList.item(i);

                if (perPeptideNodeListNode.getNodeName() == "numberOfMatchedProteins") {
                    numberOfMatchedProteinsInt = perPeptideNodeListNode.getTextContent();
                    log.info(numberOfMatchedProteinsInt);
                }

                if (perPeptideNodeListNode.getNodeName() == "matchedProtein") {
                    NodeList matchedProteinNodeList = perPeptideNodeListNode.getChildNodes();
                    //log.info("here3");
                    start = "";
                    end = "";
                    length = "";
                    sequence_ac = "";
                    sequence_id = "";
                    for (int j = 0; j < matchedProteinNodeList.getLength(); j++) {
                        //log.info("here4");
                        Node matchedProteinNode = matchedProteinNodeList.item(j);
                        if (matchedProteinNode.getNodeName() == "proteinAC") {

                            sequence_ac = matchedProteinNodeList.item(j).getTextContent();

                            log.info(sequence_ac);



                        }

                        if (matchedProteinNode.getNodeName() == "proteinID") {

                            sequence_id = matchedProteinNodeList.item(j).getTextContent();


                            log.info(sequence_id);
                        }

                        if (matchedProteinNode.getNodeName() == "seqLength") {

                            length = matchedProteinNodeList.item(j).getTextContent();

                            log.info("length ------- ");
                            log.info(length);



                        }
                        if (matchedProteinNode.getNodeName() == "matchRanges") {
                            log.info("here5");
                            NodeList matchRangesNodeList = matchedProteinNode.getChildNodes();

                            //log.info("here6");
                            for (int k = 0; k < matchRangesNodeList.getLength(); k++) {
                                Node matchRangeNode = matchRangesNodeList.item(k);
                                //match = null;
                                match = new JSONObject();
                                log.info("++++");
                                log.info(matchRangeNode.getNodeName());
                                log.info("----");
                                if (matchRangeNode.getNodeName() == "matchRange") {

//                                    Element sequenceElement = (Element) entryNodeListNode;
//
//                                    proteinToUniprotMap.put("length", sequenceElement.getAttribute("length"));
//                                    //log.info(entryNodeList.item(i).getTextContent());
//                                    proteinToUniprotMap.put("sequence", entryNodeList.item(i).getTextContent().replace("\n", ""));




                                    log.info("here8");
                                    Element matchRangeElement = (Element) matchRangeNode;
                                    start = matchRangeElement.getAttribute("start");
                                    end = matchRangeElement.getAttribute("end");

                                    match.put("start", matchRangeElement.getAttribute("start"));
                                    match.put("stop", matchRangeElement.getAttribute("end"));
                                    match.put("length", length);
                                    match.put("sequence_ac", sequence_ac);
                                    match.put("sequence_id", sequence_id);
                                    log.info("----------------");
                                    log.info(start);
                                    log.info(end);
                                    log.info(length);
                                    log.info(sequence_ac);
                                    log.info(sequence_id);
                                    log.info(match.toJSONString());
                                    matchList.add(match);
                                    log.info(match.toJSONString());
//                                    proteinToUniprotMap.put("sequence", entryNodeList.item(i).getTextContent().replace("\n", ""));
//
//                                    sequence_ac = matchedProteinNodeList.item(j).getTextContent();
//                                    match.put("sequence_ac", sequence_ac);

                                }

//                            NodeList geneNodeList = entryNodeListNode.getChildNodes();
//                            proteinToGeneList.add(geneNodeList.item(1).getTextContent());


                            }
                        }
                        ///


                    }


                }

            }
            peptideToProteinMap.put("matchset", matchList.toJSONString());
            peptideToProteinMap.put("n_match", numberOfMatchedProteinsInt);
            peptideToProteinJson.put("matchset", matchList.toJSONString());
            peptideToProteinJson.put("n_match", numberOfMatchedProteinsInt);
        }

        return peptideToProteinJson;

    }

//    public static Map<String, Object> parse(JSONObject json , Map<String,Object> out) throws JSONException{
//        Iterator<String> keys = json.keys();
//        while(keys.hasNext()){
//            String key = keys.next();
//            String val = null;
//            try{
//                JSONObject value = json.getJSONObject(key);
//                parse(value,out);
//            }catch(Exception e){
//                val = json.getString(key);
//            }
//
//            if(val != null){
//                out.put(key,val);
//            }
//        }
//        return out;
//    }


}
