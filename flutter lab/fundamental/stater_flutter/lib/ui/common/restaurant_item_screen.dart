import 'package:flutter/material.dart';
import 'package:stater_flutter/navigation/route_utils.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:transparent_image/transparent_image.dart';

class RestauranItemScreen extends StatelessWidget {
  const RestauranItemScreen({super.key, required this.restaurant});
  final RestaurantItem restaurant;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        // Navigator.of(context).push(RouteUtils.navigateToScreen(restaurant));
        Navigator.pushNamed(context, '/detail', arguments: restaurant);
      },
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: FadeInImage.memoryNetwork(
                  placeholder: kTransparentImage,
                  image: restaurant.pictureId,
                  fit: BoxFit.cover,
                  width: 120,
                  height: 100,
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
