document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".slider-wrapper").forEach(wrapper => {
        const container = wrapper.querySelector(".movie-carousel");
        const leftBtn = wrapper.querySelector(".left-handle");
        const rightBtn = wrapper.querySelector(".right-handle");

        rightBtn.addEventListener("click", () => {
            container.scrollBy({ left: window.innerWidth / 2, behavior: "smooth" });
        });

        leftBtn.addEventListener("click", () => {
            container.scrollBy({ left: -window.innerWidth / 2, behavior: "smooth" });
        });
    });
});
