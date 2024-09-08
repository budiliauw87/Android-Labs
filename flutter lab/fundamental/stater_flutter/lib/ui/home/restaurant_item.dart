import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';

class RestauranItem extends StatelessWidget {
  const RestauranItem({super.key, required this.restaurant});
  final RestaurantItem restaurant;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Logger().log(Level.debug, 'oke wowkring');
      },
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: Image.network(
                  'https://restaurant-api.dicoding.dev/images/medium/25',
                  height: 80.0,
                  width: 100.0,
                  fit: BoxFit.cover,
                ),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.only(left: 16.0, right: 16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        restaurant.name,
                        style: const TextStyle(
                          fontWeight: FontWeight.w600,
                          fontFamily: 'Roboto',
                          letterSpacing: 0.5,
                          fontSize: 18,
                        ),
                      ),
                      Text(
                        restaurant.description,
                        maxLines: 2,
                        overflow: TextOverflow.ellipsis,
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
