const express = require('express');
const path = require('path');
const createError = require('http-errors');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const cors = require('cors');
const app = express();
const connectDB = require('./db/connect');

// Routes
const indexRouter = require('./routes');
const usersRouter = require('./routes/users');
const reviewsRouter = require('./routes/reviews');
const messagesRouter = require('./routes/messages');

// Swagger
const swaggerUi = require('swagger-ui-express');
const swaggerJsDoc = require('swagger-jsdoc');

const options = {
  definition: {
    openapi: '3.1.0',
    info: {
      title: 'Express Data Server API',
      version: '1.0',
    },
    servers: [{ url: 'http://localhost:3001' }],
  },
  apis: ['./routes/*.js'],
};
const swaggerSpecification = swaggerJsDoc(options);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpecification));

// View engine setup
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Middleware
app.use(cors());
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

// API
app.use('/', indexRouter);
app.use('/users', usersRouter)
app.use('/reviews', reviewsRouter);
app.use('/messages', messagesRouter);

// Catch 404 and forward to error handler
app.use(function(req, res, next) {
  res.status(404).json({ error: "Resource not found." });
});

// Error handler
app.use(function(err, req, res, next) {
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  res.status(err.status || 500);

  if (req.headers['accept'] && req.headers['accept'].includes('application/json')) {
    return res.json({ error: err.message });
  }

  res.render('error');
});

module.exports = app;