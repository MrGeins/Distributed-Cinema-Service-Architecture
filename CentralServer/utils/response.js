const response = (res, status= 500, data)=>{
    res.status(status).json(data)
}

module.exports = response;