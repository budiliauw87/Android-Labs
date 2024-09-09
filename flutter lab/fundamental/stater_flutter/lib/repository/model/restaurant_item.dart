class RestaurantItem {
  final String id;
  final String name;
  final String description;
  final String pictureId;
  final String city;
  final double rating;

  RestaurantItem({
    required this.id,
    required this.name,
    required this.description,
    required this.pictureId,
    required this.city,
    required this.rating,
  });

  factory RestaurantItem.fromJson(Map<String, dynamic> restaurant) =>
      RestaurantItem(
          id: restaurant['id'],
          name: restaurant['name'],
          description: restaurant['description'],
          pictureId: restaurant['pictureId'],
          city: restaurant['city'],
          rating: double.parse(restaurant['rating'].toString()));
}
