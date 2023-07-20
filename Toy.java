public class Toy implements Comparable {  // Класс игрушек
    private int id;
    private int luckyWeight;
    private int num;
    private String name;

    Toy(int id, int luckyWeight, String name, int num) {  // Конструктор
        this.id = id;
        this.luckyWeight = luckyWeight;
        this.name = name;
        this.num = num;
    }
    
    public int getId() {
        return id;
    }

    public int getLuckyWeight() {
        return luckyWeight;
    }

    public String getName() {
        return name;
    }

    public void getOnce() {
        --num;
    }

    public int getNum() {
        return num;
    }

    // Сравнение игрушек по частоте выпадения, на случай необходимости определения приоретета в очереди
    @Override
    public int compareTo(Object another) {  
        Toy alienToy = (Toy)another;
        if (this.luckyWeight > alienToy.luckyWeight)
            return -1;
        else if (this.luckyWeight == alienToy.luckyWeight)
            return 0;
        return 1;       
    }

    public void letsPrint() {  // Напечать состояние игрушки
        System.out.printf("id: %d; LuckyWeight: %d; Name: %s; Count: %d\n", id, luckyWeight, name, num);
    }

    @Override
    public String toString() { // Преобразовать в строку
        return name;
    }
}
