//molto prob questo duplica gli utenti
//const socket = io();
document.addEventListener("DOMContentLoaded", function() {
    const messages = document.getElementById('messages');
    let button = document.getElementById("messageButton");
    let searchBtn = document.getElementById("searchBtn");

    let textField = document.getElementById("messageInput")
    let minscore = document.getElementById("minScore");
    let maxscore = document.getElementById("maxScore");

        searchBtn.addEventListener("click", function () {
            axios.get('/search', {
                params: {
                    title: document.getElementById("title").value,
                    minScore: parseFloat(minscore.value),
                    maxScore: parseFloat(maxscore.value)
                }
            })
                .then(res => {
                    document.getElementById("results").innerHTML = res.data.reviews_found;
                })
                .catch(err => {
                    alert(err.response?.data?.error || "Errore");
                });
        });



    const btnMongo = document.getElementById("btn_Mongo");
    const sendForm = document.getElementById("send-form");
    const resultsDiv = document.getElementById("results");

    sendForm.addEventListener("submit", function (event) {
        event.preventDefault();
        resultsDiv.innerHTML = "Caricamento...";
        axios.post('/contactMongo', {})
            .then(response => {

                const reviewsArray = response.data.reviews;

                resultsDiv.innerHTML = formatReviewsToHTML(reviewsArray);
                console.log("Rotten Reviews caricate.");
            })
            .catch(err => {
                resultsDiv.innerHTML = "Errore durante il caricamento delle recensioni.";
                alert(err.response ? err.response.data.error : "Errore di connessione al Central Server.");
            });


        /*  button.addEventListener("click", function (event) {
              socket.emit("message", textField.value);
          })
          socket.on('message', function(msg) {
              const li = document.createElement('li');
              li.textContent = msg;
              messages.appendChild(li);
          }) */


    })
})

    function formatReviewsToHTML(reviews) {
        if (!reviews || reviews.length === 0) {
            return "Nessuna recensione trovata.";
        }

        let htmlOutput = "";
        reviews.forEach(review => {
            htmlOutput += `
            <div class="review-item" style="border-bottom: 1px solid #ccc; margin-bottom: 10px; padding-bottom: 5px;">
                <strong>Title:</strong> ${review.movieName || 'N/A'} <br>
                <strong>Score:</strong> ${review.movieRating || 'N/A'} <br>
            </div>`;
        });
        return htmlOutput;
    }





