import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/data_source.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/common/restaurant_item.dart';

class SearchScreen extends StatelessWidget {
  const SearchScreen({super.key});
  @override
  Widget build(BuildContext context) {
    Future<String> localData = DefaultAssetBundle.of(context)
        .loadString('assets/local_restaurant.json');

    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: const Text('Searching'),
          centerTitle: true,
        ),
        body: FutureBuilder<String>(
          future: localData,
          builder: (context, snapshot) {
            List<RestaurantItem> list =
                DataSource().parseRestaurant(snapshot.data);
            return Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(
                      left: 16.0, top: 16.0, right: 16.0, bottom: 8.0),
                  child: SearchAnchor(
                    isFullScreen: false,
                    builder:
                        (BuildContext context, SearchController controller) {
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
                    child: ListView.builder(
                        padding: const EdgeInsets.all(8),
                        itemCount: list.length,
                        itemBuilder: (BuildContext context, int index) {
                          return RestauranItem(restaurant: list[index]);
                        })),
              ],
            );
            // return ListView.builder(
            //     padding: const EdgeInsets.all(8),
            //     itemCount: list.length,
            //     itemBuilder: (BuildContext context, int index) {
            //       return RestauranItem(restaurant: list[index]);
            //     });
          },
        )
        // Padding(
        //   padding: const EdgeInsets.only(
        //       left: 16.0, top: 8.0, right: 16.0, bottom: 8.0),
        //   child: Column(
        //     children: [
        //       SearchAnchor(
        //         isFullScreen: false,
        //         builder: (BuildContext context, SearchController controller) {
        //           return SearchBar(
        //             controller: controller,
        //             onTap: () => {},
        //             onChanged: (_) {
        //               // Logger()
        //               //     .log(Level.debug, "Query onChange : " + _.toString());
        //             },
        //             leading: const Icon(Icons.search),
        //           );
        //         },
        //         suggestionsBuilder: (context, controller) => {},
        //       ),

        //     ],
        //   ),
        // ),
        );
  }
}
