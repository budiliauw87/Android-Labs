import 'package:flutter/material.dart';

class RestauranItem extends StatelessWidget {
  const RestauranItem({super.key});
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        print('oke wowkring');
      },
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: Image.network(
                  'https://restaurant-api.dicoding.dev/images/medium/25',
                  height: 80.0,
                  width: 100.0,
                  fit: BoxFit.cover,
                ),
              ),
              const Expanded(
                child: Padding(
                  padding: EdgeInsets.only(left: 16.0, right: 16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Example name restaurant',
                        style: TextStyle(
                          fontWeight: FontWeight.w600,
                          fontFamily: 'Roboto',
                          letterSpacing: 0.5,
                          fontSize: 18,
                        ),
                      ),
                      Text('Example name restaurant')
                    ],
                  ),
                ),
              )
            ],
          )),
    );
  }
}
