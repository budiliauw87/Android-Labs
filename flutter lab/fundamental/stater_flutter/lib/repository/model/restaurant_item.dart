import 'package:stater_flutter/repository/model/drink.dart';
import 'package:stater_flutter/repository/model/food.dart';

class RestaurantItem {
  final String id;
  final String name;
  final String description;
  final String pictureId;
  final String city;
  final double rating;
  final List<Drink> drinks;
  final List<Food> foods;

  RestaurantItem({
    required this.id,
    required this.name,
    required this.description,
    required this.pictureId,
    required this.city,
    required this.rating,
    required this.drinks,
    required this.foods,
  });

  factory RestaurantItem.fromJson(Map<String, dynamic> restaurant) {
    final drinks = restaurant['menus']['drinks'] as List<dynamic>?;
    final foods = restaurant['menus']['foods'] as List<dynamic>?;
    return RestaurantItem(
      id: restaurant['id'],
      name: restaurant['name'],
      description: restaurant['description'],
      pictureId: restaurant['pictureId'],
      city: restaurant['city'],
      rating: double.parse(restaurant['rating'].toString()),
      drinks: drinks != null
          ? drinks
              .map((drinks) => Drink.fromJson(drinks as Map<String, dynamic>))
              .toList()
          : <Drink>[],
      foods: foods != null
          ? foods
              .map((foods) => Food.fromJson(foods as Map<String, dynamic>))
              .toList()
          : <Food>[],
    );
  }
}
