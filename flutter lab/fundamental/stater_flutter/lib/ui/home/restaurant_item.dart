import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/detail_item/detail_screen.dart';

class RestauranItem extends StatelessWidget {
  const RestauranItem({super.key, required this.restaurant});
  final RestaurantItem restaurant;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(_routeToDetail(restaurant));
      },
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            // crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: Image.network(
                  restaurant.pictureId,
                  height: 80.0,
                  width: 100.0,
                  fit: BoxFit.cover,
                ),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.only(left: 16.0, right: 16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      Text(
                        restaurant.name,
                        style: const TextStyle(
                          fontWeight: FontWeight.w600,
                          fontFamily: 'Roboto',
                          letterSpacing: 0.5,
                          fontSize: 20,
                        ),
                      ),
                      Row(
                        children: [
                          const Icon(
                            Icons.place_outlined,
                            size: 20.0,
                            semanticLabel: 'City Restaurants',
                          ),
                          Expanded(
                            child: Padding(
                              padding:
                                  const EdgeInsets.only(left: 8.0, right: 8.0),
                              child: Text(
                                restaurant.city,
                              ),
                            ),
                          )
                        ],
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 8.0),
                        child: Text(
                          '${restaurant.rating} Ratings',
                          maxLines: 2,
                          style: TextStyle(color: Colors.amberAccent.shade700),
                        ),
                      )
                    ],
                  ),
                ),
              )
            ],
          )),
    );
  }
}

Route _routeToDetail(RestaurantItem restaurant) {
  return PageRouteBuilder(
    pageBuilder: (context, animation, secondaryAnimation) => DetailScreen(
      title: 'Detail Page',
      restaurant: restaurant,
    ),
    transitionsBuilder: (context, animation, secondaryAnimation, child) {
      const begin = Offset(1.0, 0.0);
      const end = Offset.zero;
      const curve = Curves.ease;

      var tween = Tween(begin: begin, end: end).chain(CurveTween(curve: curve));

      return SlideTransition(
        position: animation.drive(tween),
        child: child,
      );
    },
  );
}
