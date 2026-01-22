/**
 * Middleware to ensure the user is logged in.
 *
 * Checks for the presence of a 'userData' cookie.
 * If not present, responds with a 403 error.
 *
 * @function requireLogin
 * @param {import('express').Request} req - Express request object
 * @param {import('express').Response} res - Express response object
 * @param {import('express').NextFunction} next - Express next middleware function
 * @returns {void}
 */
const requireLogin = (req, res, next) => {
    const cookies = req.cookies?.userData;
    if (!cookies) {
        const error = new Error('Access denied. Login first!');
        error.status = 403;
        return next(error);
    }
    next()
}

module.exports = {requireLogin}