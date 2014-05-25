interface SpringAttachable {
  void attachSpring(Spring s);
  void detachSpring(Spring s);
  boolean contains(double x, double y);
  double getPosition();
}