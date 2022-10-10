package telecardio;


public class Main {
    public static void main(String[] args) {
        DossierExpertise dossierExpertise = new DossierExpertise("Tiav", "Fadwa" , "Problème de coeur", SimulateurEcg.genererEcg(3));
        ServeurTelecardiolSansSW serveur = new ServeurTelecardiolSansSW();
        serveur.deposerDossier(dossierExpertise.getPatient(), dossierExpertise.getGeneraliste(), dossierExpertise.getSignes(), dossierExpertise.getEcg());
        new SpecialisteAccueil(serveur);
        /*System.out.println("Déposer dossier");
        int numDossier = serveur.deposerDossier(dossierExpertise.getPatient(), dossierExpertise.getGeneraliste(), dossierExpertise.getSignes(), dossierExpertise.getEcg());
        System.out.println(numDossier);
        System.out.println(serveur.getEtat(numDossier));
        System.out.println(serveur.trace());
        //Récupérer dossier
        System.out.println("Proposition dossier");
        int numDossierRecuperer = serveur.recupererDossier("Ouais");
        System.out.println(numDossierRecuperer);
        System.out.println(serveur.getEtat(numDossierRecuperer));
        System.out.println(serveur.trace());
        //Traiter dossier
        System.out.println("Traitement dossier");
        serveur.deposerAvis("Avis deposé", numDossierRecuperer);
        serveur.lireAvis(numDossierRecuperer);
        System.out.println(serveur.trace());
        //TEST REFUSER DOSSIER
        DossierExpertise dossierExpertise2 = new DossierExpertise("Toto", "Tata" , "Problème de rein", SimulateurEcg.genererEcg(3));
        int numDossier2 = serveur.deposerDossier(dossierExpertise2.getPatient(), dossierExpertise2.getGeneraliste(), dossierExpertise2.getSignes(), dossierExpertise2.getEcg());
        System.out.println(numDossier2);
        System.out.println(serveur.getEtat(numDossier2));
        System.out.println(serveur.trace());
        //Recupérer Dossier 2
        System.out.println("Proposition 2eme dossier");
        int numDossierRecuperer2 = serveur.recupererDossier("Watson");
        System.out.println(numDossierRecuperer2);
        System.out.println(serveur.getEtat(numDossierRecuperer2));
        System.out.println(serveur.trace());
        //Refuser dossier
        System.out.println("Refuser le dossier");
        serveur.refuserDossier(numDossierRecuperer2);
        System.out.println(serveur.trace());

        //Recuperer un dossier déjà proposé au spécialiste
        serveur.recupererDossier("Watson");*/
    }
}