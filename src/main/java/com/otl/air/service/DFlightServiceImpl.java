package com.otl.air.service;

import com.otl.air.dto.DFlightDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor // 클래스의 모든 필드를 인자로 받는 생성자 자동 생성
@NoArgsConstructor // 인자가 없는 기본 생성자 자동 생성
@PropertySource("classpath:application-API-KEY.properties")
public class DFlightServiceImpl implements DFlightService {

    @Value("${service_key}")
    private String serviceKey;

    // 공항 코드와 공항 이름을 매핑하는 Map 초기화
    private static final Map<String, String> AIRPORT_MAP = new HashMap<>();
    static {
        AIRPORT_MAP.put("NAARKJJ", "광주");
        AIRPORT_MAP.put("NAARKJK", "군산");
        AIRPORT_MAP.put("NAARKSS", "김포");
        AIRPORT_MAP.put("NAARKPK", "김해/부산");
        AIRPORT_MAP.put("NAARKTN", "대구");
        AIRPORT_MAP.put("NAARKJB", "무안");
        AIRPORT_MAP.put("NAARKPS", "사천");
        AIRPORT_MAP.put("NAARKNY", "양양");
        AIRPORT_MAP.put("NAARKJY", "여수");
        AIRPORT_MAP.put("NAARKPU", "울산");
        AIRPORT_MAP.put("NAARKNW", "원주");
        AIRPORT_MAP.put("NAARKSI", "인천");
        AIRPORT_MAP.put("NAARKPC", "제주");
        AIRPORT_MAP.put("NAARKTU", "청주");
        AIRPORT_MAP.put("NAARKTH", "포항");
    }

    // 공항 코드 -> 공항 이름으로 반환
    @Override
    public String nameSet(String startPortName) {
        return AIRPORT_MAP.getOrDefault(startPortName, "error");
    }

    // 항공편 정보를 조회하여 DFlightDTO 객체 리스트로 반환하는 메소드
    @Override
    public ArrayList<DFlightDTO> airApi(String daID, String aaID, String dpTime, String airline, Integer pageNum) throws IOException, JSONException {

        ArrayList<DFlightDTO> list = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNum), "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/

        urlBuilder.append("&" + URLEncoder.encode("depAirportId","UTF-8") + "=" + URLEncoder.encode(daID, "UTF-8")); /*출발공항ID*/
        urlBuilder.append("&" + URLEncoder.encode("arrAirportId","UTF-8") + "=" + URLEncoder.encode(aaID, "UTF-8")); /*도착공항ID*/
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(dpTime, "UTF-8")); /*출발일(YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("airlineId","UTF-8") + "=" + URLEncoder.encode(airline, "UTF-8")); /*항공사ID*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json"); // 리소스의 미디어 타입: application/json
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd; // 응답을 읽기 위한 BufferedReader 객체 선언
        // 응답 코드에 따라 입력 스트림(전송 방식) 설정
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line; // 각 줄을 저장할 문자열 변수
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

//        System.out.println(sb.toString());

        //

        String jsonString = sb.toString();
        JSONObject jObject = new JSONObject(jsonString);

        // (response)
        JSONObject responseObject = jObject.getJSONObject("response");

        // (response -> header)
        JSONObject headerObject = responseObject.getJSONObject("header");
        String resultCode = headerObject.getString("resultCode"); // 결과 코드 추출
        String resultMsg = headerObject.getString("resultMsg"); // 결과 메시지 추출
        System.out.println("(header)resultCode: " + resultCode);
        System.out.println("(header)resultMsg: " + resultMsg);

        // (response -> body)
        JSONObject bodyObject = responseObject.getJSONObject("body");

        String numOfRows = Integer.toString(bodyObject.getInt("numOfRows"));
        String pageNo = Integer.toString(bodyObject.getInt("pageNo"));
        String totalCount = Integer.toString(bodyObject.getInt("totalCount"));

        System.out.println("(body)numOfRows: " + numOfRows + "  (body)pageNo: " + pageNo + "  (body)totalCount: " + totalCount);

        if(totalCount.equals("0")) {
            System.out.println("존재하지 않는 항공편. 항공편 개수: " + totalCount);
            return list;
        }

        // 항공편 정보를 포함한 item 배열을 추출
        JSONObject itemsObject = bodyObject.getJSONObject("items");

        // (response -> body -> items -> item(node 2개이상))
        JSONArray itemArray = itemsObject.getJSONArray("item");


        for (int i = 0; i < itemArray.length(); i++) {

            DFlightDTO svo = new DFlightDTO();
            JSONObject dobj = itemArray.getJSONObject(i);

            svo.setVihicleId(dobj.getString("vihicleId")); // 항공편명
            svo.setAirlineNm(dobj.optString("airlineNm", "airlineName is Null")); // 항공사명
            svo.setDepPlandTime(dobj.getLong("depPlandTime")); // 출발시간
            svo.setArrPlandTime(dobj.getLong("arrPlandTime")); // 도착시간
            svo.setEconomyCharge(dobj.optInt("economyCharge", 86000)); // 일반석운임 (기본값으로 86000원 설정)
            svo.setPrestigeCharge(dobj.optInt("prestigeCharge")); // 비즈니스석운임
            svo.setDepAirportNm(dobj.getString("depAirportNm")); // 출발공항
            svo.setArrAirportNm(dobj.getString("arrAirportNm")); // 도착공항
            svo.setPageNo(Integer.parseInt(pageNo));
            svo.setTotalCount(Integer.parseInt(totalCount));

            list.add(svo);
        }
        return list;
    }
}
