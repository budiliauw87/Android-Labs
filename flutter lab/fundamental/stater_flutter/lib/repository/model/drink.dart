class Drink {
  final String name;

  Drink({
    required this.name,
  });

  factory Drink.fromJson(Map<String, dynamic> drink) =>
      Drink(name: drink['name']);
}
