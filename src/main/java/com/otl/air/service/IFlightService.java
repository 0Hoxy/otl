package com.otl.air.service;

import com.otl.air.dto.IFlightDTO;

import java.io.IOException;
import java.util.ArrayList;

public interface IFlightService {

    // 항공 스케쥴 조회 api 요청
    public ArrayList<IFlightDTO> airApi(String daID, String aaID, String dpTime, String airline, Integer pageNum) throws IOException;
}
