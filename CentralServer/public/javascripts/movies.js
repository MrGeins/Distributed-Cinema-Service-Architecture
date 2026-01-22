const API_BASE_URL = 'http://localhost:3000/api';

// --- STATO GLOBALE PER LA PAGINAZIONE ---
let currentPage = 0;
let currentSearchType = 'filter';
let currentSearchValue = '';
let currentSort = 'rating';
let currentOrder = 'DESC';
let isLoading = false;
const pageSize = 12;

//spinner
const spinnerHTML = `
            <div id="movie-loading-spinner" class="loading-spinner-container" style="grid-column: 1 / -1; padding: 40px 0;">
                <div class="loading-spinner"></div>
                <p class="loading-text">Ricerca in corso...</p>
            </div>
        `;

document.addEventListener('DOMContentLoaded', () => {

    const genreSelect = document.getElementById('genreSelect');
    const yearSelect = document.getElementById('yearSelect');
    const moviesGrid = document.getElementById('moviesGrid');
    const applyFiltersBtn = document.getElementById('applyFiltersBtn');



    // --- 3. CARICAMENTO GENERI ---
    async function loadGenres() {
        try {
            const response = await fetch(`${API_BASE_URL}/genres/all`);
            const uniqueGenres = await response.json();
            //const uniqueGenres = [...new Set(genresData.map(g => g.genre))].sort();

            const genreSelect = document.getElementById('genreSelect');
            genreSelect.innerHTML = '<option value="all">Tutti i Generi</option>';

            uniqueGenres.forEach(genreName => {
                const option = document.createElement('option');
                option.value = genreName;
                option.textContent = genreName;
                genreSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Errore caricamento generi:", error);
        }
    }

    // --- 3B : CARICAMENTO DEGLI ANNI DEI FILM
    async function loadYears() {
        try {
            const response = await fetch(`${API_BASE_URL}/years`);
            const yearsList = await response.json();

            yearsList.forEach(year => {
                const option = document.createElement('option');
                option.value = year;
                option.textContent = year;
                yearSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Errore anni:", error);
        }
    }


    // ---. CARICAMENTO FILM (LOGICA LOAD MORE) ---
    async function loadMovies(type = 'filter', value = '', isNewSearch = true) {
        if (isLoading) return;
        isLoading = true;



        if (isNewSearch) {
            currentPage = 0;
            currentSearchType = type;
            currentSearchValue = value;
           // moviesGrid.innerHTML = spinnerHTML;

        } else {
            const oldBtn = document.getElementById('loadMoreBtn');
            if (oldBtn) oldBtn.remove();
            moviesGrid.insertAdjacentHTML('beforeend', spinnerHTML);
        }

        let url = '';
        // Parametri base per la paginazione
        const params = `page=${currentPage}&size=${pageSize}&sort=rating`;

        // Costruiamo la query string. Se 'all', non lo mandiamo (o il backend lo ignora)
        const queryParams = new URLSearchParams({
            page: currentPage,
            size: pageSize,
            sort: currentSort,
            order: currentOrder

        });

        // --- COSTRUZIONE URL ---
        if (type === 'title') {
            // CASO 1: Ricerca per Titolo (Navbar/Home)
            url = `${API_BASE_URL}/movies/searchForName?title=${encodeURIComponent(value)}&${params}`;
        } else {

            const selectedGenre = genreSelect.value;
            const selectedYear = yearSelect.value;

            if (selectedGenre !== 'all') queryParams.append('genre', selectedGenre);
            if (selectedYear !== 'all') queryParams.append('year', selectedYear);

            url = `${API_BASE_URL}/movies?${queryParams.toString()}`;
        }
        // -----------------------

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("Errore network");

            const data = await response.json();

            const moviesList = data.content || [];
            const isLastPage = data.last; //  il backend restituisce questo campo

            const activeSpinner = document.getElementById('movie-loading-spinner');
            if (activeSpinner) activeSpinner.remove();
            const initialSpinner = document.getElementById('initial-spinner');
            if (initialSpinner) initialSpinner.remove();

            if (isNewSearch && moviesList.length === 0) {
                moviesGrid.innerHTML = `<p style="text-align:center; color:#aaa; grid-column:1/-1;">Nessun film trovato.</p>`;
                return;
            }

            const htmlContent = moviesList.map(movie => UIComponents.createMovieCard(movie)).join('');

            if (isNewSearch) {
                moviesGrid.innerHTML = htmlContent;
            } else {
                moviesGrid.insertAdjacentHTML('beforeend', htmlContent);
            }

            // Se non è l'ultima pagina, mostriamo la card "+"
            // Nota: verifica che data.last esista, altrimenti basati sulla lunghezza
            const hasMore = (data.last === false) || (moviesList.length >= pageSize);

            if (hasMore) {
                moviesGrid.insertAdjacentHTML('beforeend', UIComponents.createLoadMoreCard());
            }

        } catch (error) {
            console.error("Errore caricamento film:", error);

            // Rimuoviamo spinner anche in caso di errore
            const activeSpinner = document.getElementById('movie-loading-spinner');
            if (activeSpinner) activeSpinner.remove();

            if (isNewSearch) {
                moviesGrid.innerHTML = `<p style="color:red; text-align:center; grid-column:1/-1; padding: 20px;">Si è verificato un errore durante il caricamento.</p>`;
            }
        } finally {
            isLoading = false;
        }
    }

    // --- 5. FUNZIONE GLOBALE PER IL TASTO "+" ---
    window.loadNextPage = function() {
        currentPage++; // Incrementiamo la pagina
        // Usiamo le variabili globali corrette: currentFilterType e currentFilterValue
        loadMovies(currentSearchType, currentSearchValue, false);
    };
    // ---  EVENT LISTENERS ---

    // Cambio Genere
    if (applyFiltersBtn) {
        applyFiltersBtn.addEventListener('click', () => {
            // Quando si clicca Filtra, resetta la ricerca usando i valori delle select
            const spinner = document.getElementById('initial-spinner');
            moviesGrid.innerHTML = spinnerHTML;
            //moviesGrid.insertAdjacentHTML('beforeend', spinnerHTML);
            loadMovies('filter', '', true);
        });
    }


    // --- AVVIO ---
    async function init() {
        await loadGenres();
        await loadYears();

        const urlParams = new URLSearchParams(window.location.search);
        const titleFromUrl = urlParams.get('title');
        const genreFromUrl = urlParams.get('genre');

        const sortSelect = document.getElementById("sortOrder");
        if (sortSelect) {
            const parts = sortSelect.value.split('_');
            currentSort = parts[0];
            currentOrder = parts[1].toUpperCase();
        }


        if (titleFromUrl) {
            genreSelect.value = 'all';
            yearSelect.value = 'all';
            loadMovies('title', titleFromUrl, true);
        } else if (genreFromUrl) {
            genreSelect.value = genreFromUrl;
            loadMovies('filter', '', true);
        } else {
            loadMovies('filter', '', true);
        }


    }

    moviesGrid.addEventListener('click', (event) => {
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




    window.applySort = function() {     //Selezione dell'ordinamento dei risultati
        const sortSelect = document.getElementById("sortOrder");
        const selectedValue = sortSelect.value; // es: "year_desc"

        const parts = selectedValue.split('_');
        currentSort = parts[0]; // "year" o "rating"
        currentOrder = parts[1].toUpperCase(); // "ASC" o "DESC"
        moviesGrid.innerHTML = spinnerHTML;

        loadMovies(currentSearchType, currentSearchValue, true);
    };

    init();
});
