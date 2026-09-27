<script setup>
    import StageForm from '@/components/StageForm.vue'
    import { ref, onMounted, computed } from 'vue'

    const filtreStatut = ref('')
    const filtreNomEtudiant = ref('')
    const filtreNomEntreprise = ref('')
    const filtreDateDebut = ref('')

    const stagesFiltres = computed(() => {
        return stages.value.filter(stage =>{
            const matchStatut = !filtreStatut.value || stage.statut === filtreStatut.value
            const matchEtudiant = !filtreNomEtudiant.value ||  stage.stagiaire.nom.toLowerCase().includes(filtreNomEtudiant.value.toLowerCase())
            const matchEntreprise = !filtreNomEntreprise.value || stage.organisation.nomEntreprise.toLowerCase().includes(filtreNomEntreprise.value.toLowerCase())
            const matchDate = !filtreDateDebut.value ||  stage.dateDebut.split('T')[0] >= filtreDateDebut.value
            return matchStatut && matchEtudiant && matchEntreprise && matchDate
        })
    })

    const stageEnEdition = ref(null)
    const stages = ref([])

    async function chargerStages(){
        const response = await fetch('http://localhost:8080/api/stages')
        const data = await response.json()
        stages.value = data
    }

    async function supprimerStages(id){
        await fetch(`http://localhost:8080/api/stages/${id}`, {
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
</script>

<template>
    <div>
        <h1>Liste des stages</h1>

        <StageForm :stage-a-modifier="stageEnEdition"
        @created="onStageCreee"
        @updated="onStageModifiee"
        />
        
        <div class="filtres">
            <select v-model="filtreStatut">
                <option value="">Tous les status</option>
                <option value="CANDIDATURE">Candidature</option>
                <option value="EN_COURS">En cours</option>
                <option value="TERMINE">Terminé</option>
                <option value="REFUSE">Refusé</option>
            </select>
            <input v-model="filtreNomEtudiant" placeholder="Rechercher par nom d'étudiant" />
            <input v-model="filtreNomEntreprise" placeholder="Rechercher par nom d'entreprise" />
            <input v-model="filtreDateDebut" type="date" placeholder="Rechercher par date" />
        </div>
        <ul>
            <li v-for="stage in stagesFiltres" :key="stage.id">
                {{ stage.stagiaire.nom }} — {{ stage.stagiaire.prenom }} — {{ stage.organisation.nomEntreprise }} — {{ formaterDate(stage.dateDebut) }} — {{ formaterDate(stage.dateFin) }}
                <button @click="modifierStages(stage)">Modifier</button>
                <button @click="supprimerStages(stage.id)">Supprimer</button>
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
  border-radius: 6px;
  padding: 0.75rem 1rem;
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
</style>