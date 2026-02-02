const UserService = require("../service/userService");
module.exports = class UserController {
    static async createUser(req, res) {
        const { name, email, password } = req.body;

        if (!name || !email || !password) {
            return res.status(400).json({
                error: true,
                message: "Missing required filds."
            });
        }

        try {
            await UserService.createUser(name, email, password);
            return res.status(201).json({
                error: false,
                message: "User created sucessfully."
            });
        } catch (error) {
            return res.status(error.status || 500).json({
                error: true,
                message: error.message || "Internal Server Error."
            });
        }
    }
    
    static async readAllUsers(req, res) {
        try {
            const users = await UserService.readAllUsers();
            return res.status(200).json({
                error: false,
                message: "Users fetched successfully.",
                data: users
            });
        } catch (error) {
            return res.status(error.status || 500).json({
                error: true,
                message: error.message || "Internal server error."
            });
        }
    }
}