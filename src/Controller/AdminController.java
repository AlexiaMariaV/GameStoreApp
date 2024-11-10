package Controller;

import Service.AdminService;
import java.util.Scanner;

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public void deleteGame(int gameId) {
        // Apelăm metoda de ștergere a jocului din AdminService
        adminService.deleteGame(gameId);
    }
}
