package com.otl.air.dto;

import lombok.Data;

@Data
public class DFlightDTO {

    private String vihicleId;         // 항공편명
    private String airlineNm;         // 항공사명
    private long depPlandTime;        // 출발시간
    private long arrPlandTime;        // 도착시간
    private int economyCharge;        // 일반석운임
    private int prestigeCharge;       // 비즈니스석운임
    private String depAirportNm;      // 출발공항
    private String arrAirportNm;      // 도착공항

    private String startPortName;     // input 출발공항
    private String endPortName;       // input 도착공항
    private String startDate;         // 조회 날짜 - 출발
    private String airline;           // 조회 항공사 (선택)
    private String endPN_ko;          // 도착공항 한글이름 (?)
    private Integer pageNo;           // 페이지 번호 (페이지 수)
    private int totalCount;           // 총 데이터 개수

    // 생성자
    public DFlightDTO() { }

    // 주요 필드 초기화하는 생성자 (항공편의 기본 정보 설정)
    public DFlightDTO(String vihicleId, String airlineNm, long depPlandTime, long arrPlandTime,
                    int economyCharge, int prestigeCharge, String depAirportNm, String arrAirportNm) {
        this.vihicleId = vihicleId;
        this.airlineNm = airlineNm;
        this.depPlandTime = depPlandTime;
        this.arrPlandTime = arrPlandTime;
        this.economyCharge = economyCharge;
        this.prestigeCharge = prestigeCharge;
        this.depAirportNm = depAirportNm;
        this.arrAirportNm = arrAirportNm;
    }

    // 문자열 형식의 데이터를 받아 내부적으로 변환하여 초기화하는 생성자 (JSON이나 XML에서 데이터 받을 때)
    public DFlightDTO(String vihicleId, String airlineNm, String depPlandTime, String arrPlandTime,
                    String economyCharge, String prestigeCharge, String depAirportNm, String arrAirportNm) {
        this.vihicleId = vihicleId;
        this.airlineNm = airlineNm;
        this.depPlandTime = Long.valueOf(depPlandTime); // 문자열 -> Long 객체
        this.arrPlandTime = Long.valueOf(arrPlandTime);
        this.economyCharge = Integer.valueOf(economyCharge); // 문자열 -> Integer 객체
        this.prestigeCharge =Integer.valueOf(prestigeCharge);
        this.depAirportNm = depAirportNm;
        this.arrAirportNm = arrAirportNm;
    }

    // 조회 조건 및 페이지 정보를 인자로 받아 초기화하는 생성자 (검색 조건을 설정할 때 사용)
    public DFlightDTO(String startPortName, String endPortName, String startDate, String airline,
                    Integer pageNo, int totalCount, String endPN_ko) {
        this.startPortName = startPortName;
        this.endPortName = endPortName;
        this.startDate = startDate;
        this.airline = airline;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
        this.endPN_ko = endPN_ko;
    }


}
