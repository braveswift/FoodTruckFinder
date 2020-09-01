package com.company.foodtruck.finder;

import com.fasterxml.jackson.databind.JsonNode;


class FoodTruckDTO {
    static FoodTruck jsonNodeToFoodTruck(final JsonNode jsonNode){
        return FoodTruck.builder()
            .applicant(jsonNode.get("applicant").toString().replace("\"", ""))
            .dayOfWeekStr(jsonNode.get("dayofweekstr").toString().replace("\"", ""))
            .dayOrder(jsonNode.get("dayorder").asInt())
            .location(jsonNode.get("location").toString().replace("\"", ""))
            .start24(jsonNode.get("start24").toString().replace("\"", ""))
            .end24(jsonNode.get("end24").toString().replace("\"", ""))
            .build();
    }
}
