package lk.ijse.gdse.instritutefirstsemfinal;

import javafx.scene.paint.Color;

import java.util.Random;

public class RandomColorGenerator {

    public static Color getRandomColor() {
        Random random = new Random();
        // Generate random RGB values between 0 and 1
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }
}
