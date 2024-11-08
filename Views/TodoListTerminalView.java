package Views;

import Services.TodoListService;
import entities.TodoList;

import java.util.Scanner;

public class TodoListTerminalView implements views.TodoListView {
    public static Scanner scanner = new Scanner(System.in);
    private final TodoListService todoListService;

    public TodoListTerminalView(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @Override
    public void run() {
        showMainMenu();
    }

    public void showMainMenu() {
        boolean isRunning = true;
        while (isRunning) {
            showTodoList();
            System.out.println("MENU : ");
            System.out.println("1. Tambah");
            System.out.println("2. Hapus");
            System.out.println("3. Edit");
            System.out.println("4. Keluar");

            String selectedMenu = input("Pilih");

            switch (selectedMenu) {
                case "1":
                    showMenuAddTodoList();
                    break;
                case "2":
                    showMenuRemoveTodoList();
                    break;
                case "3":
                    showMenuEditTodoList();
                    break;
                case "4":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Pilih menu dengan benar");
            }
        }
    }

    public void showMenuAddTodoList() {
        System.out.println("MENAMBAH TODO LIST");
        var todo = input("Todo (x jika batal)");
        if (todo.equals("x")) {
            return;
        }
        todoListService.addTodoList(todo);
    }

    public void showMenuRemoveTodoList() {
        System.out.println("MENGHAPUS TODO LIST");
        var number = input("Nomor yang dihapus (x jika batal)");
        if (number.equals("x")) {
            return;
        }
        try {
            boolean success = todoListService.removeTodoList(Integer.parseInt(number));
            if (!success) {
                System.out.println("Gagal menghapus todo list: " + number);
            }
        } catch (NumberFormatException e) {
            System.out.println("Nomor yang dimasukkan tidak valid.");
        }
    }

    public void showMenuEditTodoList() {
        System.out.println("MENGEDIT TODO LIST");
        String selectedTodo = input("Masukkan nomor todo (x jika batal)");
        if (selectedTodo.equals("x")) {
            return;
        }
        String newTodo = input("Masukkan todo yang baru (x jika batal)");
        if (newTodo.equals("x")) {
            return;
        }
        try {
            boolean isEditTodoSuccess = todoListService.editTodoList(Integer.parseInt(selectedTodo), newTodo);
            if (isEditTodoSuccess) {
                System.out.println("Berhasil mengedit todo");
            } else {
                System.out.println("Gagal mengedit todo");
            }
        } catch (NumberFormatException e) {
            System.out.println("Nomor yang dimasukkan tidak valid.");
        }
    }

    public String input(String info) {
        System.out.print(info + " : ");
        return scanner.nextLine();
    }

    public void showTodoList() {
        System.out.println("TODO LIST");
        TodoList[] todos = todoListService.getTodoList();
        for (int i = 0; i < todos.length; i++) {
            TodoList todo = todos[i];
            if (todo != null) {
                System.out.println((i + 1) + ". " + todo.getTodo());
            }
        }
    }
}