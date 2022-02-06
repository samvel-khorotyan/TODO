package todo;

import todo.manager.ToDoManager;
import todo.manager.UserManager;
import todo.model.ToDo;
import todo.model.ToDoStatus;
import todo.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ToDoMain implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    public static UserManager userManager = new UserManager();
    public static ToDoManager toDoManager = new ToDoManager();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static User currentUser = null;

    public static void main(String[] args) {

        boolean isRun = true;

        while (isRun) {

            Commands.printMainCommands();

            int command;

            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }

            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case REGISTER:
                    registerUser();
                    break;
                case LOGIN:
                    loginUser();
                    break;
                default:
                    System.out.println();
                    System.err.println("INVALID COMMAND!!!");
            }
        }
    }

    private static void registerUser() {
        System.out.println("PLEASE INPUT USER DATA--NAME,SURNAME,EMAIL,PASSWORD");

        try {
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            User userFromStorage = userManager.getByEmail(userDataArr[2]);
            if (userFromStorage == null) {

                User user = new User();

                user.setName(userDataArr[0]);
                user.setSurname(userDataArr[1]);
                user.setEmail(userDataArr[2]);
                user.setPassword(userDataArr[3]);
                if (userManager.register(user)) {
                    System.out.println("USER WAS SUCCESSFULLY ADDED");
                } else {
                    System.err.println("SOMETHING WENT WRONG!");
                }
            } else {
                System.err.println("user already exists");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("WRONG DATA!");
        }
    }

    private static void loginUser() {
        System.out.println("PLEASE INPUT EMAIL,PASSWORD");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = userManager.getByEmailAndPassword(loginArr[0], loginArr[1]);
            if (user != null) {
                currentUser = user;
                loginSuccess();
            } else {
                System.err.println("WRONG EMAIL OR PASSWORD");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("WRONG DATA!");
        }
    }

    private static void loginSuccess() {
        System.out.println("WELCOME " + currentUser.getName() + "!");

        boolean isRun = true;

        while (isRun) {

            Commands.printUserCommands();

            int command;

            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }

            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_TODO:
                    addNewToDO();
                    break;
                case MY_ALL_LIST:
                    printToDos(toDoManager.getAllToDosByUser(currentUser.getId()));
                    break;
                case MY_TODO_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.TODO));
                    ;
                    break;
                case MY_IN_PROGRESS_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.IN_PROGRESS));
                    ;
                    break;
                case MY_FINISHED_LIST:
                    printToDos(toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.FINISHED));
                    ;
                    break;
                case CHANGE_TODO_STATUS:
                    changeToDoStatus();
                    break;
                case DELETE_TODO:
                    deleteToDo();
                    break;
                default:
                    System.err.println("WRONG COMMAND!");
            }
        }
    }

    private static void printToDos(List<ToDo> allToDosByUser) {
        for (ToDo toDo : allToDosByUser) {
            System.out.println(toDo);
        }
    }

    private static void addNewToDO() {
        System.out.println("PLEASE INPUT TITLE,DEADLINE(yyyy-MM-dd HH:mm:ss)");
        String toDoDataStr = scanner.nextLine();
        String[] split = toDoDataStr.split(",");
        ToDo toDo = new ToDo();
        try {
            String title = split[0];
            toDo.setTitle(title);
            try {
                if (split[1] != null) {
                    toDo.setDeadline(sdf.parse(split[1]));
                }
            } catch (IndexOutOfBoundsException e) {
            } catch (ParseException e) {
                System.err.println("PLEASE INPUT DATE BY THIS FORMAT:yyyy-MM-dd HH:mm:ss");
            }
            toDo.setStatus(ToDoStatus.TODO);
            toDo.setUser(currentUser);
            if (toDoManager.create(toDo)) {
                System.out.println("TODO WAS ADDED");
            } else {
                System.err.println("SOMETHING WENT WRONG");
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("WRONG DATA");
        }
    }

    private static void deleteToDo() {
        System.out.println("PLEASE CHOOSE TODO FROM LIST");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            toDoManager.delete(id);
        } else {
            System.err.println("WRONG ID");
        }
    }

    private static void changeToDoStatus() {
        System.out.println("-PLEASE CHOOSE TODO FROM LIST-");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("PLEASE CHOOSE STATUS");
            System.out.println(Arrays.toString(ToDoStatus.values()));
            ToDoStatus status = ToDoStatus.valueOf(scanner.nextLine());
            if (toDoManager.update(id, status)) {
                System.out.println("STATUS WAS CHANGED");
            } else {
                System.err.println("SOMETHING WENT WRONG");
            }
        } else {
            System.err.println("WRONG ID");
        }
    }
}