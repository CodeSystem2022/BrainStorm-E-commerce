document.addEventListener("DOMContentLoaded", function() {
    const preguntas = document.querySelectorAll(".pregunta");

    preguntas.forEach((pregunta) => {
        pregunta.addEventListener("click", function() {
            const respuesta = pregunta.querySelector(".respuesta");
            respuesta.style.display = respuesta.style.display === "block" ? "none" : "block";
        });
    });
});
