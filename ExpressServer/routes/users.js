let express = require('express');
let router = express.Router();
const usersController = require('../controllers/usersController');

router.post('/', function(req, res) {
    res.send('respond with a resource');
});

/**
 * @swagger
 * /users/login:
 *   post:
 *     summary: User login
 *     tags: [Users]
 *     description: Authenticates a user with the provided username and password.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - username
 *               - password
 *             properties:
 *               username:
 *                 type: string
 *                 format: username
 *                 description: User's username
 *                 example: test66
 *               password:
 *                 type: string
 *                 description: User's password
 *                 example: mySecretPassword
 *     responses:
 *       200:
 *         description: Login successful.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Login successful
 *                 userData:
 *                   type: object
 *                   properties:
 *                     username:
 *                       type: string
 *                       example: test66
 *       400:
 *         description: Username and password are required.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Username and password are required.
 *       401:
 *         description: Invalid credentials.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Invalid username or password.
 *       500:
 *         description: Internal server error.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: An internal server error occurred. Please try again later.
 */
router.post('/login', usersController.loginUser);

/**
 * @swagger
 * /users/register:
 *   post:
 *     tags: [Users]
 *     summary: User registration
 *     description: Registers a new user with a username and password.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - username
 *               - password
 *             properties:
 *               username:
 *                 type: string
 *                 description: The desired username.
 *                 example: test67
 *               password:
 *                 type: string
 *                 description: The user's chosen password.
 *                 example: mySecurePassword
 *     responses:
 *       200:
 *         description: Registration successful.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Registration successful
 *                 userData:
 *                   type: object
 *                   properties:
 *                     username:
 *                       type: string
 *                       example: test67
 *       400:
 *         description: Required parameters are missing or invalid.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Username and password are required.
 *       409:
 *         description: User already exists.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: User already exists.
 *       500:
 *         description: Internal server error.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: An internal server error occurred. Please try again later.
 */
router.post('/register', usersController.registerUser);

module.exports = router;