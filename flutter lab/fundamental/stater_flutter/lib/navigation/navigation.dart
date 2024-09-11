import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stater_flutter/navigation/route_utils.dart';
import 'package:stater_flutter/repository/provider/global_provider.dart';
import 'package:stater_flutter/ui/about/about_screen.dart';
import 'package:stater_flutter/ui/error/error_screen.dart';
import 'package:stater_flutter/ui/home/home_screen.dart';

class NavigationApp extends StatefulWidget {
  const NavigationApp({super.key, required this.themeMode});
  final ThemeMode themeMode;
  @override
  State<NavigationApp> createState() => _NavigationApp();
}

class _NavigationApp extends State<NavigationApp> {
  @override
  Widget build(BuildContext context) {
    int indexPage = context.read<GlobalProvider>().positionNavigation;
    final bool isDark = (widget.themeMode == ThemeMode.dark);
    var title = isDark ? 'Dark Mode' : 'Light Mode';
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: Text(title),
          actions: [
            IconButton(
                icon: const Icon(Icons.search),
                tooltip: 'find restaurats',
                onPressed: () => Navigator.of(context)
                    .push(RouteUtils().navigateToScreen(null))),
            IconButton(
              icon: isDark
                  ? const Icon(Icons.light_mode)
                  : const Icon(Icons.dark_mode),
              tooltip: 'theme Mode',
              // onPressed: onToggleTheme,
              onPressed: () => context
                  .read<GlobalProvider>()
                  .setThemeMode(widget.themeMode == ThemeMode.dark),
            ),
          ],
        ),
        bottomNavigationBar: NavigationBar(
          onDestinationSelected: (int index) =>
              context.read<GlobalProvider>().setIndexNavigation(index),
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
          const ErrorScreen(
            errorMessage: 'Something when wrong!!',
          ),
          const AboutScreen(),
        ][indexPage]);
  }
}
