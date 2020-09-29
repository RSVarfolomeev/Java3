package HomeWork;

import java.util.ArrayList;

public class Box<T> {

    private final ArrayList<T> arrayList = new ArrayList<>();

    public void add(T fruit) {
        arrayList.add(fruit);
    }

    public float getWeight() {
        float sumWeight = 0;
        Fruit obj;
        for (int i = 0; i < arrayList.size(); i++){
            obj = (Fruit) arrayList.get(i);
            sumWeight += obj.getWeightF();
        }
        return sumWeight;
    }

    public boolean compare(Box otherBox) {
        return (getWeight() == otherBox.getWeight());
    }

    public void addToBox(Box otherBox) {
        for (int i = 0; i < arrayList.size(); i++){
            otherBox.add(arrayList.get(i));
        }
        arrayList.clear();
    }
}