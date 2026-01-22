const {catchedAsync} = require('../utils');
const actorsService = require('../services/actorsService');

const getActors= async (req, res) => {
    const actorName = req.params.name; // Usa req.params per ottenere il nome
    if (req.headers['x-requested-with'] === 'XMLHttpRequest'){
        try{
            const actorResponse = await actorsService.getActors(actorName);
            res.render('partials/actors', {layout:false , title: actorName, actor: actorResponse});
        }catch (err) {
            err.statusCode=500;
            err.message="Error fetching actor details";
            throw err;
        }
    }else{
        res.render('actors/actor', { title: actorName, actorName: actorName });
    }
}

module.exports = {
    getActors: catchedAsync(getActors),
}