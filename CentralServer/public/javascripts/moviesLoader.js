let searchPage = 0;
let currentSearchQuery = '';
const searchPageSize = 20;
let isSearchLoading = false;

document.addEventListener('DOMContentLoaded', async () => {
    const queryInput = document.getElementById('searchQueryData');
    const grid = document.getElementById('movieGrid');
    const countText = document.getElementById('resultsCount');


    async function performSearch(isNewSearch = true) {
        if (isSearchLoading) return;
        isSearchLoading = true;

        if (isNewSearch) {
            searchPage = 0;
            currentSearchQuery = queryInput.value;
        }

        // Rimuoviamo la card "+" esistente
        const oldBtn = grid.querySelector('.load-more-card');
        if (oldBtn) oldBtn.remove();

        try {
            // Assicurati che il tuo backend accetti page e size per la ricerca!
            const url = `/api/movies/searchForName?title=${encodeURIComponent(currentSearchQuery)}&page=${searchPage}&size=${searchPageSize}`;
            const response = await fetch(url);
            const data = await response.json();


            const movies = data.content || data;
            const isLast = data.last !== undefined ? data.last : true; // Se il backend non è paginato, ipotizziamo sia l'ultima

            const loadingMsg = document.getElementById('search-loading');
            if (loadingMsg) loadingMsg.remove();

            if (isNewSearch && movies.length === 0) {
                countText.innerText = "Nessun risultato trovato.";
                grid.innerHTML = `<div class="no-results"><p>Oops! Nessun film trovato.</p></div>`;
                return;
            }

            if (isNewSearch) countText.innerText = `Risultati per: "${currentSearchQuery}"`;

            const htmlContent = movies.map(movie => UIComponents.createMovieCard(movie)).join('');

            if (isNewSearch) {
                grid.innerHTML = htmlContent;
            } else {
                grid.insertAdjacentHTML('beforeend', htmlContent);
            }

            // Aggiungiamo la card "+" se necessario
            if (!isLast && movies.length >= searchPageSize) {
                grid.insertAdjacentHTML('beforeend', UIComponents.createLoadMoreCard());
            }

        } catch (error) {
            console.error("Errore nel caricamento:", error);
            grid.innerHTML = `<p>Errore durante il caricamento dei risultati.</p>`;
        } finally {
            isSearchLoading = false;
        }
    }

    // Definiamo la funzione globale che la card "+" cercherà
    window.loadNextPage = function() {
        searchPage++;
        performSearch(false);
    };

    // --- AVVIO RICERCA ---
    if (queryInput && grid) {
        performSearch(true);
    }

    // --- DELEGA DEGLI EVENTI ---
    grid.addEventListener('click', (event) => {
        const chatBtn = event.target.closest('.btn-open-chat');
        if (chatBtn) {
            event.stopPropagation();
            const title = chatBtn.getAttribute('data-title');
            const id = chatBtn.getAttribute('data-id');
            openChat(title, id);
            return;
        }

        const movieCard = event.target.closest('.movie-card');
        if (movieCard && !movieCard.classList.contains('load-more-card')) {
            const id = movieCard.getAttribute('data-movie-id');
            window.location.href = `/movie/${id}`;
        }
    });
});



