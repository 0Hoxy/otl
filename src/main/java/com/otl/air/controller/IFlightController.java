package com.otl.air.controller;

import com.otl.air.dto.IFlightDTO;
import com.otl.air.service.IFlightService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("fli")
@AllArgsConstructor
public class IFlightController {

    private final IFlightService iService;

    // 항공편 조회 페이지
    @GetMapping("/flight")
    public String flight() throws Exception {
        return "pages/air/searchFlightI";
    }

    // 항공편 조회 결과 페이지
    @GetMapping("/flightPage")
    public String flightList() throws Exception {
        return "pages/air/searchFlightPageI";
    }

    // 디버깅 강화를 위해 프린트 함수가 중간중간 존재합니다. !

    @GetMapping("/flightList")
    public String searchFlight(@RequestParam String daID,
                               @RequestParam String aaID,
                               @RequestParam String dpTime,
                               @RequestParam(required = false) String airline,
                               @RequestParam(required = true, defaultValue = "1") int pageNum,
                               Model model) {
        // 메서드 구현
        return "pages/air/searchFlightPageI";
    }


    // 항공 스케줄 조회
    @RequestMapping(value = "/flightList", method=RequestMethod.POST)
    public String searchFlight(HttpServletRequest request, HttpServletResponse response, Model model,
                               @RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(defaultValue = "15") int pageSize) throws IOException, ParseException, JSONException {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // GET 요청일 경우, 단순히 검색 페이지를 반환
            return "pages/air/searchFlightI";
        }

        // HTTP 요청에서 각종 파라미터들을 추출
        String daID = request.getParameter("daID");
        String aaID = request.getParameter("aaID");
        String dpTime = request.getParameter("dpTime");
        String airCode = request.getParameter("airCode");
        String airline = request.getParameter("airline"); //
        String pageNo = request.getParameter("pageNum");

        System.out.println();

        // 기본 출발 날짜 설정 (현재 날짜 + 3일)
        if (isNullOrEmpty(dpTime)) {
            dpTime = getDefaultStartDate();
            System.out.println("============ startDate: " + dpTime + " ===========");
        }

        // 출발날짜 미지정 조회시
        if ("".equals(request.getParameter("date_start")) || (request.getParameter("date_start")) == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('출발 날짜를 선택해주세요.'); </script>");
            out.flush();
            System.out.println("--- 출발 날짜를 선택해주세요. ---");
            return "pages/air/searchFlightI";
        }

        dpTime = formatDate(dpTime); // dpTime을 지정된 형식(yyyyMMdd)으로 변환

        // 항공사 미지정 조회 시, 전체조회 처리됨 (항공사: 선택옵션 / null값)
        if ("".equals(request.getParameter("airline")) || "N".equals(request.getParameter("airline")) || (request.getParameter("airline")) == null) {
            airline = "";
            System.out.println("항공사 미지정");
        } else {
            airline = request.getParameter("airline");
        }

        System.out.println("@@filght schedule search >>>> startPortName: " + daID + " // endPortName: " + aaID +
            " // time: " + dpTime + " // airline: " + airline + " // pageNum: " + pageNo); // 로그용

        // iService.airApi 메소드를 호출하여 항공편 정보를 조회
        ArrayList<IFlightDTO> flist = null;

        try {
            flist = iService.airApi(daID, aaID, dpTime, airCode, airline, pageNum);
            System.out.println("################ flist: " + flist + " ################");
        } catch (JSONException e) {
            e.printStackTrace();
            sendAlert(response, "항공편 데이터를 처리하는 데 실패했습니다.");
            return "pages/air/searchFlightI";

        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException");
            throw new RuntimeException(e);
        } catch (SAXException e) {
            System.out.println("SAXException");
            throw new RuntimeException(e);
        }

        // api 조회 결과가 없을 때 = 항공편 미존재
        if (flist.isEmpty()) {
            System.out.println("해당 항공편은 존재하지 않습니다.");
            sendAlert(response, "해당 항공편은 존재하지 않습니다.");
            return "pages/air/searchFlightI";
        }

        // TODO - view 화면 처리하고 어떤 식으로 처리할지 고민해봐야함.
        // 페이지네이션 처리
        int totalCount = flist.get(0).getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        IFlightDTO fhlist = new IFlightDTO(daID, aaID, dpTime, airline, pageNum,
            flist.get(0).getTotalCount());

        // flist : api요청 결과 / fhlist : api요청 정보
        model.addAttribute("flist", flist);
        model.addAttribute("fhlist", fhlist);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        return "pages/air/searchFlightPageI";
    }


    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void sendAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); </script>");
        out.flush();
    }

    private String formatDate(String date) throws ParseException {
        // 입력된 날짜 문자열이 이미 yyyyMMdd 형식인 경우 변환을 생략
        if (date.matches("\\d{8}")) {
            return date;
        }
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
