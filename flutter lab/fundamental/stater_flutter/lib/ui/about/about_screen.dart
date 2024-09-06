import 'package:flutter/material.dart';
import 'package:stater_flutter/ui/about/card_profile.dart';

class AboutScreen extends StatelessWidget {
  const AboutScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          const CardProfile(),
          const Padding(
            padding: EdgeInsets.all(16.0),
            child: Text(
              'Information Profile',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
            ),
          ),
          for (var i = 0; i < 5; i++) ...[
            Row(
              children: [
                Icon(
                  Icons.call_outlined,
                  color: Theme.of(context).hintColor,
                  size: 30.0,
                ),
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.all(16.0),
                    child: Text(
                      '0821112233456',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                        color: Theme.of(context).hintColor,
                      ),
                    ),
                  ),
                ),
                ElevatedButton(onPressed: () {}, child: Text('Call'))
              ],
            ),
          ]
        ],
      ),
    );
  }
}
