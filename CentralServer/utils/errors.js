//Error for problems with client requests
class ClientError extends Error {
    constructor(message, statusCode=400, details="") {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }
}
class authError extends ClientError {
    constructor(message, statusCode=401, details="") {
        super(message, statusCode, details);
    }
}

/*
* Richieste errata parte del utente
* Richiesta di autenticazioni con credenziali sbagliate
* Richiesta di dati non validi
*
*
*/
module.exports = {
    ClientError,
    authError,
}