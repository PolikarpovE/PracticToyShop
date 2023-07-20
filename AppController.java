class AppController {
    private ToyShop toyShop;  // Магазин
    private UserInterface userInterface;  // Пользовательский интерфейс


    AppController() {  // Констурктор
        toyShop = new ToyShop();
        userInterface = new UserInterface();
    }

    void run() {  // Работа приложения
        while (true) {
            int command = userInterface.UserDialog();
            if (command < 0)
                break;
            if (command == 0)
                userInterface.PrintErr("Wrong input, try again...");
            else
                switch (command) {
                    case 1:
                        addToy();
                        break;
                    case 2:
                        clearShop();
                        break;
                    case 3:
                        int numTurs = userInterface.getIntFromUser("Please input number of tours: ");
                        if (toyShop.checkTur(numTurs))
                            getPrizes(numTurs);
                        else
                            userInterface.PrintErr("Invalid value, try again");
                        break;
                    case 4:
                        userInterface.printCatalog(toyShop.getCatalogToys());
                        break;
                    case 5:
                        userInterface.PrintErr("Catalog: ");
                        userInterface.printCatalog(toyShop.getCatalogToys());
                        int numToy = userInterface.getIntFromUser("Please input number of the toy:");
                        changeToy(numToy);
                        break;
                }
        }
    }

    void clearShop() {  // Обработка команды отчистки магазина
        toyShop.cleanCatalog();
        userInterface.PrintErr("Succesfull");
    }

    void addToy() {  // Добавляем игрушку
        Toy tmpToy = userInterface.getNewToy();
        if (toyShop.checkId(tmpToy.getId())) {
            toyShop.addToy(tmpToy);
            userInterface.PrintErr("Succesfull");
        }
        else
            userInterface.PrintErr("Wrong ID!!!");
    }

    void changeToy(int numToy) {  // Замена игрушки
        if (numToy < 0 || numToy > toyShop.getCatalogToys().size()) {
            userInterface.PrintErr("Wrong input!!!");
            return;
        }
        userInterface.printCatalog(toyShop.getCatalogToys());
        Toy tmpToy = userInterface.getNewToy();
        if (toyShop.checkIdChange(tmpToy.getId(), numToy)) {
            toyShop.changeToy(numToy, tmpToy);
            userInterface.PrintErr("Succesfull");
        }
        else
            userInterface.PrintErr("Wrong ID!!!");
    }

    void getPrizes(int numTurs) {  // Проводим розыгрыш
        toyShop.getPrize(numTurs);
        userInterface.PrintErr("Succesfull");
    }
}