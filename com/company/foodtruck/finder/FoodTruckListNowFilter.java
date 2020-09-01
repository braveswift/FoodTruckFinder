package com.company.foodtruck.finder;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


class FoodTruckListNowFilter {
    static List<FoodTruck> currentOpenFoodTruck(List<FoodTruck> foodTruckList){
        Calendar now = Calendar.getInstance();
        int todayWeekdayOrder = adjustDayOfWeek(now.get(Calendar.DAY_OF_WEEK));
        final int nowHour = now.get(Calendar.HOUR_OF_DAY);
        final int nowMinute = now.get(Calendar.MINUTE);
        return foodTruckList.stream()
            .filter(foodTruck -> foodTruck.getDayOrder() == todayWeekdayOrder && isBetween(nowHour, nowMinute, foodTruck.getStart24(), foodTruck.getEnd24()))
            .sorted(Comparator.comparing(FoodTruck::getApplicant))
            .collect(Collectors.toList());
    }

    // Java uses 1 to stand for Sunday, 2 for Monday, etc. Convert it to 7 for Sunday and 1 for Monday.
    private static int adjustDayOfWeek(int dayOfWeek) {
        dayOfWeek = (dayOfWeek - 1) % 7;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    private static boolean isBetween(int hour, int min, String startString, String endString){
        return onOrAfter(hour, min, startString) && before(hour, min, endString);
    }

    private static boolean before(int hour, int min, String timeStr) {
        return hour < toHour(timeStr) || hour == toHour(timeStr) && min < toMinute(timeStr);
    }

    private static boolean onOrAfter(int hour, int min, String timeStr) {
        return !before(hour, min, timeStr);
    }

    private static int toMinute(String timeStr){
        String[] time = timeStr.split(":");
        return new Integer(time[1]);
    }

    private static int toHour(String timeStr){
        String[] time = timeStr.split(":");
        return new Integer(time[0]);
    }
}
