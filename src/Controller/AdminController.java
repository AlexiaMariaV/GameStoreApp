package Controller;

import Service.AdminService;

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public void deleteGame(int gameId) {
        adminService.deleteGame(gameId);
    }

    public void applyDiscountToGame(int gameId, float discountPercentage) {
        adminService.applyDiscountToGame(gameId, discountPercentage);
    }
}
