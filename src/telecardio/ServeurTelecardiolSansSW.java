package telecardio;

/* ServeurHopitalSansSW.java */



import java.util.*;


/**
 * Serveur TeleCardio.
 */
public class ServeurTelecardiolSansSW {


    public ArrayList<DossierExpertise> getListeCentralise() {
        return listeCentralise;
    }

    public ArrayList<DossierExpertise> getListe_dossiers() {
        return liste_dossiers;
    }

    public ArrayList<DossierExpertise> getListeAttente() {
        return listeAttente;
    }

    public ArrayList<DossierExpertise> getListeTraite() {
        return listeTraite;
    }

    public HashMap<String, ArrayList<Integer>> getDicoRefus() {
        return dicoRefus;
    }

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
     *Dictionnaire comportant les spécialiste en clé et les numéros de dossier refusés en value
     */
    private HashMap<String, ArrayList<Integer>> dicoRefus;

    /**
     * Constructeur.
     */
    public ServeurTelecardiolSansSW() {
        this.liste_dossiers = new ArrayList<>();
        this.listeAttente = new ArrayList<>();
        this.listeTraite = new ArrayList<>();
        this.listeCentralise = new ArrayList<>();
        this.dicoRefus = new HashMap<>();
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
        ArrayList<DossierExpertise> newList = new ArrayList<>();
        try {
            for (DossierExpertise dossier : listeTraite){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
        if(newList.size() > 0){
            return newList.get(0).getAvis();
        }
        else{
            return "Pas d'avis enregistré";
        }
    }

    /**
     * Permet de récupérer l'ECG se trouvant dans le dossier voulu
     * @param numDossier numéro du dossier d'où on désire récupérer l'ECG
     * @return l'ECG
     */
    public List<Double> getEcg(int numDossier){
        this.listeCentralise = new ArrayList<>();
        listeCentralise.addAll(listeAttente);
        listeCentralise.addAll(listeTraite);
        listeCentralise.addAll(liste_dossiers);
        ArrayList<DossierExpertise> newList = new ArrayList<>();
        try {
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
        return newList.get(0).getEcg();
    }
    /**
     * Permet de récuper les informations relatives au spécialiste ayant traité le dossier, si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier numéro du dossier traité par le spécialiste recherché
     * @return les informations relatives au spécialiste ayant traité le dossier
     *
     */
    public String getSpecialiste(int numDossier){
        try {
            this.listeCentralise = new ArrayList<>();
            listeCentralise.addAll(listeAttente);
            listeCentralise.addAll(listeTraite);
            listeCentralise.addAll(liste_dossiers);
            ArrayList<DossierExpertise> newList = new ArrayList<>();
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
            return newList.get(0).getSpecialiste();
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }
    /**
     *Permet de récuper les informations relatives au généraliste ayant déposé le dossier,si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier numéro du dossier déposé par le généraliste recherché
     * @return les informations relatives au généraliste ayant traité le dossier
     */
    public String getGeneraliste(int numDossier){
        try {
            this.listeCentralise = new ArrayList<>();
            listeCentralise.addAll(listeAttente);
            listeCentralise.addAll(listeTraite);
            listeCentralise.addAll(liste_dossiers);
            ArrayList<DossierExpertise> newList = new ArrayList<>();
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
            return newList.get(0).getGeneraliste();
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * Permet d'obtenir L'état du dossier dont le numéro est mis en argument, si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier Numéro du dossier dont on veut avoir l'état
     * @return L'état du dossier
     */
    public String getEtat(int numDossier){

        try {
            this.listeCentralise = new ArrayList<>();
            listeCentralise.addAll(listeAttente);
            listeCentralise.addAll(listeTraite);
            listeCentralise.addAll(liste_dossiers);
            ArrayList<DossierExpertise> newList = new ArrayList<>();
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
            int etat = newList.get(0).getEtat();
            var result =  switch (etat) {
                case 1 -> "Déposé";
                case 2 -> "En Attente";
                case 3 -> "Traité";
                default -> throw new IllegalStateException("Invalid etat: " + etat);
            };
            return result;
        } catch (IllegalStateException e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de récuperer les signes du dossier dont le numéro est mis en argument,si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier Numéro du dossier
     * @return Les signes
     */
    public String getSignes(int numDossier) {
        try {
            this.listeCentralise = new ArrayList<>();
            listeCentralise.addAll(listeAttente);
            listeCentralise.addAll(listeTraite);
            listeCentralise.addAll(liste_dossiers);
            ArrayList<DossierExpertise> newList = new ArrayList<>();
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
            return newList.get(0).getSignes();
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe");
            throw new RuntimeException(e);
        }
    }
    /**
     * Permet de récupérer les infos relatives au patient dont le numéro de dossier est indiqué en argument, si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier numéro du dossier du patient
     * @return Informations relatives au patient
     */
    public String getPatient(int numDossier){
        try {
            this.listeCentralise = new ArrayList<>();
            listeCentralise.addAll(listeAttente);
            listeCentralise.addAll(listeTraite);
            listeCentralise.addAll(liste_dossiers);
            ArrayList<DossierExpertise> newList = new ArrayList<>();
            for (DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
            return newList.get(0).getPatient();
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }
    /**
     * Permet de proposer un dossier à un spécialiste, si celui existe déjà dans sa liste de refus le dossier suivant lui est attribué .si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param specialiste le spécialiste ayant traité le dossier
     * @param i L'indice permettant de récupérer un dossier dans la liste
     * @return le numéro du dossier à récupérer
     */
    public int recupererDossier(String specialiste, int i){
        try {

            DossierExpertise dossier = new DossierExpertise(liste_dossiers.get(i).getPatient(), liste_dossiers.get(i).getGeneraliste(), liste_dossiers.get(i).getSignes(), liste_dossiers.get(i).getEcg());
            dossier.setEtat(2);
            dossier.setSpecialiste(specialiste);
            if(!dicoRefus.containsKey(specialiste)){
                dicoRefus.put(specialiste, new ArrayList<>());
            }
            dossier.setNumero(liste_dossiers.get(i).getNumero());
            listeAttente.add(dossier);
            liste_dossiers.remove(liste_dossiers.get(i));
            for (Map.Entry<String, ArrayList<Integer>> entry : dicoRefus.entrySet()) {
                if(entry.getKey().equals(dossier.getSpecialiste())){
                    if(entry.getValue().contains(dossier.getNumero())){
                        return -1;
                    }
                }
            }
            return dossier.getNumero();
        } catch (Exception e) {
            return -2;
        }
    }
    /**
     * Permet au spécialiste de déposer son avis dans le dossier qu'il a traité
     * @param avis L'avis du spécialiste
     * @param numDossier le numéro du dossier traité
     */
    public void deposerAvis(String avis, int numDossier){
        ArrayList<DossierExpertise> newList = new ArrayList<>();
        ArrayList<DossierExpertise> toRemove = new ArrayList<>();
        try {
            for(DossierExpertise dossier : listeAttente){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                    toRemove.add(dossier);
                }
            }
            newList.get(0).setAvis(avis);
            newList.get(0).setEtat(3);
            listeTraite.add(newList.get(0));
            listeAttente.removeAll(toRemove);
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }



    public void refuserDossier(int numDossier){
        ArrayList<DossierExpertise> toRemove = new ArrayList<>();
        try {
            for(DossierExpertise dossier : listeAttente){
                if(dossier.getNumero() == numDossier){
                    for (Map.Entry<String, ArrayList<Integer>> entry : dicoRefus.entrySet()) {
                        if(entry.getKey().equals(dossier.getSpecialiste())){
                            if(!entry.getValue().contains(numDossier)){
                                entry.getValue().add(numDossier);
                            }

                        }
                    }
                }
                dossier.setEtat(1);
                dossier.setSpecialiste(null);
                toRemove.add(dossier);
                liste_dossiers.add(0,dossier);
            }

            listeAttente.removeAll(toRemove);
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de récupérer les antécédents du patient dont le numéro de dossier est mis en argument. si ce dossier n'exsite pas alors un message d'erreur s'affiche
     * @param numDossier
     * @return Les antécédents du patient
     */
    public String recupererDossierSIH(int numDossier){
        this.listeCentralise = new ArrayList<>();
        listeCentralise.addAll(listeAttente);
        listeCentralise.addAll(listeTraite);
        listeCentralise.addAll(liste_dossiers);
        ServeurHopitalSansSW sih = new ServeurHopitalSansSW();
        ArrayList<DossierExpertise> newList = new ArrayList<>();
        try {
            for(DossierExpertise dossier : listeCentralise){
                if(dossier.getNumero() == numDossier){
                    newList.add(dossier);
                }
            }
        } catch (Exception e) {
            System.out.println("Le numéro de dossier n'existe pas");
            throw new RuntimeException(e);
        }
        return sih.rechercherAntecedents(newList.get(0).getPatient());
    }
    /**
     * Permet de récupérer toutes les informations existantes dans le serveur
     * @return Les informations existantes dans le serveur
     */
    public String trace(){
        return liste_dossiers.toString() + "\n" + listeAttente.toString() + "\n" + listeTraite.toString() + "\n" + dicoRefus.toString();
    }

    public ArrayList<DossierExpertise> rechercheDossierNomGeneraliste(String nomGeneraliste){
        ArrayList<DossierExpertise> newList = new ArrayList<>();
        this.listeCentralise = new ArrayList<>();
        listeCentralise.addAll(listeAttente);
        listeCentralise.addAll(listeTraite);
        listeCentralise.addAll(liste_dossiers);
        try {
            for(DossierExpertise dossier : listeCentralise){
                if(dossier.getGeneraliste().equals(nomGeneraliste)){
                    newList.add(dossier);
                }
            }
        } catch (Exception e) {
            System.out.println("Aucun dossier à pour ce généraliste");
            throw new RuntimeException(e);
        }
        return newList;
    }

}