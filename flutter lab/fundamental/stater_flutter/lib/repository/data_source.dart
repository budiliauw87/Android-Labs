import 'dart:convert';

import 'package:stater_flutter/repository/model/restaurant_item.dart';

class DataSource {
  static List<RestaurantItem> parseRestaurant(String? json) {
    if (json == null) {
      return [];
    }

    final parsed = jsonDecode(json);
    final List parsedList = parsed['restaurants'];
    return parsedList.map((json) => RestaurantItem.fromJson(json)).toList();
  }
}
