/**
 * Express error-handling middleware.
 *
 * Sends a JSON response if the request is AJAX or expects JSON,
 * otherwise renders an error page.
 *
 * @function errorHandler
 * @param {Error} err - The error object, expected to have statusCode and message.
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 * @param {import('express').NextFunction} next - Express next middleware function.
 */
function errorHandler(err, req, res, next) {
    const {status, message} = err;
    if (req.xhr || req.headers['accept'] === 'application/json') {
        res.status(status).json({
            error: message,
            message: message,
        });
    } else {
        res.status(status).render('error', {
            title: 'Error',
            status: status,
            message: message,
        })
    }
}

module.exports = errorHandler;