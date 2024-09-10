import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:stater_flutter/repository/provider/global_provider.dart';
import 'navigation/navigation.dart';

void main() {
  // runApp(const MyApp());
  runApp(
    ChangeNotifierProvider(
      create: (context) => GlobalProvider(),
      child: const MyApp(),
    ),
  );
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
