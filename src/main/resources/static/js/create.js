async function loadRooms() {
    try {
        const response = await fetch("/reservations/salles");
        if (!response.ok) {
            throw new Error("Erreur HTTP " + response.status);
        }
        const reservations = await response.json();

        const select = document.getElementById("salle");
        reservations.forEach(s => {
            const option = document.createElement("option");
            option.value = `${s.nom}`;
            option.textContent = `${s.nom} (capacité :  ${s.capacite})`;
            select.appendChild(option);
        });
    } catch (err) {
        console.error("Impossible de charger les salles :", err);
    }
}

loadRooms();