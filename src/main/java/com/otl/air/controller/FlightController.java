package com.otl.air.controller;

import com.otl.air.dto.DFlightDTO;
import com.otl.air.service.DFlightService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("fl")
@AllArgsConstructor
public class FlightController {

    private final DFlightService dService;

    // 항공편 조회 페이지
    @GetMapping("/flight")
    public String flight() throws Exception {
        return "pages/air/searchFlight";
    }

    // 항공편 조회 결과 페이지
    @GetMapping("/flightPage")
    public String flightList() throws Exception {
        return "pages/air/searchFlightPage";
    }

    // 항공 스케줄 조회
    @RequestMapping(value = "/flightList", method=RequestMethod.POST)
    public String searchFlight(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ParseException, JSONException {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // GET 요청일 경우, 단순히 검색 페이지를 반환
            return "pages/air/searchFlight";
        }

//        String startPortName = request.getParameter("from_place");
//        String endPortName = request.getParameter("to_place");
        String startPortName = request.getParameter("startPortName");
        String endPortName = request.getParameter("endPortName");
        String startDate = request.getParameter("date_start");
//        String airline = request.getParameter("airline");
        String airline = "";
        Integer pageNum = 1;

        // 기본 출발 날짜 설정 (현재 날짜 + 3일)
        if (isNullOrEmpty(startDate)) {
            startDate = getDefaultStartDate();
            System.out.println("============ startDate: " + startDate + " ===========");
        }

        // 출발날짜 미지정 조회시
        if ("".equals(request.getParameter("date_start")) || (request.getParameter("date_start")) == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('출발 날짜를 선택해주세요.'); </script>");
            out.flush();
            System.out.println("=========== 출발 날짜를 선택해주세요. =============");
            return "pages/air/searchFlight";
        }

        startDate = formatDate(startDate);

        // 항공사 미지정 조회 시, 전체조회 처리됨 (항공사: 선택옵션 / null값)
//        airline = getAirline(request.getParameter("airline"));
        if ("".equals(request.getParameter("airline")) || "N".equals(request.getParameter("airline")) || (request.getParameter("airline")) == null) {
            airline = "";
        } else {
            airline = request.getParameter("airline");
        }


        // === LOG ===
        System.out.println("filght schedule search >>>> startPortName: " + startPortName + " // endPortName: " + endPortName
            + " // startDate: " + startDate + " // airline: " + airline + " // pageNum: " + pageNum);

        ArrayList<DFlightDTO> flist = null;
        try {
            flist = dService.airApi(startPortName, endPortName, startDate, airline, pageNum);
        } catch (JSONException e) {
            System.err.println("JSON 파싱 실패.");
            e.printStackTrace();
            sendAlert(response, "항공편 데이터를 처리하는 데 실패했습니다.");
            return "pages/air/searchFlight";
        }

        if (flist.isEmpty()) { // api 조회 결과가 없을 때 = 항공편 미존재
            sendAlert(response, "해당 항공편은 존재하지 않습니다.");
            return "pages/air/searchFlight";
        }

        DFlightDTO fhlist = new DFlightDTO(startPortName, endPortName, startDate, airline, pageNum,
            flist.get(0).getTotalCount(), dService.nameSet(endPortName));

        // flist : api요청 결과 / fhlist : api요청 정보
        model.addAttribute("flist", flist);
        model.addAttribute("fhlist", fhlist);

        return "pages/air/searchFlightPage"; // 검색 결과 페이지
    }

    // 메서드
    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void sendAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); </script>");
        out.flush();
    }

//    private String getAirline(String airlineParam) {
//        if (isNullOrEmpty(airlineParam) || "N".equals(airlineParam)) {
//            return "";
//        }
//        return airlineParam;
//    }

    private String formatDate(String date) throws ParseException {
        SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat after = new SimpleDateFormat("yyyyMMdd");
        Date temp = before.parse(date);
        return after.format(temp);
    }

    // 기본 출발 날짜 계산 메서드
    private String getDefaultStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3); // 현재 날짜에 3일 추가
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷
        return sdf.format(calendar.getTime());
    }

}
