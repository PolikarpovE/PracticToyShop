import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ToyShop {  // Магазин игрушек
    private List<Toy> catalogToys;

    ToyShop() {
        catalogToys = new ArrayList<>();
    }

    List<Toy> getCatalogToys() {  // Получить каталог
        return catalogToys;
    }

    void cleanCatalog() {  // Отчистить каталог
        catalogToys.clear();
    }

    void addToy(Toy newToy) {  // Внесение новой игрушки
        catalogToys.add(newToy);
    }

    void changeToy(int numToy, Toy newToy) {  // Замена игрушки
        catalogToys.set(numToy - 1, newToy);
    }

    int getTotalLuckyWeight() {  // Получаем суммарную частоту выпадения
        int totalWeight = 0;
        int sizeCatalog = catalogToys.size();
        for (int i = 0; i < sizeCatalog; ++i) {
            totalWeight += catalogToys.get(i).getLuckyWeight();
        }
        return totalWeight;
    }

    List<Integer> getLuckyList() {  // Разбиваем каталог на частоты выпадения
        List<Integer> luckyList = new ArrayList<>();
        int sizeCatalog = catalogToys.size();
        int totalWeight = getTotalLuckyWeight();
        int offset = 0;
        for (int i = 0; i < sizeCatalog; ++i) {
            luckyList.add(offset + (catalogToys.get(i).getLuckyWeight() * 100) / totalWeight);
            offset = luckyList.get(i);
        }
        luckyList.set(sizeCatalog - 1, 99);
        return luckyList;
    }


    boolean checkTur(int numTurs) {  // Проверка на возможность проведения указанного количества розыгрышей
        int sum = 0;
        for (Toy toy : catalogToys) {
            sum += toy.getNum();
        }
        return sum >= numTurs;
    }


    boolean checkId(int tryId) {  // Проверка на свободный id
        for (Toy toy : catalogToys) 
            if (toy.getId() == tryId)
                return false;
        return true;
    }

    boolean checkIdChange(int tryId, int numToy) {  // Проверка на свободный id при редактировании
        int n = 0;
        for (Toy toy : catalogToys) {
            if (toy.getId() == tryId && n != numToy)
                return false;
            ++n;
        }
        return true;
    }
    

    void saveStat(HashMap<Toy, Integer> stat, List<Toy> prizesList) throws IOException { // Вывести протокол розыгрыша и сохранить в файл
        Writer writer = new FileWriter("stat.txt", false);
            for (int i = 0; i < prizesList.size(); ++i) {
                String line = String.format("in round %d selected toy: %s", i + 1, prizesList.get(i).toString());
                System.out.println(line);
                writer.write(line + "\n");
            }
            System.out.println("Statistics: ");
            for (var el : stat.entrySet()) {
                double pers = ((double)el.getValue() / prizesList.size() * 100);
                String line = String.format("toy: %s:, count: %d; percent: %.2f%%", el.getKey().toString(), el.getValue(), pers);
                System.out.println(line);
                writer.write(line  + "\n");
            }
        writer.close();
        writer.flush();     
    }


    void getPrize(int numTurs) {  // Проводим розыгрыш
        var luckyList = getLuckyList();
        Random rnd = new Random();
        HashMap<Toy, Integer> stat = new HashMap<Toy, Integer>();
        List<Toy> prizesList = new ArrayList<>();

        for (int z = 0; z < catalogToys.size(); ++z)
            stat.put(catalogToys.get(z), 0);

        for (int i = 0; i < numTurs; ++i) {
            int newVal = rnd.nextInt(99);
            int currentToy = 0;
            for (Integer item : luckyList) {
                if (newVal <= item) {
                    while (catalogToys.get(currentToy).getNum() <= 0)
                        if (currentToy < catalogToys.size() - 1)
                            ++currentToy;
                        else
                            currentToy = 0;
                    prizesList.add(catalogToys.get(currentToy));
                    catalogToys.get(currentToy).getOnce();
                    stat.put(catalogToys.get(currentToy), stat.get(catalogToys.get(currentToy)) + 1);                     
                    break;
                }    
                ++currentToy;    
            }
        }
        try {
        saveStat(stat, prizesList);
        }
        catch (Exception ex) {
            System.out.println("Can't save statistics");
        }
    }
}
