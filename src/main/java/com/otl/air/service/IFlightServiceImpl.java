// File: src/main/java/com/otl/air/service/IFlightServiceImpl.java

package com.otl.air.service;

import com.otl.air.dto.IFlightDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

@Service
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:application-API-KEY.properties")
public class IFlightServiceImpl implements IFlightService {

    @Value("${service_key}")
    private String serviceKey;

    @Override
    public ArrayList<IFlightDTO> airApi(String daID, String aaID, String dpTime, String airCode, String airline, Integer pageNum) throws IOException, ParserConfigurationException, SAXException {
        ArrayList<IFlightDTO> list = new ArrayList<>();

        StringBuilder urlBuilder = new StringBuilder("http://openapi.airport.co.kr/service/rest/FlightStatusList/getFlightStatusList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("schStDate","UTF-8") + "=" + URLEncoder.encode(dpTime, "UTF-8")); // 날짜 파라미터 추가
        urlBuilder.append("&" + URLEncoder.encode("schStTime","UTF-8") + "=" + URLEncoder.encode("0000", "UTF-8")); // 시작 시간을 00:00으로 설정
        urlBuilder.append("&" + URLEncoder.encode("schEdTime","UTF-8") + "=" + URLEncoder.encode("2359", "UTF-8")); // 종료 시간을 23:59로 설정
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); // 페이지당 행 수
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNum.toString(), "UTF-8")); // 페이지 번호

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());



        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(sb.toString())));

        // response 요소
        Element responseElement = (Element) doc.getElementsByTagName("response").item(0);
        // header 요소
        Element headerElement = (Element) responseElement.getElementsByTagName("header").item(0);
        String resultCode = getElementValue(headerElement, "resultCode");
        String resultMsg = getElementValue(headerElement, "resultMsg");
        System.out.println("(header)resultCode: " + resultCode); // (header)resultCode: 00
        System.out.println("(header)resultMsg: " + resultMsg);   // (header)resultMsg: NORMAL SERVICE.

        // body 요소
        Element bodyElement = (Element) responseElement.getElementsByTagName("body").item(0);

        String numOfRows = getElementValue(bodyElement, "numOfRows");
        String pageNo = getElementValue(bodyElement, "pageNo");
        String totalCount = getElementValue(bodyElement, "totalCount");
        // (body)numOfRows: 10  (body)pageNo: 1  (body)totalCount: 1631
        System.out.println("(body)numOfRows: " + numOfRows + "  (body)pageNo: " + pageNo + "  (body)totalCount: " + totalCount);

        // totalCount가 0이면 항공편이 존재하지 않음
        if(totalCount.equals("0")) {
            System.out.println("존재하지 않는 항공편. 항공편 개수: " + totalCount);
            return list;
        }

        // items 요소
        Element itemsElement = (Element) bodyElement.getElementsByTagName("items").item(0);
        NodeList itemList = itemsElement.getElementsByTagName("item");

        for (int i = 0; i < itemList.getLength(); i++) {
            Element itemElement = (Element) itemList.item(i);
            IFlightDTO svo = new IFlightDTO();

            svo.setAirFln(getElementValue(itemElement, "airFln"));
            svo.setAirlineKorean(getElementValue(itemElement, "airlineKorean"));
            svo.setStd(Long.parseLong(getElementValue(itemElement, "std", "0")));
            svo.setSta(Long.parseLong(getElementValue(itemElement, "sta", "0")));
            svo.setEconomyCharge(Integer.parseInt(getElementValue(itemElement, "economyCharge", "160000"))); // 기본값을 0으로 변경
            svo.setPrestigeCharge(Integer.parseInt(getElementValue(itemElement, "prestigeCharge", "540000"))); // 기본값을 0으로 변경
            svo.setBoardingKor(getElementValue(itemElement, "boardingKor"));
            svo.setArrivedKor(getElementValue(itemElement, "arrivedKor"));
            svo.setPageNo(Integer.parseInt(pageNo));
            svo.setTotalCount(Integer.parseInt(totalCount));

            list.add(svo);
            System.out.println(svo);
        }
        return list;
    }

    private String getElementValue(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    private String getElementValue(Element parent, String elementName, String defaultValue) {
        String value = getElementValue(parent, elementName);
        return value.isEmpty() ? defaultValue : value;
    }
}