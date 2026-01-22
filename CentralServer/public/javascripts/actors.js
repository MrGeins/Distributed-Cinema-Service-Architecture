const API_BASE_URL = 'http://localhost:3000/actors';
let currentActorPage = 0;
const actorPageSize = 12;

document.addEventListener('DOMContentLoaded', () => {
    const btnSearch = document.getElementById('btnSearchActor');
    const inputSearch = document.getElementById('actorSearchInput');
    const actorsGrid = document.getElementById('actorsGrid');


    async function searchActors(isNewSearch = true) {
        const query = document.getElementById('actorSearchInput').value.trim();
        if (!query) return;

        const actorsGrid = document.getElementById('actorsGrid');
        if (isNewSearch) {
            currentActorPage = 0;
            actorsGrid.innerHTML = '<div class="loading-spinner-container"><div class="loading-spinner"></div></div>';
        }


        const oldBtn = document.getElementById('loadMoreBtn');
        if (oldBtn) oldBtn.remove();

        try {
            // Chiama l'endpoint che restituisce List<String>
            const response = await fetch(`/actors/list?searchValue=${encodeURIComponent(query)}&page=${currentActorPage}&size=${actorPageSize}`);
            const namesList = await response.json();

            if (isNewSearch) actorsGrid.innerHTML = '';

            if (isNewSearch && namesList.length === 0) {
                actorsGrid.innerHTML = `<p style="text-align:center; color:#aaa; grid-column:1/-1;">Nessun attore trovato per "${query}".</p>`;
                return;
            }

            const htmlContent = namesList.map(name => UIComponents.createActorCard(name)).join('');
            actorsGrid.insertAdjacentHTML('beforeend', htmlContent);

            //Ricevuta una pagina piena, mostriamo card per il caricamento di nuove pagine
            if (namesList.length === actorPageSize) {
                actorsGrid.insertAdjacentHTML('beforeend', UIComponents.createLoadMoreCard("loadNextActorPage()"));
            }
        } catch (error) {
            console.error("Errore:", error);
            actorsGrid.innerHTML = `<p style="color:red; text-align:center; grid-column:1/-1;">Errore di comunicazione con il server.</p>`;
        }
    }

    btnSearch.addEventListener('click', searchActors);
    inputSearch.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') searchActors();
    });



    window.loadNextActorPage = function() {
        currentActorPage++;
        searchActors(false);
    };




    window.showActorDetails = window.showActorDetails = async function(actorName) {
        const detailsContainer = document.getElementById('actorDetailsContent');
        const modal = document.getElementById('actorModal');

        modal.style.display = "block";
        detailsContainer.innerHTML = '<div class="loading-spinner-container"><div class="loading-spinner"></div></div>';

        try {
            const response = await fetch(`/actors/search?name=${encodeURIComponent(actorName)}`);
            if (!response.ok) throw new Error("Errore dati");
            const actorData = await response.json();

            // Utilizzo del componente UI centralizzato
            detailsContainer.innerHTML = UIComponents.createActorDetailsHTML(actorData);
        } catch (error) {
            detailsContainer.innerHTML = `<p style="color:red; text-align:center;">Errore nel caricamento dei dettagli.</p>`;
        }
    };

// Funzione per chiudere la modale
    window.closeModal = function() {
        document.getElementById('actorModal').style.display = "none";
    };


    window.onclick = function(event) {
        const modal = document.getElementById('actorModal');
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
});



