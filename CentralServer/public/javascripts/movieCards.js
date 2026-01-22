const UIComponents = {
    createMovieCard: function(movie) {
        const movieTitle = movie.movieTitle || 'Film';
        const id = movie.movieId || movie._id || movie.id; // Gestisce diversi formati di ID
        const posterStyle = movie.posterLink
            ? `background-image: url('${movie.posterLink}');`
            : `background: linear-gradient(to bottom, #333, #111);`;

        const oscarHtml = movie.oscarWinner ? `<div class="oscar-badge">🏆 Oscar</div>` : '';

        return `
            <div class="movie-card" data-movie-id="${id}">
                <div class="card-poster-area" style="${posterStyle}"></div>
                <div class="rating-badge">⭐ ${movie.rating || 'N/A'}</div>
                ${oscarHtml}
                <div class="movie-info">
                    <div class="movie-title">${movieTitle}</div>
                    <div class="movie-meta"><span>${movie.yearOfRelease || ''}</span></div>
                    <button class="chat-btn btn-open-chat" 
                        data-title="${movieTitle.replace(/"/g, '&quot;')}" 
                        data-id="${id}">
                        💬 Apri Chat
                    </button>
                </div>
            </div>
        `;

    },


    createLoadMoreCard : function() {
    return `
            <div class="movie-card load-more-card" id="loadMoreBtn" onclick="loadNextPage()">
                <div class="card-poster-area" style="display: flex; align-items: center; justify-content: center; background: #1a1a1a; border: 2px dashed #444; cursor: pointer;">
                    <span style="font-size: 4rem; color: #555;">+</span>
                </div>
                <div class="movie-info" style="text-align: center;">
                    <div class="movie-title">Carica Altri</div>
                </div>
            </div>
        `;
    },

    createActorCard: function(actorName) {
    // Usiamo il nome dell'attore come ID per la ricerca successiva
    const safeName = actorName.replace(/'/g, "\\'");

    return `
        <div class="movie-card">
            <div class="card-poster-area" style="background: linear-gradient(to bottom, #444, #111); display: flex; align-items: center; justify-content: center;">
                <span style="font-size: 3rem;">👤</span>
            </div>
            <div class="movie-info">
                <div class="movie-title">${actorName}</div>
                <button onclick="showActorDetails('${safeName}')" class="chat-btn">
                    Visualizza Filmografia & Oscar
                </button>
            </div>
        </div>
    `;
    },
    createActorDetailsHTML: function(actor) {
        return `
            <div class="actor-details-header" style="border-bottom: 1px solid #333; margin-bottom: 20px; padding-bottom: 10px;">
                <h2 style="color: #E50914; font-size: 2.2rem; margin-bottom: 5px;">${actor.name}</h2>
                <p class="oscar-count" style="font-size: 1.3rem;">
                    🏆 Oscar vinti: <strong>${actor.oscarNumber || 0}</strong>
                </p>
            </div>
            <div class="actor-filmography">
                <h3 style="margin-bottom: 15px; color: #ccc;">Filmografia Principale</h3>
                <div class="filmography-list" style="display: grid; grid-template-columns: 1fr 1fr; gap: 12px;">
                    ${actor.movies && actor.movies.length > 0
            ? actor.movies.map(movie => `
                            <div class="film-item-link" 
                                 onclick="window.location.href='/movie/${movie.movieId}'" 
                                 style="background: #222; padding: 12px; border-radius: 8px; border-left: 4px solid #E50914; cursor: pointer; transition: transform 0.2s;">
                                <span style="font-weight: bold; color: #fff; display: block;">${movie.movieTitle}</span>
                                <span style="font-size: 0.85rem; color: #888;">${movie.yearOfRelease || 'N/A'}</span>
                            </div>`).join('')
            : '<p style="grid-column: 1/-1;">Nessun film trovato.</p>'}
                </div>
            </div>`;
    }



};