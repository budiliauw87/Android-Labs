import 'package:flutter/material.dart';
import 'package:stater_flutter/ui/about/about_screen.dart';
import 'package:stater_flutter/ui/favorite/favorite_screen.dart';
import 'package:stater_flutter/ui/home/home_screen.dart';

class NavigationApp extends StatefulWidget {
  const NavigationApp({super.key, required this.onToggleTheme});
  final Function(bool isDark) onToggleTheme;
  @override
  State<NavigationApp> createState() => _NavigationApp();
}

class _NavigationApp extends State<NavigationApp> {
  ThemeMode themeMode = ThemeMode.light;
  int indexPage = 0;
  var title = 'Light Mode';
  bool isDark = false;
  void _onToggleTheme() {
    setState(() {
      isDark = !isDark;
      title = isDark ? 'Dark Mode' : 'Light Mode';
      widget.onToggleTheme(isDark);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: Text(title),
          actions: [
            IconButton(
              icon: isDark
                  ? const Icon(Icons.light_mode)
                  : const Icon(Icons.dark_mode),
              tooltip: 'theme Mode',
              onPressed: _onToggleTheme,
            ),
            IconButton(
              icon: const Icon(Icons.settings),
              tooltip: 'Theme Style',
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('This is a snackbar')));
              },
            ),
          ],
        ),
        bottomNavigationBar: NavigationBar(
          onDestinationSelected: (int index) =>
              {setState(() => indexPage = index)},
          selectedIndex: indexPage,
          destinations: const <Widget>[
            NavigationDestination(
              selectedIcon: Icon(Icons.home),
              icon: Icon(Icons.home_outlined),
              label: 'Home',
            ),
            NavigationDestination(
              selectedIcon: Icon(Icons.apps),
              icon: Icon(Icons.apps_outlined),
              label: 'Favorite',
            ),
            NavigationDestination(
              selectedIcon: Icon(Icons.info),
              icon: Icon(Icons.info_outlined),
              label: 'About',
            ),
          ],
        ),
        body: <Widget>[
          /// Home page
          const HomeScreen(),
          const FavoriteScreen(),
          const AboutScreen(),
        ][indexPage]);
  }
}
