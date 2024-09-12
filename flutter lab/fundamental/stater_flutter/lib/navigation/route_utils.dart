import 'package:flutter/material.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/ui/detail_item/detail_screen.dart';

import '../ui/search/search_screen.dart';

class RouteUtils {
  static Route navigateToScreen(Object? object) {
    return PageRouteBuilder(
      pageBuilder: (context, animation, secondaryAnimation) {
        if (object is RestaurantItem) {
          return DetailScreen(title: 'Detail Restaurants', restaurant: object);
        } else {
          return const SearchScreen();
        }
      },
      transitionsBuilder: (context, animation, secondaryAnimation, child) {
        const begin = Offset(1.0, 0.0);
        const end = Offset.zero;
        const curve = Curves.ease;

        var tween =
            Tween(begin: begin, end: end).chain(CurveTween(curve: curve));

        return SlideTransition(
          position: animation.drive(tween),
          child: child,
        );
      },
    );
  }
}
