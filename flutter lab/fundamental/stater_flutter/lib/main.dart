import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stater_flutter/repository/provider/global_provider.dart';
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
    final ThemeMode themeMode = context.watch<GlobalProvider>().themeMode;
    return MaterialApp(
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      darkTheme: ThemeData.dark(),
      // themeMode: themeMode,
      themeMode: themeMode,
      home: NavigationApp(
        themeMode: themeMode,
      ),
    );
  }
}
