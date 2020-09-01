package com.company.foodtruck.finder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodTruckFinder {

    public static void main(String[] args) {
        try {
            StringBuilder result = new StringBuilder();
            // I noticed that there is a default return limit of 1000 results. I will assume that is good as code sample.
            // However in reality, I would get all the results and show it to user.
            URL url = new URL("http://data.sfgov.org/resource/bbb8-hzi6.json?");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            // read data as JsonNode
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode jsonNodeTree = mapper.readValue(result.toString(), JsonNode.class);
            // Convert JsonNode to ArrayList of FoodTruck
            final ArrayList<FoodTruck> foodTruckLinkedList = new ArrayList<>();
            jsonNodeTree.forEach(node -> foodTruckLinkedList.add(FoodTruckDTO.jsonNodeToFoodTruck(node)));
            // Filter and sort FoodTruckList
            final List<FoodTruck> currentOpenFoodTruckList = FoodTruckListNowFilter.currentOpenFoodTruck(foodTruckLinkedList);
            // Showing results to user
            int currentIndex = 0; // start from the first item
            int listSize = currentOpenFoodTruckList.size();
            do {
                int itemPerPage = (listSize - currentIndex) > 10? 10: (listSize - currentIndex);
                for (int i = 0; i < itemPerPage; i ++){
                    FoodTruck truck = currentOpenFoodTruckList.get(currentIndex);
                    System.out.println(truck.getApplicant());
                    System.out.println(truck.getLocation());
                    System.out.println();
                    currentIndex ++;
                }
                if (currentIndex != listSize){
                    System.out.println("Press Enter to show more.");
                    System.in.read();
                }
            } while(currentIndex != listSize);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
