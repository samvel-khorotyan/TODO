package todo;

public interface Commands {

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_NEW_TODO = 1;
    int MY_ALL_LIST = 2;
    int MY_TODO_LIST = 3;
    int MY_IN_PROGRESS_LIST = 4;
    int MY_FINISHED_LIST = 5;
    int CHANGE_TODO_STATUS = 6;
    int DELETE_TODO = 7;

    static void printMainCommands() {
        System.out.println("PLEASE INPUT " + EXIT + " FOR EXIT");
        System.out.println("PLEASE INPUT " + LOGIN + " FOR LOGIN");
        System.out.println("PLEASE INPUT " + REGISTER + " FOR REGISTER");
    }

    static void printUserCommands() {
        System.out.println("PLEASE INPUT " + LOGOUT + " FOR LOGOUT");
        System.out.println("PLEASE INPUT " + ADD_NEW_TODO + " FOR ADD NEW TODO");
        System.out.println("PLEASE INPUT " + MY_ALL_LIST + " FOR MY_ALL_LIST");
        System.out.println("PLEASE INPUT " + MY_TODO_LIST + " FOR MY TODO LIST");
        System.out.println("PLEASE INPUT " + MY_IN_PROGRESS_LIST + " FOR MY IN PROGRESS LIST");
        System.out.println("PLEASE INPUT " + MY_FINISHED_LIST + " FOR MY FINISHED LIST");
        System.out.println("PLEASE INPUT " + CHANGE_TODO_STATUS + " FOR CHANGE_TODO_STATUS");
        System.out.println("PLEASE INPUT " + DELETE_TODO + " FOR DELETE_TODO");
    }
}
