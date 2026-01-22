let express = require('express');
let router = express.Router();
let rottenController = require('../controllers/rottenController');



//router.get('/', getHomePage); //TO DO: HomePage function

router.post('/', async (req, res) => {
  try {
    const reviews = await rottenController.listOfRottenReviews();
    res.json({reviews});
  }
  catch (err) {
    res.status(500).json({ error: err.message });
  }
})





router.get('/search', async (req, res) => {
  try {
    const reviews = await rottenController.search(req.query);
    res.json({reviews});
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
}); //TO DO: Search Reviews function

/*router.post('/rottenReviewsList', async (req, res) => {
  await rottenController.listOfRottenReviews(req, res);

  //res.send('I am the MongoDB Server Home!');
}) */


router.get('/film/paginated/:title', async (req, res) => {
  try {
    const {page, size} = req.query;
    const data = await rottenController.findByTitlePaginated(req.params.title, page, size);
    res.json(data);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
})

module.exports = router;