package com.github.basp1.decisions;

public class Data {
    public static Feature outlook, temp, humidity, wind;
    public static FeatureSample rainy, overcast, sunny;
    public static FeatureSample hot, mild, cool;
    public static FeatureSample low, normal, high;
    public static FeatureSample windy, calm;

    static {
        outlook = new Feature("Outlook");
        sunny = outlook.assume("sunny", 0);
        overcast = outlook.assume("overcast", 1);
        rainy = outlook.assume("rainy", 2);

        temp = new Feature("Temp");
        cool = temp.assume("cool", -100, 0);
        mild = temp.assume("mild", 0, 15);
        hot = temp.assume("hot", 15, 100);

        humidity = new Feature("Humidity");
        low = humidity.assume("low", 0);
        normal = humidity.assume("normal", 1);
        high = humidity.assume("high", 2);

        wind = new Feature("Wind");
        calm = wind.assume("calm", 0);
        windy = wind.assume("windy", 1);
    }
}
