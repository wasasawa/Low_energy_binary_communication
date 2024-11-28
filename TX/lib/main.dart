import 'package:flutter/material.dart';
import 'package:test_app/screens/screen_home.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Morse Flasher',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ScreenHome(),
    );
  }
}

