let express = require('express');
let router = express.Router();
const usersController = require("../controllers/usersController");

const {validateUsername,validateNewReview,validateLogin, validateRegister, validateIdReview, validateReviewUpdate} = require("../middlewares/validation");
const requireLogin = require("../middlewares/auth");

/*
router.get("/dashboard",usersController.getDashboardPage) */

/**
 * @swagger
 * /users/login:
 *   get:
 *     summary: Renders the login page
 *     tags:
 *       - Users
 *     responses:
 *       200:
 *         description: Login page rendered
 */
router.get('/login', usersController.getLoginPage);





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
router.post('/login', validateLogin, usersController.loginUser);

/**
 * @swagger
 * /users/logout:
 *   get:
 *     summary: User logout
 *     tags:
 *       - Users
 *     responses:
 *       302:
 *         description: Logout successful, redirect
 */
router.get('/logout', usersController.logoutUser);

/**
 * @swagger
 * /users/register:
 *   get:
 *     summary: Renders the registration page
 *     tags:
 *       - Users
 *     responses:
 *       200:
 *         description: Registration page rendered
 */
router.get('/register', usersController.getRegisterPage);

/**
 * @swagger
 * /users/register:
 *   post:
 *     tags: [Users]
 *     summary: User registration
 *     description: Registers a new user with a username, email, and password.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - username
 *               - email
 *               - password
 *             properties:
 *               username:
 *                 type: string
 *                 description: The desired username.
 *                 example: test67
 *               email:
 *                 type: string
 *                 format: email
 *                 description: The user's email address.
 *                 example: test67@gmail.com
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
 *                     email:
 *                       type: string
 *                       example: test67@gmail.com
 *       400:
 *         description: Required parameters are missing or invalid.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Username, email, and password are required.
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
router.post('/register', validateRegister, usersController.registerUser);

/**
 * @swagger
 * /users/getMyReviews:
 *   get:
 *     summary: Recupera le recensioni dell'utente
 *     tags:
 *       - Users
 *     parameters:
 *       - in: query
 *         name: username
 *         schema:
 *           type: string
 *         required: true
 *         description: Username dell'utente di cui recuperare le recensioni
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 0
 *         required: false
 *         description: Numero della pagina per la paginazione
 *       - in: query
 *         name: limit
 *         schema:
 *           type: integer
 *           default: 12
 *         required: false
 *         description: Numero di recensioni per pagina
 *     responses:
 *       200:
 *         description: Lista delle recensioni dell'utente
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 reviews:
 *                   type: array
 *                   items:
 *                     type: object
 *                     example:
 *                       _id: "68323a6b839bc356f281d2a8"
 *                       movieId: "1000006"
 *                       movieTitle: "Oppenheimer"
 *                       username: "tester"
 *                       reviewTitle: "test"
 *                       reviewText: "test"
 *                       rating: 4
 *                       createdAt: "2025-05-24T21:30:19.548Z"
 *                       updatedAt: "2025-05-24T21:30:19.548Z"
 *                       __v: 0
 */
router.get('/getMyReviews', usersController.getMyReviews);

/**
 * @swagger
 * /users/newReview:
 *   post:
 *     summary: Add a new review
 *     tags:
 *       - Users
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               movieId:
 *                 type: string
 *               movieTitle:
 *                 type: string
 *               username:
 *                 type: string
 *               reviewTitle:
 *                 type: string
 *               reviewText:
 *                 type: string
 *               rating:
 *                 type: number
 *             required:
 *               - movieId
 *               - movieTitle
 *               - username
 *               - reviewTitle
 *               - reviewText
 *               - rating
 *     responses:
 *       200:
 *         description: Review added
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                 review:
 *                   type: object
 *                   properties:
 *                     movieId:
 *                       type: string
 *                     movieTitle:
 *                       type: string
 *                     username:
 *                       type: string
 *                     reviewTitle:
 *                       type: string
 *                     reviewText:
 *                       type: string
 *                     rating:
 *                       type: number
 *                     _id:
 *                       type: string
 *                     createdAt:
 *                       type: string
 *                     updatedAt:
 *                       type: string
 *                     __v:
 *                       type: integer
 *             example:
 *               message: "Review added successfully"
 *               review:
 *                 movieId: "1000006"
 *                 movieTitle: "Oppenheimer"
 *                 username: "tester"
 *                 reviewTitle: "Test api docs - Title"
 *                 reviewText: "Testing api docs - Review Text"
 *                 rating: 3
 *                 _id: "6853c93c2195e90c267ca1fb"
 *                 createdAt: "2025-06-19T08:24:28.364Z"
 *                 updatedAt: "2025-06-19T08:24:28.364Z"
 *                 __v: 0
 */
router.post('/newReview',validateNewReview,usersController.newReview);

/**
 * @swagger
 * /users/updateReview:
 *   post:
 *     tags:
 *       - Users
 *     summary: Update an existing review
 *     description: Update the title, text, or rating of an existing user review.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - _id
 *               - reviewTitle
 *               - reviewText
 *               - rating
 *             properties:
 *               _id:
 *                 type: string
 *                 example: "685289d651281884d0a8dbf1"
 *                 description: The unique identifier of the review.
 *               reviewTitle:
 *                 type: string
 *                 example: "Refreshing perspective"
 *                 description: The new title for the review.
 *               reviewText:
 *                 type: string
 *                 example: "Well-written and engaging, the movie exceeded expectations."
 *                 description: The new content for the review.
 *               rating:
 *                 type: integer
 *                 example: 5
 *                 description: The updated rating (1-5).
 *     responses:
 *       200:
 *         description: Review updated successfully.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Review updated successfully
 *                 review:
 *                   type: object
 *                   properties:
 *                     _id:
 *                       type: string
 *                       example: 685289d651281884d0a8dbf1
 *                     movieId:
 *                       type: string
 *                       example: "1000006"
 *                     movieTitle:
 *                       type: string
 *                       example: "Oppenheimer"
 *                     username:
 *                       type: string
 *                       example: tester
 *                     reviewTitle:
 *                       type: string
 *                       example: "Refreshing perspective"
 *                     reviewText:
 *                       type: string
 *                       example: "Well-written and engaging, the movie exceeded expectations."
 *                     rating:
 *                       type: integer
 *                       example: 5
 *                     createdAt:
 *                       type: string
 *                       format: date-time
 *                       example: 2025-06-18T09:41:42.261Z
 *                     updatedAt:
 *                       type: string
 *                       format: date-time
 *                       example: 2025-06-18T09:45:42.261Z
 *                     __v:
 *                       type: integer
 *                       example: 0
 *       400:
 *         description: Missing or invalid input fields.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: All fields are required.
 *       404:
 *         description: Review not found.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Review not found
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
router.post('/updateReview',validateReviewUpdate, usersController.updateReview);

/**
 * @swagger
 * /users/deleteReview:
 *   post:
 *     summary: Delete a review
 *     tags:
 *       - Users
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               _id:
 *                 type: string
 *             required:
 *               - _id
 *     responses:
 *       200:
 *         description: Review deleted
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *             example:
 *               message: "Review deleted successfully"
 */
router.post('/deleteReview',validateIdReview, usersController.deleteReview);

module.exports = router;
