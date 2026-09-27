<script setup>
    import { ref, onMounted, watch } from 'vue'

    const props = defineProps({
        tuteurAModifier: {
            type: Object,
            default: null
        }
    })
    
    const emit = defineEmits(['created', 'updated'])

    const nomTuteur = ref('')
    const prenomTuteur = ref('')
    const numeroTuteur = ref('')
    const organisationId = ref('')
    const organisations = ref([])

    async function chargerOrganisations() {
        const response = await fetch('http://localhost:8080/api/organisations')
        organisations.value = await response.json()
    }

    onMounted(() => {
        chargerOrganisations()
    })

    watch(() => props.tuteurAModifier, (tuteur) => {
        if (tuteur) {
            nomTuteur.value = tuteur.nomTuteur
            prenomTuteur.value = tuteur.prenomTuteur
            numeroTuteur.value = tuteur.numeroTuteur
            organisationId.value = tuteur.organisation.id
        } else {
            nomTuteur.value = ''
            prenomTuteur.value = ''
            numeroTuteur.value = ''
            organisationId.value = ''
        }
    })

    async function soumettreFormulaire() {
        const donnees = {
            nomTuteur: nomTuteur.value,
            prenomTuteur: prenomTuteur.value,
            numeroTuteur: numeroTuteur.value,
            organisation: { id: organisationId.value }
        }

        if (props.tuteurAModifier){
            await fetch(`http://localhost:8080/api/tuteurs/${props.tuteurAModifier.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(donnees)
            })
            emit('updated')
        } else {
            await fetch('http://localhost:8080/api/tuteurs', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(donnees)
            })
            emit('created')
        }
        nomTuteur.value = ''
        prenomTuteur.value = ''
        numeroTuteur.value = ''
        organisationId.value = ''
    }
    
</script>

<template>
  <form @submit.prevent="soumettreFormulaire">
    <input v-model="nomTuteur" placeholder="Nom du tuteur" required />
    <input v-model="prenomTuteur" placeholder="Prénom du tuteur" required />
    <input v-model="numeroTuteur" placeholder="Numéro" type="number" required />
    <select v-model="organisationId" required>
      <option value="" disabled>Choisir une organisation</option>
      <option v-for="organisation in organisations" :key="organisation.id" :value="organisation.id">
        {{ organisation.nomEntreprise }}
      </option>
    </select>
    <button type="submit">{{ tuteurAModifier ? 'Modifier' : 'Créer' }}</button>
  </form>
</template>