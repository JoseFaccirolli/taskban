const bcrypt = require("bcrypt");
const connect = require("../database/connect");
const crypto = require("crypto");
const SALT_ROUNDS = 10; 
module.exports = class UserController {
    static async createUser(name, email, password) {
        if(!email.includes("@") || !email.includes(".")) {
            throw {status: 400, message: "Invalid email format."}
        }
        if(password.length < 6) {
            throw {status: 400, message: "Invalid password: minimum length is 6 characters."}
        }

        const hashedPassword = await bcrypt.hash(password, SALT_ROUNDS);
        const userId = crypto.randomUUID();
        const query = `INSERT INTO users (user_id, name, email, password) VALUES (?, ?, ?, ?)`;
        const values = [userId, name, email, hashedPassword];

        try {
            await connect.execute(query, values);
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

    static async updateUser(userId, name, email, password) {
        const updates = [];
        const values = [];

        if (name) {
            updates.push("name = ?");
            values.push(name);
        }
        if (email) {
            if (!email.includes("@") || !email.includes(".")) {
                throw { status: 400, message: "Invalid email format." }
            }
            updates.push("email = ?");
            values.push(email);
        }
        if (password) {
            if (password.length < 6) {
                throw { status: 400, message: "Invalid password: minimum length is 6 characters." }
            }
            const hashedPassword = await bcrypt.hash(password, SALT_ROUNDS);
            updates.push("password = ?");
            values.push(hashedPassword);
        }

        values.push(userId);
        const query = `UPDATE users SET ${updates.join(", ")} WHERE user_id = ?`;
        
        try {
            const [result] = await connect.execute(query, values);
            if (result.affectedRows === 0) {
                throw { status: 404, message: "User not found." }
            }
            return result;
        } catch (error) {
            if (error.code === "ER_DUP_ENTRY") {
                throw { status: 209, message: "Email already registered." }
            }
            if (error.status) throw error;
            throw { status: 500, message: "Internal server error." }
        }
    }

    static async deleteUser(userId) {
        const query = `DELETE FROM users WHERE user_id = ?`;

        try {
            const [result] = await connect.execute(query, [userId]);
            if (result.affectedRows === 0) {
                throw { status: 404, message: "User not found." }
            }
            return result;
        } catch (error) {
            if (error.status) throw error;
            throw { status: 500, message: "Internal server error." }            
        }
    }
}