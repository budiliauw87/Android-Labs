import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:stater_flutter/ui/home/restaurant_item.dart';

parseRestaurant(String? json) {
  if (json == null) {
    return;
  }

  final parsed = jsonDecode(json);
  Logger().log(Level.debug, parsed['restaurants'].toString());
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
          parseRestaurant(snapshot.data);

          return ListView.builder(
              padding: const EdgeInsets.all(8),
              itemCount: 5,
              itemBuilder: (BuildContext context, int index) {
                return const RestauranItem();
              });
        },
      ),
    );
  }
}
