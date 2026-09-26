<script setup>
    import StageForm from '@/components/StageForm.vue'
    import { ref, onMounted } from 'vue'

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

        <ul>
            <li v-for="stage in stages" :key="stage.id">
                {{ stage.stagiaire.nom }} — {{ stage.stagiaire.prenom }} — {{ stage.organisation.nomEntreprise }} — {{ formaterDate(stage.dateDebut) }} — {{ formaterDate(stage.dateFin) }}
                <button @click="modifierStages(stage)">Modifier</button>
                <button @click="supprimerStages(stage.id)">Supprimer</button>
            </li>
        </ul>
    </div>
</template>