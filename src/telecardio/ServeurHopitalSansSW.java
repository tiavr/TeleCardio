package telecardio;

/* ServeurHopitalSansSW.java */


import java.util.HashMap;

/**
 * Systeme d'Information Hospitalier.
 * CETTE CLASSE NE DOIT PAS ÊTRE MODIFIÉE.
 * @author Francis JAMBON - Polytech'Grenoble
 * @version sans SW
 */
public class ServeurHopitalSansSW {

    /**
     * Serveur enregistrant les antécédents médicaux des patients.
     * La clé est le nom du patient (c-à-d son identificateur unique)
     * et on suppose donc qu'il n'y a pas d'homonymes.
     * L'enregistrement des données est commun toutes les instances.
     */
    private static HashMap<String, String> antecedents;

    /**
     * Constructeur.
     * Initialise le serveur et enregistre des antécédents
     * pour les patients "Dupont" et "Dupond".
     */
    public ServeurHopitalSansSW() {
        if (ServeurHopitalSansSW.antecedents==null) {
            ServeurHopitalSansSW.antecedents = new HashMap<>();
            ServeurHopitalSansSW.antecedents.put("Dupont",
                    formatterActe("Durand","Athérosclérose coronarienne sévère"));
            ServeurHopitalSansSW.antecedents.put("Dupond",
                    formatterActe("Martin","Arythmie cardiaque sporadique"));
        }
    }

    /**
     * Recherche les antécédents médicaux d'un patient.
     * @param patient le nom du patient
     * @return les antécédents du patient ou une chaîne de caractères vide
     * si le patient est inconnu
     */
    public String rechercherAntecedents(String patient) {
        if (ServeurHopitalSansSW.antecedents.containsKey(patient))
            return ServeurHopitalSansSW.antecedents.get(patient);
        else return "";
    }

    /**
     * Enregistre un (nouvel) acte médical pour un patient.
     * L'acte est ajouté aux antécédents médicaux du patient s'il existe.
     * Sinon, un nouveau dossier d'antécédents pour ce patient est créé.
     * @param patient le nom du patient
     * @param medecin le nom du médecin
     * @param acte l'acte médical
     * @return "DOSSIER ANTECEDENTS MIS A JOUR" si le patient existait ou
     *         "DOSSIER ANTECEDENTS CREE" si un nouveau dossier a été créé
     */
    public String enregistrerActe(String patient, String medecin, String acte) {
        if (ServeurHopitalSansSW.antecedents.containsKey(patient)) {
            String tmp = ServeurHopitalSansSW.antecedents.get(patient);
            tmp+= formatterActe(medecin,acte);
            ServeurHopitalSansSW.antecedents.put(patient, tmp);
            return "DOSSIER ANTECEDENTS MIS A JOUR";
        } else {
            String tmp = formatterActe(medecin,acte);
            ServeurHopitalSansSW.antecedents.put(patient, tmp);
            return "DOSSIER ANTECEDENTS CREE";
        }
    }
    
    /**
     * Liste l'ensemble des dossiers d'antécédents présents
     * sur le serveur de l'hopital.
     * ATTENTION : OPERATION A N'UTLISER QUE POUR LES TESTS.
     * @return l'ensemble des dossiers d'antécédents
     * sous forme de chaîne de caractères
     */
    public String listerDossiersAntecedents() {
        String resultat = new String();
        resultat = resultat.concat("LISTE DES DOSSIERS D'ANTECEDENTS : \r\n ");
        for (String patient : ServeurHopitalSansSW.antecedents.keySet()) {
            resultat = resultat.concat(" - "+patient+" : "+ServeurHopitalSansSW.antecedents.get(patient)+" \r\n ");
        }
        return resultat;
    }
    
    
    /**
     * Formattage d'un un acte medical.
     * Usage interne à la classe.
     * @param medecin le nom du médecin
     * @param acte l'acte médical
     * @return l'acte médical selon le format : "[jj/mm/aa] Dr. médecin : acte ; "
     */
    private static String formatterActe(String medecin, String acte) {
        String date = java.text.DateFormat.getDateInstance(
                java.text.DateFormat.SHORT,java.util.Locale.FRANCE).format(
                new java.util.Date());
        return "["+date+"] "+"Dr. "+medecin+" : "+acte+" ; ";
    }

}