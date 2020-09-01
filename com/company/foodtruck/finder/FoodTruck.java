package com.company.foodtruck.finder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodTruck {
    private int dayOrder;

    private String dayOfWeekStr;

    private String start24;

    private String end24;

    private String location;

    private String applicant;
}
