/* ServeurHopitalSansSW.java */



import java.util.*;


/**
 * Serveur TeleCardio.
 */
public class ServeurTelecardiolSansSW {


    /**
     * Liste regroupant les listes : liste_dossiers , listeAttente  et listeTraite:
     */

    private ArrayList<DossierExpertise> listeCentralise;
    /**
     * Liste des dossiers déposé
     */
    private ArrayList<DossierExpertise> liste_dossiers;

    /**
     * Liste des dossiers en cours de traitement
     */
    private ArrayList<DossierExpertise> listeAttente;

    /**
     * Liste des dossiers traités
     */
    private ArrayList<DossierExpertise> listeTraite;

    /**
     * Constructeur.
     */
    public ServeurTelecardiolSansSW() {
        this.liste_dossiers = new ArrayList<>();
        this.listeAttente = new ArrayList<>();
        this.listeTraite = new ArrayList<>();
        this.listeCentralise = new ArrayList<>();
    }

    /**
     * Dépose d'un dossier de demande d'expertise.
     * @param patient nom du patient
     * @param generaliste nom du généraliste
     * @param signes signe cliniques
     * @param ecg ECG
     * @return numéro du dossier déposé
     */


    public int deposerDossier(String patient, String generaliste, String signes, List<Double> ecg) {
        DossierExpertise dp = new DossierExpertise(patient, generaliste, signes, ecg);
        this.liste_dossiers.add(dp);
        return dp.getNumero();
    }
    /**
     * Permet de lire l'avis du dossier d'expertise après avoir été traité par un spécialiste
     * @param numDossier numéro du dossier dont on désire lire l'avis
     * @return l'avis
     */
    public String lireAvis(int numDossier){
        String avis = listeTraite.get(numDossier).getAvis();
        return avis;
    }

    /**
     * Permet de récupérer l'ECG se trouvant dans le dossier voulu
     * @param numDossier numéro du dossier d'où on désire récupérer l'ECG
     * @return l'ECG
     */
    public List<Double> getEcg(int numDossier){
        List<Double> ecg = liste_dossiers.get(numDossier).getEcg();
        return ecg;
    }
    /**
     * Permet de récuper les informations relatives au spécialiste ayant traité le dossier
     * @param numDossier numéro du dossier traité par le spécialiste recherché
     * @return les informations relatives au spécialiste ayant traité le dossier
     */
    public String getSpecialiste(int numDossier){
        String specialiste = liste_dossiers.get(numDossier).getSpecialiste();
        return specialiste;
    }
    /**
     *Permet de récuper les informations relatives au généraliste ayant déposé le dossier
     * @param numDossier numéro du dossier déposé par le généraliste recherché
     * @return les informations relatives au généraliste ayant traité le dossier
     */
    public String getGeneraliste(int numDossier){
        String generaliste = liste_dossiers.get(numDossier).getGeneraliste();
        return generaliste;
    }

    /**
     *
     * Permet d'obtenir L'état du dossier dont le numéro est mis en argument
     * @param numDossier Numéro du dossier dont on veut avoir l'état
     * @return L'état du dossier
     */
    public String getEtat(int numDossier){
        listeCentralise.addAll(listeAttente);
        listeCentralise.addAll(listeTraite);
        listeCentralise.addAll(liste_dossiers);
        int etat = listeCentralise.get(numDossier).getEtat();
        var result =  switch (etat) {
            case 1 -> "Déposé";
            case 2 -> "En Attente";
            case 3 -> "Traité";
            default -> throw new IllegalStateException("Invalid etat: " + etat);
        };
        return result;
    }

    /**
     * Permet de récuperer les signes du dossier dont le numéro est mis en argument
     * @param numDossier Numéro du dossier
     * @return Les signes
     */
    public String getSignes(int numDossier) {
        String signes = liste_dossiers.get(numDossier).getSignes();
        return signes;
    }
    /**
     * Permet de récupérer les infos relatives au patient
     * @param numDossier numéro du dossier du patient
     * @return Informations relatives au patient
     */
    public String getPatient(int numDossier){
        String patient = liste_dossiers.get(numDossier).getPatient();
        return patient;
    }
    /**
     * Permet de récupérer le dossier traité
     * @param specialiste le spécialiste ayant traité le dossier
     * @return le numéro du dossier à récupérer
     */
    public int recupererDossier(String specialiste){
        DossierExpertise dossier = new DossierExpertise(getPatient(0), getGeneraliste(0), getSignes(0), getEcg(0));
        dossier.setEtat(2);
        dossier.setSpecialiste(specialiste);
        dossier.setNumero(liste_dossiers.get(0).getNumero());
        listeAttente.add(dossier);
        liste_dossiers.remove(liste_dossiers.get(0));
        return dossier.getNumero();
    }
    /**
     * Permet au spécialiste de déposer son avis
     * @param avis L'avis du spécialiste
     * @param numDossier le numéro du dossier traité
     */
    public void deposerAvis(String avis, int numDossier){
        for(DossierExpertise dossier : listeAttente){
            if(dossier.getNumero() == numDossier){
                dossier.setAvis(avis);
                dossier.setEtat(3);
                listeTraite.add(dossier);
                listeAttente.remove(dossier);
            }
        }
    }


}