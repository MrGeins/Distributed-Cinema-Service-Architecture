const resError = (res, status= 400, message)=>{
    res.status(status).json({
        error: true,
        message: message,
    });
};


module.exports = resError;