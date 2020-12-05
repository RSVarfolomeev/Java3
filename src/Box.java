public class Box implements Comparable<Box> {
    private String name;
    private double cost;
    private double weight;

    public Box(String name, double cost, double weight) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public double getCost(){
        return cost;
    }

    public double getWeight(){
        return weight;
    }

    public double getCostWeight(){
        return cost/weight;
    }

    @Override
    public int compareTo(Box b) {
        if (cost/weight - b.getCostWeight() > 0) {
            return 1;
        } else if (cost/weight - b.getCostWeight() == 0){
            return 0;
        } else {
            return -1;
        }
    }
}
