const UserService = require("../service/userService");
module.exports = class UserController {
    static async createUser(req, res) {
        const { name, email, password } = req.body;

        if (!name || !email || !password) {
            return res.status(400).json({
                error: true,
                message: "Missing required filds"
            });
        }

        try {
            const rows = await UserService.createUser(name, email, password);
            console.log(rows);
            return res.status(201).json({
                error: false,
                message: "User created sucessfully"
            })
        } catch (error) {
            console.log("controller:", error); // ----------- TEMP -----------
            return res.status(error.status || 500).json({
                error: true,
                message: error.message || "Internal Server Error"
            });
        }
    }
}