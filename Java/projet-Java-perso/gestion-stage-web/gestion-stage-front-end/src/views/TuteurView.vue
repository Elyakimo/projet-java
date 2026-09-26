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