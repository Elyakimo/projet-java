<script setup>
    import StageForm from '@/components/StageForm.vue'
    import { ref, onMounted, computed } from 'vue'
    import { apiFetch } from '@/stores/auth'

    const filtreStatut = ref('')
    const filtreNomEtudiant = ref('')
    const filtreNomEntreprise = ref('')
    const filtreDateFin = ref('')

    const stagesFiltres = computed(() => {
        return stages.value.filter(stage =>{
            const matchStatut = !filtreStatut.value || stage.statut === filtreStatut.value
            const matchEtudiant = !filtreNomEtudiant.value ||  stage.stagiaire.nom.toLowerCase().includes(filtreNomEtudiant.value.toLowerCase())
            const matchEntreprise = !filtreNomEntreprise.value || stage.organisation.nomEntreprise.toLowerCase().includes(filtreNomEntreprise.value.toLowerCase())
            const matchDate = !filtreDateFin.value ||  stage.dateFin.split('T')[0] >= filtreDateFin.value
            return matchStatut && matchEtudiant && matchEntreprise && matchDate
        })
    })

    const stageEnEdition = ref(null)
    const stages = ref([])

    async function chargerStages(){
        const response = await apiFetch('http://localhost:8080/api/stages')
        const data = await response.json()
        stages.value = data
    }

    async function supprimerStages(id){
        await apiFetch(`http://localhost:8080/api/stages/${id}`, {
            method: 'DELETE'
        })
        await chargerStages()
    }
    
    function modifierStages(stage) {
        stageEnEdition.value = stage
    }

    async function onStageCreee() {
        await chargerStages()
    }

    async function onStageModifiee() {
        stageEnEdition.value = null
        await chargerStages()
    }

    onMounted(() => {
        chargerStages()
    })
    
    function formaterDate(dateString) {
        return dateString.split('T')[0]
    }
    function classeStatut(statut){
      return {
        CANDIDATURE: 'badge-candidature',
        EN_COURS: 'badge-en-cours',
        TERMINE: 'badge-termine',
        REFUSE: 'badge-refuse'
      }[statut]
    }

    function libelleStatut(statut) {
      return {
        CANDIDATURE: 'Candidature',
        EN_COURS: 'En cours',
        TERMINE: 'Terminé',
        REFUSE: 'Refusé'
      }[statut]
    }
</script>

<template>
    <div>
        <h1>Liste des stages</h1>

        <StageForm :stage-a-modifier="stageEnEdition"
        @created="onStageCreee"
        @updated="onStageModifiee"
        />
        
        <div class="filtres">
            <select v-model="filtreStatut" id="selectStatut">
                <option value="" id="Statut">Tous les status</option>
                <option value="CANDIDATURE" id="Statut_candidature">Candidature</option>
                <option value="EN_COURS" id="Statut_Encours">En cours</option>
                <option value="TERMINE" id="Statut_Termine">Terminé</option>
                <option value="REFUSE" id="Statut_Refuse">Refusé</option>
            </select>
            <input v-model="filtreNomEtudiant" placeholder="Rechercher par nom d'étudiant" id="filtreNomEtudiant" />
            <input v-model="filtreNomEntreprise" placeholder="Rechercher par nom d'organisation" id="filtreNomEntreprise"/>
            <input v-model="filtreDateFin" type="date" placeholder="Rechercher par date" id="filtreDate" />
        </div>
        <ul>
            <li v-for="stage in stagesFiltres" :key="stage.id">
              <span>
                  <span class="badge" :class="classeStatut(stage.statut)">{{ libelleStatut(stage.statut) }}</span>
                  <span class="infos">
                    <strong>{{ stage.stagiaire.nom }} {{ stage.stagiaire.prenom }}</strong>
                    <span class="detail">{{ stage.organisation.nomEntreprise }} — stage du {{ formaterDate(stage.dateDebut) }} au {{ formaterDate(stage.dateFin) }}</span>
                  </span>
                </span>
              <span>
                <button @click="modifierStages(stage)">Modifier</button>
                <button @click="supprimerStages(stage.id)">Supprimer</button>
              </span>
            </li>
        </ul>
    </div>
</template>
<style scoped>
h1 {
  font-size: 1.75rem;
  margin-bottom: 1.5rem;
}

ul {
  list-style: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: var(--surface);
  border: 1px solid var(--border);
  border-left: 3px solid var(--accent);
  border-radius: 6px;
  padding: 0.75rem 1rem;
  transition: box-shadow 0.15s ease, transform 0.15s ease;
}

li:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transform: translateY(-1px);
}
li button {
  margin-left: 0.5rem;
  padding: 0.4rem 0.8rem;
  border: 1px solid var(--border);
  border-radius: 4px;
  background: var(--surface);
}

li button:hover {
  background: var(--bg);
}

li button:last-child {
  color: var(--danger);
  border-color: var(--danger);
}
form,
.filtres {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

form input,
form select,
.filtres input,
.filtres select {
  flex: 1;
  min-width: 140px;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--border);
  border-radius: 4px;
  background: var(--surface);
  font-size: 0.9rem;
}

form button {
  padding: 0.5rem 1.25rem;
  border: none;
  border-radius: 4px;
  background: var(--accent);
  color: white;
  font-weight: 500;
}

form button:hover {
  background: var(--accent-hover);
}
.filtres {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 6px;
  padding: 0.75rem 1rem;
  margin-bottom: 1.5rem;
}
li button {
  min-width: 100px;
  text-align: center;
}
.badge {
  display: inline-block;
  padding: 0.15rem 0.6rem;
  border-radius: 12px;
  font-size: 0.75rem;
  font-weight: 600;
  margin-right: 0.6rem;
}

.badge-candidature {
  background: #EFE6D8;
  color: #8A6D3B;
}

.badge-en-cours {
  background: #E1EAE4;
  color: var(--accent);
}

.badge-termine {
  background: #DCE4E8;
  color: #46606B;
}

.badge-refuse {
  background: #F3DCD5;
  color: var(--danger);
}
.infos {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.infos strong {
  font-weight: 600;
}

.infos .detail {
  font-size: 0.85rem;
  color: var(--text-muted);
}
</style>