import 'package:flutter/material.dart';
import 'package:logger/logger.dart';
import 'package:stater_flutter/ui/detail.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title, required this.isPressed});
  final Function(bool isDark) isPressed;
  final String title;
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  var indexPage = 0;
  bool isDark = false;
  var logger = Logger();

  void _changeIconTheme() {
    setState(() {
      isDark = !isDark;
      widget.isPressed(isDark);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
        actions: [
          IconButton(
            icon: isDark
                ? const Icon(Icons.light_mode)
                : const Icon(Icons.dark_mode),
            tooltip: 'theme Mode',
            onPressed: _changeIconTheme,
          ),
          IconButton(
            icon: Icon(Icons.settings),
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
            label: 'Component',
          ),
          NavigationDestination(
            selectedIcon: Icon(Icons.info),
            icon: Icon(Icons.info_outlined),
            label: 'About',
          ),
        ],
      ),

      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Press button to Detail Screen:',
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: ElevatedButton(
                onPressed: () => {
                  Navigator.of(context).push(_createRoute())
                  // Navigator.push(
                  //   context,
                  //   MaterialPageRoute(
                  //       builder: (context) => const DetailScreen(
                  //             title: 'Detail Screen',
                  //           )),
                  // )
                },
                child: const Text('Navigate'),
              ),
            ),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

Route _createRoute() {
  return PageRouteBuilder(
    pageBuilder: (context, animation, secondaryAnimation) =>
        const DetailScreen(title: 'Detail Page'),
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
