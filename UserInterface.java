import java.util.List;
import java.util.Scanner;

public class UserInterface {  // Класс взаимодействия с пользователем
    private Scanner myConsole;

    UserInterface() {
        myConsole = new Scanner(System.in);
    }

    Toy getNewToy() {  // Диалог Получения новой игрушки
        System.out.println("Введите через пробел: id Шанс_Выйгрыша Наименование_игрушки Количество (Напр: 1 5 Кубик 10)");
        String userInput = myConsole.nextLine();
        var prepareNewToy = userInput.split(" ");
        return new Toy(Integer.parseInt(prepareNewToy[0]), Integer.parseInt(prepareNewToy[1]), prepareNewToy[2], 
        Integer.parseInt(prepareNewToy[3]));
    }

    int UserDialog() {  // Главное меню, общение с пользователем
        while (true) {
            try {
                System.out.println("Введите команду: ");
                System.out.println("1. Добавить игрушки");
                System.out.println("2. Очистить магазин");
                System.out.println("3. Провести розыгрыш");
                System.out.println("4. Просмотреть каталог магазина");
                System.out.println("5. Изменить каталог");
                System.out.println("q - Выход");
                String inp = myConsole.nextLine();

                if (inp.toLowerCase().equals("q"))
                    return -1;

                int result = Integer.parseInt(inp);

                if (result < 1 || result > 5)
                    return 0;

                return result;
            }

            catch (Exception e){
                PrintErr("Wrong input, try again...");
            }
        }       
    }


    int getIntFromUser(String msg) {  // Получение числа от пользователя
        int result = 0;
        while (true) 
            try {
                System.out.println(msg);
                result = Integer.parseInt(myConsole.nextLine());
                break;
            }
            catch (Exception ex) {
                System.out.println("Wrong input, try again...");
            }          
        return result;
    }


    void PrintErr(String msg) {  // Вывести сообщение (об ошибке)
        System.out.println(msg);
    }


    void printCatalog(List<Toy> catalogToys) {  // Распечатать каталог
        int n = 1;
        for (Toy toy : catalogToys) {
            System.out.printf("%d: ", n);
            toy.letsPrint();
            ++n;
        }
    }
}
