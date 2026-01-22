const moviesService = require("../services/moviesService");
const catchedAsync = require("../utils");
const response = require("../utils/response");
const searchService = require("../services/searchService");

const SIZES={
    "movie_size": 12,
    "actor_size": 20,
}

/**
 * Controller to render the home page with a carousel of popular movies.
 *
 * Fetches movies sorted by popularity and renders the index view.
 * Throws a 500 error if fetching fails.
 *
 * @async
 * @function getHomePage
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching movies fails.
 */
const getHomePage = async (req, res)=>{
    res.render('index', {title:"Welcome to our website"} /*{carouselMovieList: carouselResponse}*/)
}

const getHomePageCarousel = async (req, res) => {
    let carouselResponse= {};
    try {
        carouselResponse = await moviesService.getMovies({Sort: "Popularity", Order: "ASC"});
    } catch (err) {
        err.status = 500;
        throw err;
    }

    res.render('partials/carousel', {layout:false, carouselMovieList: carouselResponse})
}

/**
 * Controller to handle movie and actor search requests.
 *
 * Handles both standard and AJAX search requests. Renders search results or partials based on request type.
 * Throws a 500 error if fetching or rendering fails.
 *
 * @async
 * @function searchMovies
 * @param {import('express').Request} req - Express request object, expects `searchValue` in query.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If search or rendering fails.
 */
const searchMovies = async (req, res)=> {
    const {searchValue} = req.query;
    if (!(req.headers['x-requested-with'] === 'XMLHttpRequest')) {
        res.render('search/search', {title: 'search', search:searchValue, movie_hasMore:true, actor_hasMore:true});
    } else {
        const {page, type}=req.query;
        let data, view, options, hasMore
        switch (type) {
            case "movie":
                view = 'partials/movies_list';
                try{
                    data = await searchService.getMovies(searchValue,page, SIZES.movie_size);
                }
                catch (err){
                    err.status = 500;
                    throw err;
                }
                data = data.data;
                options = {layout:false, movie_list:data};
                hasMore = data.length >= SIZES.movie_size;
                break;
            case "actor":
                view = 'partials/actors_list';
                try {
                    data= await searchService.getActors(searchValue, page, SIZES.actor_size);
                }
                catch (err) {
                    err.status = 500;
                    throw err;
                }
                data = data.data;
                options = {layout:false, actors:data};
                hasMore = data.length >= SIZES.actor_size;
                break;
            default:
                throw new Error("Invalid type");
        }
        res.render(view,options,(err,html)=>{
            if(err){
                err.status = 500;
                err.message="Error rendering movies list";
                throw err;
            }
            response(res,200,{html, hasMore})
        })
    }
}

module.exports = {
    /*searchMovies: catchedAsync(searchMovies),
    getHomePage: catchedAsync(getHomePage),
    getHomePageCarousel: catchedAsync(getHomePageCarousel),*/
    moviesController: require('./moviesController'),
    actorsController: require('./actorsController'),
    usersController: require('./usersController'),
}