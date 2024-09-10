import 'package:flutter/material.dart';

class GlobalProvider extends ChangeNotifier {
  ThemeMode themeMode = ThemeMode.light;
  int positionNavigation = 0;

  void setThemeMode(bool isDark) {
    themeMode = isDark ? ThemeMode.light : ThemeMode.dark;
    notifyListeners();
  }

  void setIndexNavigation(int position) {
    positionNavigation = position;
    notifyListeners();
  }
}
