/** @type {import('mongoose').Model<any>} */
const User = require('../models/users');
const bcrypt = require('bcrypt');

/**
 * Authenticates a user by username and password.
 * @param {Object} req - Express request object.
 * @param {Object} res - Express response object.
 * @returns {Promise<void>}
 */
const loginUser = async (req,res) => {
    try {
        const {username, password} = req.body;

        if (!username || !password) {
            return res.status(400).json({ message: "All fields are required." });
        }

        const user = await User.findOne({ username });

        if (!user) {
            return res.status(404).json({ message: "User not found" });
        }

        console.log("User: " + user);
        //Check password using bcrypt
        const isMatch = await bcrypt.compare(password, user.password);
        if (isMatch) {
            return res.status(200).json({
                message: "Login successful",
                userData: {
                    username: user.username,
                    role: user.role
                }
            });
        } else {
            return res.status(401).json({message: "Incorrect password"});
        }
    } catch (error) {
        return res.status(500).json({ message: "An internal server error occurred. Please try again later." });
    }
}
/**
 * Registers a new user.
 *
 * @param {Object} req - Express request object.
 * @param {Object} res - Express response object.
 * @returns {Promise<void>}
 */
const registerUser = async (req,res) => {
    try {
        let {username, password, role} = req.body;
        if (!username || !password) {
            return res.status(400).json({ message: "Username and password are required." });
        }

        const hashedPassword = await bcrypt.hash(password, 10);


        const newUser = new User({
            username: username,
            password: hashedPassword,
            role: role || 'fan'
        });
        const savedUser = await newUser.save();

        res.status(201).json({
            message: 'Registration successful',
            userData: { username: savedUser.username, role: savedUser.role }
        });

    } catch (error) {
        if (error.code === 11000) {
            return res.status(409).json({ message: "Username already exists." });
        }
        console.log(error)
        res.status(500).json({ message: "Internal server error." });
    }
};

/**
 * Retrieves user data by username.
 *
 * @param {Object} req - Express request object.
 * @param {Object} res - Express response object.
 * @returns {Promise<void>}
 */
const getUserData = async  (req, res) => {
    try {
        const { username } = req.body;
        if (!username) {
            return res.status(400).json({ message: "Username is required." });
        }
        const user = await User.findOne({ username: username });
        if (!user) {
            return res.status(404).json({ message: "User not found" });
        }
        res.status(200).json({ message: "User data", username: user.username});
    }
    catch (error) {
        res.status(500).json({ message: "An internal server error occurred. Please try again later." });
        console.error(error);
    }
}

/**
 * Queries users based on the provided filter.
 *
 * @param {Object} body - The filter object for querying users.
 * @returns {Promise<Array<Object>>} Array of user documents with _id set to null.
 */
function query(body) {
    return new Promise((resolve, reject) => {
        User.find(body)
            .then(results => {
                results.forEach((user) => {
                    user._id = null;
                });
                resolve(results);
            })
            .catch(error => {
                reject(error);
            });
    });
}

module.exports = {
    loginUser,
    registerUser,
    getUserData,
    query
};

