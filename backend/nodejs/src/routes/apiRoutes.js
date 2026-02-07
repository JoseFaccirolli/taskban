const router = require("express").Router();
const UserController = require("../controller/userController");

// -------------------- USUARIO -------------------- //
router.post("/user", UserController.createUser);
router.get("/user", UserController.readAllUsers);
router.patch("/user/:userId", UserController.updateUser);
router.delete("/user/:userId", UserController.deleteUser);
router.post("/user/login", UserController.loginUser);

module.exports = router;