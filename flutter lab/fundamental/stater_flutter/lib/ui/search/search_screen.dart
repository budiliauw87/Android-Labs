import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/data_source.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/common/restaurant_item_screen.dart';
import 'package:stater_flutter/ui/error/error_screen.dart';

class SearchScreen extends StatefulWidget {
  const SearchScreen({super.key});
  @override
  State<SearchScreen> createState() => _SearchScreen();
}

class _SearchScreen extends State<SearchScreen> {
  late Future<String> localData;

  @override
  void initState() {
    super.initState();

    localData = Future.delayed(
        const Duration(seconds: 1),
        () => localData = DefaultAssetBundle.of(context)
            .loadString('assets/local_restaurant.json'));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text('Searching'),
        centerTitle: true,
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.only(
                left: 16.0, top: 16.0, right: 16.0, bottom: 8.0),
            child: SearchAnchor(
              isFullScreen: false,
              builder: (BuildContext context, SearchController controller) {
                return SearchBar(
                  controller: controller,
                  onTap: () => {},
                  onChanged: (_) {
                    // Logger()
                    //     .log(Level.debug, "Query onChange : " + _.toString());
                  },
                  leading: const Icon(Icons.search),
                );
              },
              suggestionsBuilder: (context, controller) => {},
            ),
          ),
          Expanded(
            child: FutureBuilder<String>(
              future: localData,
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
          )
        ],
      ),
    );
  }
}
