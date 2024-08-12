package com.otl.air.service;

import com.otl.air.dto.Airport;
import com.otl.air.entity.FlightEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application-dev.properties")
public class FlightRequestService {

    @Value("${GOOGLE_FLIGHTS_API_KEY}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public List<FlightEntity> searchFlights(FlightEntity query) throws Exception {
        try {
            String baseUrl = "https://serpapi.com/search.json?engine=google_flights";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("departure_id", query.getDeparture_id())
                .queryParam("arrival_id", query.getArrival_id())
                .queryParam("type", query.getType())
                .queryParam("outbound_date", query.getOutbound_date())
                .queryParam("api_key", apiKey);
            addQueryParameters(builder, query);

            String url = builder.toUriString();
            String response = restTemplate.getForObject(url, String.class);

            // JSON 응답 파싱
            JSONObject jsonObject = new JSONObject(response);
            System.out.println(jsonObject.toString(0)); // JSON 응답 전체 출력

            JSONArray flightsArray = jsonObject.has("best_flights")
                ? jsonObject.getJSONArray("best_flights")
                : jsonObject.getJSONArray("other_flights");

            List<FlightEntity> list = new ArrayList<>();
            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject flightObject = flightsArray.getJSONObject(i);
                JSONArray flightDetailsArray = flightObject.getJSONArray("flights");

                for (int j = 0; j < flightDetailsArray.length(); j++) {
                    JSONObject flightDetail = flightDetailsArray.getJSONObject(j);
                    FlightEntity flight = new FlightEntity();

                    // Set departure and arrival airports
                    JSONObject departureAirport = flightDetail.getJSONObject("departure_airport");
                    Airport departure = new Airport();
                    departure.setId(departureAirport.getString("id"));
                    departure.setName(departureAirport.getString("name"));
                    departure.setTime(departureAirport.getString("time"));
                    flight.setDeparture_airport(departure);

                    JSONObject arrivalAirport = flightDetail.getJSONObject("arrival_airport");
                    Airport arrival = new Airport();
                    arrival.setId(arrivalAirport.getString("id"));
                    arrival.setName(arrivalAirport.getString("name"));
                    arrival.setTime(arrivalAirport.getString("time"));
                    flight.setArrival_airport(arrival);

                    // 항공편 세부 정보 추출
                    flight.setDeparture_id(flightDetail.getJSONObject("departure_airport").getString("id"));
                    flight.setArrival_id(flightDetail.getJSONObject("arrival_airport").getString("id"));
                    flight.setStops(flightDetail.optInt("stops", 0));
                    flight.setMax_price(flightDetail.optInt("price", 0));
                    flight.setHl(flightDetail.optString("hl", null));
                    flight.setGl(flightDetail.optString("gl", null));
                    flight.setCurrency(flightDetail.optString("currency", null));
                    // ================================================================
                    flight.setFlight_number(flightDetail.optString("flight_number", "N/A"));
                    flight.setDuration(flightDetail.optInt("duration", 0));
                    flight.setAirline(flightDetail.optString("airline", "N/A"));
                    flight.setAirplane(flightDetail.optString("airplane", "N/A"));

                    list.add(flight);
                    System.out.println(flight.toString()); // 로그용
                }
            }
            return list;

        } catch (RestClientException e) {
            throw new Exception("Failed to communicate with the flight API.", e);
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred while searching for flights.", e);
        }
    }


    private void addQueryParameters(UriComponentsBuilder builder, FlightEntity query) throws Exception {
        if (query.getType() != 1 && query.getType() != 2) {
            throw new IllegalArgumentException("Invalid type value.");
        } else if (query.getType() == 2 && query.getReturn_date() == null) {
            throw new Exception("Return date is required only for round trip flights.");
        } else if (query.getType() == 1 && query.getReturn_date() != null) {
            builder.queryParam("return_date", query.getReturn_date());
        }

        if (query.getTravel_class() >= 1 && query.getTravel_class() <= 4) {
            builder.queryParam("travel_class", String.valueOf(query.getTravel_class()));
        }
        if (query.getStops() >= 0 && query.getStops() <= 3) {
            builder.queryParam("stops", String.valueOf(query.getStops()));
        }
        if (query.getMax_price() != 0) {
            builder.queryParam("max_price", String.valueOf(query.getMax_price()));
        }
        if (query.getHl() != null) {
            builder.queryParam("hl", String.valueOf(query.getHl()));
        }
        if (query.getGl() != null) {
            builder.queryParam("gl", String.valueOf(query.getGl()));
        }
        if (query.getCurrency() != null) {
            builder.queryParam("currency", String.valueOf(query.getCurrency()));
        }
    }
}