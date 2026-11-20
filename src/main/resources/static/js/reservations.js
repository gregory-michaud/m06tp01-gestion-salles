async function loadReservations() {
    try {
        const response = await fetch("/reservations");
        if (!response.ok) {
            throw new Error("Erreur HTTP " + response.status);
        }
        const reservations = await response.json();

        const tbody = document.getElementById("reservations");

        reservations.forEach((r, index) => {
            const tr = document.createElement("tr");

            const hDebut = r.heureDebut;
            const hFin = r.heureFin;
            const [hD, mD] = hDebut.split(":");
            const [hF,mF]=hFin.split(":");

            //Création d'un template avec les littéraux de gabarit
            const template =
                `<td>${r.date}</td>
                 <td>${hD}:${mD}</td>
                 <td>${hF}:${mF}</td>
                 <td>${r.salle.nom}</td>
                 <td>${r.duree}</td>
                 <td>${r.nomEmploye}</td>`;

            tr.innerHTML = template;
            if(index %2==0){
                tr.style.background="#F0F0F0";
            }
            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error("Impossible de charger les réservations :", err);
    }
}

loadReservations();