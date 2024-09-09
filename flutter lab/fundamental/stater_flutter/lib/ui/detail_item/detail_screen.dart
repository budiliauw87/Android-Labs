import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';

class DetailScreen extends StatelessWidget {
  const DetailScreen(
      {super.key, required this.title, required this.restaurant});
  final String title;
  final RestaurantItem restaurant;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(title),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Image.network(
              restaurant.pictureId,
              fit: BoxFit.cover,
              width: MediaQuery.of(context).size.width,
              height: 200,
            ),
            Padding(
              padding: const EdgeInsets.only(left: 16.0, top: 8.0, right: 16.0),
              child: Text(
                restaurant.name,
                style: const TextStyle(
                  fontWeight: FontWeight.w600,
                  fontFamily: 'Roboto',
                  letterSpacing: 0.5,
                  fontSize: 20,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(left: 16.0, right: 16.0, top: 8.0),
              child: Row(
                children: [
                  const Icon(
                    Icons.place_outlined,
                    size: 20.0,
                    semanticLabel: 'City Restaurants',
                  ),
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.only(left: 8.0, right: 8.0),
                      child: Text(
                        restaurant.city,
                      ),
                    ),
                  ),
                  ElevatedButton.icon(
                    onPressed: () {
                      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                          content: Text('Feature comming soon')));
                    },
                    icon: const Icon(Icons.favorite),
                    label: const Text('Favorite'),
                  ),
                ],
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text(restaurant.description),
            ),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
