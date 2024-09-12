import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stater_flutter/repository/model/restaurant_item.dart';
import 'package:stater_flutter/repository/provider/global_provider.dart';
import 'package:stater_flutter/ui/detail_item/detail_screen.dart';
import 'package:stater_flutter/ui/search/search_screen.dart';
import 'navigation/navigation.dart';
import 'package:flutter_native_splash/flutter_native_splash.dart';

void main() {
  WidgetsBinding widgetsBinding = WidgetsFlutterBinding.ensureInitialized();
  FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
  runApp(
    ChangeNotifierProvider(
      create: (context) => GlobalProvider(),
      child: const MyApp(),
    ),
  );
  FlutterNativeSplash.remove();
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return Consumer<GlobalProvider>(builder: (context, globalProvider, child) {
      return MaterialApp(
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
          pageTransitionsTheme: PageTransitionsTheme(builders: {
            TargetPlatform.iOS: CupertinoPageTransitionsBuilder(),
            TargetPlatform.android: CupertinoPageTransitionsBuilder(),
          }),
        ),
        darkTheme: ThemeData.dark(),
        themeMode: globalProvider.themeMode,
        initialRoute: '/',
        routes: <String, WidgetBuilder>{
          '/': (context) => NavigationApp(themeMode: globalProvider.themeMode),
          '/search': (context) => const SearchScreen(),
          '/detail': (context) => DetailScreen(
                title: 'Detail Restaurant',
                restaurant: ModalRoute.of(context)?.settings.arguments
                    as RestaurantItem,
              ),
        },

        // home: NavigationApp(
        //   themeMode: globalProvider.themeMode,
        // ),
      );
    });
  }
}
