import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/data_source.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/common/restaurant_item_screen.dart';
import 'package:stater_flutter/ui/error/error_screen.dart';

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
            backgroundColor: const Color(0xFFE1DFDF),
            expandedHeight: 200,
            flexibleSpace: FlexibleSpaceBar(
                background: Image.asset('assets/images/banner.jpg',
                    fit: BoxFit.fitWidth)))
      ],
      body: FutureBuilder<String>(
        future: DefaultAssetBundle.of(context)
            .loadString('assets/local_restaurant.json'),
        builder: (context, snapshot) {
          switch (snapshot.connectionState) {
            case ConnectionState.none:
              return const ErrorScreen(errorMessage: 'No Connection');
            case ConnectionState.active:
            case ConnectionState.waiting:
              return const Center(child: CircularProgressIndicator());
            case ConnectionState.done:
              if (snapshot.hasData) {
                List<RestaurantItem> list =
                    DataSource().parseRestaurant(snapshot.data);
                return ListView.builder(
                    padding: const EdgeInsets.all(8),
                    itemCount: list.length,
                    itemBuilder: (BuildContext context, int index) {
                      return RestauranItemScreen(restaurant: list[index]);
                    });
              }
          }
          return const Center(child: CircularProgressIndicator());
        },
      ),
    );
  }
}
