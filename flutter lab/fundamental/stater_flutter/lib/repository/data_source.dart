import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:stater_flutter/repository/model/restaurant_item.dart';

class DataSource {
  final String baseUrl = 'https://restaurant-api.dicoding.dev/';

  Future<http.Response> fetchRestaurants() {
    return http.get(Uri.parse('${baseUrl}list'));
  }

  Future<http.Response> fetchRestaurant(String id) {
    return http.get(Uri.parse('${baseUrl}detail/$id'));
  }

  Future<http.Response> searchRestaurants(String query) {
    return http.get(Uri.parse('${baseUrl}search?q=$query'));
  }

  Future<http.Response> addReviews(String query) {
    return http.post(Uri.parse('${baseUrl}review'),
        body: {'name': 'doodle', 'color': 'blue'});
  }

  List<RestaurantItem> parseRestaurant(String? json) {
    if (json == null) {
      return [];
    }

    final parsed = jsonDecode(json);
    final List parsedList = parsed['restaurants'];
    return parsedList.map((json) => RestaurantItem.fromJson(json)).toList();
  }
}
