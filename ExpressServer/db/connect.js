const mongoose = require('mongoose');

const connectToMongo = async () => {
    try {
        await mongoose.connect('mongodb://localhost:27017/filmdata');
        console.log('✅ Connected to MongoDB!');
    } catch (error) {
        console.error('❌ MongoDB connection failed');
        console.error(error.message);
        process.exit(1);
    }
};

module.exports = connectToMongo;
