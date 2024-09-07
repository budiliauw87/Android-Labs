import 'package:flutter/material.dart';

class CardProfile extends StatelessWidget {
  const CardProfile({super.key});
  @override
  Widget build(BuildContext context) {
    return const Card(
        child: Padding(
      padding: EdgeInsets.all(16.0),
      child: Column(
        children: [
          Row(
            children: [
              CircleAvatar(
                radius: 48,
                backgroundImage: AssetImage('assets/images/dummy_profile.jpg'),
              ),
              Expanded(
                child: Padding(
                  padding: EdgeInsets.only(left: 16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'John Doe',
                        style: TextStyle(
                          fontWeight: FontWeight.w800,
                          fontFamily: 'Roboto',
                          letterSpacing: 0.5,
                          fontSize: 20,
                        ),
                        textAlign: TextAlign.left,
                      ),
                      Text(
                        'is simply dummy text of the printing and type setting industry. Lorem Ipsum has been the industrys'
                        'standard dummy text ever since the 1500s, when an unknown printer took a galley',
                        maxLines: 3,
                        overflow: TextOverflow.ellipsis,
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Padding(
            padding: EdgeInsets.only(top: 16.0, bottom: 16.0),
            child: Divider(
              height: 20,
              thickness: 0.5,
              endIndent: 0,
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Expanded(
                child: Column(
                  children: [
                    Text(
                      '123',
                      style: TextStyle(
                        fontWeight: FontWeight.w800,
                        fontFamily: 'Roboto',
                        letterSpacing: 0.5,
                        fontSize: 24,
                      ),
                    ),
                    Text(
                      'Pending',
                    )
                  ],
                ),
              ),
              Expanded(
                child: Column(
                  children: [
                    Text(
                      '50',
                      style: TextStyle(
                        fontWeight: FontWeight.w800,
                        fontFamily: 'Roboto',
                        letterSpacing: 0.5,
                        fontSize: 24,
                      ),
                    ),
                    Text(
                      'Progress',
                    )
                  ],
                ),
              ),
              Expanded(
                child: Column(
                  children: [
                    Text(
                      '1951',
                      style: TextStyle(
                        fontWeight: FontWeight.w800,
                        fontFamily: 'Roboto',
                        letterSpacing: 0.5,
                        fontSize: 24,
                      ),
                    ),
                    Text(
                      'Resolved',
                    )
                  ],
                ),
              )
            ],
          )
        ],
      ),
    ));
  }
}
