package com.otl.air.dto;

import lombok.Data;

@Data
public class IFlightDTO {

        private String daID;
        private String aaID; // Ensure this field exists
        private String dpTime;

    private String airFln;              // 항공편명
    private String airlineKorean;       // 항공사명
    private long std;                   // 출발시간
    private long sta;                   // 도착시간 (임의로 해야함 -> +6시간 해서 걍 하기)
    private String boardingKor;         // 출발공항 (국문)
    private String arrivedKor;          // 도착공항 (국문)
    private String boardingEng;         // 출발공항 (영문)
    private String arrivedEng;          // 도착공항 (영문)

    private String airport;             // input 출발공항 (기준공항코드)
    private String city;                // input 도착공항 (운항구간코드)
    private String startDate;           // 조회 날짜 - 출발
    private String airline;             // 조회 항공사 (선택)
    private String endPN_ko;            // 도착공항 한글이름 (?)

    private int economyCharge;          // 일반석운임
    private int prestigeCharge;         // 비즈니스석운임

    private Integer pageNo;             // 페이지 번호 (페이지 수)
    private Integer numOfRows;          // 열 숫자
    private int totalCount;             // 총 데이터 개수

    // 생성자
    public IFlightDTO() { }

    // 조회 조건 및 페이지 정보를 인자로 받아 초기화하는 생성자 (검색 조건을 설정할 때 사용)
    // 출발 공항, 도착 공항, 조회 날짜, 항공사, 페이지 번호, 총 데이터 개수
    public IFlightDTO(String boardingKor, String arrivedKor, String startDate, String airline,
                      Integer pageNo, int totalCount) {
        this.boardingKor = boardingKor;
        this.arrivedKor = arrivedKor;
        this.startDate = startDate;
        this.airline = airline;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
    }

}
