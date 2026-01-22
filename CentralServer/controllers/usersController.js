const {catchedAsync} = require("../utils");
const usersService = require("../services/usersService");

let bcrypt = require('bcrypt');
const {response} = require('../utils/response');
const req = require("express/lib/request");
const SIZES={
    "review": 12,
}

/**
 * Renders the login page for users.
 *
 * @async
 * @function getLoginPage
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 */
const getLoginPage = async (req,res)=> {
    res.render('users/login', { title: 'Login' });
}

/**
 * Handles user login by verifying credentials and setting a cookie.
 *
 * @async
 * @function loginUser
 * @param {import('express').Request} req - Express request object, expects `username` and `password` in body.
 * @param {import('express').Response} res - Express response object.
 */
const loginUser = async (req,res)=> {
    const { username, password } = req.body;
    try {
        const response = await usersService.login(username, password);
        const userData = response.data.userData;

        req.session.user = userData;

        res.cookie("userData", JSON.stringify(userData), {
            httpOnly: false,
            secure: false,
            maxAge: 24 * 60 * 60 * 1000,
        });

        res.redirect('/');
    } catch (err) {
        res.render('users/login', { title: 'Login', error: err.message });
    }
}
/**
 * Renders the registration page for new users.
 *
 * @async
 * @function getRegisterPage
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 */
const getRegisterPage= async (req,res)=> {
    res.render('users/register', { title: 'Registration' });
}
/**
 * Handles user registration, hashes the password, and sets a cookie.
 *
 * @async
 * @function registerUser
 * @param {import('express').Request} req - Express request object, expects `username`, `password` in body.
 * @param {import('express').Response} res - Express response object.
 */
const registerUser = async (req,res)=> {
    const { username, password, role } = req.body;
    try {
        //const hashedPassword = await bcrypt.hash(password, 10);
        console.log("PASSWD: " + password)
        const response = await usersService.register(username, password, role);
        const userData = response.data.userData;

        req.session.user = {
            username: userData.username,
            role: userData.role
        };

        res.cookie("userData", JSON.stringify(userData), {
            httpOnly: false,
            maxAge: 24 * 60 * 60 * 1000,
        });

        console.log("Registration done! ")
        res.redirect('/');
    } catch (err) {
        res.render('users/register', { title: 'Registration', error: err.message });
    }
}

/**
 * Logs out the user by clearing the authentication cookie.
 *
 * @async
 * @function logoutUser
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 */
const logoutUser= async (req,res)=> {
    res.clearCookie('userData');
    res.redirect('/');
}



/**
 * Renders the user dashboard with user data and their reviews.
 *
 * @async
 * @function getDashboardPage
 * @param {import('express').Request} req - Express request object, expects `userData` cookie.
 * @param {import('express').Response} res - Express response object.
 */
/*const getDashboardPage = async (req,res)=> {
    const cookieData = req.cookies.userData;
    const userData = cookieData ? JSON.parse(decodeURIComponent(cookieData)) : null;
    let reviews = [];

    if(userData && userData.username){
        try{
            const response = await usersService.getMyReviews(userData.username, 0, 12);
            reviews = response.data.reviews || [];
        }catch(err){
            err.statusCode=500;
            throw err;
        }
    }
    res.render('users/dashboard', {title: 'Dashboard', userData, reviews});

} */

/**
 * Retrieves reviews written by a specific user and renders them as a partial.
 *
 * @async
 * @function getMyReviews
 * @param {import('express').Request} req - Express request object, expects `username`, `page`, and `size` in body.params.
 * @param {import('express').Response} res - Express response object.
 */
const getMyReviews = async (req,res)=> {

    const { username, page=0, size=SIZES.review } = req.body.params;
    console.log(req.body.params,);
    let hasMore, response;
    try {
        response =  await usersService.getMyReviews(username, page, size);

        hasMore = response.data.reviews.length >= SIZES.review;
    }catch (err){
        err.statusCode=500;
        throw err;
    }

    return res.render('partials/reviews', { layout: false, title: 'Dashboard', reviews: response.data.reviews, hasMore });
}

/**
 * Adds a new review for a movie.
 *
 * @async
 * @function newReview
 * @param {import('express').Request} req - Express request object, expects `movieId`, `movieTitle`, `username`, `reviewTitle`, `reviewText`, and `rating` in body.
 * @param {import('express').Response} res - Express response object.
 */
const newReview = async (req,res)=> {
    const { movieId, movieTitle, username, reviewTitle, reviewText, rating } = req.body;
    let response;
    try{
        response = await usersService.newReview(movieId, movieTitle, username, reviewTitle, reviewText, rating);
        response = response.data;
    }catch(err){
        err.statusCode=500;
        throw err;
    }
    return res.status(200).json(response);
}

/**
 * Updates an existing review.
 *
 * @async
 * @function updateReview
 * @param {import('express').Request} req - Express request object, expects `_id`, `reviewTitle`, `reviewText`, and `rating` in body.
 * @param {import('express').Response} res - Express response object.
 */
const updateReview = async (req,res)=> {
    const { _id, reviewTitle, reviewText, rating }= req.body;
    let response;
    try {
        response =  await usersService.updateReview(_id, reviewTitle, reviewText, rating);
    } catch (err) {
        err.statusCode=500;
        throw err;
    }
    response(res, 200, response.data);
}

/**
 * Deletes a review by its ID.
 *
 * @async
 * @function deleteReview
 * @param {import('express').Request} req - Express request object, expects `_id` in body.
 * @param {import('express').Response} res - Express response object.
 */
const deleteReview = async (req,res)=> {
    const { _id } = req.body;
    let response;
    try{
        response = await usersService.deleteReview(_id);
    }catch(err){
        err.statusCode=500;
        throw err;
    }
    response(res,200,response.data);
}

module.exports = {
    getLoginPage: catchedAsync(getLoginPage),
    loginUser: catchedAsync(loginUser),
    getRegisterPage: catchedAsync(getRegisterPage),
    registerUser: catchedAsync(registerUser),
    logoutUser: catchedAsync(logoutUser),
    /*getDashboardPage: catchedAsync(getDashboardPage),*/
    getMyReviews: catchedAsync(getMyReviews),
    newReview: catchedAsync(newReview),
    updateReview: catchedAsync(updateReview),
    deleteReview: catchedAsync(deleteReview),
}