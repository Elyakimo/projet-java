<script setup>
    import TuteurForm from '@/components/TuteurForm.vue'
    import { ref, onMounted } from 'vue'

    const tuteurEnEdition = ref(null)
    const tuteurs = ref([])

    async function chargerTuteurs(){
        const response = await fetch('http://localhost:8080/api/tuteurs')
        const data = await response.json()
        tuteurs.value = data
    }

    async function supprimerTuteurs(id){
        await fetch(`http://localhost:8080/api/tuteurs/${id}`, {
            method: 'DELETE'
        })
        await chargerTuteurs()
    }
    
    function modifierTuteurs(tuteur) {
        tuteurEnEdition.value = tuteur
    }

    async function onTuteurCreee() {
        await chargerTuteurs()
    }

    async function onTuteurModifiee() {
        tuteurEnEdition.value = null
        await chargerTuteurs()
    }

    onMounted(() => {
        chargerTuteurs()
    })
</script>

<template>
    <div>
        <h1>Liste des tuteurs</h1>

        <TuteurForm :tuteur-a-modifier="tuteurEnEdition"
        @created="onTuteurCreee"
        @updated="onTuteurModifiee"
        />

        <ul>
            <li v-for="tuteur in tuteurs" :key="tuteur.id">
                {{ tuteur.nomTuteur }} — {{ tuteur.prenomTuteur }} — {{ tuteur.organisation.nomEntreprise }}
                <button @click="modifierTuteurs(tuteur)">Modifier</button>
                <button @click="supprimerTuteurs(tuteur.id)">Supprimer</button>
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
li button {
  min-width: 100px;
  text-align: center;
}
</style>