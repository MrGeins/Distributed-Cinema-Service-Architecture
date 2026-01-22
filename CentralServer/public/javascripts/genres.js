const API_BASE_URL = 'http://localhost:8080';


document.addEventListener('DOMContentLoaded', () => {
    const genresGrid = document.getElementById('genresGrid');

    // Lista di gradienti per rendere le card carine (Stile Prime/Spotify)
    const gradients = [
        'linear-gradient(45deg, #ff9a9e 0%, #fad0c4 99%, #fad0c4 100%)',
        'linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%)',
        'linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%)',
        'linear-gradient(120deg, #84fab0 0%, #8fd3f4 100%)',
        'linear-gradient(to right, #4facfe 0%, #00f2fe 100%)',
        'linear-gradient(to top, #cfd9df 0%, #e2ebf0 100%)',
        'linear-gradient(45deg, #8baaaa 0%, #ae8b9c 100%)',
        'linear-gradient(to right, #f83600 0%, #f9d423 100%)',
        'linear-gradient(to top, #0ba360 0%, #3cba92 100%)',
        'linear-gradient(to top, #c79081 0%, #dfa579 100%)'
    ];

    function getRandomGradient() {
        return gradients[Math.floor(Math.random() * gradients.length)];
    }

    async function loadGenres() {
        try {
            // Chiama il backend Java
            const response = await fetch(`${API_BASE_URL}/genres/all`);
            const uniqueGenres = await response.json();

            // Rimuovi duplicati (Set) e ordina alfabeticamente
            //const uniqueGenres = [...new Set(data.map(g => g.genre))].sort();

            // Pulisci il caricamento
            genresGrid.innerHTML = '';

            // Genera le Card
            uniqueGenres.forEach(genre => {
                const card = document.createElement('div');
                card.className = 'genre-card';

                // Assegna uno sfondo casuale
                card.style.background = getRandomGradient();

                // Crea il link cliccabile
                // Quando clicchi, vai alla pagina film col filtro attivo nell'URL
                card.addEventListener('click', () => {
                    window.location.href = `/movies?genre=${encodeURIComponent(genre)}`;
                });

                card.innerHTML = `<span class="genre-name">${genre}</span>`;
                genresGrid.appendChild(card);
            });

        } catch (error) {
            console.error("Errore generi:", error);
            genresGrid.innerHTML = '<p style="color:red">Errore caricamento generi dal server.</p>';
        }
    }

    loadGenres();
});