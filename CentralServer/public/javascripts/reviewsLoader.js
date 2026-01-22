let reviewPage = 0;
const reviewSize = 5;
const displayedSignatures = new Set();

document.addEventListener('DOMContentLoaded', () => {
    const movieTitle = document.getElementById('movieTitleForReviews').value;
    const btnLoadMore = document.getElementById('loadMoreReviewsBtn');
    const noReviewsMsg = document.getElementById('no-reviews-msg');

    async function fetchReviews() {
        try {
            const url = `/api/reviews/film/${encodeURIComponent(movieTitle)}?page=${reviewPage}&size=${reviewSize}`;
            const response = await fetch(url);
            const data = await response.json();

            const activeSpinner = document.getElementById('active-review-spinner');
            if (activeSpinner) activeSpinner.remove();

            if (data.content && data.content.length > 0) {
                renderReviews(data.content);
            } else if (reviewPage === 0) {
                noReviewsMsg.style.display = 'block';
                btnLoadMore.style.display = 'none';
            }

            if (data.isLast || data.content.length === 0) {
                btnLoadMore.style.display = 'none';
            } else {
                btnLoadMore.style.display = 'inline-block';
            }

        } catch (error) {
            console.error("Errore fetch:", error);
            const activeSpinner = document.getElementById('active-review-spinner');
            if (activeSpinner) activeSpinner.remove();

            if (reviewPage === 0) {
                btnLoadMore.style.display = 'none';
            }
        }
    }

    function renderReviews(reviews) {
        const container = document.getElementById('reviewsContainer');

        if (reviewPage === 0) {
            container.innerHTML = '';
            displayedSignatures.clear();
        }

        const html = reviews
            .filter(rev => {
                const signature = `${rev.critic_name}-${rev.review_content}`;
                if (displayedSignatures.has(signature)) return false;
                displayedSignatures.add(signature);
                return true;
            })
            .map(rev => `
            <div class="review-card">
                <div class="review-header">
                    <strong>${rev.critic_name}</strong> 
                    <span class="review-type ${rev.review_type.toLowerCase()}">${rev.review_type}</span>
                </div>
                <div class="review-publisher">${rev.publisher_name}</div>
                <p class="review-content">"${rev.review_content}"</p>
                <div class="review-date">${formatDate(rev.review_date)}</div>
            </div>
        `).join('');

        container.insertAdjacentHTML('beforeend', html);
    }

    btnLoadMore.addEventListener('click', () => {
        reviewPage++;

        const spinnerHtml = `
            <div id="active-review-spinner" class="loading-spinner-container">
                <div class="loading-spinner"></div>
                <p style="color: #aaa;">Caricamento in corso...</p>
            </div>
        `;

        btnLoadMore.style.display = 'none';
        btnLoadMore.insertAdjacentHTML('beforebegin', spinnerHtml);

        fetchReviews();
    });

    fetchReviews();
});

function formatDate(dateString) {
    if (!dateString) return "Data non disponibile";

    const date = new Date(dateString);

    return date.toISOString().split('T')[0];
}
