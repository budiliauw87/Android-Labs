class Food {
  final String name;

  Food({
    required this.name,
  });
  factory Food.fromJson(Map<String, dynamic> food) => Food(name: food['name']);
}
