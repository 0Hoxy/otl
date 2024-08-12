package com.otl.air.entity;


import com.otl.air.dto.Airport;

import java.time.LocalDate;

public class FlightEntity {

    private String api_key;
    private String hl;
    private String gl;
    private int type;
    private String currency;
    private String departure_id;
    private String arrival_id;
    private LocalDate outbound_date;
    private LocalDate return_date;
    /*---------------------------------------------------------*/
    private int travel_class;
    private int stops;
    private int max_price;
    /*---------------------------------------------------------*/
    private String airline;
    private String airplane;
    private Airport arrival_airport;
    private Airport departure_airport;
    private int duration;

    private String flight_number;

    public FlightEntity() {}

    public FlightEntity(String hl, String gl, int type, String currency,
                        String departure_id, String arrival_id, LocalDate outbound_date,
                        LocalDate return_date, int travel_class, int stops, int max_price) {
        this.hl = hl;
        this.gl = gl;
        this.type = type;
        this.currency = currency;
        this.departure_id = departure_id;
        this.arrival_id = arrival_id;
        this.outbound_date = outbound_date;
        this.return_date = return_date;
        this.travel_class = travel_class;
        this.stops = stops;
        this.max_price = max_price;
    }

    // getter setter
    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getArrival_id() {
        return arrival_id;
    }

    public void setArrival_id(String arrival_id) {
        this.arrival_id = arrival_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeparture_id() {
        return departure_id;
    }

    public void setDeparture_id(String departure_id) {
        this.departure_id = departure_id;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public LocalDate getOutbound_date() {
        return outbound_date;
    }

    public void setOutbound_date(LocalDate outbound_date) {
        this.outbound_date = outbound_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public int getTravel_class() {
        return travel_class;
    }

    public void setTravel_class(int travel_class) {
        this.travel_class = travel_class;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public Airport getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(Airport arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public Airport getDeparture_airport() {
        return departure_airport;
    }

    public void setDeparture_airport(Airport departure_airport) {
        this.departure_airport = departure_airport;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
            "departure_id='" + departure_id + '\'' +
            ", arrival_id='" + arrival_id + '\'' +
            ", outbound_date=" + outbound_date +
            ", return_date=" + return_date +
            ", travel_class=" + travel_class +
            ", stops=" + stops +
            ", max_price=" + max_price +
            '}';
    }
}