const bcrypt = require("bcrypt");
const connect = require("../database/connect");
const SALT_ROUNDS = 10
module.exports = class UserController {
    static async createUser(name, email, password) {
        if(!email.includes("@") || !email.includes(".")) {
            throw {status: 400, message: "Invalid email format."}
        }
        if(password.length < 8) {
            throw {status: 400, message: "Invalid password: minimum length is 8 characters."}
        }

        const hashedPassword = await bcrypt.hash(password, SALT_ROUNDS);
        const query = `INSERT INTO users (name, email, password) VALUES (?, ?, ?)`;
        const values = [name, email, hashedPassword];

        try {
            const [rows] = await connect.execute(query, values);
            return rows;
        } catch (error) {
            if (error.code === "ER_DUP_ENTRY") {
                throw {status: 409, message: "Email already registered."}
            }
            throw {status: 500, message: "Internal server error."}
        }
    }

    static async readAllUsers(){
        const query = `SELECT user_id, name, email FROM users`;
        try {
            const [users] = await connect.execute(query);
            return users;
        } catch (error) {
            throw {status: 500, message: "Internal server error."}
        }
    }
}