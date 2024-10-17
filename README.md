
# JoystickView for Android

A custom `View` class that provides an interactive joystick interface. This class can be used in Android applications to capture user input for various directions and movements, making it ideal for games, robotic controls, or any other scenario that requires joystick interaction.

## Features

- **Customizable Joystick**: A base circle and a movable hat (knob) represent the joystick.
- **Directional Arrows**: Visual indicators for "Up", "Down", "Left", and "Right" to guide the user.
- **Responsive Feedback**: The `OnJoystickMoveListener` interface provides real-time updates on joystick movement, including direction and percentage-based displacement from the center.
- **Smooth Interaction**: The joystick responds to touch events, including dragging, and snaps back to the center when released.

## Preview
![885shots_so](https://github.com/user-attachments/assets/16062d86-ba57-4634-b228-77f939b8fd3d)


## Usage

### XML Layout

To include the `JoystickView` in your layout, simply declare it in your XML file:

```xml
<com.smartherd.joystick.JoystickView
    android:id="@+id/joystickView"
    android:layout_width="300dp"
    android:layout_height="300dp" />
```

### Java Setup

In your `Activity` or `Fragment`, set up the joystick and attach a listener:

```java
JoystickView joystickView = findViewById(R.id.joystickView);
joystickView.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
    @Override
    public void onMove(float xPercent, float yPercent, String direction) {
        // Handle joystick movement
        Log.d("Joystick", "X: " + xPercent + ", Y: " + yPercent + ", Direction: " + direction);
    }
});
```

### Handling Movement

The `onMove` method gives you three key parameters:
- **xPercent**: Horizontal displacement from the center (-1 to 1).
- **yPercent**: Vertical displacement from the center (-1 to 1).
- **direction**: A string representing the direction (e.g., "Up", "Down", "Left", "Right", etc.).


## Customization

You can easily customize the appearance of the joystick by modifying the paint properties for:
- **Base Paint**: `basePaint` controls the look of the joystick base.
- **Hat Paint**: `hatPaint` controls the appearance of the movable part.
- **Arrow Paint**: `arrowPaint` controls the direction indicators.

Example:

```java
// Change base color
basePaint.setColor(Color.BLUE);
// Change hat color
hatPaint.setColor(Color.GREEN);
```
