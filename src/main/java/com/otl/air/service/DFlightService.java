package com.otl.air.service;

import com.otl.air.dto.DFlightDTO;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface DFlightService {

    public String nameSet(String name);

    // 항공 스케쥴 조회 api 요청
    public ArrayList<DFlightDTO> airApi(String daID, String aaID, String dpTime, String airline, Integer pageNum) throws IOException, JSONException;
}
