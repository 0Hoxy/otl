package com.otl.air.controller;

import com.otl.air.entity.FlightEntity;
import com.otl.air.service.FlightRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/api")
public class FlightRequestController {

    @Autowired
    private FlightRequestService flightRequestService;

    @GetMapping("/list")
    public String searchFlights(@RequestParam(value = "departure_id", required = true, defaultValue = "PUS") String departure_id,
                                @RequestParam(value = "arrival_id", required = true, defaultValue = "NRT") String arrival_id,
                                @RequestParam(value = "outbound_date", required = true, defaultValue = "2024-08-20") String outbound_date,
                                @RequestParam(value = "return_date", required = false, defaultValue = "2024-08-25") String return_date,
                                @RequestParam(value = "travel_class", required = false, defaultValue = "0") int travel_class,
                                @RequestParam(value = "stops", required = false, defaultValue = "0") int stops,
                                @RequestParam(value = "max_price", required = false, defaultValue = "0") int max_price,
                                @RequestParam(value = "hl", required = false) String hl,
                                @RequestParam(value = "gl", required = false) String gl,
                                @RequestParam(value = "currency", required = false) String currency,
                                @RequestParam(value = "type", required = true, defaultValue = "1") int type, Model model
    ) throws Exception {

        FlightEntity requestEntity = new FlightEntity( hl, gl, type, currency, departure_id, arrival_id,
            LocalDate.parse(outbound_date, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)),
            return_date.equals(" ") ? null : LocalDate.parse(return_date, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)),
            travel_class, stops, max_price);

        // FlightRequestService에서 검색 요청
        List<FlightEntity> flights = flightRequestService.searchFlights(requestEntity);

        // Add response data to the model
        model.addAttribute("departure_id", departure_id);
        model.addAttribute("arrival_id", arrival_id);
        model.addAttribute("outbound_date", outbound_date);
        model.addAttribute("return_date", return_date.equals(" ") ? null : return_date);
        model.addAttribute("travel_class", travel_class);
        model.addAttribute("stops", stops);
        model.addAttribute("max_price", max_price);
        model.addAttribute("hl", hl);
        model.addAttribute("gl", gl);
        model.addAttribute("currency", currency);
        model.addAttribute("type", type);
        model.addAttribute("flights", flights); // 모델에 flights 데이터 추가

        return "pages/air/list";
    }
}


