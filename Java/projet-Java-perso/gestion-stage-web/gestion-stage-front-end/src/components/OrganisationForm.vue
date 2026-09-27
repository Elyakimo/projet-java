<script setup>
    import { ref, watch } from 'vue'

    const props = defineProps({
        organisationAModifier: {
            type: Object,
            default: null
        }
    })
    const emit = defineEmits(['created', 'updated'])

    const nomEntreprise = ref('')
    const mailEntreprise = ref('')
    const telephoneEntreprise = ref('')
    const adresseEntreprise = ref('')

    watch(() => props.organisationAModifier, (organisation) => {
        if (organisation) {
            nomEntreprise.value = organisation.nomEntreprise
            mailEntreprise.value = organisation.mailEntreprise
            telephoneEntreprise.value = organisation.telephoneEntreprise
            adresseEntreprise.value = organisation.adresseEntreprise
        } else {
            nomEntreprise.value = ''
            mailEntreprise.value = ''
            telephoneEntreprise.value = ''
            adresseEntreprise.value = ''
        }
    })

    async function soumettreFormulaire() {
        const donnees = {
            nomEntreprise: nomEntreprise.value,
            mailEntreprise: mailEntreprise.value,
            telephoneEntreprise: telephoneEntreprise.value,
            adresseEntreprise: adresseEntreprise.value
        }
    
      if (props.organisationAModifier) {
        await fetch(`http://localhost:8080/api/organisations/${props.organisationAModifier.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(donnees)
        })
        emit('updated')
    } else {
        await fetch('http://localhost:8080/api/organisations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(donnees)
        })
        emit('created')
      }

        nomEntreprise.value = ''
        mailEntreprise.value = ''
        telephoneEntreprise.value = ''
        adresseEntreprise.value = ''
    } 
</script>

<template>
  <form @submit.prevent="soumettreFormulaire">
    <input v-model="nomEntreprise" placeholder="Nom de l'entreprise" required id="nom-entreprise"/>
    <input v-model="mailEntreprise" placeholder="Mail" required id="mail-entreprise"/>
    <input v-model="telephoneEntreprise" placeholder="Téléphone" type="number" required id="telephone-entreprise"/>
    <input v-model="adresseEntreprise" placeholder="Adresse" required id="adresse-entreprise"/>
    <button type="submit">{{ organisationAModifier ? 'Modifier' : 'Créer' }}</button>
  </form>
</template>