import 'package:flutter/material.dart';

class ErrorScreen extends StatelessWidget {
  const ErrorScreen({
    super.key,
    required this.errorMessage,
  });
  final String errorMessage;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Icon(
            Icons.error_outline,
            size: 100.0,
            semanticLabel: errorMessage,
          ),
          const Text(
            'Opss!!',
            style: TextStyle(fontWeight: FontWeight.w800, fontSize: 32),
          ),
          Padding(
            padding: const EdgeInsets.only(bottom: 16.0),
            child: Text(
              errorMessage,
            ),
          ),
          ElevatedButton(
            onPressed: () => {},
            child: const Text('Try again'),
          ),
        ],
      ),
    );
  }
}
