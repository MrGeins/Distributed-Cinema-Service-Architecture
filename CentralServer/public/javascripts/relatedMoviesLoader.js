let currentRelatedPage = 0;
const relatedPageSize = 6;
let isRelatedLoading = false;

document.addEventListener('DOMContentLoaded', () => {
    const movieId = document.getElementById('currentMovieId').value;
    const container = document.getElementById('relatedMoviesContainer');
    const noResultsDiv = document.getElementById('no-related-msg');
    const errorDiv = document.getElementById('error');

    async function fetchRelatedMovies(isNewSearch = true) {
        if (isRelatedLoading) return;
        isRelatedLoading = true;

        if (isNewSearch) {
            currentRelatedPage = 0;
        }

        const oldBtn = container.querySelector('.load-more-card');
        if (oldBtn) oldBtn.remove();

        try {

            const url = `/api/movies/related/${movieId}?page=${currentRelatedPage}&size=${relatedPageSize}`;
            const response = await fetch(url);
            const data = await response.json();
            console.log("Dati ricevuti dal server:", data);




            const initialSpinner = container.querySelector('.loading-spinner-container');
            if (initialSpinner) initialSpinner.remove();

            const movies = Array.isArray(data) ? data : (data.content || []);

            const isLast = data.last !== undefined ? data.last : (movies.length < relatedPageSize);

            if (isNewSearch && movies.length === 0) {
                noResultsDiv.style.display = 'block';
                return;
            }

            noResultsDiv.style.display = 'none';
            errorDiv.style.display = 'none';


            const htmlContent = movies.map(movie => UIComponents.createMovieCard(movie)).join('');
            container.insertAdjacentHTML('beforeend', htmlContent);


            if (!isLast) {

                container.insertAdjacentHTML('beforeend', UIComponents.createLoadMoreCard("loadNextRelatedPage()"));
            }

        } catch (error) {
            console.error("Errore caricamento correlati:", error);
            const initialSpinner = container.querySelector('.loading-spinner-container');
            if (initialSpinner) initialSpinner.remove();

            if (isNewSearch) errorDiv.style.display = 'block';
        } finally {
            isRelatedLoading = false;
        }
    }


    window.loadNextPage = function() {
        currentRelatedPage++;
        fetchRelatedMovies(false);
    };

    // Avvio automatico al caricamento della pagina
    if (movieId && container) {
        fetchRelatedMovies(true);
    }



    container.addEventListener('click', (event) => {
        const chatBtn = event.target.closest('.btn-open-chat');
        if (chatBtn) {
            event.preventDefault();
            event.stopPropagation(); // Evita di aprire la pagina del film
            const title = chatBtn.getAttribute('data-title');
            const id = chatBtn.getAttribute('data-id');
            openChat(title, id);
            return;
        }

        const movieCard = event.target.closest('.movie-card');
        if (movieCard) {
            const id = movieCard.getAttribute('data-movie-id');
            if (id) {
                window.location.href = `/movie/${id}`;
            }
        }
    });

});