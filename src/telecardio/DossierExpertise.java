package telecardio;

/* DossierExpertise.java */


import java.util.*;

/**
 * Dossier d'expertise pour la demande d'avis.
 * CETTE CLASSE PEUT ÊTRE MODIFIÉE.
 * @author Francis JAMBON - Polytech Grenoble
 * @version 1.2 Ajout du numéro aléatoire et calcul de l'état
 */
public class DossierExpertise {

    private static int dernier_numero_dossier = 0; // dernier numero de dossier utilise
    private int numero; // numero (unique) du dossier patient
    private final String patient; // nom du patient
    private final String generaliste; // nom du generaliste
    private final String signes; // signes cliniques
    private final List<Double> ecg; // enregistrement de l'ECG
    private String specialiste; // nom du specialiste (peut etre null)
    private String avis; // avis du specialiste (peut etre null)

    private int etat; // état du dossier


    /**
     * Construit un dossier d'expertise.
     * @param patient Le nom du patient
     * @param generaliste Le nom du generaliste
     * @param signes Les signes cliniques
     * @param ecg L'enregistrement de l'ECG
     */
    public DossierExpertise(String patient, String generaliste, String signes, List<Double> ecg) {
        int increment = 1+(int)(Math.random()*9); // incrément aléatoire entre 1 et 10
        DossierExpertise.dernier_numero_dossier=DossierExpertise.dernier_numero_dossier+increment;
        this.numero = DossierExpertise.dernier_numero_dossier;
        this.patient = patient;
        this.generaliste = generaliste;
        this.signes = signes;
        this.ecg = ecg;
        this.specialiste = null;
        this.avis = null;
        this.etat = 1;
    }

    /**
     * Accesseur.
     * @return le numero du dossier
     */
    public int getNumero() {
        return this.numero;
    }
    
    /**
     * Accesseur.
     * @return le nom du patient
     */
    public String getPatient() {
        return this.patient;
    }

    /**
     * Accesseur.
     * @return le nom du generaliste
     */
    public String getGeneraliste() {
        return this.generaliste;
    }

    /**
     * Accesseur.
     * @return les signes cliniques
     */
    public String getSignes() {
        return this.signes;
    }

    /**
     * Accesseur.
     * @return l'enregistrement de l'ECG
     */
    public List<Double> getEcg() {
        return this.ecg;
    }
        
    /**
     * Accesseur.
     * @return le nom du specialiste (peut etre null)
     */
    public String getSpecialiste() {
        return this.specialiste;
    }
    
    /**
     * Accesseur virtuel.
     * @return l'etat du dossier d'expertise : 1 pour depose,
     * 2 pour en cours d'expertise, et 3 pour avis renseigne
     */
    public int getEtat() {
        etat = 1;
        if (this.avis!=null) etat = 3;
        else if (this.specialiste!=null) etat=2;
        return etat;
    }

    /**
     * Accesseur.
     * @return l'avis du specialiste (peut etre null)
     */
    public String getAvis() {
        return this.avis;
    }

    /**
     * Modifieur.
     * @param specialiste le nom du specialiste
     */
    public void setSpecialiste(String specialiste) {
        this.specialiste = specialiste;
    }

    /**
     * Modifieur.
     * @param avis l'avis du specialiste
     */
    public void setAvis(String avis) {
        this.avis = avis;
    }

    /**
     * Modifieur
     * @param etat le nouvel état
     */
    public void setEtat(int etat) {this.etat = etat;}

    public void setNumero(int numero){
        this.numero = numero;
    }
    /**
     * Retourne une version texte du dossier
     * (avec pour l'ECG seulement le nombre d'enregistrements).
     * @return un resume du dossier patient
     */
    @Override
    public String toString() {
        String tmp = "{ ";
        tmp += "Dossier #" + this.numero + " | ";
        tmp += "Etat : " + this.getEtat() + " | ";
        tmp += "Patient : " + this.patient + " | ";
        tmp += "Generaliste : " + this.generaliste + " | ";
        tmp += "Signes : " + this.signes + " | ";
        tmp += "ECG : " + this.ecg.size() + " enregistrements" + " | ";
        tmp += "Specialiste : " + this.specialiste + " | ";
        tmp += "Avis : " + this.avis;
        tmp += " }";
        return tmp;
    }

}