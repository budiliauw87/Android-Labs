import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/home/restaurant_item.dart';

List<RestaurantItem> parseRestaurant(String? json) {
  if (json == null) {
    return [];
  }

  final parsed = jsonDecode(json);
  final List parsedList = parsed['restaurants'];
  return parsedList.map((json) => RestaurantItem.fromJson(json)).toList();
  // List<RestaurantItem> restaurants =
  //     parsedList.map((json) => RestaurantItem.fromJson(json)).toList();
  // if (restaurants != null) {
  //   Logger().log(Level.debug, 'total : ${restaurants.length}');
  // }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return NestedScrollView(
      // Setting floatHeaderSlivers to true is required in order to float
      // the outer slivers over the inner scrollable.
      floatHeaderSlivers: true,
      headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) => [
        SliverAppBar(
            expandedHeight: 200,
            flexibleSpace: FlexibleSpaceBar(
                background: Image.asset('assets/images/banner.jpg',
                    fit: BoxFit.fitWidth)))
      ],
      body: FutureBuilder<String>(
        future: DefaultAssetBundle.of(context)
            .loadString('assets/local_restaurant.json'),
        builder: (context, snapshot) {
          List<RestaurantItem> list = parseRestaurant(snapshot.data);
          return ListView.builder(
              padding: const EdgeInsets.all(8),
              itemCount: list.length,
              itemBuilder: (BuildContext context, int index) {
                return RestauranItem(restaurant: list[index]);
              });
        },
      ),
    );
  }
}
